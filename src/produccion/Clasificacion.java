package produccion;

import java.util.ArrayList;
import java.util.List;

import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public class Clasificacion {

	public Integer idClasificacion;
	public String nombre;
	public String descripcionAlcance;
	public Soporte creador;
	public List<GrupoDeResolucion> gruposPertenecientes; 
	
	public Clasificacion (Integer idClasificacion, String nombre, String descripcionAlcance, Soporte creador) {
		
		this.idClasificacion = idClasificacion;
		this.nombre = nombre;
		this.descripcionAlcance = descripcionAlcance;
		this.creador = creador;
		this.gruposPertenecientes = new ArrayList<GrupoDeResolucion>();
	}
	
	public Integer getIdClasificacion() {
		return idClasificacion;
	}
	public void setIdClasificacion(Integer idClasificacion) {
		this.idClasificacion = idClasificacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcionAlcance() {
		return descripcionAlcance;
	}
	public void setDescripcionAlcance(String descripcionAlcance) {
		this.descripcionAlcance = descripcionAlcance;
	}
	public Soporte getCreador() {
		return creador;
	}
	public void setCreador(Soporte creador) {
		this.creador = creador;
	}
	
	public ArrayList<Integer> getIdGrupos() {
		
		ArrayList<Integer> resultado = new ArrayList<Integer>();
		for(int i = 0; i < this.gruposPertenecientes.size(); i++) {
			resultado.add(this.gruposPertenecientes.get(i).idGrupo);
		}
		
		return resultado;
	}
	
	public String toString() {
		return this.nombre;
	}

}
