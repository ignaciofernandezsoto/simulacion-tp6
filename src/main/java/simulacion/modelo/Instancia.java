package simulacion.modelo;

import java.util.ArrayList;
import java.util.List;

public class Instancia {

	private static final int MAX_REQUESTS = 250;

	private int cantRequests = 0;
	private List<Hilo> hilos = new ArrayList<>();

	public Instancia(int cantHilos) {
		for (int i = 0; i < cantHilos; i++) {
			hilos.add(new Hilo());
		}
	}

	public Integer getMenorTPS(){
		Integer menorTPS = hilos.get(0).getTPS();

		for(int i=1; i< hilos.size(); i++) {
			if(menorTPS > hilos.get(i).getTPS())
				menorTPS = hilos.get(i).getTPS();
		}

		return menorTPS;
	}

	public int getRequests(){
		return cantRequests;
	}


	
}
