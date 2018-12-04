package clasesDTO;


import usuarios.Nivel;

public class GrupoDTO {
	
	public Integer idGrupo;
	public String nombre;
	
	public GrupoDTO(Integer idGrupo, String nombre) {
		
		this.idGrupo = idGrupo;
		this.nombre = nombre;
	}
	
	public String toString() {
		return this.nombre;
	}
}
