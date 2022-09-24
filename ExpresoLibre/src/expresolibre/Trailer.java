package expresolibre;

public class Trailer extends Transporte {
								// IREP
	private double segCarga;	//> 0
	
	public Trailer(String patente, float peso, float vol, float costoKm, boolean tieneRefrigeracion, double segCarga) {
		super(patente, peso, vol, costoKm);
		setFrio(tieneRefrigeracion);
		if (segCarga < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.segCarga = segCarga;
	}

	@Override
	public double obtenerCostoViaje(int distancia) throws Exception {
		double costo = 0;
		costo += distancia*getCostoKm();
		costo += segCarga;
		return costo;
	}

	@Override
	public void asignarDestino(Destino destino) { 	//Tiene que poder dar excepciones
		if (destino.getDistancia() > 500) {
			throw new RuntimeException("Los Trailers solo pueden viajar un maximo de 500 Km. Km hasta el destino: " + destino.getDistancia());
		}		
		super.asignarDestino(destino);
	} 
	
	public double getSegCarga() {
		return segCarga;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Caracteristicas Especifias: ");
		text.append("Vehiculo Tipo: Trailer, seguro de carga: " + getSegCarga());
		
		text.append("\nCaracteristicas Generales del " + super.toString());
		return text.toString();
	}
	
	

}
