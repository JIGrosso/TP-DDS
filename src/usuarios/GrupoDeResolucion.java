package usuarios;

import java.util.ArrayList;
import java.util.List;

import produccion.Intervencion;

public class GrupoDeResolucion {

	public Integer idGrupo;
	public String nombre;
	public Nivel nivel;
	public String descripcion;
	public List<Intervencion> intervenciones;
	
	public GrupoDeResolucion(Integer idGrupo, String nombre, String nivel, String descripcion) {
		// Verificar el string NIVEL con la enumeracion
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.nivel = Nivel.NIVEL_0;
		this.descripcion = descripcion;
		this.intervenciones = new ArrayList<Intervencion>();
	}
	
	public GrupoDeResolucion(Integer idGrupo, String nombre, String nivel, String descripcion, List<Intervencion> intervenciones) {
		// Verificar el string NIVEL con la enumeracion
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.nivel = Nivel.NIVEL_0;
		this.descripcion = descripcion;
		this.intervenciones = intervenciones;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Intervencion> getIntervenciones() {
		return this.intervenciones;
	}
}
