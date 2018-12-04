package clasesDTO;

import usuarios.Soporte;

public class ClasificacionDTO {
	
	public Integer idClasificacion;
	public String nombre;
	public String descripcionAlcance;
	
	public ClasificacionDTO(Integer idClasificacion, String nombre, String descripcion) {
		
		this.idClasificacion = idClasificacion;
		this.nombre = nombre;
		this.descripcionAlcance = descripcion;
	}

	public String toString() {
		return this.nombre;
	}
}
