package produccion;

import java.util.Date;

import gestores.GestorBD;
import gestores.GestorDeTicket;
import usuarios.Soporte;

public class HistorialEstadoTicket {

	public Integer idHistorialEstadoTic;
	public Date fechaDesde;
	public Date fechaHasta;
	public EstadoTicket estado;
	public Soporte actor;

	public HistorialEstadoTicket(Soporte soporte, Date fechaCreacion) {
		
		this.idHistorialEstadoTic = GestorBD.nroNuevoHistorialET();
		this.fechaDesde = fechaCreacion;
		this.fechaHasta = null;
		this.estado = GestorDeTicket.abiertoMA;
		this.actor = soporte;
	};

	public HistorialEstadoTicket(Integer idHistorial, Soporte soporte, EstadoTicket estado, Date fechaCreacion, Date fechaHasta) {
		
		this.idHistorialEstadoTic = idHistorial;
		this.fechaDesde = fechaCreacion;
		this.fechaHasta = fechaHasta;
		this.estado = estado;
		this.actor = soporte;
	}

	public void cerrarHistorialEstadoTicket() {
		this.fechaHasta = new Date();
	}
	
	public void cerrarHistorialEstadoTicket(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}
