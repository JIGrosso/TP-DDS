package produccion;

import java.util.Date;

import gestores.GestorBD;

public class HistorialClasificacionTicket {

	public Integer idHistorialClasificacion;
	public Date fechaDesde;
	public Date fechaHasta;
	public Clasificacion clasificacion;
	
	public HistorialClasificacionTicket(Clasificacion clasificacion, Date fechaCreacion) {
		this.idHistorialClasificacion = GestorBD.nroNuevoHistorialC();
		this.fechaDesde = fechaCreacion;
		this.clasificacion = clasificacion;
	}
	
	public HistorialClasificacionTicket(Integer idHistorial, Date fechaDesde, Date fechaHasta, Clasificacion clasificacion) {
		this.idHistorialClasificacion = idHistorial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.clasificacion = clasificacion;
	}
	
	public void cerrarHistorialClasificacionTicket() {
		this.fechaHasta = new Date();
	}
}
