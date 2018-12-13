package clasesDTO;

import produccion.EstadoIntervencion;

import java.util.Date;

public class IntervencionDTO {
	public Integer idIntervencion;
	public Date fechaAsignacion;
	public EstadoIntervencion estadoIntervencionActual;

	public IntervencionDTO(Integer idIntervencion, Date fechaAsignacion, EstadoIntervencion estadoIntervencion) {
		this.idIntervencion = idIntervencion;
		this.fechaAsignacion = fechaAsignacion;
		this.estadoIntervencionActual = estadoIntervencion;
	}

	public Integer getId() {
		return this.idIntervencion;
	}

	public EstadoIntervencion getEstado() {
		return estadoIntervencionActual;
	}

}
