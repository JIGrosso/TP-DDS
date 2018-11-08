package usuarios;

public class GrupoDeResolucion {

	public Integer idGrupo;
	public String nombre;
	public Nivel nivel;
	public String descripcion;
	
	public GrupoDeResolucion(Integer idGrupo, String nombre, String idNivel, String descripcion) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.nivel = Nivel.valueOf(idNivel);
		this.descripcion = descripcion;
	}
}
