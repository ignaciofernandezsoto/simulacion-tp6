package simulacion;

import simulacion.modelo.Simulacion;

import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Bienvenido a la simulación de nuestra aplicación HTTP");

		System.out.println("Ingrese la cantidad de servidores que desea simular");
		Integer instancias = sc.nextInt();

		System.out.println("Ingrese la cantidad de hilos por instancia que desea simular");
		Integer hilosPorInstancia = sc.nextInt();

		System.out.println("Comenzando con la simulacion de aplicación HTTP con "
                + instancias + "servidores con " + hilosPorInstancia +" hilos por cada servidor");

		Simulacion simulacion = new Simulacion(instancias, hilosPorInstancia);
		simulacion.simular();



		
	}
	
}
