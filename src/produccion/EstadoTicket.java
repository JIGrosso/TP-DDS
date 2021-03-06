package produccion;

public class EstadoTicket {

	public EstadosTicket idEstadoTicket;
	public String nombre;
	public String descripcion;
	
	public EstadoTicket() {};
	
	public EstadoTicket (EstadosTicket idEstadoTicket, String nombre, String descripcion) {
		this.idEstadoTicket = idEstadoTicket;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public EstadosTicket getIdEstadoTicket() {
		return idEstadoTicket;
	}
	public void setIdEstadoTicket(EstadosTicket idEstadoTicket) {
		this.idEstadoTicket = idEstadoTicket;
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
	public String toString() {
		return this.nombre;
	}
	
}
