package simulacion.fdp;

import java.math.BigDecimal;

public abstract class FDP {
	
	public BigDecimal obtenerValor(double probabilidad) {
				
		double exponenteDeE = -Math.pow((Math.log(probabilidad) - mu().doubleValue()), 2) / (2 * Math.pow(sigma().doubleValue(), 2));
		BigDecimal numerador = new BigDecimal(Math.pow(Math.E, exponenteDeE));
		BigDecimal denominador = sigma().multiply(new BigDecimal(probabilidad)).multiply(new BigDecimal(Math.sqrt(2 * Math.PI)));
		
		return numerador.divide(denominador);
		
	}
	
	protected abstract BigDecimal sigma();
	
	protected abstract BigDecimal mu();
	
}
