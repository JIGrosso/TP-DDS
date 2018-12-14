package produccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gestores.GestorDeTicket;
import usuarios.Cliente;
import usuarios.GrupoDeResolucion;

public class Ticket {

	public Integer nroTicket;
	public Cliente cliente;
	public GrupoDeResolucion grupoAsignado;
	public Clasificacion clasificacion;
	public Date fechaYHoraApertura;
	public String descripcion;
	public String observaciones;
	public EstadoTicket estadoActual;
	public List<HistorialEstadoTicket> historialesEstado;
	public List<HistorialClasificacionTicket> historialesClasificacion;
	public List<Intervencion> intervenciones;
	
	
	public Ticket(Integer nroTicket, Cliente cliente, Clasificacion clasificacion, GrupoDeResolucion grupoAsignado, Date fechaApertura, String descripcion) {
		
		this.nroTicket = nroTicket;
		this.cliente = cliente;
		this.clasificacion = clasificacion;
		this.fechaYHoraApertura = fechaApertura;
		this.descripcion = descripcion;
		this.observaciones = null;
		this.grupoAsignado = grupoAsignado;
		this.estadoActual = GestorDeTicket.mapearEstadoTicket("ABIERTO_MA");
		this.historialesEstado = new ArrayList<HistorialEstadoTicket>();
		this.historialesClasificacion = new ArrayList<HistorialClasificacionTicket>();
		this.intervenciones = new ArrayList<Intervencion>();
		
	};
	
	public Ticket(Integer nroTicket, Cliente cliente, Clasificacion clasificacion, GrupoDeResolucion grupoAsignado, Date fechaApertura, String descripcion, String observaciones, EstadoTicket estadoActual) {
		
		this.nroTicket = nroTicket;
		this.cliente = cliente;
		this.clasificacion = clasificacion;
		this.fechaYHoraApertura = fechaApertura;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.grupoAsignado = grupoAsignado;
		this.estadoActual = estadoActual;
		this.historialesEstado = new ArrayList<HistorialEstadoTicket>();
		this.historialesClasificacion = new ArrayList<HistorialClasificacionTicket>();
		this.intervenciones = new ArrayList<Intervencion>();
		
	};
	
	public void addHistorialEstadoTicket(HistorialEstadoTicket historialEstado) {
		this.historialesEstado.add(historialEstado);
	}
	
	public void addHistorialClasificacionTicket(HistorialClasificacionTicket historialClasificacion) {
		this.historialesClasificacion.add(historialClasificacion);
	}
	
	public void setEstadoCerrado(EstadoTicket estado) {
		try {
			this.historialesEstado.get(this.historialesEstado.size()-1).cerrarHistorialEstadoTicket();
			this.historialesClasificacion.get(this.historialesClasificacion.size()-1).cerrarHistorialClasificacionTicket();
			this.estadoActual = estado;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public Clasificacion getClasificacion() {
		return this.clasificacion;
	}
	
	public Intervencion getUltimaIntervencion() {
		return this.intervenciones.get(this.intervenciones.size()-1);
	}
	
	public Intervencion getPenultimaIntervencion() {
		return this.intervenciones.get(this.intervenciones.size()-2);
	}
	
	public HistorialEstadoTicket getUlitmoHistorialET() {
		return this.historialesEstado.get(this.historialesEstado.size()-1);
	}
	
	public HistorialEstadoTicket getPenulitmoHistorialET() {
		return this.historialesEstado.get(this.historialesEstado.size()-2);
	}
	
	public HistorialClasificacionTicket getUlitmoHistorialC() {
		return this.historialesClasificacion.get(this.historialesClasificacion.size()-1);
	}
	
	public HistorialClasificacionTicket getPenulitmoHistorialC() {
		return this.historialesClasificacion.get(this.historialesClasificacion.size()-2);
	}
	
	public void setClasificacion (Clasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}
	
	public List<Intervencion> getIntervenciones(){
		return this.intervenciones;
	}

	public void setEstado(EstadoTicket estadoTicket) {
		this.estadoActual = estadoTicket;
	}

	public void actualizarIntervencion(Intervencion intervencionA) {
		for(Intervencion aux: intervenciones) {
			if(aux.equals(intervencionA)) {
				intervenciones.remove(aux);
				intervenciones.add(intervencionA);
				break;
			}
		}
	}

	
}
	
