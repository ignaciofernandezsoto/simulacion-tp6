package simulacion.modelo.instancia;

import simulacion.modelo.Simulacion;

public class Hilo {

	private Double TPS = Simulacion.HV;

	public Double getTPS() {
		return TPS;
	}

	public void setTPS(Double TPS) {
		this.TPS = TPS;
	}
}
