package produccion;

import java.util.Date;

import gestores.GestorDeIntervencion;
import usuarios.Soporte;

public class HistorialEstadoIntervencion {

	public Integer idHistorialEstadoInt;
	public Date fechaDesde;
	public Date fechaHasta;
	public Soporte actor;
	public EstadoIntervencion estado;
	
	public HistorialEstadoIntervencion(Integer idHistorial, Date fechaDesde, Soporte actor) {
		this.idHistorialEstadoInt = idHistorial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = null;
		this.actor = actor;
		this.estado = GestorDeIntervencion.activa;
	}
	
	public HistorialEstadoIntervencion(Integer idHistorial, Date fechaDesde, Date fechaHasta, Soporte actor, EstadoIntervencion estado) {
		this.idHistorialEstadoInt = idHistorial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.actor = actor;
		this.estado = estado;
	}
	
}
