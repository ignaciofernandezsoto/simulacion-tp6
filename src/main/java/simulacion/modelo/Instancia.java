package simulacion.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Instancia {

	private static final int MAX_REQUESTS = 250;

	private Double cantRequests = 0D;
	private Double ITC = 0D;
	private Double ITO = 0D;
	private Double STO = 0D;
	private List<Hilo> hilos = new ArrayList<>();

	public Instancia(int cantHilos) {
		for (int i = 0; i < cantHilos; i++) {
			hilos.add(new Hilo());
		}
	}

	public void agregarRequest() {
		cantRequests ++;
	}

	public void restarRequest() {
		cantRequests --;
	}

	private Hilo getHiloMenorTPS(){
		Hilo hiloConMenorTPS = hilos.get(0);

		for(int i=1; i< hilos.size(); i++) {
			if(hiloConMenorTPS.getTPS() > hilos.get(i).getTPS())
				hiloConMenorTPS = hilos.get(i);
		}
		return hiloConMenorTPS;
	}

	public Double getMenorTPS() {
		return this.getHiloMenorTPS().getTPS();
	}

	public Double getRequests(){
		return cantRequests;
	}

	public void addTPS(Double tps){
		this.getHiloMenorTPS().setTPS(tps);
	}

	public void addSTO(Double tiempoActual) {
		STO += tiempoActual - ITO;
	}

	public void setITO(Double tiempo){
		ITO = tiempo;
	}

	public Double getSTO(){
		return STO;
	}

	public void setITC(Double itc){
		this.ITC = itc;
	}

	public Double getITC(){
		return ITC;
	}
	
}
