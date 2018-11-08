package usuarios;

public class Cliente {

	public String nombre;
	public Integer dni;
	public String email;
	public String telefono;
	public Integer nroLegajo;
	
	public Cliente(String nombre, Integer dni, String email, String telefono, Integer nroLegajo) {
		this.nombre = nombre;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.nroLegajo = nroLegajo;
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



}
