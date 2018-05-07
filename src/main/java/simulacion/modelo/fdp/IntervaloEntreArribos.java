package simulacion.modelo.fdp;

import java.math.BigDecimal;

public class IntervaloEntreArribos extends FDP {

	@Override
	protected BigDecimal sigma() {
		return new BigDecimal(0.71553);
	}

	@Override
	protected BigDecimal mu() {
		return new BigDecimal(6.3325);
	}

}
