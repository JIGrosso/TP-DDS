package produccion;

import usuarios.Soporte;

public class Clasificacion {

	public Integer idClasificacion;
	public String nombre;
	public String descripcionAlcance;
	public Soporte creador;
	
	public Clasificacion (Integer idClasificacion, String nombre, String descripcionAlcance, Soporte creador) {
		this.idClasificacion = idClasificacion;
		this.nombre = nombre;
		this.descripcionAlcance = descripcionAlcance;
		this.creador = creador;
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
	


}
