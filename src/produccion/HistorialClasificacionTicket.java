package produccion;

import java.util.Date;

public class HistorialClasificacionTicket {

	public Date fechaDesde;
	public Date fechaHasta;
	public Clasificacion clasificacion;
	
	public HistorialClasificacionTicket(Clasificacion clasif, Integer nroTick) {
		Date fechaD = new Date();
		this.fechaDesde = fechaD;
		this.clasificacion = clasif;
	}
	
	public void cerrarHistorialClasificacionTicket() {
		this.fechaHasta = new Date();
	}
}
