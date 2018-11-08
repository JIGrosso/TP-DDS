package produccion;

import java.util.Date;

import usuarios.Soporte;
import vistas.Principal;

public class HistorialEstadoTicket {
	
	public Integer idHistorialEstadoTic;
	public Date fechaDesde;
	public Date fechaHasta;
	public EstadoTicket estado;
	public Soporte actor;
	
	public HistorialEstadoTicket(Soporte soporte) {
		//idHistorialEstadoTic = Null, ver en persistencia
		Date fechaD = new Date();
		this.estado = Principal.abiertoMA;
		this.actor = soporte;
	}
	
	public HistorialEstadoTicket(Soporte soporte, EstadoTicket estado) {
		//idHistorialEstadoTic = Null, ver en persistencia
		Date fechaD = new Date();
		this.estado = estado;
		this.actor = soporte;
	}
	
	public void cerrarHistorialEstadoTicket() {
		this.fechaHasta = new Date();
	}
}
