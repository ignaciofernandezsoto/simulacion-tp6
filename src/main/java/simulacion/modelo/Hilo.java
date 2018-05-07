package simulacion.modelo;

public class Hilo {

	private static int HV = Simulacion.HV;
	private Integer TPS = HV;

	public Integer getTPS() {
		return TPS;
	}

	public void setTPS(Integer TPS) {
		this.TPS = TPS;
	}
}
