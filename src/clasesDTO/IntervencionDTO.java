package clasesDTO;

import java.util.List;
import java.util.Date;

public class IntervencionDTO {
	public Integer idIntervencion;
	public Date fechaAsignacion;
	public Date fechaFin;
	public EstadoIntervencionDTO estadoIntervencionActual;
	public List<HistorialEstadoIntervencionDTO> historialEstados;
	
	public IntervencionDTO(Integer idInt, Date fechaAs, Date fechaFn, EstadoIntervencionDTO estadoIntervencion) {
		this.idIntervencion = idInt;
		this.fechaAsignacion = fechaAs;
		this.fechaFin = fechaFn;
		this.estadoIntervencionActual = estadoIntervencion;
		this.historialEstados = null;
	}

	public Integer getId() {
		return this.idIntervencion;
	}

	public EstadoIntervencionDTO getEstado() {
		return estadoIntervencionActual;
	}

	public HistorialEstadoIntervencionDTO getUltimoHistEstInt() {
		return this.historialEstados.get(this.historialEstados.size()-1);
	}

	public void setHistoriales(List<HistorialEstadoIntervencionDTO> historial) {
		this.historialEstados = historial;
		
	}

	public void addHistorial(HistorialEstadoIntervencionDTO nuevo) {
		this.historialEstados.add(nuevo);
	}

	public void setEstado(EstadoIntervencionDTO nuevoEstado) {
		this.estadoIntervencionActual = nuevoEstado;
	}

}
