package simulacion.modelo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import simulacion.modelo.fdp.FDP;
import simulacion.modelo.fdp.IntervaloEntreArribos;
import simulacion.modelo.fdp.TiempoDeAtencion;

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
		this.obtenerResultado();
	}


	private void simular() {
		Double menorTPS = this.getInstanciaMenorTPS().getMenorTPS();

		if(TPLL <= menorTPS)
			this.simularLlegada();
		else
			this.simularSalida();
	}


	public void simularLlegada() {
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

	public void simularSalida() {
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

	public void imprimirResultados() {
		Resultado resultado = new Resultado(
				(NTimeOut/NT) * 100,
				(STSC - STLL) / (NT - NTimeOut),
				(STS - STLL) / (NT-NTimeOut)
		);

		DecimalFormat df = new DecimalFormat("#.##");

		System.out.println("Cantidad de Requests " + NT);
		/*for(int i = 0; i < instancias.size(); i++){
		System.out.println("El Porcentaje de Tiempo Ocioso para la Instancia "+i+" es "+ instancias.get(i).getPTO(TiempoActual) + "%");
		}*/

        System.out.println("El Porcentaje de Tiempo Ocioso " + df.format(getPromedioPTOs() / instancias.size()) + "%");


        System.out.println("El Promedio de Espera en Cola es: " + df.format(resultado.PEC) + " segundos");
		System.out.println("El Promedio de Permanencia en el Sistema es: " + df.format(resultado.PPS) + " segundos");
		System.out.println("El Porcentaje de TimeOut es: " + df.format(resultado.PT) + "%");
	}

	private Instancia getInstanciaMenorTPS() {
		Instancia instMenorTPS = instancias.get(0);

		for(int i=1; i < instancias.size(); i++) {
			if(instMenorTPS.getMenorTPS() > instancias.get(i).getMenorTPS())
				instMenorTPS = instancias.get(i);
		}
		return instMenorTPS;
	}

	private Double getPromedioPTOs(){
	    return 	instancias.stream().map(ins -> ins.getPTO(TiempoActual)).reduce(0D, (acum, ins) -> acum + ins);
    }

	private Instancia getInstanciaMenorNS() {
		Instancia instancia = instancias.get(0);

		for(int i=1; i < instancias.size(); i++){
			if(instancia.getRequests() > instancias.get(i).getRequests())
				instancia = instancias.get(i);
		}
		return instancia;
	}


	private void obtenerResultado() {

		while(TiempoActual < tiempoFinal)
			simular();

		vaciar();
		this.imprimirResultados();

	}


	private boolean hayQueVaciar() {
		return instancias.stream().mapToDouble(Instancia::getRequests).sum() > 0;
	}


}
