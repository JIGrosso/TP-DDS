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
import produccion.HistorialClasificacionTicket;
import produccion.HistorialEstadoIntervencion;
import produccion.HistorialEstadoTicket;
import produccion.Intervencion;
import produccion.Ticket;
import usuarios.Cliente;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public class GestorBD {

//	private static SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
//	private static SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss.SSS");
//	private static SimpleDateFormat parseFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

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

				Statement statement;
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM public.soporte WHERE nroLegajo = "+ nroLegajo);
	
				resultSet.next();
				Integer nroLeg = resultSet.getInt("nrolegajo");
				String password = resultSet.getString("contrasenia");
				String nombre = resultSet.getString("nombre");
				Integer dni = resultSet.getInt("dni");
				String telefono = resultSet.getString("telefono");
				Integer telefonoInterno = resultSet.getInt("telefono_interno");
				String ubicacion = resultSet.getString("ubicacion");
				String email = resultSet.getString("email");
				String cargo = resultSet.getString("cargo");
				Integer idGrupo = resultSet.getInt("idGrupo");
				
				
				connection.close();
				
				GrupoDeResolucion grupo = mapearGrupoDeResolucion(idGrupo);
				Soporte soporte = new Soporte(nroLeg, password, nombre, dni, telefono, telefonoInterno, ubicacion, email, cargo, grupo);
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
			
			statement.executeQuery("SELECT nextval('seqIntervencion')");

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
			
			statement.executeQuery("SELECT nextval('seqHistorialEstadoTicket')");

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
			
			statement.executeQuery("SELECT nextval('seqHistorialEstadoIntervencion')");

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
			
			statement.executeQuery("SELECT nextval('seqHistorialClasificacion')");

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

			Integer nroLeg = resultSet.getInt("nrolegajoc");
			String nombre = resultSet.getString("nombre");
			Integer dni = resultSet.getInt("dni");
			String email = resultSet.getString("email");
			String telefono = resultSet.getString("telefono");
			Integer interno = resultSet.getInt("telefono_interno");
			String ubicacion = resultSet.getString("ubicacion");
			String cargo = resultSet.getString("cargo");
			
			connection.close();
			
			Cliente cliente = new Cliente(nombre, dni, email, telefono, interno, ubicacion, cargo, nroLeg);

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

			connection.close();
			return estadoTicket;

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

			connection.close();
			return estadoIntervencion;

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
			Date fechaAsignacion = resultSet.getTimestamp("fechaAsignacion");
			Date fechaFin = resultSet.getTimestamp("fechaFin");
			String idEstadoActual = resultSet.getString("idEstadoIntervencion");
			String observaciones = resultSet.getString("observaciones");
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
			
			Intervencion intervencion = new Intervencion(idInterv, observaciones, fechaAsignacion, fechaFin, estadoActual, historiales);
			
			
			return intervencion;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static HistorialEstadoIntervencion mapearHistorialEstadoIntervencion(Integer idHistorialEstadoIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.historial_estado_intervencion WHERE idHistorialEstadoIntervencion = "+ idHistorialEstadoIntervencion);

			resultSet.next();

			Integer idHistorial = resultSet.getInt("idHistorialEstadoIntervencion");
			Date fechaDesde = resultSet.getTimestamp("fechaDesde");
			Date fechaHasta = resultSet.getTimestamp("fechaHasta");
			String idEstadoIntervencion = resultSet.getString("idEstadoIntervencion");
			EstadoIntervencion estado = GestorBD.mapearEstadoIntervencion(idEstadoIntervencion);
			Integer nroSoporte = resultSet.getInt("nroLegajoSoporte");
			Soporte actor = mapearSoporte(nroSoporte);
				
			HistorialEstadoIntervencion aux = new HistorialEstadoIntervencion(idHistorial, fechaDesde, fechaHasta, actor, estado);

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
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("INSERT INTO public.ticket VALUES (" + ticket.nroTicket + ", '" + ticket.descripcion + "', '" + ticket.observaciones + "', '" + ticket.fechaYHoraApertura + "', " + ticket.cliente.nroLegajo + ", '" + ticket.estadoActual.idEstadoTicket + "', " + ticket.clasificacion.idClasificacion + ")" );
			
			//Ultimo y penúltimo
			String fechaDesdeHistorialET = String.valueOf(ticket.historialesEstado.get(0).fechaDesde);

			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("INSERT INTO public.historial_estado_ticket VALUES (" + ticket.historialesEstado.get(0).idHistorialEstadoTic + ", '" + fechaDesdeHistorialET + "', null, " + ticket.nroTicket + ", '" + ticket.estadoActual.idEstadoTicket + "', " + soporte.nroLegajo + ")" );
			
			String fechaDesdeHistorialC = String.valueOf(ticket.historialesClasificacion.get(0).fechaDesde);
			
			Statement statement3;
			statement3 = connection.createStatement();
			statement3.executeUpdate("INSERT INTO public.historial_clasificacion_ticket VALUES (" + ticket.historialesClasificacion.get(0).idHistorialClasificacion + ", '" + fechaDesdeHistorialC + "', null, " + ticket.clasificacion.getIdClasificacion() + ", " + ticket.nroTicket + ")" );
			
			Statement statement4;
			statement4 = connection.createStatement();
			statement4.executeQuery("SELECT nextval('seqNroTicket')");
			
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
			
			String fechaAsignacion = String.valueOf(intervencion.fechaAsignacion);
			String fechaFin = null;
			if(!(intervencion.fechaFin == null)) {
				fechaFin = String.valueOf(intervencion.fechaFin);
				Statement statement1;
				statement1 = connection.createStatement();
				statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', '" + fechaFin + "', " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ")");
			}
			else {
				Statement statement1;
				statement1 = connection.createStatement();
				statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', null, " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ")");

			}
			// Hacer un for para guardar todas las intervenciones que sufren cambios
			HistorialEstadoIntervencion auxHistorial = intervencion.historialesEstado.get(0);
			String fechaDesdeHistorialEI = String.valueOf(auxHistorial.fechaDesde);
			
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
				ClasificacionDTO clasificacion = new ClasificacionDTO (resultSet.getInt("idClasificacion"), resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"));
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

			// Modifica solo el estado
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("UPDATE public.ticket SET idEstadoTicket = '"+ ticket.estadoActual.getIdEstadoTicket() +"' WHERE nroTicket = "+ ticket.nroTicket);

			// Implementar para el último y penúltimo
			
			String fechaHastaHistorialET = String.valueOf(ticket.getUlitmoHistorialET().fechaHasta);
			Integer idHistorialET = ticket.getUlitmoHistorialET().idHistorialEstadoTic;
			
			// Modifica fecha del historial estado, clasificacio y observaciones de ticket
			
			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("UPDATE public.historial_estado_ticket SET fechaHasta = '"+ fechaHastaHistorialET +"' WHERE idHistorialEstadoTicket = "+ idHistorialET);
			
			String fechaHastaHistorialC = String.valueOf(ticket.getUlitmoHistorialC().fechaHasta);
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
			
			String fechaFin = String.valueOf(intervencion.fechaFin);
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("UPDATE public.intervencion SET fechaFin = '"+ fechaFin +"' WHERE idIntervencion = "+ intervencion.idIntervencion);

			// Ultimo y penúltimo
			HistorialEstadoIntervencion auxHistorial = intervencion.getUltimoHistorialIntervencion();
			String fechaHastaHistorialEI = String.valueOf(auxHistorial.fechaHasta);
			
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
			fechaA = "'"+String.valueOf(fechaApertura)+"'";
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Buscar Tickets");
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT t.nroticket, t.nrolegajocliente, t.idclasificacion, t.fechaapertura, t.idestadoticket, i.idgrupo "
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
				Date apertura = rs.getTimestamp("fechaapertura");
				GrupoDTO grupo = mapearGrupoDTO(rs.getInt("idgrupo"));
				ClasificacionDTO clasificacion = mapearClasificacionDTO(rs.getInt("idclasificacion"));
				EstadoTicket estado = mapearEstadoTicket(rs.getString("idestadoticket"));
				
				aux = new TicketDTO(rs.getInt("nroticket"), rs.getInt("nrolegajocliente"), grupo, clasificacion, apertura, estado);
				resultado.add(aux);
			}
			
			for(int i = 0; i < resultado.size(); i++) {
				TicketDTO ticket = resultado.get(i);
				ticket.setHistoriales(mapearHistorialesEstadoTicketDTO(ticket.nroTicket));
				ticket.setHistorialesClasificacion(mapearHistorialesClasificacionDTO(ticket.nroTicket));
				ticket.setIntervenciones(mapearIntervencionesDTOTicket(ticket.nroTicket));
			}
			return resultado;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<HistorialEstadoTicket> mapearHistorialesEstadoTicket(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_estado_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialEstadoTicket> resultado = new ArrayList<HistorialEstadoTicket>();
			HistorialEstadoTicket aux;
			
			while(resultSet.next()) {
				
				aux = new HistorialEstadoTicket(resultSet.getInt("idHistorialEstadoTicket"), mapearSoporte(resultSet.getInt("nroLegajo")), mapearEstadoTicket(resultSet.getString("idEstadoTicket")), resultSet.getTimestamp("fechaDesde"), resultSet.getTimestamp("fechaHasta"));
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
	
	private static List<HistorialEstadoTicketDTO> mapearHistorialesEstadoTicketDTO(Integer nroTicket) {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_estado_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialEstadoTicketDTO> resultado = new ArrayList<HistorialEstadoTicketDTO>();
			HistorialEstadoTicketDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialEstadoTicketDTO(resultSet.getInt("idHistorialEstadoTicket"), resultSet.getTimestamp("fechaDesde"), resultSet.getTimestamp("fechaHasta"),
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

	private static List<HistorialClasificacionTicket> mapearHistorialesClasificacion(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_clasificacion_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialClasificacionTicket> resultado = new ArrayList<HistorialClasificacionTicket>();
			HistorialClasificacionTicket aux;
			
			while(resultSet.next()) {
				aux = new HistorialClasificacionTicket(resultSet.getInt("idHistorialClasificacion"), resultSet.getTimestamp("fechaDesde"), resultSet.getTimestamp("fechaHasta"), mapearClasificacion(resultSet.getInt("idClasificacion")));
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
	
	private static List<HistorialClasificacionDTO> mapearHistorialesClasificacionDTO(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_clasificacion_ticket WHERE nroTicket = "+ nroTicket);
			
			List<HistorialClasificacionDTO> resultado = new ArrayList<HistorialClasificacionDTO>();
			HistorialClasificacionDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialClasificacionDTO(resultSet.getInt("idHistorialClasificacion"), resultSet.getInt("idClasificacion"));
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
	
	private static List<Intervencion> mapearIntervencionesTicket(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM intervencion WHERE nroTicket = "+ nroTicket);
			
			List<Intervencion> resultado = new ArrayList<Intervencion>();
			Intervencion aux;
			
			while(resultSet.next()) {
				aux =  mapearIntervencion(resultSet.getInt("idIntervencion"));
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
	
	private static List<IntervencionDTO> mapearIntervencionesDTOTicket(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM intervencion WHERE nroTicket = "+ nroTicket);
			
			List<IntervencionDTO> resultado = new ArrayList<IntervencionDTO>();
			IntervencionDTO aux;
			
			while(resultSet.next()) {
				EstadoIntervencion estadoActual = mapearEstadoIntervencion(resultSet.getString("idEstadoIntervencion"));
				aux = new IntervencionDTO(resultSet.getInt("idIntervencion"), resultSet.getTimestamp("fechaAsignacion"), estadoActual);
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
			
			Statement infoGrupo;
			infoGrupo = connection.createStatement();
			ResultSet resultSet = infoGrupo.executeQuery("SELECT * FROM public.intervencion WHERE idIntervencion = " + idIntervencion);
			resultSet.next();

			connection.close();

			GrupoDTO grupoDeResolucionDTO = new GrupoDTO( resultSet.getInt("idGrupo"), "nombre grupo");
		
			return grupoDeResolucionDTO;
			
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}	
	}

	public static List<HistorialEstadoIntervencionDTO> mapearHistorialesEstadosIntervencionDTO(Integer idIntervencion) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM historial_estado_intervencion WHERE nroTicket = "+ idIntervencion);
			
			List<HistorialEstadoIntervencionDTO> resultado = new ArrayList<HistorialEstadoIntervencionDTO>();
			HistorialEstadoIntervencionDTO aux;
			
			while(resultSet.next()) {
				aux = new HistorialEstadoIntervencionDTO(resultSet.getInt("idEstadoIntHis"), resultSet.getTimestamp("fechaDesde"), 
						resultSet.getTimestamp("fechaHasta"), GestorBD.mapearEstadoIntervencion(resultSet.getString("idEstadoInt")), resultSet.getInt("nroLegajo"));
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
				EstadoIntervencion estadoActual = mapearEstadoIntervencion(resultSet.getString("idEstadoIntervencion"));
				aux = new IntervencionDTO(resultSet.getInt("idIntervencion"),resultSet.getTimestamp("fechaAsignacion"), estadoActual);
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

	public static Ticket mapearTicket(Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.ticket WHERE nroTicket = " + nroTicket);
			
			resultSet.next();
	
			String descripcion = resultSet.getString("descripcion");
			String observaciones = resultSet.getString("Observaciones");
			Date fechaApertura = resultSet.getTimestamp("fechaApertura");
			Integer legajoCliente = resultSet.getInt("nroLegajoCliente");
			String idEstado = resultSet.getString("idEstadoTicket");
			Integer idClasificacion = resultSet.getInt("idClasificacion");
			
			resultSet = statement.executeQuery("SELECT idGrupo FROM intervencion WHERE nroTicket = " + nroTicket + " AND idEstadoIntervencion = 'ACTIVA';");
			
			resultSet.next();
			
			Integer grupoAsignado = resultSet.getInt("idGrupo");
			
			connection.close();
			
			EstadoTicket estadoActual = mapearEstadoTicket(idEstado);
			Cliente cliente = mapearCliente(legajoCliente);
			Clasificacion clasificacion = mapearClasificacion(idClasificacion);
			GrupoDeResolucion grupo = mapearGrupoDeResolucion(grupoAsignado);
			
			Ticket ticketMapeado = new Ticket(nroTicket, cliente, clasificacion, grupo, fechaApertura, descripcion, observaciones, estadoActual);
			
			ticketMapeado.historialesEstado = mapearHistorialesEstadoTicket(nroTicket);
			ticketMapeado.historialesClasificacion = mapearHistorialesClasificacion(nroTicket);
			ticketMapeado.intervenciones = mapearIntervencionesTicket(nroTicket);
			
			return ticketMapeado;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}


