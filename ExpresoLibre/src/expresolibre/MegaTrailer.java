package expresolibre;

public class MegaTrailer extends Transporte {
								// IREP
	private double segCarga;	//> 0
	private double costoFijo;	//> 0
	private double costoComida;	//> 0
	
	public MegaTrailer(String patente, double peso, double vol, double costoKm, boolean tieneRefrigeracion, double segCarga , double costoFijo, double costoComida) {
		super(patente, peso, vol, costoKm);
		if (segCarga < 0 || costoFijo < 0 || costoComida < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.segCarga = segCarga;
		this.costoFijo = costoFijo;
		this.costoComida = costoComida;
		setFrio(tieneRefrigeracion);
	}

	@Override
	public double obtenerCostoViaje(int distancia) {
		double costo = 0;
		costo += distancia*getCostoKm();
		costo += costoFijo + costoComida + segCarga;
		return costo;
	}
	@Override
	public void asignarDestino(Destino destino) { 	//Tiene que poder dar excepciones
		if (destino.getDistancia() < 499) {
			throw new RuntimeException("Los MegaTrailers solo pueden hacer viajes a partir de los 500 Km. Km hasta el destino: " + destino.getDistancia());
		}		
		super.asignarDestino(destino);
	} 
	
	/*
	 * Getters y Setters
	 */
	
	public double getSegCarga() {
		return segCarga;
	}

	public double getCostoFijo() {
		return costoFijo;
	}

	public double getCostoComida() {
		return costoComida;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Caracteristicas Especifias: ");
		text.append("Vehiculo Tipo: Megatrailer, seguro de carga: " + getSegCarga());
		text.append(", costo fijo del viaje: " + getCostoFijo());
		text.append(", costo de comida del conductor: " + getCostoComida());
		
		text.append("\nCaracteristicas Generales del " + super.toString());
		return text.toString();
	}
	
	
}
