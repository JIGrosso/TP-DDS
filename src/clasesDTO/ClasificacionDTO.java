package clasesDTO;

import java.util.ArrayList;

import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public class ClasificacionDTO {
	public Integer idClasificacion;
	public String nombre;
	public String descripcionAlcance;
	public Soporte creador;
	
	public ClasificacionDTO(Integer idClasificacion, String string, String string2, Soporte soporte) {
		this.idClasificacion = idClasificacion;
		this.nombre = nombre;
		this.descripcionAlcance = descripcionAlcance;
		this.creador = creador;
	}

}
