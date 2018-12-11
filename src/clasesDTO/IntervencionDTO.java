package clasesDTO;

import java.util.List;

import produccion.EstadoIntervencion;

import java.util.Date;

public class IntervencionDTO {
	public Integer idIntervencion;
	public Date fechaAsignacion;
	public Date fechaFin;
	public EstadoIntervencion estadoIntervencionActual;
	public List<HistorialEstadoIntervencionDTO> historialEstados;
	
	public IntervencionDTO(Integer idInt, Date fechaAs, Date fechaFn, EstadoIntervencion estadoIntervencion) {
		this.idIntervencion = idInt;
		this.fechaAsignacion = fechaAs;
		this.fechaFin = fechaFn;
		this.estadoIntervencionActual = estadoIntervencion;
		this.historialEstados = null;
	}

	public Integer getId() {
		return this.idIntervencion;
	}

	public EstadoIntervencion getEstado() {
		return estadoIntervencionActual;
	}

	public HistorialEstadoIntervencionDTO getUltimoHistEstInt() {
		return this.historialEstados.get(this.historialEstados.size()-1);
	}

}
