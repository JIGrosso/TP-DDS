package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoIntervencionDTO;
import clasesDTO.EstadoTicketDTO;
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
import vistas.Principal;

public interface GestorBD {

	SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss.SSS");

	//Creación de listas para evitar crear duplicados de Objetos GrupoDeResolucion y Soporte desde la BDD
	
	List<GrupoDeResolucion> gruposMapeados = new ArrayList<GrupoDeResolucion>();
	List<Soporte> soportesMapeados = new ArrayList<Soporte>();

	
	
	public static boolean validarSoporte(Integer nroLegajo, String password) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM soporte WHERE nroLegajo = " + nroLegajo +" AND contrasenia = '" + password + "'");
			
			if(!resultSet.next()) {
				return false;
			}
			else {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Soporte mapearSoporte(Integer nroLegajo) {
		
		Soporte nuevoSoporte = null;
		
		for (int i=0; i < soportesMapeados.size(); i++) {
			if (soportesMapeados.get(i).nroLegajo.equals(nroLegajo)) {
				nuevoSoporte = soportesMapeados.get(i);
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
				GrupoDeResolucion grupo = mapearGrupoDeResolucion(idGrupo);
				Soporte soporte = new Soporte(nroLeg, "contrasenia", resultSet.getString("nombre"), dni, telefono, resultSet.getString("email"), grupo);
				soportesMapeados.add(soporte);
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

			return cliente;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	// OJO A LA HORA DE NECESITAR CLASIFICACIONES PQ ESTAN EN UNA VARIABLE GLOBAL
	
	public static ArrayList<Clasificacion> mapearClasificaciones() {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.clasificacion");

			ArrayList<Clasificacion> resultado = new ArrayList<Clasificacion>();

			while(resultSet.next()) {
				Integer idClasificacion = resultSet.getInt("idClasificacion");
				Integer nroLegajoCreador = Integer.valueOf(resultSet.getString("nroLegajoSoporte"));
				Soporte soporte = mapearSoporte(nroLegajoCreador);
				Clasificacion clasificacion = new Clasificacion (idClasificacion, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"), soporte);
				resultado.add(clasificacion);
			}
			
			return resultado;
			
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
		
		for (int i=0; i < gruposMapeados.size(); i++) {
			if (gruposMapeados.get(i).idGrupo == idGrupo) {
				nuevoGrupo = gruposMapeados.get(i);
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
				Integer idNuevoGrupo = Integer.valueOf(resultSet.getString("idGrupo"));
				String nombreGrupo = resultSet.getString("nombre");
				String nivelGrupo = resultSet.getString("nivel");
				String descripcionGrupo = resultSet.getString("descripcion");
				
				// Intervenciones Asignadas
				
				Statement consultaIntervenciones;
				consultaIntervenciones = connection.createStatement();
				ResultSet resultSet2 = consultaIntervenciones.executeQuery("SELECT i.idIntervencion FROM public.intervencion i WHERE i.idGrupo = " + idGrupoConsulta);

				List<Intervencion> intervencionesAsignadas = new ArrayList<Intervencion>();
				while(resultSet2.next()) {
					Integer idIntervencion = Integer.valueOf(resultSet2.getString("idIntervencion"));
					Intervencion aux = mapearIntervencion(idIntervencion);
					intervencionesAsignadas.add(aux);
				}
				
				// Creacion de la instancia
				
				GrupoDeResolucion grupoDeResolucion = new GrupoDeResolucion(idNuevoGrupo, nombreGrupo, nivelGrupo, descripcionGrupo, intervencionesAsignadas);
				System.out.println("Grupo: " + grupoDeResolucion.nombre + " mapeado desde BDD.");
				gruposMapeados.add(grupoDeResolucion);
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
			String idEstadoActual = resultSet.getString("idEstadoIntervencion");
			EstadoIntervencion estadoActual = buscarEstadoIntervencion(idEstadoActual);
			
			// Historiales de estado de intervencion
			
			Statement consultaHistoriales;
			consultaHistoriales = connection.createStatement();
			ResultSet resultSet2 = consultaHistoriales.executeQuery("SELECT * FROM public.historial_estado_intervencion WHERE idIntervencion = "+ idIntervencionConsulta);

			List<HistorialEstadoIntervencion> historiales = new ArrayList<HistorialEstadoIntervencion>();
			while(resultSet2.next()) {
				Integer idHistorialEstadoIntervencion = resultSet2.getInt("idHistorialEstadoIntervencion");
				HistorialEstadoIntervencion aux = mapearHistorialEstadoIntervencion(idHistorialEstadoIntervencion);
				historiales.add(aux);
			}
			
			//Creacion de la intervencion
			
			Intervencion intervencion = new Intervencion(idInterv, resultSet.getString("observacions"), fechaAsignacion, null, estadoActual, historiales);
			
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
	
	public static EstadoTicketDTO buscarEstadoTicket(String idEstadoTicket) {
		
		for(EstadoTicketDTO estado : Principal.estadosTicket) {
			if(idEstadoTicket.equalsIgnoreCase(estado.getIdEstadoTicket().name())){
				return estado;
			}
		}
		return null;
	}

	public static void guardarTicket (Ticket ticket, Soporte soporte) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Guardar Ticket");

			String fechaApertura = formatFecha.format(ticket.fechaYHoraApertura);
			String horaApertura = formatHora.format(ticket.fechaYHoraApertura);
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("INSERT INTO public.ticket VALUES (" + ticket.nroTicket + ", '" + ticket.descripcion + "', '-' , '" + horaApertura + "', '" + fechaApertura + "', " + ticket.cliente.nroLegajo + ", '" + ticket.estadoActual.idEstadoTicket + "', " + ticket.clasificacion.idClasificacion + ")" );

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


		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void guardarIntervencion(Intervencion intervencion, Soporte soporte, Integer nroTicket) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			System.out.println("Connected to PostgreSQL database! --> Guardar Intervencion");
			
			String fechaAsignacion = formatFecha.format(intervencion.fechaAsignacion);
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ")");

			// Hacer un for para guardar todas las intervenciones que sufren cambios
			HistorialEstadoIntervencion auxHistorial = intervencion.historialesEstado.get(0);
			String fechaDesdeHistorialEI = formatFecha.format(auxHistorial.fechaDesde);
			
			Statement statement2;
			statement2 = connection.createStatement();
			statement2.executeUpdate("INSERT INTO public.historial_estado_intervencion VALUES (" + auxHistorial.idHistorialEstadoInt + ", '" + fechaDesdeHistorialEI + "', null, " + intervencion.getIdIntervencion() + ", '" + intervencion.getEstadoIntervencionActual().idEstadoInt.name() + "', " + soporte.nroLegajo + ")");


		} catch (SQLException e) {
			e.printStackTrace();
			return;
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
				Integer nroLegajoCreador = Integer.valueOf(resultSet.getString("nroLegajoSoporte"));
				Soporte soporte = mapearSoporte(nroLegajoCreador);
				ClasificacionDTO clasificacion = new ClasificacionDTO (idClasificacion, resultSet.getString("nombre"), resultSet.getString("descripcionAlcance"), soporte);
				resultado.add(clasificacion);
			}
			
			return resultado;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return null;
	}
}
