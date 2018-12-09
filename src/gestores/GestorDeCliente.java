package gestores;

import usuarios.Cliente;

public class GestorDeCliente {
	
	public static Cliente mapearCliente(Integer nroLegajo) {
		Cliente cliente = GestorBD.mapearCliente(nroLegajo);
		return cliente;
	}

}
