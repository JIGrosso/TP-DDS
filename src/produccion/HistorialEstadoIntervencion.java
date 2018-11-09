package produccion;

import java.util.Date;

import usuarios.Soporte;
import vistas.Principal;

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
		this.estado = Principal.activa;
	}
}
