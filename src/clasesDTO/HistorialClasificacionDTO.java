package clasesDTO;

import java.util.Date;

public class HistorialClasificacionDTO {

	public Integer idHistorialClasificacion;
	public Date fechaDesde;
	public Date fechaHasta;
	public Integer idClasificacion;
	
	public HistorialClasificacionDTO(Integer idClasificacionHis, Date fechaDesde, Date fechaHasta, Integer idClasificacion) {
		this.idHistorialClasificacion = idClasificacionHis;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idClasificacion = idClasificacion;
	}
	
	public HistorialClasificacionDTO(Integer idClasificacionHis, Date fechaDesde, Date fechaHasta, Integer idClasificacion) {
		this.idHistorialClasificacion = idClasificacionHis;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idClasificacion = idClasificacion;
	}
}
