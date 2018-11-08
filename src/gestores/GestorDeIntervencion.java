package gestores;

import usuarios.GrupoDeResolucion;

public interface GestorDeIntervencion {

	public static void crearIntervencion(Integer nroLegajo) {
		
		Integer nroIdIntervencion = GestorBD.nroNuevoIntervencion();
		GrupoDeResolucion grupo = GestorBD.mapearGrupoDeResolucion(nroLegajo);
		//Intervencion intervencion = new Intervencion();
		
	}

}
