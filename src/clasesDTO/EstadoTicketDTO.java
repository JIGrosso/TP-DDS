package clasesDTO;

import produccion.EstadosTicket;

public class EstadoTicketDTO {

	public EstadosTicket idEstadoTicket;
	public String nombre;
	public String descripcion;
	
	public EstadoTicketDTO () {}
	
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

}
