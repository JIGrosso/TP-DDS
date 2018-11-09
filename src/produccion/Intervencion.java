package produccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import usuarios.GrupoDeResolucion;
import vistas.Principal;

public class Intervencion {

	public Integer idIntervencion;
	public String observaciones;
	public Date fechaAsignacion;
	public Date fechaFin;
	public GrupoDeResolucion grupoAsignado;
	public EstadoIntervencion estadoIntervencionActual;
	public List<HistorialEstadoIntervencion> historialesEstado;
	
	public Intervencion(Integer idIntervencion, Date fechaAsignacion, GrupoDeResolucion grupo, HistorialEstadoIntervencion primerHistorial) {
		//ACTIVA
		this.idIntervencion = idIntervencion;
		this.observaciones = "Intervencion por creacion de ticket";
		this.fechaAsignacion = fechaAsignacion;
		this.fechaFin = null;
		this.grupoAsignado = grupo;
		this.estadoIntervencionActual = Principal.activa;
		this.historialesEstado = new ArrayList<HistorialEstadoIntervencion>();
		this.historialesEstado.add(primerHistorial);
		
	}
}
