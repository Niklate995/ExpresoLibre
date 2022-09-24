package expresolibre;

import java.util.ArrayList;

public abstract class Transporte {
										// IREP
	private String patente;				//!= null
	private double pesomax;				//> 0
	private double volmax;				//> 0
	private double costoKm;				//> 0
	private ArrayList<Paquete> paquetes;//!= null (puede ser conj vacio{})
	private Destino destino;			//!= null
	private boolean enViaje = false;	//
	private boolean frio = false;		//Por defecto, no llevan equipo de frio.
	
	/**
	 * Crea un transporte (abstracto)
	 * @param patente 	La patente del transporte.
	 * @param peso		El peso maximo del transporte.
	 * @param vol		El volumen maximo del transporte.
	 * @param costoKm	El costo de cada KM recorrido por el transporte.
	 */
	public Transporte (String patente, double peso, double vol, double costoKm) {
		if (peso < 0 || vol < 0 || costoKm < 0 || patente == null) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		this.patente = patente;
		this.pesomax = peso;
		this.volmax = vol;
		this.costoKm = costoKm;
		paquetes = new ArrayList<Paquete>();
	}
		
	public void asignarDestino(Destino destino) { 	//Tiene que poder dar excepciones
		this.destino = new Destino(destino.getNombre(), destino.getDistancia());
	} 
	
	/**
	 * Carga un paquete al Transporte, siempre y cuando el Transporte tenga el volumen suficiente para guardarlo.
	 * @param pak El paquete a guardar.
	 * @return el volumen ocupado por el paquete cargado.
	 */
	public double cargarPaquete(Paquete pak) {
		if (pak.isFrio() != isFrio()) {
			return 0;
		}
		if (pak.getVolumen() >= getVolumenLibre()) {
			return 0;
		}
		paquetes.add(pak);
		return pak.getVolumen();
	};
	
	/**
	 * Inicia el INCONDICIONALMENTE viaje del Transporte.
	 */
	public void iniciarViaje() {
		/*
		 *  Tal vez deberia agregar los condicionales aqui, pero el metodo iniciarViaje de Empresa
		 *  ya los tiene. Tal vez deberia moverlos aqui?
		 */
		this.enViaje = true;
	};
	
	/**
	 * Termina INCONDICIONALMENTE el viaje en curso del Transporte, limpiando su destino y vaciando todos sus paquetes.
	 */
	public void finalizarViaje() {
		this.paquetes = new ArrayList<Paquete>();
		this.enViaje = false;
		this.destino = null;
	};
	
	public abstract double obtenerCostoViaje (int distancia) throws Exception;	//Tiene que poder dar excepciones

	
	
	
	
	/*
	 * Getters y Setters
	 */
	/**
	 *
	 * @return Un ArrayList conteniendo todos los paquetes.
	 */
	public ArrayList<Paquete> getPaquetes() {
		return paquetes;
	}
	
	public double volumenOcupado() {
		return getVolmax() - getVolumenLibre();
	}
	
	public double pesoOcupado() {
		double temp = 0;
		for (Paquete pak : paquetes) {
			temp = temp + pak.getPeso();
		}
		return temp;
	}
	
	public String getPatente() {
		return patente;
	}

	public double getPesomax() {
		return pesomax;
	}

	public double getVolmax() {
		return volmax;
	}

	public double getCostoKm() {
		return costoKm;
	}

	public Destino getDestino() {
		return destino;
	}

	public double getVolumenLibre() {
		double vollibre = getVolmax();
		for (Paquete pak : paquetes) {
			vollibre = vollibre - pak.getVolumen();
		}
		return vollibre;
	}

	public boolean isFrio() {
		return frio;
	}

	protected void setFrio(boolean frio) {
		this.frio = frio;
	}
	
	public boolean isEnViaje() {
		return enViaje;
	}
		
	public String getPaquetesToString() {
		StringBuilder text = new StringBuilder();
		if (!paquetes.isEmpty()) {
			text.append("\n");
			for (Paquete pak : paquetes) {
				text.append("    " + pak.toString() + "\n");
			}
		}
		else 
			text.append(" Ninguno.");
		
		return text.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + (int) costoKm;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + (enViaje ? 1231 : 1237);
		result = prime * result + (frio ? 1231 : 1237);
		//result = prime * result + (int) pesomax;
		//result = prime * result + (int) volmax;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		//Busco otro transporte que sea "igual" al transporte que encontré.
		/* Son iguales si:
		 * Son el mismo tipo de transporte,
		 * Tienen el mismo destino y
		 * Llevan la misma carga de paquetes
		 */
		
		if (this == obj) {
			return true;
		}		
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Transporte)) {
			return false;
		}
		Transporte other = (Transporte) obj;

		// Compruebo si: Son el mismo tipo de transporte,
		if (!(getClass().getName() == other.getClass().getName())) {
			return false;
		}
		// Compruebo si: Existe nulidad en alguno de los destinos.
		if ((other.getDestino() == null) != (getDestino() == null)) {
			return false;
		}
		// Compruebo si: Tienen el mismo destino
		if (!(getDestino().getNombre() == other.getDestino().getNombre())) {
			return false;
		}
		// Compruebo si: Llevan la misma carga de paquetes.
		boolean cumpleExt = true;		//Acumulador externo
		for (Paquete pakPasada : getPaquetes()) {
			if (!cumpleExt) {
				continue;
			}
			
			boolean cumpleInt = false;	//Acumulador interno
			for (Paquete pakTentativo : other.getPaquetes()) {
				if (cumpleInt) {
					continue;
				}
				cumpleInt = cumpleInt || (pakPasada.equals(pakTentativo));
			}
			cumpleExt = cumpleExt && cumpleInt;
		}
		
		// Si se cumplen las 3, devuelvo la patente. 
		// Caso contrario, sigo tanteando vehiculos.
		if (cumpleExt) {
			return true;
		}
		
		return false;
	}


	
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("Transporte: DOM: " + getPatente());
		text.append(", volumen: " + volumenOcupado() + "/" + getVolmax());
		text.append(", peso: " + pesoOcupado() + "/" + getPesomax());
		text.append(", destino: ");
		
		if (getDestino() == null) text.append("Sin Definir (En espera)");
			else if (isEnViaje() == false) text.append(getDestino() + " (En espera)");
			else text.append(getDestino() + " (En Viaje)");
		
		text.append("\n  Paquetes cargados:");
		if (!paquetes.isEmpty()) {
			text.append("\n");
			for (Paquete pak : paquetes) {
				text.append("    " + pak.toString() + "\n");
			}
		}
		else 
			text.append(" Ninguno.");
		
		return text.toString();
	}
	
}
