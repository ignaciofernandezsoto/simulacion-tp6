package simulacion.modelo.resultado;

import java.text.DecimalFormat;

public class ImpresorDeResultados {

    public void imprimir(Resultado resultado) {

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("El Porcentaje de Tiempo Ocioso " + df.format(resultado.getPTO()) + "%");
        System.out.println("El Promedio de Espera en Cola es: " + df.format(resultado.getPEC()) + " segundos");
        System.out.println("El Promedio de Permanencia en el Sistema es: " + df.format(resultado.getPPS()) + " segundos");
        System.out.println("El Porcentaje de TimeOut es: " + df.format(resultado.getPT()) + "%");

    }

}
