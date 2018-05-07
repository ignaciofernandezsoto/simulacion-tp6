package simulacion.modelo.fdp;

import java.math.BigDecimal;

public class TiempoDeAtencion extends FDP {

	@Override
	protected double sigma() {
		return 0.93806;
	}

	@Override
	protected double mu() {
		return 6.8707;
	}
	
}
