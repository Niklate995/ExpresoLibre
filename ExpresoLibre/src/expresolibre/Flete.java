package expresolibre;

public class Flete extends Transporte {
									// IREP
	private int acompañantes;		//>= 0
	private float costoacompañantes;//>= 0
	
	public Flete(String patente, float peso, float vol, float costoKm, int acompañantes, float costoacomp) {
		super(patente, peso, vol, costoKm);
		if (acompañantes < 0 || costoacompañantes < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.acompañantes = acompañantes;
		this.costoacompañantes = costoacomp;
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
		costo += acompañantes*costoacompañantes;
		return costo;
	}

	
	/*
	 * Getters y Setters
	 */
	
	public int getAcompañantes() {
		return acompañantes;
	}


	public float getCostoacompañantes() {
		return costoacompañantes;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Caracteristicas Especifias: ");
		text.append("Vehiculo Tipo: Flete, acompañantes: " + getAcompañantes());
		text.append(", costo por cada acompañante: " + getCostoacompañantes());
		
		text.append("\nCaracteristicas Generales del " + super.toString());
		return text.toString();
	}

	

}
