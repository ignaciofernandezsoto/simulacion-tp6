package simulacion.modelo.fdp;

public abstract class FDP {
	
	public Double obtenerValor() {

		Double r = Math.random();

		return Math.sqrt((r + C()) / medioM());

	}
	
	protected abstract Double C();
	
	protected abstract Double medioM();
	
}
