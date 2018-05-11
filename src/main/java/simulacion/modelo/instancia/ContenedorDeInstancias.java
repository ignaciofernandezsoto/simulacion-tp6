package simulacion.modelo.instancia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContenedorDeInstancias {

    private List<Instancia> instancias;

    public ContenedorDeInstancias(Integer cantidadDeInstancias, Integer cantidadDeHilos) {

        this.instancias = new ArrayList<>();

        for (int i = 0; i < cantidadDeInstancias; i++)
            this.instancias.add(new Instancia(cantidadDeHilos));

    }

    public Instancia getInstanciaMenorTPS() {
        Instancia instMenorTPS = instancias.get(0);

        for(int i=1; i < instancias.size(); i++) {
            if(instMenorTPS.getMenorTPS() > instancias.get(i).getMenorTPS())
                instMenorTPS = instancias.get(i);
        }
        return instMenorTPS;
    }

    public Instancia getInstanciaMenorNS() {
        Instancia instancia = instancias.get(0);

        for(int i=1; i < instancias.size(); i++){
            if(instancia.getRequests() > instancias.get(i).getRequests())
                instancia = instancias.get(i);
        }
        return instancia;
    }

    public boolean hayQueVaciar() {
        return instancias.stream().mapToDouble(Instancia::getRequests).sum() > 0;
    }

    public Integer cantidadDeInstancias() {

        return this.instancias.size();

    }

    public List<Double> obtenerPTOs(Double tiempoActual) {

        return this.instancias
                .stream()
                .map(ins -> ins.getPTO(tiempoActual))
                .collect(Collectors.toList());

    }

}
