package gestores;

import java.util.Date;

import produccion.HistorialEstadoIntervencion;
import produccion.Intervencion;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public interface GestorDeIntervencion {

	public static Intervencion crearIntervencion(Soporte soporte, Date fechaAsignacion) {

		Integer IdIntervencion = GestorBD.nroNuevoIntervencion();
		GrupoDeResolucion grupo = GestorBD.mapearGrupoDeResolucion(soporte.nroLegajo);
		Integer idPrimerHistorial = GestorBD.nroNuevoHistorialEI();

		HistorialEstadoIntervencion primerHistorial = new HistorialEstadoIntervencion(idPrimerHistorial, fechaAsignacion, soporte);
		Intervencion intervencion = new Intervencion(IdIntervencion, fechaAsignacion, grupo, primerHistorial);

		return intervencion;
	}

}
