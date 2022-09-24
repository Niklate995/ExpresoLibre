package expresolibre;

public class Paquete {
							// IREP
	private String destino;	//!= null
	private double volumen;	//> 0
	private double peso;	//> 0
	private boolean frio;	//
	
	/**
	 * Crea un paquete
	 * @param destino al que debe ser llevado
	 * @param volumen que ocupa el paquete
	 * @param peso del paquete
	 * @param frio Si necesita o no refrigeracion
	 */
	public Paquete(String destino, double volumen, double peso, boolean frio) {
		if (destino == null || volumen < 0 || peso < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.destino = destino;
		this.volumen = volumen;
		this.peso = peso;
		this.frio = frio;
	}

	/*
	 * Getters y Setters
	 */
	public String getDestino() {
		return destino;
	}
	public double getVolumen() {
		return volumen;
	}
	public double getPeso() {
		return peso;
	}
	public boolean isFrio() {
		return frio;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destino.hashCode();
		result = prime * result + (frio ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(volumen);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Paquete)) {
			return false;
		}
		Paquete other = (Paquete) obj;
		
		boolean verificar = true;
		verificar = verificar && (getDestino() == other.getDestino());
		verificar = verificar && (getVolumen() == other.getVolumen());
		verificar = verificar && (getPeso() == other.getPeso());
		verificar = verificar && (isFrio() == other.isFrio());
		
		return verificar;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Volumen de: " + getVolumen());
		text.append(", Peso de: " + getPeso());
		text.append(", Con a destino: "+ getDestino() +",");
		if (!isFrio()) text.append(" NO");
		text.append(" REQUIERE frio.");
		text.append(" - Hashcode: ");
		text.append(hashCode());
		return text.toString();
	}
	
}
