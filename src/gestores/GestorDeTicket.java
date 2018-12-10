package gestores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoTicketDTO;
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

		System.out.println("Historial Estado Ticket: " + nuevoTicket.historialesEstado.get(0).estado.nombre);

		Intervencion nuevaIntervencion = GestorDeIntervencion.crearIntervencion(soporte, fechaCreacion);
		nuevoTicket.intervenciones.add(nuevaIntervencion);
		
		System.out.println("Ticket creado: Nro Ticket: " + nuevoTicket.nroTicket);
		System.out.println("Estado Actual: " + nuevoTicket.estadoActual.nombre);

		GestorBD.guardarTicket(nuevoTicket, nuevaIntervencion, soporte);
		// GuardarGrupo pq se actualiza su lista de intervenciones
		
		return nuevoTicket;

	}
	
	public static void cerrarTicket(Ticket ticket, String obs) {
		
		ticket.setEstadoCerrado(cerrado);
		ticket.observaciones = obs;
		Intervencion intervencion = ticket.getUltimaIntervencion();
		GestorDeIntervencion.cerrarIntervencion(intervencion);
		GestorBD.modificarTicket(ticket, intervencion);
		JOptionPane.showMessageDialog(null, "El ticket "+ticket.nroTicket+" se ha cerrado con éxito!", "CierreExitoso", JOptionPane.INFORMATION_MESSAGE);
	}

	public static ArrayList<EstadoTicketDTO> mapearEstadosDTO() {
		
		ArrayList<EstadoTicketDTO> estadosTickets = new ArrayList<EstadoTicketDTO>();
		
		EstadoTicketDTO abiertoMAdto = GestorBD.mapearEstadoTicketDTO("ABIERTO_MA");
		estadosTickets.add(abiertoMAdto);
		abiertoMA = GestorBD.mapearEstadoTicket("ABIERTO_MA");
		
		EstadoTicketDTO abiertoDdto = GestorBD.mapearEstadoTicketDTO("ABIERTO_D");
		estadosTickets.add(abiertoDdto);
		abiertoD = GestorBD.mapearEstadoTicket("ABIERTO_D");
		
		EstadoTicketDTO solucionadoOKdto = GestorBD.mapearEstadoTicketDTO("SOLUCIONADO_OK");
		estadosTickets.add(solucionadoOKdto);
		solucionadoOK = GestorBD.mapearEstadoTicket("SOLUCIONADO_OK");
		
		EstadoTicketDTO cerradodto = GestorBD.mapearEstadoTicketDTO("CERRADO");
		estadosTickets.add(cerradodto);
		cerrado = GestorBD.mapearEstadoTicket("CERRADO");
		
		return estadosTickets;
	}
	
	public static Integer nroNuevoTicket() { 
		return GestorBD.nroNuevoTicket();
	}

	public static List<TicketDTO> buscarTickets(Integer nroTicket, Integer nroLegajo, Integer idClasificacion,
			EstadosTicket idEstado, Date fechaApertura, Date fechaUltimoCambio, Integer idGrupo) {
		// TODO Auto-generated method stub
		return GestorBD.buscarTickets(nroTicket, nroLegajo, idClasificacion, idEstado, fechaApertura, fechaUltimoCambio, idGrupo);
		
	}

	public static void derivarTicket(TicketDTO ticket, EstadoTicketDTO nuevoEstado, ClasificacionDTO clasificacionDto, GrupoDTO grupoDto, String observaciones) {
		if(clasificacionDto != ticket.getClasificacion()) {
			ticket.setClasificacion(clasificacionDto);
			HistorialClasificacionDTO ultimoHistorial = ticket.getUltimoHistorialClasificacion();
			Date fechaActual = new Date();
			ultimoHistorial.setFechaHasta(fechaActual);
			HistorialClasificacionDTO nuevo = new HistorialClasificacionDTO(GestorBD.nroNuevoHistorialC(), fechaActual, null, clasificacionDto.getIdClasificacion());
			ticket.addHistorialclasificacion(nuevo);
			List<IntervencionDTO> intervenciones = ticket.getIntervenciones();
			Integer aux = (intervenciones.size() - 1);
			while(aux >= 0) {
				GrupoDTO grupoIntervencion = GestorBD.mapearGrupoIntervencionDTO(intervenciones.get(aux).getId());
				if(grupoIntervencion == grupoDto && intervenciones.get(aux).getEstado().getIdEstadoInt() == EstadosIntervencion.ESPERA) {
					GestorDeIntervencion.activarIntervencionDTO(intervenciones.get(aux));
					aux = 0;
				} else {
					aux--;
				}
			}
			if(aux != 0) {
				IntervencionDTO nuevaIntervencion = GestorDeIntervencion.crearIntervencionDTO();
				GestorDeGrupo.setIntervenciones(grupoDto, GestorBD.mapearIntervencionesGrupoDTO(grupoDto.idGrupo));
				GestorDeGrupo.addIntervencion(grupoDto, nuevaIntervencion);
				ticket.addIntervencion(nuevaIntervencion);
			}
			HistorialEstadoTicketDTO ultimoEstado = ticket.getUltimoHistorialEstado();
			ultimoEstado.setFechaHasta(fechaActual);
			ticket.setEstadoActual(nuevoEstado);
			
			//Constructor con observaciones, revisar
			
			HistorialEstadoTicketDTO nuevoHistorial = new HistorialEstadoTicketDTO(GestorBD.nroNuevoHistorialET(), fechaActual, null, observaciones, Principal.usuarioIniciado.nroLegajo);
			ticket.addHistorialEstadoTicket(nuevoHistorial);
			
			//GUARDAR TICKET Y GRUPO
		}
		
	}
	
	public static EstadoTicketDTO devolverEstadoDTO(String idEstado) {
		return GestorBD.mapearEstadoTicketDTO(idEstado);
	}

}
