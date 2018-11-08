package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import produccion.Clasificacion;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import produccion.EstadosIntervencion;
import produccion.EstadosTicket;
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
			String telefono = resultSet.getString("telefono");
			// Tuve que cambiar el telefono a String porque sino con Integer me daba out of range (el telefono es mas grande que el maximo tamaño Integer disponible)
			Soporte soporte = new Soporte(nroLeg, "contrasenia", resultSet.getString("nombre"), dni, telefono, resultSet.getString("email"));
			
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
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			System.out.println("Connected to PostgreSQL database!");
			String nroLegajoConsulta = nroLegajo.toString();
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cliente WHERE nroLegajo = "+nroLegajoConsulta);
			
			resultSet.next(); 
			Integer nroLeg = Integer.valueOf(resultSet.getString("nrolegajoc"));
			Integer dni = Integer.valueOf(resultSet.getString("dni"));
			String telefono = resultSet.getString("telefono");
			Cliente cliente = new Cliente(resultSet.getString("nombre"), dni, resultSet.getString("email"), telefono, nroLeg);
			
			return cliente;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Clasificacion mapearClasificacion(Integer idClasificacion) {
	
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
				
				System.out.println("Connected to PostgreSQL database!");
				String idClasificacionConsulta = idClasificacion.toString();
				
				Statement statement;
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion WHERE idClasificacion = " + idClasificacionConsulta);
				
				resultSet.next(); 
				Integer idClas = Integer.valueOf(resultSet.getString("idClasificacion"));
				Integer nroLegajoCreador = Integer.valueOf(resultSet.getString("nroLegajoSoporte"));
				Soporte soporte = mapearSoporte(nroLegajoCreador);
				Clasificacion clasificacion = new Clasificacion (idClas, resultSet.getString("nombre"), resultSet.getString("descripcion"), soporte);
				
				return clasificacion;
			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

	}

	public static EstadoTicket mapearEstadoTicket(String idEstado) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			System.out.println("Connected to PostgreSQL database!");
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.estado_ticket WHERE idEstadoTicket = '" + idEstado + "'");
			
			resultSet.next(); 
			
			String idEstadosTicket = resultSet.getString("idEstadoTicket");
			
			EstadoTicket estadoTicket = new EstadoTicket();
			
			if (idEstadosTicket.equals("ABIERTO_MA")) {
				estadoTicket.setIdEstadoTicket(EstadosTicket.ABIERTO_MA);
				estadoTicket.setNombre(resultSet.getString("nombre"));
				estadoTicket.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosTicket.equals("ABIERTO_D")) {
				estadoTicket.setIdEstadoTicket(EstadosTicket.ABIERTO_D);
				estadoTicket.setNombre(resultSet.getString("nombre"));
				estadoTicket.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosTicket.equals("SOLUCIONADO_OK")) {
				estadoTicket.setIdEstadoTicket(EstadosTicket.SOLUCIONADO_OK);
				estadoTicket.setNombre(resultSet.getString("nombre"));
				estadoTicket.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosTicket.equals("CERRADO")) {
				estadoTicket.setIdEstadoTicket(EstadosTicket.CERRADO);
				estadoTicket.setNombre(resultSet.getString("nombre"));
				estadoTicket.setDescripcion(resultSet.getString("descripcion"));
			}

			return estadoTicket;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static EstadoIntervencion mapearEstadoIntervencion(String idEstadoIntervencion) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			System.out.println("Connected to PostgreSQL database!");
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.estado_intervencion WHERE idEstadoIntervencion = '" + idEstadoIntervencion + "'");
			
			resultSet.next(); 
			
			String idEstadosIntervencion = resultSet.getString("idEstadoIntervencion");
			
			EstadoIntervencion estadoIntervencion = new EstadoIntervencion();
			
			if (idEstadosIntervencion.equals("ASIGNADA")) {
				estadoIntervencion.setIdEstadoInt(EstadosIntervencion.ASIGNADA);
				estadoIntervencion.setNombre(resultSet.getString("nombre"));
				estadoIntervencion.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosIntervencion.equals("ACTIVA")) {
				estadoIntervencion.setIdEstadoInt(EstadosIntervencion.ACTIVA);
				estadoIntervencion.setNombre(resultSet.getString("nombre"));
				estadoIntervencion.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosIntervencion.equals("ESPERA")) {
				estadoIntervencion.setIdEstadoInt(EstadosIntervencion.ESPERA);
				estadoIntervencion.setNombre(resultSet.getString("nombre"));
				estadoIntervencion.setDescripcion(resultSet.getString("descripcion"));
			}
			
			else if (idEstadosIntervencion.equals("CERRADA")) {
				estadoIntervencion.setIdEstadoInt(EstadosIntervencion.CERRADA);
				estadoIntervencion.setNombre(resultSet.getString("nombre"));
				estadoIntervencion.setDescripcion(resultSet.getString("descripcion"));
			}

			return estadoIntervencion;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
