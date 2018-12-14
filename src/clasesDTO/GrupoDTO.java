package clasesDTO;


import java.util.List;

import usuarios.Nivel;

public class GrupoDTO {
	
	public Integer idGrupo;
	public String nombre;
	public List<IntervencionDTO> intervenciones;
	
	public GrupoDTO(Integer idGrupo, String nombre) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
	}
	
	public String toString() {
		return this.nombre;
	}

	public void setIntervenciones(List<IntervencionDTO> intervencionesGrupoDTO) {
		this.intervenciones = intervencionesGrupoDTO;
	}
	
	public void addIntervencion(IntervencionDTO nueva) {
		this.intervenciones.add(nueva);
	}

	public String getNombre() {
		return this.nombre;
	}
}
