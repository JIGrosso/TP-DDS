package clasesDTO;

import produccion.EstadosTicket;

public class EstadoTicketDTO {

	public EstadosTicket idEstadoTicket;
	public String nombre;
	public String descripcion;
	
	public EstadoTicketDTO () {};
	
	public EstadoTicketDTO (EstadosTicket idEstadoTicket, String nombre, String descripcion) {
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
