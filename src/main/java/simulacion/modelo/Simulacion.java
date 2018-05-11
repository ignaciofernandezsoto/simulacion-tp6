package simulacion.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import simulacion.modelo.fdp.FDP;
import simulacion.modelo.fdp.IntervaloEntreArribos;
import simulacion.modelo.fdp.TiempoDeAtencion;
import simulacion.modelo.resultado.ResolvedorDeResultados;
import simulacion.modelo.resultado.Resultado;

public class Simulacion {

	private static final Double DURACION_DE_UN_DIA_SEGUNDOS = 86400D;

	private static final Double tiempoFinal = DURACION_DE_UN_DIA_SEGUNDOS;
	public static Double HV = tiempoFinal * 999;
	private static final int MAX_REQUESTS = 250;

	private FDP intervaloEntreArribos = new IntervaloEntreArribos();
	private FDP tiempoDeAtencion = new TiempoDeAtencion();

	private Double STLL = 0D;
	private Double STSC = 0D;
	private Double TPLL = 0D;
	private Double STS = 0D;
	private Double TiempoActual = 0D;
	private Double NT = 0D;
	private Double NTimeOut = 0D;
	private int cantHilos;

	private List<Instancia> instancias = new ArrayList();

	public Simulacion(int cantInstancias, int cantHilosPorInstancia) {

		cantHilos = cantHilosPorInstancia;

		for (int i = 0; i < cantInstancias; i++) {
			instancias.add(new Instancia(cantHilosPorInstancia));
		}

	}

	public Resultado obtenerResultado() {

		while(TiempoActual < tiempoFinal)
			simular();

		vaciar();

		return this.construirResultados();

	}

	private void simular() {
		Double menorTPS = this.getInstanciaMenorTPS().getMenorTPS();

		if(TPLL <= menorTPS)
			this.simularLlegada();
		else
			this.simularSalida();
	}


	private void simularLlegada() {
		NT ++;
		TiempoActual = TPLL;

		Double TPLLAux = TPLL;

		Double IA = this.intervaloEntreArribos.obtenerValor();
		TPLL = TiempoActual + IA;

		Instancia instMenorRequests = this.getInstanciaMenorNS();
		if(instMenorRequests.getRequests() > (MAX_REQUESTS + cantHilos)) {
			NTimeOut++;
		}
		else{
			STLL += TPLLAux;

			instMenorRequests.agregarRequest();
			if(instMenorRequests.getRequests() <= cantHilos) {
				if(instMenorRequests.getRequests() == 1)
					instMenorRequests.addSTO(TiempoActual);
				Double TA = tiempoDeAtencion.obtenerValor();
				instMenorRequests.llegada(TiempoActual + TA);
			}
		}
	}

	private void simularSalida() {
		Instancia instMenorTPS = this.getInstanciaMenorTPS();

		//instMenorTPS.addSTC(TiempoActual);
		STSC+=TiempoActual;
		TiempoActual = instMenorTPS.getMenorTPS();
		STS += instMenorTPS.getMenorTPS(); // se podría con TiempoActual pero para dejarlo "metódicamente" y hacerlo lindo
		instMenorTPS.restarRequest();

		if(instMenorTPS.getRequests() >= cantHilos){
			Double TA = tiempoDeAtencion.obtenerValor();
			instMenorTPS.addTPS(TiempoActual + TA);
		}
		else{
			instMenorTPS.addTPS(HV);
			instMenorTPS.setITO(TiempoActual);
		}
	}


	private void vaciar() {
		TPLL = HV;
		while (hayQueVaciar())
			simular();
	}

	private Resultado construirResultados() {

        return new ResolvedorDeResultados(
                NT,
                NTimeOut,
                STSC,
                STLL,
                STS,
                instancias
                        .stream()
                        .map(ins -> ins.getPTO(TiempoActual))
                        .collect(Collectors.toList()),
                instancias.size()
        ).resolver();

	}

	private Instancia getInstanciaMenorTPS() {
		Instancia instMenorTPS = instancias.get(0);

		for(int i=1; i < instancias.size(); i++) {
			if(instMenorTPS.getMenorTPS() > instancias.get(i).getMenorTPS())
				instMenorTPS = instancias.get(i);
		}
		return instMenorTPS;
	}

	private Instancia getInstanciaMenorNS() {
		Instancia instancia = instancias.get(0);

		for(int i=1; i < instancias.size(); i++){
			if(instancia.getRequests() > instancias.get(i).getRequests())
				instancia = instancias.get(i);
		}
		return instancia;
	}

	private boolean hayQueVaciar() {
		return instancias.stream().mapToDouble(Instancia::getRequests).sum() > 0;
	}

}