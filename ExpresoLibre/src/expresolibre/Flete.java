package expresolibre;

public class Flete extends Transporte {
									// IREP
	private int acompa�antes;		//>= 0
	private float costoacompa�antes;//>= 0
	
	public Flete(String patente, float peso, float vol, float costoKm, int acompa�antes, float costoacomp) {
		super(patente, peso, vol, costoKm);
		if (acompa�antes < 0 || costoacompa�antes < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.acompa�antes = acompa�antes;
		this.costoacompa�antes = costoacomp;
		//setFrio(false); // Ya Transporte lo settea como false.
	}


	/**
	 * Devuelve el costo del viaje
	 * 
	 */
	@Override
	public double obtenerCostoViaje(int distancia) {
		double costo = 0;
		costo += distancia*getCostoKm();
		costo += acompa�antes*costoacompa�antes;
		return costo;
	}

	
	/*
	 * Getters y Setters
	 */
	
	public int getAcompa�antes() {
		return acompa�antes;
	}


	public float getCostoacompa�antes() {
		return costoacompa�antes;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Caracteristicas Especifias: ");
		text.append("Vehiculo Tipo: Flete, acompa�antes: " + getAcompa�antes());
		text.append(", costo por cada acompa�ante: " + getCostoacompa�antes());
		
		text.append("\nCaracteristicas Generales del " + super.toString());
		return text.toString();
	}

	

}
