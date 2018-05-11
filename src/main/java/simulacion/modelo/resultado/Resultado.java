package simulacion.modelo.resultado;

import java.text.DecimalFormat;

public class Resultado {

    private Double PT;
    private Double PEC;
    private Double PPS;
    private Double PTO;

    public Resultado(Double PT, Double PEC, Double PPS, Double PTO) {
        this.PT = PT;
        this.PEC = PEC;
        this.PPS = PPS;
        this.PTO = PTO;
    }

    public void imprimir() {

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("El Porcentaje de Tiempo Ocioso " + df.format(this.getPTO()) + "%");
        System.out.println("El Promedio de Espera en Cola es: " + df.format(this.getPEC()) + " segundos");
        System.out.println("El Promedio de Permanencia en el Sistema es: " + df.format(this.getPPS()) + " segundos");
        System.out.println("El Porcentaje de TimeOut es: " + df.format(this.getPT()) + "%");
        
    }

    public Double getPT() {
        return PT;
    }

    public Double getPEC() {
        return PEC;
    }

    public Double getPPS() {
        return PPS;
    }

    public Double getPTO() {
        return PTO;
    }

}
