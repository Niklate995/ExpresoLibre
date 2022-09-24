/**
 * 
 */
package expresolibre;

/**
 * @author Nicolas
 *
 */
public class main {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Exception {
		
		Empresa emp;
		double volumen, ctoViaje;
		
		emp = new Empresa("30112223334", "Expreso Libre", 30000);
		emp.agregarDestino("Cordoba", 350);
		
		
		
		//System.out.println("Debe tirar false: " + emp.incorporarPaquete("Cordoba", 300, 30001, true));
		
		//System.out.println("Debe tirar true: " + emp.incorporarPaquete("Cordoba", 100, 5, true));
		
		// DEBE TIRAR ERROR DESTINO DUPLICADO
		//emp.agregarDestino("Cordoba", 320);
		// DEBE TIRAR ERROR TRANSPORTE INEXISTENTE
		//emp.iniciarViaje("AC314PI");
		// DEBE TIRAR ERROR TRANSPORTE SIN CARGA
		//emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		//emp.iniciarViaje("AC314PI");
		// DEBE TIRAR ERROR TRANSPORTE SIN CARGA
		//emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		//emp.finalizarViaje("AC314PI");
		
		/*
		 * Test Trailer Frio
		 * 
		emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		emp.asignarDestino("AC314PI", "Cordoba");
		emp.incorporarPaquete("Cordoba", 100, 5, true);
		//System.out.println("El volumen frio es: " + emp.getDepofrio().getCapacidadOcupada());
		emp.incorporarPaquete("Cordoba", 250, 10, true);
		emp.incorporarPaquete("Cordoba", 150, 8, true);
		emp.incorporarPaquete("Cordoba", 50, 2.5, false); // no es compatible con el transprote
		emp.incorporarPaquete("Cordoba", 300, 15, true);
		emp.incorporarPaquete("Cordoba", 400, 12, true);
		emp.incorporarPaquete("Cordoba", 125, 5, false); // no es compatible con el transprote
		volumen = emp.cargarTransporte("AC314PI");
		System.out.println("El volumen tiene que ser igual a 50.0: " + volumen);
		emp.iniciarViaje("AC314PI");
		ctoViaje = emp.obtenerCostoViaje("AC314PI");
		System.out.println("El costo tiene que ser igual a 1850.0: " + ctoViaje);
		emp.finalizarViaje("AC314PI");
		*/
		
		/*
		 * Test Agregar MegaTrailer
		 * 
		emp.agregarDestino("Corrientes", 900);
		// distancia a Corrientes = 900Km
		// costo viaje = 900 * 10 + 150 + 500 + 300 = 
		//             = 9000 + 950 = $ 9950
		emp.agregarMegaTrailer("AD161AU", 18000, 120, false, 10, 150, 500, 300);
		emp.asignarDestino("AD161AU", "Corrientes");
		emp.incorporarPaquete("Corrientes", 100, 5, true); // no es compatible con el transprote
		emp.incorporarPaquete("Corrientes", 400, 12, true); // no es compatible con el transprote
		emp.incorporarPaquete("Corrientes", 50, 2.5, false);
		emp.incorporarPaquete("Corrientes", 125, 5, false);
		emp.incorporarPaquete("Corrientes", 75, 4, false);
		emp.incorporarPaquete("Corrientes", 150, 7.5, false);
		emp.incorporarPaquete("Corrientes", 200, 6, false);
		volumen = emp.cargarTransporte("AD161AU");
		System.out.println("El volumen tiene que ser igual a 25.0: " + volumen);
		emp.iniciarViaje("AD161AU");
		ctoViaje = emp.obtenerCostoViaje("AD161AU");
		System.out.println("El costo tiene que ser igual a 9950.0: " + ctoViaje);
		emp.finalizarViaje("AD161AU");
		*/
		
		
		/*
		 * Testeo del obtenerTransporteIgual
		 */
		emp.agregarDestino("Parana", 30);
		
		emp.agregarFlete("AB271NE", 8000, 40, 3, 2, 200);
		emp.asignarDestino("AB271NE", "Parana");
		emp.incorporarPaquete("Parana", 100, 5, false);
		emp.incorporarPaquete("Parana", 400, 12, false);
		emp.incorporarPaquete("Parana", 50, 8, false);
		emp.cargarTransporte("AB271NE");

		emp.agregarFlete("AD235NP", 6000, 30, 2, 1, 100);
		emp.asignarDestino("AD235NP", "Parana");
		emp.incorporarPaquete("Parana", 400, 12, false);
		emp.incorporarPaquete("Parana", 50, 8, false);
		emp.incorporarPaquete("Parana", 100, 5, false);
		emp.cargarTransporte("AD235NP");
		
		//Test Equals: assertEquals("AD235NP", emp.obtenerTransporteIgual("AB271NE"));
		System.out.println("Dame una patente igual a AB271NE (equals):");
		System.out.println("AD235NP es igual a AB271NE? " + emp.obtenerTransporteIgual("AB271NE"));
		
		
		System.out.println("\n\n---===CAMBIO DE TEST===---\n\n");
		
		
	
		Empresa e = new Empresa("30112223334","Expreso Libre", 40000);
		//System.out.println(e.toString());
		e.agregarDestino("Rosario", 100);
		e.agregarDestino("Buenos Aires", 400);
		e.agregarDestino("Mar del Plata", 800);
		e.agregarTrailer("AA333XQ", 10000, 60, true, 2, 100);
		e.agregarMegaTrailer("AA444PR", 15000, 100, false, 3, 150, 200, 50);
		e.agregarFlete("AB555MN", 5000, 20, 4, 2, 300);
		e.asignarDestino("AA333XQ", "Buenos Aires");
		e.asignarDestino("AB555MN", "Rosario");
		// paquetes que necesitan frio
		e.incorporarPaquete("Buenos Aires", 100, 2, true);
		e.incorporarPaquete("Buenos Aires", 150, 1, true);
		e.incorporarPaquete("Mar del Plata", 100, 2, true);
		e.incorporarPaquete("Mar del Plata", 150, 1, true);
		e.incorporarPaquete("Rosario", 100, 2, true);
		e.incorporarPaquete("Rosario", 150, 1, true);
		// paquetes que NO necesitan frio
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 400, 4, false);
		e.incorporarPaquete("Mar del Plata", 200, 3, false);
		e.incorporarPaquete("Rosario", 80, 2, false);
		e.incorporarPaquete("Rosario", 250, 2, false);
		// Carga de transporte
		volumen = e.cargarTransporte("AA333XQ");
		System.out.println("Se cargaron " + volumen
		+" metros cubicos en el transp AA333XQ");
		// Viajes
		e.iniciarViaje("AA333XQ");
		System.out.println("Costo del viaje:"
		+e.obtenerCostoViaje("AA333XQ"));
		//System.out.println(e.toString());
		e.finalizarViaje("AA333XQ");
		//System.out.println(e.toString());
		
		
	}

}
