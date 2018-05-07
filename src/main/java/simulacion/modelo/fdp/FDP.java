package simulacion.modelo.fdp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class FDP {
	
	public Integer obtenerValor() {


		double probabilidad = Math.random();

		System.out.println("Probabilidad de " + probabilidad);

		double numeradorDelExponenteDeE = Math.log(probabilidad) - mu().doubleValue();
		double exponenteDeE = -Math.pow(numeradorDelExponenteDeE, 2) / (2 * Math.pow(sigma().doubleValue(), 2));
		BigDecimal numerador = new BigDecimal(Math.pow(Math.E, exponenteDeE));
		BigDecimal denominador = sigma().multiply(new BigDecimal(probabilidad)).multiply(new BigDecimal(Math.sqrt(2 * Math.PI)));
		
		return numerador.divide(denominador, 50, RoundingMode.HALF_UP).intValue();
		
	}
	
	protected abstract BigDecimal sigma();
	
	protected abstract BigDecimal mu();
	
}
