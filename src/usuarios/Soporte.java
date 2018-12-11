package usuarios;

public class Soporte {

	public String nombre;
	public Integer dni;
	public String email;
	public String telefono;
	public Integer telefonoInterno;
	public String ubicacion;
	public String cargo;
	public Integer nroLegajo;
	public String password;
	public GrupoDeResolucion grupo;
	
	public Soporte(Integer nroLegajo, String password, String nombre, Integer dni, String telefono, Integer interno, String ubicacion,  String email, String cargo, GrupoDeResolucion grupo) {
		this.nroLegajo = nroLegajo;
		this.password = password;
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
		this.telefonoInterno = interno;
		this.ubicacion = ubicacion;
		this.email = email;
		this.cargo = cargo;
		this.grupo = grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getNroLegajo() {
		return nroLegajo;
	}

	public void setNroLegajo(Integer nroLegajo) {
		this.nroLegajo = nroLegajo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GrupoDeResolucion getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDeResolucion grupo) {
		this.grupo = grupo;
	}
}
