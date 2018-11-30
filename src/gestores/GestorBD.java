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

	public static boolean validarSoporte(Integer nroLegajo, String password) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {

			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM soporte WHERE nroLegajo = " + nroLegajo +" AND contrasenia = '" + password + "'");
			
			if(!resultSet.next()) {
				resultSet.close();
				connection.close();
				return false;
			}
			else {
				resultSet.close();
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
		
		for (int i=0; i < Principal.soportesMapeados.size(); i++) {
			if (Principal.soportesMapeados.get(i).nroLegajo.equals(nroLegajo)) {
				nuevoSoporte = Principal.soportesMapeados.get(i);
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
				Principal.soportesMapeados.add(soporte);
				
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

	// OJO A LA HORA DE NECESITAR CLASIFICACIONES PQ ESTAN EN UNA VARIABLE GLOBAL
	
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
			
			resultSet.close();
			connection.close();
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
	
	public static GrupoDeResolucion mapearGrupoDeResolucion(Integer idGrupo) {
		
		GrupoDeResolucion nuevoGrupo = null;
		
		for (int i=0; i < Principal.gruposMapeados.size(); i++) {
			if (Principal.gruposMapeados.get(i).idGrupo == idGrupo) {
				nuevoGrupo = Principal.gruposMapeados.get(i);
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
				Principal.gruposMapeados.add(grupoDeResolucion);
		
				
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
			EstadoIntervencion estado = buscarEstadoIntervencion(idEstadoIntervencion);
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

		if(idEstadoIntervencion.equalsIgnoreCase(Principal.asignada.getIdEstadoInt().name())) {
			return Principal.asignada;
		}
		else if(idEstadoIntervencion.equalsIgnoreCase(Principal.activa.getIdEstadoInt().name())) {
			return Principal.activa;
		}
		else if(idEstadoIntervencion.equalsIgnoreCase(Principal.cerrada.getIdEstadoInt().name())) {
			return Principal.cerrada;
		}
		else if(idEstadoIntervencion.equalsIgnoreCase(Principal.espera.getIdEstadoInt().name())) {
			return Principal.cerrada;
		}
		else {
			return null;
		}
		
	}
	
	public static EstadoTicket buscarEstadoTicket(String idEstadoTicket) {

		if(idEstadoTicket.equalsIgnoreCase(Principal.abiertoMA.getIdEstadoTicket().name())) {
			return Principal.abiertoMA;
		}
		else if(idEstadoTicket.equalsIgnoreCase(Principal.abiertoD.getIdEstadoTicket().name())) {
			return Principal.abiertoD;
		}
		else if(idEstadoTicket.equalsIgnoreCase(Principal.solucionadoOK.getIdEstadoTicket().name())) {
			return Principal.solucionadoOK;
		}
		else if(idEstadoTicket.equalsIgnoreCase(Principal.cerrado.getIdEstadoTicket().name())) {
			return Principal.cerrado;
		}
		else {
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
			
			Statement statement1;
			statement1 = connection.createStatement();
			statement1.executeUpdate("INSERT INTO public.intervencion VALUES (" + intervencion.getIdIntervencion() + ", '" + intervencion.getObservaciones() + "', '" + fechaAsignacion + "', " + nroTicket + ", '" + intervencion.getEstadoIntervencionActual().getIdEstadoInt().name() + "', " + soporte.getGrupo().idGrupo + ")");

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
}
