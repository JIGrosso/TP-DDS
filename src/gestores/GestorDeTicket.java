package gestores;

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
		
		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, fechaCreacion, fechaCreacion, descripcion);
		
		HistorialEstadoTicket historialEstado = new HistorialEstadoTicket(soporte);
		HistorialClasificacionTicket historialClasificacion = new HistorialClasificacionTicket(clasificacion, nroTicket);
		nuevoTicket.addHistorialEstadoTicket(historialEstado);
		nuevoTicket.addHistorialClasificacionTicket(historialClasificacion);
		
		GestorDeIntervencion.crearIntervencion(soporte.nroLegajo);
		
		// Persistir Cambios
		
	}
}
