package clasesDTO;

import java.util.Date;
import java.util.List;

import produccion.HistorialEstadoTicket;

public class TicketDTO {

	public Integer nroTicket;
	public Integer nroLegajoCliente;
	public GrupoDTO grupoAsignado;
	public ClasificacionDTO clasificacion;
	public Date fechaYHoraApertura;
	public String descripcion;
	public String observaciones;
	public EstadoTicketDTO estadoActual;
	public List<HistorialEstadoTicketDTO> historialesEstado;
	
	public TicketDTO(Integer nroTicket, Integer nroLegajo, GrupoDTO grupoAsignado, ClasificacionDTO clasificacion, Date fechaYHora, EstadoTicketDTO estadoActual) {
		this.nroTicket = nroTicket;
		this.nroLegajoCliente = nroLegajo;
		this.grupoAsignado = grupoAsignado;
		this.clasificacion = clasificacion;
		this.fechaYHoraApertura = fechaYHora;
		this.estadoActual = estadoActual;
	}
	
	public Integer getNroTicket() {
		return nroTicket;
	}
	
	public void setNroTicket(Integer nroTicket) {
		this.nroTicket = nroTicket;
	}
	
	public Integer getNroLegajoCliente() {
		return nroLegajoCliente;
	}
	
	public void setNroLegajoCliente(Integer nroLegajoCliente) {
		this.nroLegajoCliente = nroLegajoCliente;
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
