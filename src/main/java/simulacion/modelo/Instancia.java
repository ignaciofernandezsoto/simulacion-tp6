package simulacion.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Instancia {

	private static final int MAX_REQUESTS = 250;

	private int cantRequests = 0;
	private Integer ITC = 0;
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
			if(hiloConMenorTPS.getTPS().compareTo(hilos.get(i).getTPS()) > 1)
				hiloConMenorTPS = hilos.get(i);
		}
		return hiloConMenorTPS;
	}

	public Integer getMenorTPS() {
		return this.getHiloMenorTPS().getTPS();
	}

	public int getRequests(){
		return cantRequests;
	}

	public void addTPS(Integer tps){
		this.getHiloMenorTPS().setTPS(tps);
	}

	public void setITC(Integer itc){
		this.ITC = itc;
	}

	public Integer getITC(){
		return ITC;
	}
	
}
