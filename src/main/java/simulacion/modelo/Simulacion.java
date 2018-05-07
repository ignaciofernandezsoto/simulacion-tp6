package simulacion.modelo;

import java.util.ArrayList;
import java.util.List;

import simulacion.fdp.FDP;
import simulacion.fdp.IntervaloEntreArribos;
import simulacion.fdp.TiempoDeAtencion;

public class Simulacion {

	private static final int milisegundosDeSimulacion = 86400000;
	private static int HV = 86400000*2;

	private FDP intervaloEntreArribos = new IntervaloEntreArribos();
	private FDP tiempoDeAtencion = new TiempoDeAtencion();

	private Integer STLL = 0;
	private Integer TPLL = 0;
	private Integer T = 0;
	private int NT = 0;

	private List<Instancia> instancias = new ArrayList();

	public Simulacion(int cantInstancias, int cantHilosPorInstancia) {
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
		NT += 1;
		STLL += TPLL;
		T = TPLL;

		Integer IA = this.intervaloEntreArribos.getIntervalo();
		TPLL = T + IA;

		Instancia instMenorRequests = this.getInstanciaConMenorCantRequests();


	}

	public void simularSalida() {

	}

	public Resultado simular() {
		Integer menorTPS = this.getMenorTPS();
		if(TPLL <= menorTPS) this.simularLlegada();
		else this.simularSalida();

	}

}
