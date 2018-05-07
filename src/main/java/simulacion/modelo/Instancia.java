package simulacion.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Instancia {

	private static final int MAX_REQUESTS = 250;

	private int cantRequests = 0;
	private long ITC = 0;
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

	public long getMenorTPS() {
		return this.getHiloMenorTPS().getTPS();
	}

	public int getRequests(){
		return cantRequests;
	}

	public void addTPS(long tps){
		this.getHiloMenorTPS().setTPS(tps);
	}

	public void setITC(long itc){
		this.ITC = itc;
	}

	public long getITC(){
		return ITC;
	}
	
}
