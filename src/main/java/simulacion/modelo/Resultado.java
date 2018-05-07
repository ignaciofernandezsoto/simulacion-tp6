package simulacion.modelo;

import java.math.BigDecimal;

public class Resultado {

    public Integer PT;
    public BigDecimal PTO;
    public BigDecimal PEC;
    public BigDecimal PPS;

    public Resultado(Integer PT, BigDecimal PTO, BigDecimal PEC, BigDecimal PPS) {
        this.PT = PT;
        this.PTO = PTO;
        this.PEC = PEC;
        this.PPS = PPS;
    }
}
