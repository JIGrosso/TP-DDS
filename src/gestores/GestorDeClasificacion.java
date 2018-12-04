package gestores;

import java.util.ArrayList;

import clasesDTO.ClasificacionDTO;
import gestores.GestorBD;

public class GestorDeClasificacion {

	public static ArrayList<ClasificacionDTO> getClasificaciones() {
		
		ArrayList<ClasificacionDTO> clasificaciones = new ArrayList<ClasificacionDTO>();
		clasificaciones = GestorBD.mapearClasificacionesDTO();
		return clasificaciones;
	}

}
