package clasesDTO;

import java.util.Date;

public class HistorialClasificacionDTO {

	public Integer idHistorialClasificacion;
	public Integer idClasificacion;
	
	public HistorialClasificacionDTO(Integer idClasificacionHis, Integer idClasificacion) {
		this.idHistorialClasificacion = idClasificacionHis;
		this.idClasificacion = idClasificacion;
	}
}
