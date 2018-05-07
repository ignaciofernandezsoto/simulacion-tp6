package simulacion.fdp;

import java.math.BigDecimal;

public class TiempoDeAtencion extends FDP {

	@Override
	protected BigDecimal sigma() {
		return new BigDecimal(0.93806);
	}

	@Override
	protected BigDecimal mu() {
		return new BigDecimal(6.8707);
	}
	
}
