package simulacion.modelo;

import java.util.ArrayList;
import java.util.List;

import simulacion.modelo.fdp.FDP;
import simulacion.modelo.fdp.IntervaloEntreArribos;
import simulacion.modelo.fdp.TiempoDeAtencion;

public class Simulacion {

	private static final Integer tiempoFinal = 86400000;
	public static Integer HV = tiempoFinal*2;
	private static final int MAX_REQUESTS = 250;

	private FDP intervaloEntreArribos = new IntervaloEntreArribos();
	private FDP tiempoDeAtencion = new TiempoDeAtencion();

	private long STLL = 0;
	private long STO = 0;
	private long ITO = 0;
	private long TPLL = 0;
	private long STC = 0;
	private long STS = 0;
	private long T = 0;
	private long NT = 0;
	private long NTimeOut = 0;
	private int cantHilos;

	private List<Instancia> instancias = new ArrayList();

	public Simulacion(int cantInstancias, int cantHilosPorInstancia) {
		cantHilos = cantHilosPorInstancia;
		for (int i = 0; i < cantInstancias; i++) {
			instancias.add(new Instancia(cantHilosPorInstancia));
		}
		this.obtenerResultado();
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

		for(int i=0; i < instancias.size(); i++){
			if(instancia.getRequests() > instancias.get(i).getRequests())
				instancia = instancias.get(i);
		}
		return instancia;
	}

	public void simularLlegada() {
		NT ++;
		STLL += TPLL;
		T = TPLL;

		Integer IA = this.intervaloEntreArribos.obtenerValor();
		TPLL = T + IA;

		Instancia instMenorRequests = this.getInstanciaMenorNS();
		if(instMenorRequests.getRequests() >= MAX_REQUESTS) {
			NTimeOut++;
		}
		else{
			instMenorRequests.agregarRequest();
			if(instMenorRequests.getRequests() <= cantHilos) {
				int TA = tiempoDeAtencion.obtenerValor();
				instMenorRequests.addTPS(T + TA);
				STO += T - ITO;
			}
			else{
				instMenorRequests.setITC(T);
			}
		}
	}

	public void simularSalida() {
		Instancia instMenorTPS = this.getInstanciaMenorTPS();

		STC += T - instMenorTPS.getITC();
		T = instMenorTPS.getMenorTPS();
		STS += instMenorTPS.getMenorTPS(); // se podría con T pero para dejarlo "metódicamente" y hacerlo lindo
		instMenorTPS.restarRequest();

		if(instMenorTPS.getRequests() >= 1){
			Integer TA = tiempoDeAtencion.obtenerValor();
			instMenorTPS.addTPS(T + TA);
		}
		else{
			instMenorTPS.addTPS(HV);
			ITO = T;
		}

	}

	public void imprimirResultados() {

		Resultado resultado = new Resultado(
				NTimeOut * 100 / NT,
				STO * 100 / T,
				STC * 100 / T,
				(STS - STLL) / NT
		);

		System.out.println("Cantidad de Requests " + NT);
		System.out.println("El Porcentaje de Tiempo Ocioso es : " + resultado.PTO + "%");
		System.out.println("El Promedio de Espera en Cola es : " + resultado.PEC);
		System.out.println("El Promedio de permanencia en el sistema es : " + resultado.PPS);
		System.out.println("El Porcentaje de TimeOut es: " + resultado.PT + "%");

	}

	public void obtenerResultado() {

		while(T <= tiempoFinal) {

			simular();

		}

		vaciar();

		this.imprimirResultados();

	}

	private void vaciar() {

		TPLL = HV;

		while (hayQueVaciar())
			simular();

	}

	private boolean hayQueVaciar() {
		return instancias.stream().mapToInt(Instancia::getRequests).sum() == 0;
	}

	private void simular() {

		if(T % 5000 == 0)
			System.out.println("TIEMPO: " + T);

		long menorTPS = this.getInstanciaMenorTPS().getMenorTPS();

		if(TPLL <= menorTPS)
			this.simularLlegada();
		else
			this.simularSalida();

	}

}
