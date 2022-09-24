package expresolibre;

import java.util.HashSet;
import java.util.Iterator;

public class Empresa {
										// IREP
	private static Deposito depofrio;	//!= null
	private static Deposito deposinfrio;//!= null
	private String nroCuit;				//!= null
	private String nombre;				//!= null
										//
	private HashSet<Transporte> flota;	//!= null (puede ser conj vacio{})
	private HashSet<Destino> destinos;	//!= null (puede ser conj vacio{})
	
	
	/**
	 * Crea una empresa
	 * @param cuit de la empresa.
	 * @param nombre de la empresa.
	 * @param capacidad (en volumen) de los depositos de la empresa.
	 */
	public Empresa(String cuit, String nombre, int capacidad) {
		this.nroCuit = cuit;
		this.nombre = nombre;
		
		deposinfrio = new Deposito (capacidad, false);
		depofrio = new Deposito (capacidad, true);
		destinos = new HashSet<Destino>();
		flota = new HashSet<Transporte>();
	}

	/**
	 * Da por iniciado el viaje del Transporte con la patente dada, siempre y cuando se cumpla que: <br>
	 * <br>. 1) El transporte con la patente debe existir.
	 * <br>. 2) El transporte debe tener un destino valido.
	 * <br>. 3) El transporte debe estar cargado.
	 * @param patente del vehiculo
	 * @throws Throwable Cuando se incumple cualquiera de las condiciones mencionadas.
	 */
	public void iniciarViaje(String patente) throws Exception{
		Transporte temp = buscarTransporte(patente);
		/*
		 * Es el transporte el que tiene que saber las condiciones para iniciar
		 * el viaje, o la empresa? TODO ver Transporte.iniciarViaje();
		 */
		if (temp == null){
			throw new RuntimeException ("El transporte no existe.");
		}
		if (temp.getDestino() == null) {
			throw new RuntimeException ("El transporte no tiene un destino.");
		}
		if (temp.getVolumenLibre() == temp.getVolmax()) {
			throw new RuntimeException ("El transporte no esta cargado.");
		}
		if (temp.getPaquetes() == null) {
			throw new RuntimeException ("El transporte no esta cargado.");
		}
		temp.iniciarViaje();
	}

	/**
	 * Da por concluido el viaje del Transporte con la patente dada, siempre y cuando se cumpla que: <br>
	 * <br>. 1. El transporte con la patente debe existir.
	 * <br>. 2. El transporte debe estar en viaje.
	 * @param patente del vehiculo
	 * @throws Throwable Cuando se incumple cualquiera de las condiciones mencionadas.
	 */
	public void finalizarViaje(String patente) throws Exception {
		Transporte temp = buscarTransporte(patente);
		if (temp == null){
			throw new RuntimeException ("El transporte no existe.");
		}
		if (!temp.isEnViaje()) {
			throw new RuntimeException ("El transporte no esta en viaje.");
		}
		temp.finalizarViaje();
	}
	
	/**
	 * Asigna un destino a un transporte de la flota.
	 * @param matricula Matricula del transporte
	 * @param destino Destino el cual asignar
	 * @throws Exception Si el destino a ingresar no fue inicializado anteriormente.
	 */
	public void asignarDestino(String matricula, String nombreDestino) throws Exception {
		Destino dest = null;
		
		Iterator<Destino> destinoIterador = destinos.iterator();
		while(destinoIterador.hasNext()) {
			Destino destin = destinoIterador.next();
			if (destin.getNombre() == nombreDestino) {
				dest = destin;
			}
		}
		if (dest == null) {
			throw new RuntimeException("El destino no está registrado / No hay informacion sobre el destino");
		}
		buscarTransporte(matricula).asignarDestino(dest);		
 	}
	
	/**
	 * Añade un destino a la lista de posibles destinos a elegir por los Transportes.
	 * @param destino 		Nombre del destino
	 * @param km			Km de distancia
	 * @throws Throwable	Si el destino ya existe.
	 */
	public void agregarDestino(String destino, int km) throws Exception {
		for (Destino dest : destinos) {
			if (dest.getNombre()==destino) throw new RuntimeException ("Ya existe el destino");
		}
		destinos.add(new Destino (destino, km));
	};

	/**
	 * Efectua la carga de todos los paquetes validos al transporte con la patante designada.<br>
	 * Un paquete se considera valido si:
	 * <br>. 1) Tienen el mismo destino.
	 * <br>. 2) Hay volumen y peso suficiente para ingresarlo.
	 * <br>. 3) El transporte no se encuentra en viaje.
	 * <br><br> Elimina del deposito todos los paquetes cargados en el transporte.
	 * @param patente La patente del vehiculo al que se le cargaran los paquetes.
	 * @return El volumen total cargado al transporte.
	 */
	public double cargarTransporte(String patente) {
		double vol = 0;
		Deposito depo = deposinfrio;
		Transporte camionsito = buscarTransporte(patente);
		
		// Condicionales.
		if (camionsito == null) {
			return 0;
		}
		if (camionsito.getDestino() == null) {
			return 0;
		}
		if (camionsito.isEnViaje()) {
			return 0;
		}
		
		// Elijo el deposito del cual sacar los paquetes.
		if (camionsito.isFrio()) {
			depo = depofrio;
		}
		else {
			depo = deposinfrio;
		}
		
		// Efectuo propiamente la carga.
		Iterator<Paquete> pak2 = depo.getPaquetesDestino(camionsito.getDestino().getNombre()).iterator();
		while(pak2.hasNext()) {
			double temp = 0;
			temp = camionsito.cargarPaquete(pak2.next());
			if (temp != 0) {	//Es decir, cargarPaquete devolvio un volumen distinto de 0 (cargo algo)
				vol += temp;
				pak2.remove();
			}
		}
		return vol;
	}

	/**
	 * Almacena un paquete en su deposito correspondiente.
	 * @param destino 	El destino del paquete.
	 * @param peso 		El peso del paquete.
	 * @param volumen	El volumen del paquete.
	 * @param necesitaRefrigeracion	Si el paquete requiere frio o no.
	 * @return
	 */
	public boolean incorporarPaquete(String destino, double peso, 
			double volumen, boolean necesitaRefrigeracion) {
		if (necesitaRefrigeracion) 
			return depofrio.incorporarPaquete(destino, peso, volumen, necesitaRefrigeracion);
		else 
			return deposinfrio.incorporarPaquete(destino, peso, volumen, necesitaRefrigeracion);
	}

	/**
	 * Obtiene el costo del viaje de un Transporte con una matricula dada, siempre y cuando:
	 * <br>. 1. El transporte con la patente debe existir.
	 * <br>. 2. El transporte debe estar en viaje.
	 * @param matricula Patente del transporte a comprobar.
	 * @return El valor del pasaje.
	 * @throws Throwable Si no se cumple cualquiera de las condiciones mensionadas.
	 */
	public double obtenerCostoViaje(String matricula) throws Exception {
		Transporte temp = buscarTransporte(matricula);
		if (temp == null) {
			throw new Exception ("No existe el transporte.");
		}
		if (!temp.isEnViaje()) {
			throw new Exception("El transporte no se encuentra en viaje");
		}
		
		return temp.obtenerCostoViaje(temp.getDestino().getDistancia());
	}
	
	/**
	 * Dada la patente de un Transporte existente, devuelve otra patente de OTRO transporte exitente, tal que: <br>
	 * <br>. 1) Son el mismo tipo de transporte.
	 * <br>. 2) Tienen el mismo destino.
	 * <br>. 3) Llevan la misma carga de paquetes.
	 * 
	 * @param matricula La matricula del transporte al cual se le quiere encontrar otro igual.
	 * @return La patente del transporte hallado, o NULL si no existe tal transporte.
	 */
	public String obtenerTransporteIgual(String matricula) {
		// Busco el transporte con la patente que pasé
		Transporte patentePasada = buscarTransporte(matricula);
		if (patentePasada == null) return null; //No existe un vehiculo con esa patente
		
		Iterator<Transporte> flotaIterador = flota.iterator();
		while(flotaIterador.hasNext()) {
			Transporte transp = flotaIterador.next();
			if (patentePasada.getPatente() == transp.getPatente()) {
				continue; //Porque no quiero que me devuelva la misma patente
			}
			
			if (patentePasada.hashCode() == transp.hashCode() && patentePasada.equals(transp)) {
				return transp.getPatente();
			}
		}
		return null;
	}
	
	/**
	 * Ingresa a la flota de vehiculos un Mega Trailer.
	 * @param matricula
	 * @param cargaMax volumen maximo de equipaje
	 * @param capacidad peso maximo de equipaje
	 * @param tieneRefrigeracion
	 * @param costoKm
	 * @param segCarga
	 * @param costoFijo
	 * @param costoComida
	 */
	public void agregarMegaTrailer(String matricula, double cargaMax,
			 double capacidad, boolean tieneRefrigeracion,
			 double costoKm, double segCarga, double costoFijo,
			 double costoComida) {
		MegaTrailer MTrai = new MegaTrailer (matricula, capacidad, cargaMax, costoKm, tieneRefrigeracion, segCarga , costoFijo, costoComida);
		flota.add(MTrai);
		// No pide tirar excepcion si ya existe un veh con la patente, por lo tanto, no se hace nada.
	}

	/**
	 * Ingresa a la flota de vehiculos un Flete.
	 * @param patente	
	 * @param peso		Peso maximo admitido
	 * @param vol		Volumen maximo admitido
	 * @param costoKm	
	 * @param acompañantes
	 * @param costoacomp
	 */
	public void agregarFlete(String patente, float peso, float vol, float costoKm, 
			int acompañantes, float costoacomp) {
		Flete Flet = new Flete (patente, peso, vol, costoKm, acompañantes, costoacomp);
		flota.add(Flet);
		// No pide tirar excepcion si ya existe un veh con la patente, por lo tanto, no se hace nada.
	}
	
	/**
	 * Ingresa a la flota de vehiculos un Trailer.
	 * @param patente
	 * @param peso		Peso maximo admitido
	 * @param vol		Volumen maximo admitido
	 * @param costoKm
	 * @param tieneRefrigeracion
	 * @param segCarga
	 */
	public void agregarTrailer(String patente, float peso, float vol, boolean tieneRefrigeracion, 
			float costoKm, double segCarga) {
		Trailer Trai = new Trailer (patente, peso, vol, costoKm, tieneRefrigeracion, segCarga);
		flota.add(Trai);
		// No pide tirar excepcion si ya existe un veh con la patente, por lo tanto, no se hace nada.
	}

	/*
	 * Getters y setters
	 */
	
	public static Deposito getDepofrio() {
		return depofrio;
	}

	public static Deposito getDeposinfrio() {
		return deposinfrio;
	}
	
	/*
	 * Metodos menores
	 */

	/**
	 * Devuelve el Transporte con la patente dada. Devuelve null si no existe tal Transporte.
	 * @param patente
	 * @return El objeto Transporte con la patente dada. NULL si no existe.
	 */
	public Transporte buscarTransporte (String patente) {
		for (Transporte veh : flota){
			if (veh.getPatente() == patente) {
				return veh;
			}
		}
		return null;
	}



	/**
	 * Devuelve la cantidad de transportes.
	 * @return la cantidad.
	 */
	public int cantidadTransportesEnFlota() {
		return flota.size();
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append("<-----===================-----> \n");
		text.append("Empresa: \n\n");
		text.append("  Nombre: " + this.nombre + "\n");
		text.append("  CUIL: " + this.nroCuit + "\n\n");
		
		text.append(depofrio.toString());
		text.append("\n");
		text.append(deposinfrio.toString());
		text.append("\n");
		
		text.append("  Listado de la flota de Transportes (Total: " + cantidadTransportesEnFlota() + ") \n" );
		int contador = 0;
		for (Transporte trans : flota) {
			contador++;
			text.append(contador + ") Vehiculo con dominio " + trans.getPatente() + ":\n");
			text.append(trans.toString() + "\n");
		}
		
		text.append("\n<-----===================----->\n");	
		return text.toString();
	}
	
}
