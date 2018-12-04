package clasesDTO;

import usuarios.Soporte;

public class ClasificacionDTO {
	
	public Integer idClasificacion;
	public String nombre;
	public String descripcionAlcance;
	public Soporte creador;
	
	public ClasificacionDTO(Integer idClasificacion, String nombre, String descripcion, Soporte soporte) {
		this.idClasificacion = idClasificacion;
		this.nombre = nombre;
		this.descripcionAlcance = descripcion;
		this.creador = soporte;
	}

	public String toString() {
		return this.nombre;
	}
}
