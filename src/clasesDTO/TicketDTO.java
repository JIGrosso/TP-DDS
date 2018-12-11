package clasesDTO;

import java.util.Date;
import java.util.List;

import produccion.EstadoTicket;
import produccion.HistorialEstadoTicket;

public class TicketDTO {

	public Integer nroTicket;
	public Integer nroLegajoCliente;
	public GrupoDTO grupoAsignado;
	public ClasificacionDTO clasificacion;
	public Date fechaYHoraApertura;
	public String descripcion;
	public String observaciones;
	public EstadoTicket estadoActual;
	public List<HistorialEstadoTicketDTO> historialesEstado;
	public List<HistorialClasificacionDTO> historialesClasificacion;
	public List<IntervencionDTO> intervenciones;
	
	public TicketDTO(Integer nroTicket, Integer nroLegajo, GrupoDTO grupoAsignado, ClasificacionDTO clasificacion, Date fechaYHora, EstadoTicket estadoActual) {
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
	
	public EstadoTicket getEstadoActual() {
		return estadoActual;
	}
	
	public void setEstadoActual(EstadoTicket estadoActual) {
		this.estadoActual = estadoActual;
	}
	
	public void setHistoriales(List<HistorialEstadoTicketDTO> historiales) {
		this.historialesEstado = historiales;
	}

	public void setHistorialesClasificacion(List<HistorialClasificacionDTO> historiales) {
		this.historialesClasificacion = historiales;
	}
	
	public void addHistorialclasificacion(HistorialClasificacionDTO ultimoHistorial) {
		this.historialesClasificacion.add(ultimoHistorial);
	}

	public HistorialClasificacionDTO getUltimoHistorialClasificacion() {
		return this.historialesClasificacion.get((this.historialesClasificacion.size()-1));
	}

	public void setIntervenciones(List<IntervencionDTO> intervencionesDto) {
		this.intervenciones = intervencionesDto;
	}

	public List<IntervencionDTO> getIntervenciones() {
		return this.intervenciones;
	}

	public void addIntervencion(IntervencionDTO nuevaIntervencion) {
		this.intervenciones.add(nuevaIntervencion);
	}

	public HistorialEstadoTicketDTO getUltimoHistorialEstado() {
		return this.historialesEstado.get((this.historialesEstado.size()-1));
	}

	public void addHistorialEstadoTicket(HistorialEstadoTicketDTO nuevoHistorial) {
		this.historialesEstado.add(nuevoHistorial);		
	}
}
