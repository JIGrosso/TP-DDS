package gestores;

import java.util.ArrayList;
import java.util.Date;

import clasesDTO.EstadoIntervencionDTO;
import produccion.EstadoIntervencion;
import produccion.HistorialEstadoIntervencion;
import produccion.Intervencion;
import usuarios.Soporte;

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

}
