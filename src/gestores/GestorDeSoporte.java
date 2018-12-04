package gestores;

import java.util.ArrayList;
import java.util.List;

import usuarios.Soporte;

public class GestorDeSoporte {
	
	public static List<Soporte> soportesMapeados = new ArrayList<Soporte>();
	
	public static boolean validarSoporte(Integer nroLegajo, String password) {
		return GestorBD.validarSoporte(nroLegajo, password);
	}
	
	public static Soporte mapearSoporte(Integer nroLegajo) {
		return GestorBD.mapearSoporte(nroLegajo);
	}
	
}
