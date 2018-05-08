package simulacion;

import simulacion.modelo.Simulacion;

import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Bienvenido a la simulación de nuestra aplicación HTTP");

		System.out.println("Ingrese la cantidad de servidores que desea simular");
		Integer instancias = 8;

		System.out.println("Ingrese la cantidad de hilos por instancia que desea simular");
		Integer hilosPorInstancia = 500;

		System.out.println("Comenzando la simulacion de la aplicación HTTP con "
                + instancias + " servidores con " + hilosPorInstancia +" hilos por cada servidor");

		new Simulacion(instancias, hilosPorInstancia);
	}
	
}
