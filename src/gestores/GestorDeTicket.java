package gestores;

import java.util.Date;

import produccion.Clasificacion;
import produccion.HistorialClasificacionTicket;
import produccion.HistorialEstadoTicket;
import produccion.Intervencion;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.Soporte;

public interface GestorDeTicket {

	public static Ticket crearTicket(Soporte soporte, Integer nroTicket, Integer nroLegajo, Date fechaCreacion, Clasificacion clasificacion, String descripcion){

		Cliente cliente = GestorBD.mapearCliente(nroLegajo);

		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, soporte.grupo, fechaCreacion, descripcion);

		HistorialEstadoTicket primerHistorialEstado = new HistorialEstadoTicket(soporte, fechaCreacion);
		HistorialClasificacionTicket historialClasificacion = new HistorialClasificacionTicket(clasificacion, fechaCreacion);
		nuevoTicket.addHistorialEstadoTicket(primerHistorialEstado);
		nuevoTicket.addHistorialClasificacionTicket(historialClasificacion);

		System.out.println("Historial Estado Ticket: " + nuevoTicket.historialesEstado.get(0).estado.nombre);

		Intervencion nuevaIntervencion = GestorDeIntervencion.crearIntervencion(soporte, fechaCreacion);
		nuevoTicket.intervenciones.add(nuevaIntervencion);
		
		System.out.println("Ticket creado: Nro Ticket: " + nuevoTicket.nroTicket);
		System.out.println("Estado Actual: " + nuevoTicket.estadoActual.nombre);

		GestorBD.guardarTicket(nuevoTicket, soporte);
		GestorBD.guardarIntervencion(nuevaIntervencion, soporte, nroTicket);
		// GuardarGrupo pq se actualiza su lista de intervenciones
		
		return nuevoTicket;

	}
}
