package usuarios;

public class GrupoDeResolucion {

	public Integer idGrupo;
	public String nombre;
	public Nivel nivel;
	public String descripcion;
	
	public GrupoDeResolucion(Integer idGrupo, String nombre, String nivel, String descripcion) {
		// Verificar el string NIVEL con la enumeracion
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.nivel = Nivel.NIVEL_0;
		this.descripcion = descripcion;
		
		System.out.println("Grupo de Resolucion creado: IdGrupo: " + idGrupo + ", Nombre: " + nombre);
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
}
