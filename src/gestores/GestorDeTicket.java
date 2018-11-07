package gestores;

import java.util.Date;

import produccion.Clasificacion;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.Soporte;

public interface GestorDeTicket {
	
	public static void crearTicket(Soporte soporte, Integer nroTicket, Integer nroLegajo, Date fechaCreacion, Integer idClasificacion, String descripcion){
		
		Cliente cliente = GestorBD.mapearCliente(nroLegajo);
		Clasificacion clasificacion = GestorBD.mapearClasificacion(idClasificacion);
		
		// Ver fechaApertura y horaApertura
		
		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, fechaCreacion, fechaCreacion, descripcion);
		
		// Persistir Cambios
		
	}
}
