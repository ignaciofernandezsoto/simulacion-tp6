package simulacion.modelo.fdp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class FDP {
	
	public Integer obtenerValor() {

		double probabilidad = Math.random();

		double numeradorDelExponenteDeE = Math.log(probabilidad) - mu();
		double exponenteDeE = -Math.pow(numeradorDelExponenteDeE, 2) / (2 * Math.pow(sigma(), 2));
		double numerador = Math.pow(Math.E, exponenteDeE);
		double denominador = sigma() * probabilidad * Math.sqrt(2 * Math.PI);
		
		return new Double(numerador / denominador).intValue();
		
	}
	
	protected abstract double sigma();
	
	protected abstract double mu();
	
}
