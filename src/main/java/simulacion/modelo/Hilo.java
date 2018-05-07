package simulacion.modelo;

import java.math.BigDecimal;

public class Hilo {

	private static int HV = 86400000*2;
	private BigDecimal TPS = new BigDecimal(HV);

	public BigDecimal getTPS() {
		return TPS;
	}

	public void setTPS(BigDecimal TPS) {
		this.TPS = TPS;
	}
}
