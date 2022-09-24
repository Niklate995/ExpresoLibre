package expresolibre;

public class Destino {
							// IREP
	private String destino;	//!= null
	private int distancia;	//> 0
	
	/**
	 * 
	 * @param destino	Nombre del destino. 
	 * @param distancia	Distancia desde los depositos hasta el destino
	 */
	public Destino (String destino, int distancia) {
		if (destino == null || distancia < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		
		this.destino = destino;
		this.distancia = distancia;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + distancia;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Destino)) {
			return false;
		}
		Destino other = (Destino) obj;

		return getNombre() == other.getNombre() 
				&& getDistancia() == other.getDistancia();
	}


	/*
	 * Getters y Setters
	 */
	public String getNombre() {
		return destino;
	}
	public int getDistancia() {
		return distancia;
	}
	
	@Override
	public String toString() {
		return getNombre() + ", distancia: " + getDistancia();
	}
		
}
