package produccion;

import java.util.Date;
import java.util.List;

import usuarios.GrupoDeResolucion;

public class Intervencion {

	public Integer idIntervencion;
	public String observaciones;
	public Date fechaAsignacion;
	public Date fechaFin;
	public GrupoDeResolucion grupoAsignado;
	public EstadoIntervencion estadoIntervencionActual;
	public List<HistorialEstadoIntervencion> historialesEstado;
}
