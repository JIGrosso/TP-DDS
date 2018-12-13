package clasesDTO;

import java.util.Date;

import produccion.EstadoIntervencion;

public class HistorialEstadoIntervencionDTO {
	
	public Integer idHistorialEstadoIntervencion;
	public Date fechaDesde;
	public Date fechaHasta;
	public Integer nroLegajoSoporte;
	public EstadoIntervencion estado;
	
	public HistorialEstadoIntervencionDTO(Integer idHistorialEstadoInt, Date desde, Date hasta,
			EstadoIntervencion estadoIntervencion, Integer soporte) {
		
		this.idHistorialEstadoIntervencion = idHistorialEstadoInt;
		this.fechaDesde = desde;
		this.fechaHasta = hasta;
		this.estado = estadoIntervencion;
		this.nroLegajoSoporte = soporte;
	}

}
