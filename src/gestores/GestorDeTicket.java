package gestores;

import java.util.ArrayList;
import java.util.Date;

import clasesDTO.EstadoTicketDTO;
import produccion.Clasificacion;
import produccion.EstadoTicket;
import produccion.HistorialClasificacionTicket;
import produccion.HistorialEstadoTicket;
import produccion.Intervencion;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.Soporte;

public class GestorDeTicket {
	
	public static EstadoTicket abiertoMA;
	
	public static EstadoTicket abiertoD;
	
	public static EstadoTicket solucionadoOK;
	
	public static EstadoTicket cerrado;

	public static Ticket crearTicket(Soporte soporte, Integer nroTicket, Integer nroLegajo, Date fechaCreacion, Clasificacion clasificacion, String descripcion){

		Cliente cliente = GestorBD.mapearCliente(nroLegajo);

		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, soporte.grupo, fechaCreacion, descripcion);

		HistorialEstadoTicket primerHistorialEstado = new HistorialEstadoTicket(soporte, fechaCreacion);
		HistorialClasificacionTicket historialClasificacion = new HistorialClasificacionTicket(clasificacion, fechaCreacion);
		nuevoTicket.addHistorialEstadoTicket(primerHistorialEstado);
		nuevoTicket.addHistorialClasificacionTicket(historialClasificacion);

		System.out.println("Historial Estado Ticket: " + nuevoTicket.historialesEstado.get(0).estado.nombre);

		Intervencion nuevaIntervencion = GestorDeIntervencion.crearIntervencion(soporte, fechaCreacion);

		System.out.println("Ticket creado: Nro Ticket: " + nuevoTicket.nroTicket);
		System.out.println("Estado Actual: " + nuevoTicket.estadoActual.nombre);

		GestorBD.guardarTicket(nuevoTicket, soporte);
		GestorBD.guardarIntervencion(nuevaIntervencion, soporte, nroTicket);
		// GuardarGrupo pq se actualiza su lista de intervenciones
		
		return nuevoTicket;

	}

	public static ArrayList<EstadoTicketDTO> mapearEstadosDTO() {
		ArrayList<EstadoTicketDTO> estadosTickets = new ArrayList<EstadoTicketDTO>();
		EstadoTicketDTO abiertoMAdto = GestorBD.mapearEstadoTicketDTO("ABIERTO_MA");
		estadosTickets.add(abiertoMAdto);
		abiertoMA = GestorBD.mapearEstadoTicket("ABIERTO_MA");
		EstadoTicketDTO abiertoDdto = GestorBD.mapearEstadoTicketDTO("ABIERTO_D");
		estadosTickets.add(abiertoDdto);
		abiertoD = GestorBD.mapearEstadoTicket("ABIERTO_D");
		EstadoTicketDTO solucionadoOKdto = GestorBD.mapearEstadoTicketDTO("SOLUCIONADO_OK");
		estadosTickets.add(solucionadoOKdto);
		solucionadoOK = GestorBD.mapearEstadoTicket("SOLUCIONADO_OK");
		EstadoTicketDTO cerradodto = GestorBD.mapearEstadoTicketDTO("CERRADO");
		estadosTickets.add(cerradodto);
		cerrado = GestorBD.mapearEstadoTicket("CERRADO");
		return estadosTickets;
	}
}
