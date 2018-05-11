package simulacion.modelo.resultado;

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
