package produccion;

public class EstadoIntervencion {

	public EstadosIntervencion idEstadoInt;
	public String nombre;
	public String descripcion;
	
	public EstadoIntervencion () {};
	
	public EstadoIntervencion (EstadosIntervencion idEstadoInt, String nombre, String descripcion) {
		this.idEstadoInt = idEstadoInt;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
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
