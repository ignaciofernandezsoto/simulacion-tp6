package simulacion.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import simulacion.fdp.FDP;
import simulacion.fdp.IntervaloEntreArribos;
import simulacion.fdp.TiempoDeAtencion;

public class Simulacion {

	//TO DO: FIJARSE QUÉ TIEMPO FINAL PODRÍAMOS PONER DEBUDO A UN STACK OVERFLOW
	private static final BigDecimal tiempoFinal = new BigDecimal(5E-10);
	private static BigDecimal HV = new BigDecimal(86400000*2);
	private static final int MAX_REQUESTS = 250;

	private FDP intervaloEntreArribos = new IntervaloEntreArribos();
	private FDP tiempoDeAtencion = new TiempoDeAtencion();

	private BigDecimal STLL = new BigDecimal(0) ;
	private BigDecimal STO = new BigDecimal(0);
	private BigDecimal ITO = new BigDecimal(0);
	private BigDecimal TPLL = new BigDecimal(0);
	private BigDecimal STC = new BigDecimal(0);
	private BigDecimal STS = new BigDecimal(0);
	private BigDecimal T = new BigDecimal(0);
	private int NT = 0;
	private int NTimeOut = 0;
	private int cantHilos;

	private List<Instancia> instancias = new ArrayList();

	public Simulacion(int cantInstancias, int cantHilosPorInstancia) {
		cantHilos = cantHilosPorInstancia;
		for (int i = 0; i < cantInstancias; i++) {
			instancias.add(new Instancia(cantHilosPorInstancia));
		}
		this.simular();
	}

	private Instancia getInstanciaMenorTPS() {
		Instancia instMenorTPS = instancias.get(0);

		for(int i=1; i < instancias.size(); i++) {
			if(instMenorTPS.getMenorTPS().compareTo(instancias.get(i).getMenorTPS()) > 1)
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
		STLL = (STLL.add(TPLL));
		T = TPLL;

		BigDecimal IA = this.intervaloEntreArribos.obtenerValor(Math.random());
		TPLL = T.add(IA);

		Instancia instMenorRequests = this.getInstanciaMenorNS();
		if(instMenorRequests.getRequests() >= MAX_REQUESTS) {
			NTimeOut++;
		}
		else{
			instMenorRequests.agregarRequest();
			if(instMenorRequests.getRequests() <= cantHilos) {
				BigDecimal TA = tiempoDeAtencion.obtenerValor(Math.random());
				instMenorRequests.addTPS(T.add(TA));
				STO = (STO.add(T.subtract(ITO)));
			}
			else{
				instMenorRequests.setITC(T);
			}
		}
	}

	public void simularSalida() {
		Instancia instMenorTPS = this.getInstanciaMenorTPS();

		STC = STC.add(T.subtract(instMenorTPS.getITC()));
		T = instMenorTPS.getMenorTPS();
		STS = STC.add(instMenorTPS.getMenorTPS()); // se podría con T pero para dejarlo "metódicamente" y hacerlo lindo
		instMenorTPS.restarRequest();

		if(instMenorTPS.getRequests() >= 1){
			BigDecimal TA = tiempoDeAtencion.obtenerValor(Math.random());
			instMenorTPS.addTPS(T.add(TA));
		}
		else{
			instMenorTPS.addTPS(HV);
			ITO = T;
		}

	}

	public void imprimirResultados(Resultado resultado){
		System.out.println("Cantidad de Requests " + NT);
		System.out.println("El Porcentaje de Tiempo Ocioso es : " + resultado.PTO);
		System.out.println("El Promedio de Espera en Cola es : " + resultado.PEC);
		System.out.println("El Promedio de permanencia en el sistema es : " + resultado.PPS);
		System.out.println("El Porcentaje de TimeOut es: " + resultado.PT);
	}

	public void simular() {
		BigDecimal menorTPS = this.getInstanciaMenorTPS().getMenorTPS();
		if(TPLL.compareTo(menorTPS) == -1 || TPLL.compareTo(menorTPS) == 0) this.simularLlegada();
		else this.simularSalida();
		if(T.compareTo(tiempoFinal) == 1 || T.compareTo(tiempoFinal) == 0){
			if(instancias.stream().mapToInt(Instancia::getRequests).sum() == 0) {
				this.imprimirResultados(new Resultado(NTimeOut * 100 / NT,
						STO.multiply(new BigDecimal(100)).divide(T),
						STC.multiply(new BigDecimal(100)).divide(T),
						(STS.subtract(STLL)).divide(new BigDecimal(NT))));
				return;
			}
			else TPLL = HV;
		}
		this.simular();
	}

}
