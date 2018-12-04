package produccion;

import java.util.Date;

import gestores.GestorBD;
import gestores.GestorDeTicket;
import usuarios.Soporte;

public class HistorialEstadoTicket implements GestorBD{

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

	public HistorialEstadoTicket(Soporte soporte, EstadoTicket estado, Date fechaCreacion) {
		idHistorialEstadoTic = null;
		fechaDesde = fechaCreacion;
		this.estado = estado;
		this.actor = soporte;
	}

	public void cerrarHistorialEstadoTicket() {
		this.fechaHasta = new Date();
	}
}
