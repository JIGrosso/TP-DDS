package clasesDTO;

import java.util.Date;

public class TicketDTO {

	public Integer nroTicket;
	public Integer NroLegajoCliente;
	public String soporteCreador;
	public GrupoDTO grupoAsignado;
	public ClasificacionDTO clasificacion;
	public Date fechaYHoraApertura;
	public String descripcion;
	public String observaciones;
	public EstadoTicketDTO estadoActual;
	public Date ultimoCambioEstado;
	
	public Integer getNroTicket() {
		return nroTicket;
	}
	
	public void setNroTicket(Integer nroTicket) {
		this.nroTicket = nroTicket;
	}
	
	public Integer getNroLegajoCliente() {
		return NroLegajoCliente;
	}
	
	public void setNroLegajoCliente(Integer nroLegajoCliente) {
		NroLegajoCliente = nroLegajoCliente;
	}
	
	public GrupoDTO getGrupoAsignado() {
		return grupoAsignado;
	}
	
	public void setGrupoAsignado(GrupoDTO grupoAsignado) {
		this.grupoAsignado = grupoAsignado;
	}
	
	public ClasificacionDTO getClasificacion() {
		return clasificacion;
	}
	
	public void setClasificacion(ClasificacionDTO clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public Date getFechaYHoraApertura() {
		return fechaYHoraApertura;
	}
	
	public void setFechaYHoraApertura(Date fechaYHoraApertura) {
		this.fechaYHoraApertura = fechaYHoraApertura;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public EstadoTicketDTO getEstadoActual() {
		return estadoActual;
	}
	
	public void setEstadoActual(EstadoTicketDTO estadoActual) {
		this.estadoActual = estadoActual;
	}

}
