package gestores;

import java.util.ArrayList;
import java.util.Date;

import clasesDTO.EstadoIntervencionDTO;
import clasesDTO.EstadoTicketDTO;
import produccion.HistorialEstadoIntervencion;
import produccion.Intervencion;
import usuarios.Soporte;

public interface GestorDeIntervencion {

	public static Intervencion crearIntervencion(Soporte soporte, Date fechaAsignacion) {

		Integer IdIntervencion = GestorBD.nroNuevoIntervencion();
		Integer idPrimerHistorial = GestorBD.nroNuevoHistorialEI();

		HistorialEstadoIntervencion primerHistorial = new HistorialEstadoIntervencion(idPrimerHistorial, fechaAsignacion, soporte);
		Intervencion intervencion = new Intervencion(IdIntervencion, fechaAsignacion, primerHistorial);
		
		soporte.getGrupo().intervenciones.add(intervencion);

		return intervencion;
	}

	public static ArrayList<EstadoIntervencionDTO> mapearEstadosIntervencionDTO() {
		ArrayList<EstadoIntervencionDTO> estadosIntervencion = new ArrayList<EstadoIntervencionDTO>();
		EstadoIntervencionDTO asignada = GestorBD.mapearEstadoIntervencionDTO("ASIGNADA");
		estadosIntervencion.add(asignada);
		EstadoIntervencionDTO activa = GestorBD.mapearEstadoIntervencionDTO("ACTIVA");
		estadosIntervencion.add(activa);
		EstadoIntervencionDTO espera = GestorBD.mapearEstadoIntervencionDTO("ESPERA");
		estadosIntervencion.add(espera);
		EstadoIntervencionDTO cerrada = GestorBD.mapearEstadoIntervencionDTO("CERRADA");
		estadosIntervencion.add(cerrada);
		return estadosIntervencion;
		
	}

}
