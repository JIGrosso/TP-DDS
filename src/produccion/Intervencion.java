package produccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gestores.GestorDeIntervencion;

public class Intervencion {

	public Integer idIntervencion;
	public String observaciones;
	public Date fechaAsignacion;
	public Date fechaFin;
	public EstadoIntervencion estadoIntervencionActual;
	public List<HistorialEstadoIntervencion> historialesEstado;
	
	// Creacion primer intervencion
	
	public Intervencion(Integer idIntervencion, Date fechaAsignacion, HistorialEstadoIntervencion primerHistorial) {
		//ACTIVA
		this.idIntervencion = idIntervencion;
		this.observaciones = "Intervencion por creacion de ticket";
		this.fechaAsignacion = fechaAsignacion;
		this.fechaFin = null;
		this.estadoIntervencionActual = GestorDeIntervencion.activa;
		this.historialesEstado = new ArrayList<HistorialEstadoIntervencion>();
		this.historialesEstado.add(primerHistorial);
		
	}
	
	// Para el mapeo de intervenciones
	
	public Intervencion(Integer idIntervencion, String observaciones, Date fechaAsignacion, Date fechaFin, EstadoIntervencion estadoActual, List<HistorialEstadoIntervencion> historiales) {
		
		this.idIntervencion = idIntervencion;
		this.observaciones = observaciones;
		this.fechaAsignacion = fechaAsignacion;
		this.fechaFin = null;
		this.estadoIntervencionActual = estadoActual;
		this.historialesEstado = historiales;
		
	}

	public Integer getIdIntervencion() {
		return idIntervencion;
	}

	public void setIdIntervencion(Integer idIntervencion) {
		this.idIntervencion = idIntervencion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin() {
		this.fechaFin = new Date();
	}

	public EstadoIntervencion getEstadoIntervencionActual() {
		return estadoIntervencionActual;
	}

	public void setEstadoIntervencionActual(EstadoIntervencion estadoIntervencionActual) {
		try {
			
			this.getUltimoHistorialIntervencion().setFechaHasta();
			this.estadoIntervencionActual = estadoIntervencionActual;
		}
		catch(Exception e) {
			throw e;
		}
		
	}

	public List<HistorialEstadoIntervencion> getHistorialesEstado() {
		return historialesEstado;
	}

	public void setHistorialesEstado(List<HistorialEstadoIntervencion> historialesEstado) {
		this.historialesEstado = historialesEstado;
	}
	
	public HistorialEstadoIntervencion getUltimoHistorialIntervencion() {
		return this.historialesEstado.get(this.historialesEstado.size()-1);
	}
	
	public HistorialEstadoIntervencion getPenultimoHistorialIntervencion() {
		return this.historialesEstado.get(this.historialesEstado.size()-2);
	}
	
	
}
