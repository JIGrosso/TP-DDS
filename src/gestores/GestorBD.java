package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import produccion.Clasificacion;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import usuarios.Cliente;
import usuarios.Soporte;

public interface GestorBD {
		
	public static Soporte mapearSoporte(Integer nroLegajo) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			System.out.println("Connected to PostgreSQL database!");
			String nroLegajoConsulta = nroLegajo.toString();
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.soporte WHERE nroLegajo = "+nroLegajoConsulta);
			
			resultSet.next(); 
			Integer nroLeg = Integer.valueOf(resultSet.getString("nrolegajo"));
			Integer dni = Integer.valueOf(resultSet.getString("dni"));
			Integer telefono = Integer.valueOf(resultSet.getString("telefono"));
			Soporte soporte = new Soporte(nroLeg, resultSet.getString("contrasenia"), resultSet.getString("nombre"), dni, telefono, resultSet.getString("email"));
			
			return soporte;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	};
	
	public static Integer nroNuevoTicket() {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			System.out.println("Connected to PostgreSQL database!");
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT MAX(nroticket) FROM public.ticket");
			resultSet.next();
			
			if(!resultSet.next()) {
				return 1;
			}
			else {
				Integer nroTicket = Integer.valueOf(resultSet.getString("max"));
				return nroTicket+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Implementar mapeos
	
	public static Cliente mapearCliente(Integer nroLegajo) {
		return null;
	}

	public static Clasificacion mapearClasificacion(Integer idClasificacion) {
		return null;
	}

	public static EstadoTicket mapearEstadoTicket(String idEstado) {
		return null;
	}

	public static EstadoIntervencion mapearEstadoIntervencion(String string) {
		return null;
	}
	
}
