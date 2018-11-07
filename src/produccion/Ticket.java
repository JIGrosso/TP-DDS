package produccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import usuarios.Cliente;
import vistas.Principal;

public class Ticket {

	public Integer nroTicket;
	public Cliente cliente;
	public Clasificacion clasificacion;
	public Date fechaApertura;
	public Date horaApertura;
	public String descripcion;
	public String observaciones;
	public EstadoTicket estadoActual;
	public List<HistorialEstadoTicket> historialesEstado;
	public List<HistorialClasificacionTicket> historialesClasificacion;
	public List<Intervencion> intervenciones;
	
	
	public Ticket(Integer nroTicket, Cliente cliente, Clasificacion clasificacion, Date fechaApertura, Date horaApertura, String descripcion) {
		
		this.nroTicket = nroTicket;
		this.cliente = cliente;
		this.clasificacion = clasificacion;
		this.fechaApertura = fechaApertura;
		this.horaApertura = horaApertura;
		this.descripcion = descripcion;
		this.observaciones = null;
		this.estadoActual = Principal.abiertoMA;
		this.historialesEstado = new ArrayList<HistorialEstadoTicket>();
		this.historialesClasificacion = new ArrayList<HistorialClasificacionTicket>();
		this.intervenciones = new ArrayList<Intervencion>();
		
	};
	
	public Ticket(Integer nroTicket, Cliente cliente, Clasificacion clasificacion, Date fechaApertura, Date horaApertura, String descripcion, EstadoTicket estadoActual) {
		
		this.nroTicket = nroTicket;
		this.cliente = cliente;
		this.clasificacion = clasificacion;
		this.fechaApertura = fechaApertura;
		this.horaApertura = horaApertura;
		this.descripcion = descripcion;
		this.observaciones = null;
		this.estadoActual = estadoActual;
		this.historialesEstado = new ArrayList<HistorialEstadoTicket>();
		this.historialesClasificacion = new ArrayList<HistorialClasificacionTicket>();
		this.intervenciones = new ArrayList<Intervencion>();
		
	};
	
}
	
