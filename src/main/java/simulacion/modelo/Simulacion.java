package simulacion.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import simulacion.fdp.FDP;
import simulacion.fdp.IntervaloEntreArribos;
import simulacion.fdp.TiempoDeAtencion;

public class Simulacion {

	private static final int milisegundosDeSimulacion = 86400000;
	private static int HV = 86400000*2;
	private static final int MAX_REQUESTS = 250;

	private FDP intervaloEntreArribos = new IntervaloEntreArribos();
	private FDP tiempoDeAtencion = new TiempoDeAtencion();

	private Integer STLL = 0;
	private Integer TPLL = 0;
	private Integer T = 0;
	private int NT = 0;
	private int NT_TimeOut = 0;
	private int cantHilos;

	private List<Instancia> instancias = new ArrayList();

	public Simulacion(int cantInstancias, int cantHilosPorInstancia) {
		cantHilos = cantHilosPorInstancia;
		for (int i = 0; i < cantInstancias; i++) {
			instancias.add(new Instancia(cantHilosPorInstancia));
		}
	}

	private Integer getMenorTPS() {
		Integer menorTPS = instancias.get(0).getMenorTPS();

		for(int i=1; i < instancias.size(); i++) {
			if(menorTPS > instancias.get(i).getMenorTPS())
				menorTPS = instancias.get(i).getMenorTPS()
		}

		return menorTPS;
	}

	private Instancia getInstanciaConMenorCantRequests() {
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

		BigDecimal IA = this.intervaloEntreArribos.obtenerValor(Math.random());
		TPLL = T + IA.intValueExact();

		Instancia instMenorRequests = this.getInstanciaConMenorCantRequests();
		if(instMenorRequests.getRequests() <= MAX_REQUESTS) {
			NT_TimeOut ++;
			this.simular();
		}
		else{
			instMenorRequests.addRequest();
			if(instMenorRequests.getRequests() <= cantHilos) {
				BigDecimal TA = tiempoDeAtencion.obtenerValor(Math.random());
				instMenorRequests.
			}
		}

	}

	public void simularSalida() {

	}

	public Resultado simular() {
		Integer menorTPS = this.getMenorTPS();
		if(TPLL <= menorTPS) this.simularLlegada();
		else this.simularSalida();

	}

}
