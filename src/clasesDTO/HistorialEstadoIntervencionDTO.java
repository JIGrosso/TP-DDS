package clasesDTO;

import java.util.Date;

import usuarios.Soporte;

public class HistorialEstadoIntervencionDTO {
	
	public Integer idHistorialEstadoIntervencion;
	public Date fechaDesde;
	public Date fechaHasta;
	public Soporte actor;
	public EstadoIntervencionDTO estado;
	
	public HistorialEstadoIntervencionDTO(int idHistorialEstadoInt, Date desde, Date hasta,
			EstadoIntervencionDTO estadoIntervencionDTO, Soporte soporte) {
		this.idHistorialEstadoIntervencion = idHistorialEstadoInt;
		this.fechaDesde = desde;
		this.fechaHasta = hasta;
		this.estado = estadoIntervencionDTO;
		this.actor = soporte;
	}

	public void setFechaHasta(Date fechaActual) {
		
	}

}
