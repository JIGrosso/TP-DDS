package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import produccion.Clasificacion;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import produccion.EstadosIntervencion;
import produccion.EstadosTicket;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public interface GestorBD {

	SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss.SSS");


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
			Integer idGrupo = Integer.valueOf(resultSet.getString("idGrupo"));
			GrupoDeResolucion grupo = mapearGrupoDeResolucion(idGrupo);
			Soporte soporte = new Soporte(nroLeg, "contrasenia", resultSet.getString("nombre"), dni, telefono, resultSet.getString("email"), grupo);

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
			ResultSet resultSet = statement.executeQuery("SELECT nextval('seqNroTicket')");
			resultSet.next();
			
			Integer nroTicket = Integer.valueOf(resultSet.getString("nextval"));
			return nroTicket;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer nroNuevoIntervencion() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nextval('seqIntervencion')");
			resultSet.next();

			Integer idIntervencion = Integer.valueOf(resultSet.getString("nextval"));
			return idIntervencion;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer nroNuevoHistorialET() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nextval('seqHistorialEstadoTicket')");
			resultSet.next();

			Integer idHistorial = Integer.valueOf(resultSet.getString("nextval"));
			return idHistorial;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer nroNuevoHistorialEI() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nextval('seqHistorialEstadoIntervencion')");
			resultSet.next();

			Integer idHistorialI = Integer.valueOf(resultSet.getString("nextval"));
			return idHistorialI;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Cliente mapearCliente(Integer nroLegajo) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database!");
			String nroLegajoConsulta = nroLegajo.toString();

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cliente WHERE nroLegajoC = "+ nroLegajoConsulta);

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

	public static Clasificacion mapearClasificacion(String clasificacion2) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

				System.out.println("Connected to PostgreSQL database!");
				String idClasificacionConsulta = clasificacion2.toString();

				Statement statement;
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion WHERE nombre = " + "'" + idClasificacionConsulta + "'");

				resultSet.next();
				Integer idClas = Integer.valueOf(resultSet.getString("idClasificacion"));
				Integer nroLegajoCreador = Integer.valueOf(resultSet.getString("nroLegajoSoporte"));
				Soporte soporte = mapearSoporte(nroLegajoCreador);
				Clasificacion clasificacion = new Clasificacion (idClas, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"), soporte);

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

		public static GrupoDeResolucion mapearGrupoDeResolucion(Integer idGrupo) {

			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

					System.out.println("Connected to PostgreSQL database!");

					String idGrupoConsulta = idGrupo.toString();

					Statement statement;
					statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM public.grupo_resolucion g WHERE g.idGrupo = " + idGrupoConsulta);
					resultSet.next();
					Integer idNuevoGrupo = Integer.valueOf(resultSet.getString("idGrupo"));

					GrupoDeResolucion grupoDeResolucion = new GrupoDeResolucion(idNuevoGrupo, resultSet.getString("nombre"), resultSet.getString("nivel"), resultSet.getString("descripcion"));
					return grupoDeResolucion;

				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

		}

		public static void guardarTicket (Ticket ticket, Soporte soporte) {
			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

				System.out.println("Connected to PostgreSQL database! --> Guardar Ticket");

				String fechaApertura = formatFecha.format(ticket.fechaYHoraApertura);
				String horaApertura = formatHora.format(ticket.fechaYHoraApertura);

				Statement statement1;
				statement1 = connection.createStatement();
				statement1.executeUpdate("INSERT INTO public.ticket VALUES (" + ticket.nroTicket + ", '" + ticket.descripcion + "', '-' , '" + horaApertura + "', '" + fechaApertura + "', " + ticket.cliente.nroLegajo + ", '" + ticket.estadoActual.idEstadoTicket + "', " + ticket.clasificacion.idClasificacion + ")");

				String fechaDesdeHistorialET = formatFecha.format(ticket.historialesEstado.get(0).fechaDesde);

				Statement statement2;
				statement2 = connection.createStatement();
				statement2.executeUpdate("INSERT INTO public.historial_estado_ticket VALUES (" + ticket.historialesEstado.get(0).idHistorialEstadoTic + ", '" + fechaDesdeHistorialET + "', null, " + ticket.nroTicket + ", '" + ticket.estadoActual.idEstadoTicket + "', " + soporte.nroLegajo + ")");


			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		}
	}
