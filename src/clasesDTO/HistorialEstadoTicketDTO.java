package clasesDTO;

import java.util.Date;

public class HistorialEstadoTicketDTO {

	public Integer idHistorial;
	public Date fechaDesde;
	public Date fechaHasta;
	public String estado;
	public Integer legajoCreador;
	
	public HistorialEstadoTicketDTO(Integer idHistorial, Date fechaDesde, Date fechaHasta, String estado, Integer legajoCreador) {
		this.idHistorial = idHistorial;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.estado = estado;
		this.legajoCreador = legajoCreador;
	}
	
	
}
