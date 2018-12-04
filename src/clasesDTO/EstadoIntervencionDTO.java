package clasesDTO;

import produccion.EstadosIntervencion;

public class EstadoIntervencionDTO {
	
	public EstadosIntervencion idEstadoInt;
	public String nombre;
	public String descripcion;
	
	public EstadoIntervencionDTO() {};
	
	public EstadosIntervencion getIdEstadoInt() {
		return idEstadoInt;
	}
	public void setIdEstadoInt(EstadosIntervencion idEstadoInt) {
		this.idEstadoInt = idEstadoInt;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
