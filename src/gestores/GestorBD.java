package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoIntervencionDTO;
import clasesDTO.EstadoTicketDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.HistorialClasificacionDTO;
import clasesDTO.HistorialEstadoIntervencionDTO;
import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.IntervencionDTO;
import clasesDTO.TicketDTO;
import produccion.Clasificacion;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import produccion.EstadosIntervencion;
import produccion.EstadosTicket;
import produccion.HistorialEstadoIntervencion;
import produccion.Intervencion;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public class GestorBD {

	private static SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss.SSS");
	private static SimpleDateFormat parseFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static boolean validarSoporte(Integer nroLegajo, String password) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM soporte WHERE nroLegajo = " + nroLegajo +" AND contrasenia = '" + password + "'");
			
			if(!resultSet.next()) {
				connection.close();
				return false;
			}
			else {
				connection.close();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static Soporte mapearSoporte(Integer nroLegajo) {
		
		Soporte nuevoSoporte = null;
		
		for (int i=0; i < GestorDeSoporte.soportesMapeados.size(); i++) {
			if (GestorDeSoporte.soportesMapeados.get(i).nroLegajo.equals(nroLegajo)) {
				nuevoSoporte = GestorDeSoporte.soportesMapeados.get(i);
			}
		}
				
		if (nuevoSoporte != null) {
			System.out.println("Soporte: " + nuevoSoporte.nroLegajo + " ya encontrado en programa.");
			return nuevoSoporte;
		}		
		
		else {

			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

				String nroLegajoConsulta = nroLegajo.toString();
	
				Statement statement;
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM public.soporte WHERE nroLegajo = "+ nroLegajoConsulta);
	
				resultSet.next();
				Integer nroLeg = Integer.valueOf(resultSet.getString("nrolegajo"));
				Integer dni = Integer.valueOf(resultSet.getString("dni"));
				String telefono = resultSet.getString("telefono");
				Integer idGrupo = Integer.valueOf(resultSet.getString("idGrupo"));
				
				connection.close();
				
				GrupoDeResolucion grupo = mapearGrupoDeResolucion(idGrupo);
				Soporte soporte = new Soporte(nroLeg, "contrasenia", resultSet.getString("nombre"), dni, telefono, resultSet.getString("email"), grupo);
				GestorDeSoporte.soportesMapeados.add(soporte);
				
				System.out.println("Soporte: " + soporte.nroLegajo + " mapeado desde BDD.");
				
				return soporte;
	
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	public static Integer nroNuevoTicket() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT last_value FROM seqNroTicket");
			resultSet.next();
			
			Integer nroTicket = resultSet.getInt("last_value");
			
			resultSet.close();
			connection.close();
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
			ResultSet resultSet = statement.executeQuery("SELECT last_value FROM seqIntervencion");
			resultSet.next();
			
			Integer idIntervencion = resultSet.getInt("last_value");
			
			statement.execute("SELECT nextval('seqIntervencion')");
			
			resultSet.close();
			connection.close();
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
			ResultSet resultSet = statement.executeQuery("SELECT last_value FROM seqHistorialEstadoTicket");
			resultSet.next();

			Integer idHistorial = resultSet.getInt("last_value");
			
			statement.execute("SELECT nextval('seqHistorialEstadoTicket')");
			
			resultSet.close();
			connection.close();
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
			ResultSet resultSet = statement.executeQuery("SELECT last_value FROM seqHistorialEstadoIntervencion");
			resultSet.next();

			Integer idHistorialEI = resultSet.getInt("last_value");
			
			statement.execute("SELECT nextval('seqHistorialEstadoIntervencion')");
			
			resultSet.close();
			connection.close();
			return idHistorialEI;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer nroNuevoHistorialC() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT last_value FROM seqHistorialClasificacion");
			resultSet.next();

			Integer idHistorialC = resultSet.getInt("last_value");
			
			statement.execute("SELECT nextval('seqHistorialClasificacion')");
			
			resultSet.close();
			connection.close();
			return idHistorialC;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Cliente mapearCliente(Integer nroLegajo) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			String nroLegajoConsulta = nroLegajo.toString();

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cliente WHERE nroLegajoC = "+ nroLegajoConsulta);

			resultSet.next();

			Integer nroLeg = Integer.valueOf(resultSet.getString("nrolegajoc"));
			Integer dni = Integer.valueOf(resultSet.getString("dni"));
			String telefono = resultSet.getString("telefono");
			Cliente cliente = new Cliente(resultSet.getString("nombre"), dni, resultSet.getString("email"), telefono, nroLeg);

			resultSet.close();
			connection.close();
			return cliente;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<Clasificacion> mapearClasificaciones() {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion");

			ArrayList<Clasificacion> resultado = new ArrayList<Clasificacion>();

			while(resultSet.next()) {
				Integer idClasificacion = resultSet.getInt("idClasificacion");
				Integer nroLegajoCreador = resultSet.getInt("nroLegajoSoporte");
				Soporte soporte = mapearSoporte(nroLegajoCreador);
				Clasificacion clasificacion = new Clasificacion (idClasificacion, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"), soporte);
				resultado.add(clasificacion);
			}
			
			connection.close();
			return resultado;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Clasificacion mapearClasificacion(Integer idClasificacion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion WHERE idClasificacion = "+ idClasificacion);

			resultSet.next();
			
			Integer nroLegajoCreador = resultSet.getInt("nroLegajoSoporte");
		
			Statement statement2;
			statement2 = connection.createStatement();
			ResultSet resultSet2 = statement2.executeQuery("SELECT idGrupo FROM public.asocia WHERE idClasificacion = "+ idClasificacion);
			
			Integer idGrupo;
			ArrayList<Integer> idGruposAsociados = new ArrayList<Integer>();
			
			while(resultSet2.next()) {
				idGrupo = resultSet2.getInt("idGrupo");
				idGruposAsociados.add(idGrupo);
			}
			
			connection.close();
			
			Soporte soporte = mapearSoporte(nroLegajoCreador);
			Clasificacion clasificacion = new Clasificacion (idClasificacion, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"), soporte);
			
			GrupoDeResolucion grupo;
			
			for(int i = 0; i < idGruposAsociados.size(); i++) {
				grupo = mapearGrupoDeResolucion(idGruposAsociados.get(i));
				clasificacion.gruposPertenecientes.add(grupo);
			}
			
			return clasificacion;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static EstadoTicket mapearEstadoTicket(String idEstado) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

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

			resultSet.close();
			connection.close();
			return estadoTicket;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
}
	
	public static EstadoTicketDTO mapearEstadoTicketDTO(String idEstado) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.estado_ticket WHERE idEstadoTicket = '" + idEstado + "'");

			resultSet.next();

			String idEstadosTicket = resultSet.getString("idEstadoTicket");

			EstadoTicketDTO estadoTicketDTO = new EstadoTicketDTO();

			if (idEstadosTicket.equals("ABIERTO_MA")) {
				estadoTicketDTO.setIdEstadoTicket(EstadosTicket.ABIERTO_MA);
				estadoTicketDTO.setNombre(resultSet.getString("nombre"));
				estadoTicketDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosTicket.equals("ABIERTO_D")) {
				estadoTicketDTO.setIdEstadoTicket(EstadosTicket.ABIERTO_D);
				estadoTicketDTO.setNombre(resultSet.getString("nombre"));
				estadoTicketDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosTicket.equals("SOLUCIONADO_OK")) {
				estadoTicketDTO.setIdEstadoTicket(EstadosTicket.SOLUCIONADO_OK);
				estadoTicketDTO.setNombre(resultSet.getString("nombre"));
				estadoTicketDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosTicket.equals("CERRADO")) {
				estadoTicketDTO.setIdEstadoTicket(EstadosTicket.CERRADO);
				estadoTicketDTO.setNombre(resultSet.getString("nombre"));
				estadoTicketDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			return estadoTicketDTO;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static EstadoIntervencion mapearEstadoIntervencion(String idEstadoIntervencion) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

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
			
			resultSet.close();
			connection.close();
			return estadoIntervencion;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static EstadoIntervencionDTO mapearEstadoIntervencionDTO(String idEstadoIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.estado_intervencion WHERE idEstadoIntervencion = '" + idEstadoIntervencion + "'");

			resultSet.next();

			String idEstadosIntervencion = resultSet.getString("idEstadoIntervencion");

			EstadoIntervencionDTO estadoIntervencionDTO = new EstadoIntervencionDTO();

			if (idEstadosIntervencion.equals("ASIGNADA")) {
				estadoIntervencionDTO.setIdEstadoInt(EstadosIntervencion.ASIGNADA);
				estadoIntervencionDTO.setNombre(resultSet.getString("nombre"));
				estadoIntervencionDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosIntervencion.equals("ACTIVA")) {
				estadoIntervencionDTO.setIdEstadoInt(EstadosIntervencion.ACTIVA);
				estadoIntervencionDTO.setNombre(resultSet.getString("nombre"));
				estadoIntervencionDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosIntervencion.equals("ESPERA")) {
				estadoIntervencionDTO.setIdEstadoInt(EstadosIntervencion.ESPERA);
				estadoIntervencionDTO.setNombre(resultSet.getString("nombre"));
				estadoIntervencionDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			else if (idEstadosIntervencion.equals("CERRADA")) {
				estadoIntervencionDTO.setIdEstadoInt(EstadosIntervencion.CERRADA);
				estadoIntervencionDTO.setNombre(resultSet.getString("nombre"));
				estadoIntervencionDTO.setDescripcion(resultSet.getString("descripcion"));
			}

			return estadoIntervencionDTO;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static GrupoDeResolucion mapearGrupoDeResolucion(Integer idGrupo) {
		
		GrupoDeResolucion nuevoGrupo = null;
		
		for (int i=0; i < GestorDeGrupo.gruposMapeados.size(); i++) {
			if (GestorDeGrupo.gruposMapeados.get(i).idGrupo == idGrupo) {
				nuevoGrupo = GestorDeGrupo.gruposMapeados.get(i);
			}
		}
		if (nuevoGrupo != null) {
			System.out.println("Grupo: " + nuevoGrupo.nombre + " ya encontrado en programa.");
			return nuevoGrupo;
		}
					
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
				
				String idGrupoConsulta = idGrupo.toString();
				
				// Informacion del grupo
				
				Statement infoGrupo;
				infoGrupo = connection.createStatement();
				ResultSet resultSet = infoGrupo.executeQuery("SELECT * FROM public.grupo_resolucion g WHERE g.idGrupo = " + idGrupoConsulta);
				resultSet.next();
				Integer idNuevoGrupo = resultSet.getInt("idGrupo");
				String nombreGrupo = resultSet.getString("nombre");
				String nivelGrupo = resultSet.getString("nivel");
				String descripcionGrupo = resultSet.getString("descripcion");
				
				// Intervenciones Asignadas
				
				Statement consultaIntervenciones;
				consultaIntervenciones = connection.createStatement();
				ResultSet resultSet2 = consultaIntervenciones.executeQuery("SELECT i.idIntervencion FROM public.intervencion i WHERE i.idGrupo = " + idGrupoConsulta);

				List<Integer> auxIds = new ArrayList<Integer>();
				List<Intervencion> intervencionesAsignadas = new ArrayList<Intervencion>();
				while(resultSet2.next()) {
					Integer idIntervencion = Integer.valueOf(resultSet2.getString("idIntervencion"));
					auxIds.add(idIntervencion);
				}
				
				connection.close();

				for(int i = 0; i < auxIds.size()-1; i++) {
					Intervencion aux = mapearIntervencion(auxIds.get(i));
					intervencionesAsignadas.add(aux);
				}
				
				// Creacion de la instancia
				
				GrupoDeResolucion grupoDeResolucion = new GrupoDeResolucion(idNuevoGrupo, nombreGrupo, nivelGrupo, descripcionGrupo, intervencionesAsignadas);
				System.out.println("Grupo: " + grupoDeResolucion.nombre + " mapeado desde BDD.");
				GestorDeGrupo.gruposMapeados.add(grupoDeResolucion);
		
				
				return grupoDeResolucion;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}	
		}
	}

	public static Intervencion mapearIntervencion(Integer idIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			String idIntervencionConsulta = idIntervencion.toString();

			// Informacion de la intervencion
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.intervencion WHERE idIntervencion = "+ idIntervencionConsulta);

			resultSet.next();

			Integer idInterv = resultSet.getInt("idIntervencion");
			Date fechaAsignacion = resultSet.getDate("fechaAsignacion");
			Date fechaFin = resultSet.getDate("fechaFin");
			String idEstadoActual = resultSet.getString("idEstadoIntervencion");
			EstadoIntervencion estadoActual = buscarEstadoIntervencion(idEstadoActual);
			
			// Historiales de estado de intervencion
			
			Statement consultaHistoriales;
			consultaHistoriales = connection.createStatement();
			ResultSet resultSet2 = consultaHistoriales.executeQuery("SELECT * FROM public.historial_estado_intervencion WHERE idIntervencion = "+ idIntervencionConsulta);

			List<Integer> auxIds = new ArrayList<Integer>();
			while(resultSet2.next()) {
				Integer idHistorialEstadoIntervencion = resultSet2.getInt("idHistorialEstadoIntervencion");
				auxIds.add(idHistorialEstadoIntervencion);
				
			}
			
			connection.close();
			
			List<HistorialEstadoIntervencion> historiales = new ArrayList<HistorialEstadoIntervencion>();

			for(int i = 0; i < auxIds.size()-1; i++) {
				HistorialEstadoIntervencion aux = mapearHistorialEstadoIntervencion(auxIds.get(i));
				historiales.add(aux);
			}
			
			//Creacion de la intervencion
			
			Intervencion intervencion = new Intervencion(idInterv, resultSet.getString("observaciones"), fechaAsignacion, fechaFin, estadoActual, historiales);
			
			
			return intervencion;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static HistorialEstadoIntervencion mapearHistorialEstadoIntervencion(Integer idHistorialEstadoIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			String idHistorialConsulta = idHistorialEstadoIntervencion.toString();

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.historial_estado_intervencion WHERE idHistorialEstadoIntervencion = "+ idHistorialConsulta);

			resultSet.next();

			Integer idHistorial = resultSet.getInt("idHistorialEstadoIntervencion");
			Date fechaDesde = resultSet.getDate("fechaDesde");
			Date fechaHasta = resultSet.getDate("fechaHasta");
			String idEstadoIntervencion = resultSet.getString("idEstadoIntervencion");
			EstadoIntervencion estado = GestorBD.mapearEstadoIntervencion(idEstadoIntervencion);
			Integer nroSoporte = resultSet.getInt("nroLegajoSoporte");
			Soporte actor = mapearSoporte(nroSoporte);
				
			HistorialEstadoIntervencion aux = new HistorialEstadoIntervencion(idHistorial, fechaDesde, fechaHasta, actor, estado);
			
			resultSet.close();
			connection.close();
			return aux;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static EstadoIntervencion buscarEstadoIntervencion(String idEstadoIntervencion) {

		EstadoIntervencion estado = GestorBD.mapearEstadoIntervencion(idEstadoIntervencion);
		
		return estado;
		
	}
	
	public static EstadoTicket buscarEstadoTicket(String idEstadoTicket) {
		
		EstadoTicket estado = GestorBD.mapearEstadoTicket(idEstadoTicket);
		
		return estado;
		
	}

	public static void guardarTicket(Ticket ticket, Intervencion intervencion, Soporte soporte) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Guardar Ticket");

			String fechaApertura = formatFecha.format(ticket.fechaYHoraApertura);
			String horaApertura = formatHora.format(ticket.fechaYHoraApertura);
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("INSERT INTO public.ticket VALUES (" + ticket.nroTicket + ", '" + ticket.descripcion + "', '-' , '" + horaApertura + "', '" + fechaApertura + "', " + ticket.cliente.nroLegajo + ", '" + ticket.estadoActual.idEstadoTicket + "', " + ticket.clasificacion.idClasificacion + ")" );
			
			//Ultimo y penúltimo
			String fechaDesdeHistorialET = formatFecha.format(ticket.historialesEstado.get(0).fechaDesde);

			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("INSERT INTO public.historial_estado_ticket VALUES (" + ticket.historialesEstado.get(0).idHistorialEstadoTic + ", '" + fechaDesdeHistorialET + "', null, " + ticket.nroTicket + ", '" + ticket.estadoActual.idEstadoTicket + "', " + soporte.nroLegajo + ")" );
			
			String fechaDesdeHistorialC = formatFecha.format(ticket.historialesClasificacion.get(0).fechaDesde);
			
			Statement statement3;
			statement3 = connection.createStatement();
			statement3.executeUpdate("INSERT INTO public.historial_clasificacion_ticket VALUES (" + ticket.historialesClasificacion.get(0).idHistorialClasificacion + ", '" + fechaDesdeHistorialC + "', null, " + ticket.clasificacion.getIdClasificacion() + ", " + ticket.nroTicket + ")" );
			
			Statement statement4;
			statement4 = connection.createStatement();
			statement4.execute("SELECT nextval('seqNroTicket')");
			
			GestorBD.guardarIntervencion(intervencion, soporte, ticket.nroTicket);
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void guardarIntervencion(Intervencion intervencion, Soporte soporte, Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Guardar Intervencion");
			
			String fechaAsignacion = formatFecha.format(intervencion.fechaAsignacion);
			String fechaFin = null;
			if(!(intervencion.fechaFin == null)) {
				fechaFin = formatFecha.format(intervencion.fechaFin);
				Statement statement1;
				statement1 = connection.createStatement();
				statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ", '" + fechaFin + "')");
			}
			else {
				Statement statement1;
				statement1 = connection.createStatement();
				statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ")");

			}
			// Hacer un for para guardar todas las intervenciones que sufren cambios
			HistorialEstadoIntervencion auxHistorial = intervencion.historialesEstado.get(0);
			String fechaDesdeHistorialEI = formatFecha.format(auxHistorial.fechaDesde);
			
			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("INSERT INTO public.historial_estado_intervencion VALUES (" + auxHistorial.idHistorialEstadoInt + ", '" + fechaDesdeHistorialEI + "', null, " + intervencion.getIdIntervencion() + ", '" + intervencion.getEstadoIntervencionActual().idEstadoInt.name() + "', " + soporte.nroLegajo + ")");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static ClasificacionDTO mapearClasificacionDTO(Integer idClasificacion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			// Informacion del grupo
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion WHERE idClasificacion = " + idClasificacion);
			
			resultSet.next();
			
			String nombre = resultSet.getString("nombre");
			String alcance = resultSet.getString("descripcionAlcance");
			
			connection.close();
				
			// Creacion de la instancia
				
			ClasificacionDTO clasificacionDTO = new ClasificacionDTO(idClasificacion, nombre, alcance);
		
			return clasificacionDTO;
			
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public static ArrayList<ClasificacionDTO> mapearClasificacionesDTO() {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion");

			ArrayList<ClasificacionDTO> resultado = new ArrayList<ClasificacionDTO>();

			while(resultSet.next()) {
				Integer idClasificacion = resultSet.getInt("idClasificacion");
				ClasificacionDTO clasificacion = new ClasificacionDTO (idClasificacion, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"));
				resultado.add(clasificacion);
			}
			
			return resultado;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return null;
	}
	
	public static GrupoDTO mapearGrupoDTO(Integer idGrupo){
					
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			String idGrupoConsulta = idGrupo.toString();
				
			// Informacion del grupo
			
			Statement infoGrupo;
			infoGrupo = connection.createStatement();
			ResultSet resultSet = infoGrupo.executeQuery("SELECT * FROM public.grupo_resolucion g WHERE g.idGrupo = " + idGrupoConsulta);
			resultSet.next();
			
			Integer idNuevoGrupo = resultSet.getInt("idGrupo");
			String nombreGrupo = resultSet.getString("nombre");
			
			connection.close();
				
			// Creacion de la instancia
				
			GrupoDTO grupoDeResolucionDTO = new GrupoDTO(idNuevoGrupo, nombreGrupo);
		
			return grupoDeResolucionDTO;
			
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}	
	}
	
	public static ArrayList<GrupoDTO> mapearGruposDTO() {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.grupo_resolucion");

			ArrayList<GrupoDTO> resultado = new ArrayList<GrupoDTO>();

			while(resultSet.next()) {
				Integer idGrupo = resultSet.getInt("idGrupo");
				GrupoDTO grupo = new GrupoDTO (idGrupo, resultSet.getString("nombre"));
				resultado.add(grupo);
			}
			
			return resultado;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return null;
		
	}

	public static void modificarTicket(Ticket ticket, Intervencion intervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Modificar Ticket");

			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("UPDATE public.ticket SET idEstadoTicket = '"+ ticket.estadoActual.getIdEstadoTicket() +"' WHERE nroTicket = "+ ticket.nroTicket);

			// Implementar para el último y penúltimo
			
			String fechaHastaHistorialET = formatFecha.format(ticket.getUlitmoHistorialET().fechaHasta);
			Integer idHistorialET = ticket.getUlitmoHistorialET().idHistorialEstadoTic;
			
			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("UPDATE public.historial_estado_ticket SET fechaHasta = '"+ fechaHastaHistorialET +"' WHERE idHistorialEstadoTicket = "+ idHistorialET);
			
			String fechaHastaHistorialC = formatFecha.format(ticket.getUlitmoHistorialC().fechaHasta);
			Integer idHistorialC = ticket.getUlitmoHistorialC().idHistorialClasificacion;
			
			Statement statement3;
			statement3 = connection.createStatement();
			statement3.executeUpdate("UPDATE public.historial_clasificacion_ticket SET fechaHasta = '"+ fechaHastaHistorialC +"' WHERE idHistorialClasificacion = "+ idHistorialC);
			
			Statement statement4;
			statement4 = connection.createStatement();
			statement4.executeUpdate("UPDATE public.ticket SET observaciones = '"+ ticket.observaciones +"' WHERE nroTicket = "+ ticket.nroTicket);
			
			GestorBD.modificarIntervencion(intervencion);
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	private static void modificarIntervencion(Intervencion intervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Modificar Intervencion");
			
			Statement statement;
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE public.intervencion SET idEstadoIntervencion = '"+ intervencion.estadoIntervencionActual.getIdEstadoInt() +"' WHERE idIntervencion = "+ intervencion.idIntervencion);
			
			String fechaFin = formatFecha.format(intervencion.fechaFin);
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("UPDATE public.intervencion SET fechaFin = '"+ fechaFin +"' WHERE idIntervencion = "+ intervencion.idIntervencion);

			// Ultimo y penúltimo
			HistorialEstadoIntervencion auxHistorial = intervencion.getUltimoHistorialIntervencion();
			String fechaHastaHistorialEI = formatFecha.format(auxHistorial.fechaHasta);
			
			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("UPDATE public.historial_estado_intervencion SET fechaHasta = '"+ fechaHastaHistorialEI +"' WHERE idHistorialEstadoIntervencion = "+ auxHistorial.idHistorialEstadoInt);

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static List<TicketDTO> buscarTickets(Integer nroTicket, Integer nroLegajo, Integer idClasificacion,
			EstadosTicket idEstado, Date fechaApertura, Date fechaUltimoCambio, Integer idGrupo) {
		
		String idEstadoS = null;
		String fechaA = null;
		
		if(!(idEstado == null)) {
			idEstadoS = "'"+String.valueOf(idEstado)+"'";
		}
		if(!(fechaApertura == null)) {
			fechaA = "'"+formatFecha.format(fechaApertura)+"'";
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Buscar Tickets");
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT t.nroticket, t.nrolegajocliente, t.idclasificacion, t.fechaapertura, t.horaapertura, t.idestadoticket, i.idgrupo "
					+ "FROM public.ticket t, public.intervencion i "
					+ "WHERE COALESCE(t.nroTicket = "+ nroTicket +", t.nroTicket IS NOT NULL) AND "
					+ "COALESCE(t.nroLegajoCliente = "+ nroLegajo +", t.nroLegajoCliente IS NOT NULL) AND "
					+ "COALESCE(t.idClasificacion = "+ idClasificacion +", t.idClasificacion IS NOT NULL) AND "
					+ "COALESCE (t.idEstadoTicket = "+ idEstadoS +", t.idEstadoTicket IS NOT NULL) AND "
					+ "COALESCE (t.fechaApertura = "+ fechaA +", t.fechaApertura IS NOT NULL) AND "
					+ "t.nroTicket = i.nroTicket");
			
			List<TicketDTO> resultado = new ArrayList<TicketDTO>();
			TicketDTO aux = null;
			
			while(rs.next()) {
				String fecha = rs.getString("fechaapertura");
				String hora = rs.getString("horaapertura");
				Date apertura;
				apertura = parseFecha.parse(fecha+" "+hora);
				GrupoDTO grupo = mapearGrupoDTO(rs.getInt("idgrupo"));
				ClasificacionDTO clasificacion = mapearClasificacionDTO(rs.getInt("idclasificacion"));
				EstadoTicketDTO estado = mapearEstadoTicketDTO(rs.getString("idestadoticket"));
				
				aux = new TicketDTO(rs.getInt("nroticket"), rs.getInt("nrolegajocliente"), grupo, clasificacion, apertura, estado);
				resultado.add(aux);
			}
			
			for(int i = 0; i < resultado.size(); i++) {
				TicketDTO ticket = resultado.get(i);
				ticket.setHistoriales(mapearHistorialesEstadoTicket(ticket.nroTicket));
				ticket.setHistorialesClasificacion(mapearHistorialesClasificacion(ticket.nroTicket));
				ticket.setIntervenciones(mapearIntervencionesTicket(ticket.nroTicket));
			}
			return resultado;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<HistorialEstadoTicketDTO> mapearHistorialesEstadoTicket(Integer nroTicket) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_estado_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialEstadoTicketDTO> resultado = new ArrayList<HistorialEstadoTicketDTO>();
			HistorialEstadoTicketDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialEstadoTicketDTO(resultSet.getInt("idHistorialEstadoTicket"), resultSet.getDate("fechaDesde"), resultSet.getDate("fechaHasta"),
						resultSet.getString("idEstadoTicket"), resultSet.getInt("nroLegajo"));
				resultado.add(aux);
			}
			
			connection.close();
			
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	//Nacho BD
	
	private static List<HistorialClasificacionDTO> mapearHistorialesClasificacion(Integer nroTicket) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_clasificacion_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialClasificacionDTO> resultado = new ArrayList<HistorialClasificacionDTO>();
			HistorialClasificacionDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialClasificacionDTO(resultSet.getInt("idClasificacionHis"), resultSet.getDate("fechaDesde"), resultSet.getDate("fechaHasta"), resultSet.getInt("idClasificacion"));
				resultado.add(aux);
			}
			
			connection.close();
			
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<IntervencionDTO> mapearIntervencionesTicket(Integer nroTicket) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM intervencion WHERE nroTicket = "+ nroTicket);
			
			List<IntervencionDTO> resultado = new ArrayList<IntervencionDTO>();
			IntervencionDTO aux;
			
			while(resultSet.next()) {
				EstadoIntervencionDTO estadoActual = mapearEstadoIntervencionDTO(resultSet.getString("idEstadoIntervencion"));
				aux = new IntervencionDTO(resultSet.getInt("idIntervencion"),resultSet.getDate("fechaAsignacion"), resultSet.getDate("fechaFin"), estadoActual);
				resultado.add(aux);
			}
			
			connection.close();
			
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static GrupoDTO mapearGrupoIntervencionDTO(Integer idIntervencion) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			String idIntConsulta = idIntervencion.toString();
			
			Statement infoGrupo;
			infoGrupo = connection.createStatement();
			ResultSet resultSet = infoGrupo.executeQuery("SELECT * FROM public.intervencion g WHERE g.idIntervencion = " + idIntConsulta);
			resultSet.next();

			connection.close();

			GrupoDTO grupoDeResolucionDTO = new GrupoDTO( resultSet.getInt("idGrupo"), resultSet.getString("nombre"));
		
			return grupoDeResolucionDTO;
			
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}	
	}

	public static List<HistorialEstadoIntervencionDTO> mapearHistorialesEstadosIntervencion(Integer idIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_estado_intervencion WHERE nroTicket = "+ idIntervencion);
			
			List<HistorialEstadoIntervencionDTO> resultado = new ArrayList<HistorialEstadoIntervencionDTO>();
			HistorialEstadoIntervencionDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialEstadoIntervencionDTO(resultSet.getInt("idEstadoIntHis"), resultSet.getDate("fechaDesde"), 
						resultSet.getDate("fechaHasta"), GestorBD.mapearEstadoIntervencionDTO(resultSet.getString("idEstadoInt")), GestorBD.mapearSoporte(Integer.parseInt(resultSet.getString("nroLegajo"))));
				resultado.add(aux);
			}
			
			connection.close();
			
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<IntervencionDTO> mapearIntervencionesGrupoDTO(Integer idGrupo) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM intervencion WHERE idGrupo = "+ idGrupo);
			
			List<IntervencionDTO> resultado = new ArrayList<IntervencionDTO>();
			IntervencionDTO aux;
			
			while(resultSet.next()) {
				EstadoIntervencionDTO estadoActual = mapearEstadoIntervencionDTO(resultSet.getString("idEstadoIntervencion"));
				aux = new IntervencionDTO(resultSet.getInt("idIntervencion"),resultSet.getDate("fechaAsignacion"), resultSet.getDate("fechaFin"), estadoActual);
				resultado.add(aux);
			}
			
			connection.close();
			
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}


