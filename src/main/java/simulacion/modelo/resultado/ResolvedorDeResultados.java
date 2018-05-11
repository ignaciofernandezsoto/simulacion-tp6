package simulacion.modelo.resultado;

import java.util.List;

public class ResolvedorDeResultados {

    private Double NT;
    private Double NTimeOut;
    private Double STSC;
    private Double STLL;
    private Double STS;
    private List<Double> PTOs;

    private Integer instancias;

    public ResolvedorDeResultados(Double NT, Double NTimeOut, Double STSC, Double STLL,
                                  Double STS, List<Double> PTOs, Integer instancias) {

        this.NT = NT;
        this.NTimeOut = NTimeOut;
        this.STSC = STSC;
        this.STLL = STLL;
        this.STS = STS;
        this.PTOs = PTOs;
        this.instancias = instancias;

    }

    public Resultado resolver() {

        Double PT   = (NTimeOut/NT) * 100;
        Double PEC  = (STSC - STLL) / (NT - NTimeOut);
        Double PPS  = (STS - STLL) / (NT-NTimeOut);
        Double PTO  = PTOs.stream().reduce(0D, (acum, ins) -> acum + ins) / instancias;

        return new Resultado(
                PT,
                PEC,
                PPS,
                PTO
        );

    }

}
