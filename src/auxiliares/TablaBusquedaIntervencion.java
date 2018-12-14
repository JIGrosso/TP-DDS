package auxiliares;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.IntervencionDTO;
import clasesDTO.TicketDTO;
import gestores.GestorBD;

public class TablaBusquedaIntervencion extends AbstractTableModel{
	
	private List<IntervencionDTO> intervenciones;
	private String[] columnas = {"IDIntervenciónn", "Nro. Ticket", "Clasificacion Actual", "Fecha Apertura", "Hora Apertura", "Estado Intervención", 
			"Fecha Asignación", "Grupo de Resolución", "Observaciones"};
	
	public List<IntervencionDTO> getIntervenciones() {
		return intervenciones;
	}
	
	public void setIntervenciones (List<IntervencionDTO> intervenciones) {
		this.intervenciones = intervenciones;
	}
	
	public String getColumnName(int indice) {
		return this.columnas[indice];
	}
	
	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		return intervenciones.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		TicketDTO ticketRelacionado = GestorBD.mapearTicketIntervDTO(intervenciones.get(rowIndex).getId());
		switch(columnIndex) {
		case 0:
			valor = this.intervenciones.get(rowIndex).getId();
			break;
		case 1:
			valor = ticketRelacionado.getNroTicket();
			break;
		case 2:
			valor = "Legajo: " + ticketRelacionado.historialesEstado.get(0).legajoCreador;
			break;
		case 3:
			valor = ticketRelacionado.getClasificacion().nombre;
			break;
		case 4:
			SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
			valor = formatFecha.format(ticketRelacionado.getFechaYHoraApertura());
			break;
		case 5:
			SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
			valor = formatHora.format(ticketRelacionado.getFechaYHoraApertura());
			break;
		case 6:
			valor = intervenciones.get(rowIndex).getEstado().nombre;
			break;
		case 7:
			SimpleDateFormat formatFechaCompleta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			valor = formatFechaCompleta.format(intervenciones.get(rowIndex).getFechaAsignacion());
			break;
		case 8:
			valor = GestorBD.mapearGrupoIntervencionDTO(intervenciones.get(rowIndex).getId()).nombre;
			break;
		default:
			break;
		}	
		return valor;
	}

}
