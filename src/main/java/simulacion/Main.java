package simulacion;

import simulacion.modelo.resultado.Resultado;
import simulacion.modelo.Simulacion;

import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Bienvenido a la simulación de nuestra aplicación HTTP");

		System.out.println("Ingrese la cantidad de servidores que desea simular");
		Integer instancias = 1;

		System.out.println("Ingrese la cantidad de hilos por instancia que desea simular");
		Integer hilosPorInstancia = 2;

		System.out.println("Comenzando la simulacion de la aplicación HTTP con "
                + instancias + " servidores y " + hilosPorInstancia +" hilos por cada servidor");

		Simulacion simulacion = new Simulacion(instancias, hilosPorInstancia);

		Resultado resultado = simulacion.obtenerResultado();



		simulacion.

	}
	
}
