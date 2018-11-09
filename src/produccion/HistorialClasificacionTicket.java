package produccion;

import java.util.Date;

public class HistorialClasificacionTicket {

	public Date fechaDesde;
	public Date fechaHasta;
	public Clasificacion clasificacion;
	
	public HistorialClasificacionTicket(Clasificacion clasificacion) {
		this.fechaDesde = new Date();
		this.clasificacion = clasificacion;
	}
	
	public void cerrarHistorialClasificacionTicket() {
		this.fechaHasta = new Date();
	}
}
