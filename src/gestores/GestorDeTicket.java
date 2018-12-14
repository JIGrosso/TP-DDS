package gestores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import clasesDTO.ClasificacionDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.HistorialClasificacionDTO;
import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.IntervencionDTO;
import clasesDTO.TicketDTO;
import produccion.Clasificacion;
import produccion.EstadoTicket;
import produccion.EstadosIntervencion;
import produccion.EstadosTicket;
import produccion.HistorialClasificacionTicket;
import produccion.HistorialEstadoTicket;
import produccion.Intervencion;
import produccion.Ticket;
import produccion.EstadoIntervencion; 
import usuarios.Cliente;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;
import vistas.Principal;

public class GestorDeTicket {
	
	public static EstadoTicket abiertoMA;
	public static EstadoTicket abiertoD;
	public static EstadoTicket solucionadoOK;
	public static EstadoTicket cerrado;

	public static Ticket crearTicket(Soporte soporte, Integer nroTicket, Integer nroLegajo, Date fechaCreacion, ClasificacionDTO clasificacionDTO, String descripcion){

		Cliente cliente = GestorDeCliente.mapearCliente(nroLegajo);

		Clasificacion clasificacion = GestorBD.mapearClasificacion(clasificacionDTO.idClasificacion);
		
		Ticket nuevoTicket = new Ticket(nroTicket, cliente, clasificacion, soporte.grupo, fechaCreacion, descripcion);

		HistorialEstadoTicket primerHistorialEstado = new HistorialEstadoTicket(soporte, fechaCreacion);
		HistorialClasificacionTicket historialClasificacion = new HistorialClasificacionTicket(clasificacion, fechaCreacion);
		nuevoTicket.addHistorialEstadoTicket(primerHistorialEstado);
		nuevoTicket.addHistorialClasificacionTicket(historialClasificacion);

		Intervencion nuevaIntervencion = GestorDeIntervencion.crearIntervencion(soporte, fechaCreacion);
		nuevoTicket.intervenciones.add(nuevaIntervencion);

		GestorBD.guardarTicket(nuevoTicket, nuevaIntervencion, soporte);
		// GuardarGrupo pq se actualiza su lista de intervenciones
		
		return nuevoTicket;

	}
	
	public static void cerrarTicket(Ticket ticket, String obs) {
		
		ticket.setEstadoCerrado(mapearEstadoTicket("CERRADO"));
		ticket.observaciones = obs;
		Intervencion intervencion = ticket.getUltimaIntervencion();
		GestorDeIntervencion.cerrarIntervencion(intervencion);
		GestorBD.modificarTicket(ticket, intervencion);
		JOptionPane.showMessageDialog(null, "El ticket "+ticket.nroTicket+" se ha cerrado con éxito!", "CierreExitoso", JOptionPane.INFORMATION_MESSAGE);
	}

	public static ArrayList<EstadoTicket> mapearEstadosTicket() {
		
		ArrayList<EstadoTicket> estadosTickets = new ArrayList<EstadoTicket>();
		
		EstadoTicket abiertoMAdto = GestorBD.mapearEstadoTicket("ABIERTO_MA");
		estadosTickets.add(abiertoMAdto);
		abiertoMA = GestorBD.mapearEstadoTicket("ABIERTO_MA");
		
		EstadoTicket abiertoDdto = GestorBD.mapearEstadoTicket("ABIERTO_D");
		estadosTickets.add(abiertoDdto);
		abiertoD = GestorBD.mapearEstadoTicket("ABIERTO_D");
		
		EstadoTicket solucionadoOKdto = GestorBD.mapearEstadoTicket("SOLUCIONADO_OK");
		estadosTickets.add(solucionadoOKdto);
		solucionadoOK = GestorBD.mapearEstadoTicket("SOLUCIONADO_OK");
		
		EstadoTicket cerradodto = GestorBD.mapearEstadoTicket("CERRADO");
		estadosTickets.add(cerradodto);
		cerrado = GestorBD.mapearEstadoTicket("CERRADO");
		
		return estadosTickets;
	}
	
	public static Integer nroNuevoTicket() { 
		return GestorBD.nroNuevoTicket();
	}

	public static List<TicketDTO> buscarTickets(Integer nroTicket, Integer nroLegajo, Integer idClasificacion,
			EstadosTicket idEstado, Date fechaApertura, Date fechaUltimoCambio, Integer idGrupo) {
		return GestorBD.buscarTickets(nroTicket, nroLegajo, idClasificacion, idEstado, fechaApertura, fechaUltimoCambio, idGrupo);
		
	}

	public static void derivarTicket(TicketDTO ticketDTO, EstadoTicket nuevoEstado, ClasificacionDTO clasificacionDTO, GrupoDTO grupoDTO, String observaciones) {
		
		Ticket ticket = GestorBD.mapearTicket(ticketDTO.nroTicket);
		GrupoDeResolucion grupo = GestorBD.mapearGrupoDeResolucion(grupoDTO.idGrupo);
		
		System.out.println("Ticket mapeado: "+ticket.nroTicket);
		
		if(clasificacionDTO.idClasificacion != ticket.clasificacion.idClasificacion) {
			
			//Clasificacion
			
			Clasificacion clasificacion = GestorBD.mapearClasificacion(clasificacionDTO.idClasificacion);
			ticket.setClasificacion(clasificacion);
			
			HistorialClasificacionTicket ultimoHistorialClasificacion = ticket.getUlitmoHistorialC();
			Date fechaActual = new Date();
			ultimoHistorialClasificacion.cerrarHistorialClasificacionTicket();
			HistorialClasificacionTicket nuevoHistorialClasificacion = new HistorialClasificacionTicket(clasificacion, fechaActual);
			ticket.addHistorialClasificacionTicket(nuevoHistorialClasificacion);
			
			// Intervenciones
			
			List<Intervencion> intervenciones = grupo.getIntervenciones();
			Integer aux = (intervenciones.size() - 1);
			while(aux >= 0) {
				Intervencion intervencionAux = intervenciones.get(aux);
				if(intervencionAux.getEstadoIntervencionActual().idEstadoInt == EstadosIntervencion.ESPERA && (ticket.intervenciones.contains(intervencionAux))) {
					GestorDeIntervencion.activarIntervencion(intervencionAux);
					aux = 0;
				} else {
					aux--;
				}
			}
			if(aux != 0) {
				Intervencion nuevaIntervencion = GestorDeIntervencion.crearIntervencion(Principal.usuarioIniciado, fechaActual, GestorDeIntervencion.mapearEstadoIntervencion("ASIGNADA"));
				ticket.intervenciones.add(nuevaIntervencion);
			}
			
			//Historial Estado Ticket
			
			HistorialEstadoTicket ultimoHistorialEstado = ticket.getUlitmoHistorialET();
			ultimoHistorialEstado.cerrarHistorialEstadoTicket();;
			ticket.estadoActual = nuevoEstado;

			HistorialEstadoTicket nuevoHistorial = new HistorialEstadoTicket(GestorBD.nroNuevoHistorialET(), Principal.usuarioIniciado, nuevoEstado, fechaActual, null);
			ticket.addHistorialEstadoTicket(nuevoHistorial);
			
			//	GUARDAR TICKET Y GRUPO
			
			System.out.println("Ticket derivado: "+ticket.nroTicket);
			System.out.println("Nuevo Estado: "+ticket.estadoActual);
			System.out.println("Clasificacion: "+ticket.clasificacion);
			
		}
		
	}

	public static EstadoTicket mapearEstadoTicket(String idEstado) {
		return GestorBD.mapearEstadoTicket(idEstado);
	}

	public static void actualizarTicket(Integer nroTicket, Intervencion intervencionA,
			ClasificacionDTO clasificacionDto) {
		EstadosIntervencion idEstadoInt = intervencionA.getEstadoIntervencionActual().getIdEstadoInt();
		Ticket ticket = GestorBD.mapearTicket(nroTicket);
		if(idEstadoInt == EstadosIntervencion.ESPERA) {
			ticket.getUlitmoHistorialET().cerrarHistorialEstadoTicket();
			HistorialEstadoTicket historialEstadoTicket = new HistorialEstadoTicket(GestorBD.nroNuevoHistorialET(),
					Principal.usuarioIniciado, abiertoMA, new Date(), null);
			ticket.addHistorialEstadoTicket(historialEstadoTicket);
			ticket.setEstado(abiertoMA);
		} else if(idEstadoInt == EstadosIntervencion.CERRADA) {
			ArrayList<Intervencion> listaIntervenciones = (ArrayList<Intervencion>) ticket.getIntervenciones();
			Integer intervAbiertas = 0;
			for(Intervencion aux: listaIntervenciones) {
				if(aux.getEstadoIntervencionActual().getIdEstadoInt() == EstadosIntervencion.ESPERA) {
					intervAbiertas++;
					//Ya que la mesa de ayuda siempre tiene su intervencion en espera
					if(intervAbiertas > 1) {
						break;
					}
				}
			}
			ticket.getUlitmoHistorialET().cerrarHistorialEstadoTicket();
			EstadoTicket nuevoEstado;
			if(intervAbiertas > 1) {
				nuevoEstado = solucionadoOK;
			}
			else {
				nuevoEstado = abiertoMA;
			}
			HistorialEstadoTicket historialEstadoTicket = new HistorialEstadoTicket(GestorBD.nroNuevoHistorialET(),
					Principal.usuarioIniciado, nuevoEstado, new Date(), null);
			ticket.addHistorialEstadoTicket(historialEstadoTicket);
			ticket.setEstado(nuevoEstado);
			//revisar si actualizarIntervencion es necesario;
			ticket.actualizarIntervencion(intervencionA);
			
			if(clasificacionDto.getIdClasificacion() != ticket.getClasificacion().getIdClasificacion()) {
				ticket.getUlitmoHistorialC().cerrarHistorialClasificacionTicket();
				Clasificacion nuevaClasificacion = GestorBD.mapearClasificacion(clasificacionDto.getIdClasificacion());
				HistorialClasificacionTicket historialClasif = new HistorialClasificacionTicket(GestorBD.nroNuevoHistorialC(),
						new Date(), null, nuevaClasificacion);
				ticket.addHistorialClasificacionTicket(historialClasif);
				ticket.setClasificacion(nuevaClasificacion);
			}
			
			//fijar si es el guardado correcto
			
			GestorBD.guardarTicket(ticket, intervencionA, Principal.usuarioIniciado);
		}
		
	}

}
