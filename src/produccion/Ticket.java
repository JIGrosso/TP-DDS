package produccion;

import java.util.Date;
import java.util.List;

import usuarios.Cliente;

public class Ticket {

	public Integer nroTicket;
	public Date fechaApertura;
	public Date horaApertura;
	public String descripcion;
	public String observaciones;
	public Clasificacion clasificacion;
	public EstadoTicket estadoActual;
	public List<HistorialEstadoTicket> historialesEstado;
	public List<HistorialClasificacionTicket> historialesClasificacion;
	public List<Intervencion> intervenciones;
	public Cliente nroCliente;
}
	
