package gestores;

import java.util.ArrayList;
import java.util.Date;

import clasesDTO.ClasificacionDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.HistorialEstadoIntervencionDTO;
import clasesDTO.IntervencionDTO;
import clasesDTO.TicketDTO;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import produccion.HistorialEstadoIntervencion;
import produccion.Intervencion;
import usuarios.Soporte;
import vistas.Principal;

public class GestorDeIntervencion {
	
	public static EstadoIntervencion asignada;
	public static EstadoIntervencion activa;
	public static EstadoIntervencion espera;
	public static EstadoIntervencion cerrada;
	
	public static Intervencion crearIntervencion(Soporte soporte, Date fechaAsignacion) {

		Integer IdIntervencion = GestorBD.nroNuevoIntervencion();
		Integer idHistorial = GestorBD.nroNuevoHistorialEI();

		HistorialEstadoIntervencion primerHistorial = new HistorialEstadoIntervencion(idHistorial, fechaAsignacion, soporte);
		Intervencion intervencion = new Intervencion(IdIntervencion, fechaAsignacion, primerHistorial);
		
		soporte.getGrupo().intervenciones.add(intervencion);

		return intervencion;
	}
	
	public static Intervencion crearIntervencion(Soporte soporte, Date fechaAsignacion, EstadoIntervencion estado) {

		Integer IdIntervencion = GestorBD.nroNuevoIntervencion();
		Integer idHistorial = GestorBD.nroNuevoHistorialEI();

		HistorialEstadoIntervencion primerHistorial = new HistorialEstadoIntervencion(idHistorial, fechaAsignacion, null, soporte, estado);
		Intervencion intervencion = new Intervencion(IdIntervencion, "observaciones", fechaAsignacion, null, estado, null);
		intervencion.addHistorialEstadoIntervencion(primerHistorial);
		
		soporte.getGrupo().intervenciones.add(intervencion);

		return intervencion;
	}
	
	public static void cerrarIntervencion(Intervencion intervencion) {
		
		intervencion.fechaFin = new Date();
		intervencion.setEstadoIntervencionActual(mapearEstadoIntervencion("CERRADA"));
	}

	public static ArrayList<EstadoIntervencion> mapearEstadosIntervencion() {
		
		ArrayList<EstadoIntervencion> estadosIntervencion = new ArrayList<EstadoIntervencion>();
		EstadoIntervencion asignadadto = GestorBD.mapearEstadoIntervencion("ASIGNADA");
		estadosIntervencion.add(asignadadto);
		asignada = GestorBD.mapearEstadoIntervencion("ASIGNADA");
		EstadoIntervencion activadto = GestorBD.mapearEstadoIntervencion("ACTIVA");
		estadosIntervencion.add(activadto);
		activa = GestorBD.mapearEstadoIntervencion("ACTIVA");
		EstadoIntervencion esperadto = GestorBD.mapearEstadoIntervencion("ESPERA");
		estadosIntervencion.add(esperadto);
		espera = GestorBD.mapearEstadoIntervencion("ESPERA");
		EstadoIntervencion cerradadto = GestorBD.mapearEstadoIntervencion("CERRADA");
		estadosIntervencion.add(cerradadto);
		cerrada = GestorBD.mapearEstadoIntervencion("CERRADA");
		return estadosIntervencion;
		
	}

	public static void activarIntervencion(Intervencion intervencion) {
		
		EstadoIntervencion nuevoEstado = GestorBD.mapearEstadoIntervencion("ASIGNADA");
		intervencion.setEstadoIntervencionActual(nuevoEstado);
		
		HistorialEstadoIntervencion nuevoHistorial = new HistorialEstadoIntervencion(GestorBD.nroNuevoHistorialEI(), new Date(), null, Principal.usuarioIniciado, nuevoEstado);	
		intervencion.addHistorialEstadoIntervencion(nuevoHistorial);
	}

	public static EstadoIntervencion mapearEstadoIntervencion(String idEstado) {
		return GestorBD.mapearEstadoIntervencion(idEstado);
	}

	public static GrupoDTO getGrupoIntervencion(IntervencionDTO intervencion) {
		return GestorBD.mapearGrupoIntervencionDTO(intervencion.getId());
	}

	public static TicketDTO recuperarTicketDTO(Integer id) {
		return GestorBD.mapearTicketIntervDTO(id);
	}

	public static ArrayList<EstadoIntervencion> getEstadosDistintosIntervencion(EstadoIntervencion estado) {
		ArrayList<EstadoIntervencion> aux = new ArrayList<EstadoIntervencion>();
		if(estado == asignada) {
			aux.add(activa);
			aux.add(cerrada);
			aux.add(espera);
		}
		if(estado == activa) {
			aux.add(cerrada);
			aux.add(espera);
		}
		return null;
	}

	public static void actualizarEstadoIntervencion(TicketDTO ticket, IntervencionDTO intervencionDto,
			ClasificacionDTO clasificacionDto, EstadoIntervencion nuevoEstadoIntervencion, String observaciones) {
		Intervencion intervencion = GestorBD.mapearIntervencion(intervencionDto.idIntervencion);
		intervencion.getUltimoHistorialIntervencion().setFechaHasta();
		intervencion.setEstadoIntervencionActual(nuevoEstadoIntervencion);
		HistorialEstadoIntervencion historialEstInt = new HistorialEstadoIntervencion(GestorBD.nroNuevoHistorialEI(), new Date(), null, Principal.usuarioIniciado, nuevoEstadoIntervencion);
		intervencion.addHistorialEstadoIntervencion(historialEstInt);
		GestorDeTicket.actualizarTicket(ticket.nroTicket, intervencion, clasificacionDto);
	}
}
