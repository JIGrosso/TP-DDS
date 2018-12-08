package gestores;

import java.util.ArrayList;
import java.util.List;

import clasesDTO.GrupoDTO;
import produccion.Clasificacion;
import usuarios.GrupoDeResolucion;

public class GestorDeGrupo {

	public static List<GrupoDeResolucion> gruposMapeados = new ArrayList<GrupoDeResolucion>();
	
	public static GrupoDeResolucion mapearGrupoDeResolucion (Integer idGrupo) {
		return GestorBD.mapearGrupoDeResolucion(idGrupo);
	}

	public static ArrayList<GrupoDTO> getGruposClasificados(Clasificacion clasificacion) {
		
		ArrayList<Integer> idsGrupos = clasificacion.getIdGrupos();
		ArrayList<GrupoDTO> resultado = new ArrayList<GrupoDTO>();
		GrupoDTO aux;
		
		for(int i = 0; i < idsGrupos.size(); i++) {
			aux = GestorBD.mapearGrupoDTO(idsGrupos.get(i));
			resultado.add(aux);
		}
		
		return resultado;
	}
	
	public static ArrayList<GrupoDTO> getAll() {
		return GestorBD.mapearGruposDTO();
	}
}
