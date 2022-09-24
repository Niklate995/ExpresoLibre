package expresolibre;

import java.util.ArrayList;
import java.util.HashMap;

public class Deposito {
															// IREP
	private HashMap<String, ArrayList<Paquete>> paquetes;	// != null (puede ser conj vacio{})
	private boolean frio;									// False = Temp ambiente. True = Frio.
	private double capacidad;								// > 0

	/**
	 * Crea un deposito.
	 * @param capacidad del deposito. No puede almacenar un volumen mayor de paquetes que este.
	 * @param frio Si admite o no paquetes congelados.
	 */
	public Deposito (float capacidad, boolean frio) {
		if (capacidad < 0) {
			throw new RuntimeException("Error en la carga de valores. Valores prohibidos");
		}
		
		this.capacidad = capacidad;
		this.frio = frio;
		paquetes = new HashMap<String, ArrayList<Paquete>>();
	}
	
	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion){
		if (necesitaRefrigeracion != frio) {
			return false;
		}
		Paquete temp = new Paquete(destino, volumen, peso, necesitaRefrigeracion);
		
		//Inicializa por 1ra vez
		if (paquetes.get(destino)== null) {
			ArrayList<Paquete> helper = new ArrayList<Paquete>();
			paquetes.put(destino, helper);
		}
		
		if (temp.getVolumen()> getCapacidad() - getCapacidadOcupada()) {
			return false;
		}
		
		return paquetes.get(destino).add(temp);
	}
	
	
	/*
	 * Getters y Settes
	 */
	/**
	 * Devuelve TODOS los paquetes.
	 * @return PAQUETES
	 */
	public HashMap<String, ArrayList<Paquete>> getPaquetes() {
		return paquetes;
	}
	
	public String getPaquetesToString() {
		StringBuilder text = new StringBuilder();
		for (HashMap.Entry<String, ArrayList<Paquete>> pak : paquetes.entrySet()) {
	        for (Paquete paketito : pak.getValue()) {
	        	text.append(paketito.toString() + "\n");
	        }; 
	    }
		return text.toString();
	}
	/**
	 * Devuelve TODOS los paquetes con un destino especifico.
	 * @param destino al cual deben ir los paquetes
	 * @return PAQUETES
	 */
	public ArrayList<Paquete> getPaquetesDestino (String destino) {
		return paquetes.get(destino);
	}
	
	public boolean isFrio() {
		return frio;
	}

	public double getCapacidad() {
		return capacidad;
	}
	
	public double getCapacidadOcupada() {
		double temp = 0;
		for (HashMap.Entry<String, ArrayList<Paquete>> pak : paquetes.entrySet()) {
	        for (Paquete paketito : pak.getValue()) {
	        	temp = temp + paketito.getVolumen();
	        }; 
	    }
		return temp;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		if (isFrio())
			text.append("  Deposito para Paquetes refrigerados (capacidad utilizada: " + getCapacidadOcupada() + "/" + getCapacidad() + "):\n");
		else 
			text.append("  Deposito para Paquetes sin refrigerar (capacidad utilizada: " + getCapacidadOcupada() + "/" + getCapacidad() + "):\n");
		
		for (HashMap.Entry<String, ArrayList<Paquete>> pak : paquetes.entrySet()) {
	        for (Paquete paketito : pak.getValue()) {
	        	text.append(paketito.toString() + "\n");
	        }; 
	    }
		
		return text.toString();
	}
	
	
}
