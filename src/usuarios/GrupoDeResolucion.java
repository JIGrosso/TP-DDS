package usuarios;

public class GrupoDeResolucion {

	public Integer idGrupo;
	public String nombre;
	public Nivel nivel;
	public String descripcion;
	
	public GrupoDeResolucion(Integer idGrupo, String nombre, String descripcion) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.nivel = Nivel.NIVEL_0;
		this.descripcion = descripcion;
	}
}
