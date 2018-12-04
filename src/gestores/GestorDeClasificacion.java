package gestores;

import java.util.ArrayList;

import clasesDTO.ClasificacionDTO;
import gestores.GestorBD;

public interface GestorDeClasificacion {

	static ArrayList<ClasificacionDTO> getClasificaciones() {
		ArrayList<ClasificacionDTO> clasificaciones = new ArrayList<ClasificacionDTO>();
		clasificaciones = GestorBD.mapearClasificacionesDTO();
		return clasificaciones;
	}

}
