package gestores;

import java.text.SimpleDateFormat;
import java.util.Date;

import produccion.Clasificacion;
import produccion.EstadoTicket;
import produccion.HistorialClasificacionTicket;
import produccion.HistorialEstadoTicket;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.Soporte;

public interface GestorDeTicket {
	
	public static void crearTicket(Soporte soporte, Integer nroTicket, Integer nroLegajo, Date fechaCreacion, String clasificacion2, String descripcion){
		
		Cliente cliente = GestorBD.mapearCliente(nroLegajo);
		Clasificacion clasificacion = GestorBD.mapearClasificacion(clasificacion2);
		
		// Ver fechaApertura y horaApertura
		
		//Cambiar nombre de variable ticket a nuevoTicket
		
		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, fechaCreacion, descripcion);
		
		HistorialEstadoTicket primerHistorialEstado = new HistorialEstadoTicket();
		primerHistorialEstado.PrimerHistorialEstadoTicket(soporte, fechaCreacion);
		HistorialClasificacionTicket historialClasificacion = new HistorialClasificacionTicket(clasificacion, nroTicket);
		nuevoTicket.addHistorialEstadoTicket(primerHistorialEstado);
		nuevoTicket.addHistorialClasificacionTicket(historialClasificacion);
		
		System.out.println("Historial Estado Ticket: " + nuevoTicket.historialesEstado.get(0).estado.nombre);
		
		// GestorDeIntervencion.crearIntervencion(soporte, grupo);
		
		System.out.println("Ticket creado: Nro Ticket: " + nuevoTicket.nroTicket);
		System.out.println("Estado Actual: " + nuevoTicket.estadoActual.nombre);
			
		GestorBD.guardarTicket(nuevoTicket, soporte);
		
	}
}
