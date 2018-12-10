package gestores;

import java.util.ArrayList;
import java.util.Date;

import clasesDTO.EstadoIntervencionDTO;
import clasesDTO.HistorialEstadoIntervencionDTO;
import clasesDTO.IntervencionDTO;
import produccion.EstadoIntervencion;
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
		Integer idPrimerHistorial = GestorBD.nroNuevoHistorialEI();

		HistorialEstadoIntervencion primerHistorial = new HistorialEstadoIntervencion(idPrimerHistorial, fechaAsignacion, soporte);
		Intervencion intervencion = new Intervencion(IdIntervencion, fechaAsignacion, primerHistorial);
		
		soporte.getGrupo().intervenciones.add(intervencion);

		return intervencion;
	}
	
	public static void cerrarIntervencion(Intervencion intervencion) {
		
		intervencion.fechaFin = new Date();
		intervencion.setEstadoIntervencionActual(cerrada);
	}

	public static ArrayList<EstadoIntervencionDTO> mapearEstadosIntervencionDTO() {
		
		ArrayList<EstadoIntervencionDTO> estadosIntervencion = new ArrayList<EstadoIntervencionDTO>();
		EstadoIntervencionDTO asignadadto = GestorBD.mapearEstadoIntervencionDTO("ASIGNADA");
		estadosIntervencion.add(asignadadto);
		asignada = GestorBD.mapearEstadoIntervencion("ASIGNADA");
		EstadoIntervencionDTO activadto = GestorBD.mapearEstadoIntervencionDTO("ACTIVA");
		estadosIntervencion.add(activadto);
		activa = GestorBD.mapearEstadoIntervencion("ACTIVA");
		EstadoIntervencionDTO esperadto = GestorBD.mapearEstadoIntervencionDTO("ESPERA");
		estadosIntervencion.add(esperadto);
		espera = GestorBD.mapearEstadoIntervencion("ESPERA");
		EstadoIntervencionDTO cerradadto = GestorBD.mapearEstadoIntervencionDTO("CERRADA");
		estadosIntervencion.add(cerradadto);
		cerrada = GestorBD.mapearEstadoIntervencion("CERRADA");
		return estadosIntervencion;
		
	}

	public static void activarIntervencionDTO(IntervencionDTO intervencionDTO) {
		intervencionDTO.setHistoriales(GestorBD.mapearHistorialesEstadosIntervencion(intervencionDTO.idIntervencion));
		HistorialEstadoIntervencionDTO ultimoHistorial = intervencionDTO.getUltimoHistEstInt();
		Date fechaActual = new Date();
		ultimoHistorial.setFechaHasta(fechaActual);
		EstadoIntervencionDTO nuevoEstado = GestorBD.mapearEstadoIntervencionDTO("ACTIVA");
		HistorialEstadoIntervencionDTO nuevo = new HistorialEstadoIntervencionDTO(GestorBD.nroNuevoHistorialEI(), fechaActual, null,
										nuevoEstado, Principal.usuarioIniciado);
		intervencionDTO.addHistorial(nuevo);
		intervencionDTO.setEstado(nuevoEstado);
	}
	
	public static IntervencionDTO crearIntervencionDTO() {
		Date fechaActual = new Date();
		EstadoIntervencionDTO nuevoEstado = GestorBD.mapearEstadoIntervencionDTO("ASIGNADA");
		IntervencionDTO nuevaIntervencion = new IntervencionDTO(GestorBD.nroNuevoIntervencion(), fechaActual,
				null, GestorBD.mapearEstadoIntervencionDTO("ASIGNADA"));
		HistorialEstadoIntervencionDTO nuevo = new HistorialEstadoIntervencionDTO(GestorBD.nroNuevoHistorialEI(), fechaActual, null,
				nuevoEstado, Principal.usuarioIniciado);
		nuevaIntervencion.addHistorial(nuevo);
		nuevaIntervencion.setEstado(nuevoEstado);
		return nuevaIntervencion;
	}

}
