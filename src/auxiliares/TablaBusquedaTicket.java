package auxiliares;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.TicketDTO;

public class TablaBusquedaTicket extends AbstractTableModel {

	private List<TicketDTO> tickets;
	private String[] columnas = {"Nro. Ticket", "Nro.Legajo", "Fecha Apertura", "Hora Apertura", "Operador Inicial", "Clasificacion", "Estado", "Ult. Cambio de Estado", "Grupo de Resolución"};
	
	public List<TicketDTO> getTickets() {
		return tickets;
	}
	
	public void setTickets (List<TicketDTO> tickets) {
		this.tickets = tickets;
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
		return tickets.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		switch(columnIndex) {
		case 0:
			valor = this.tickets.get(rowIndex).getNroTicket();
			break;
		case 1:
			valor = this.tickets.get(rowIndex).getNroLegajoCliente();
			break;
		case 2:
			SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
			valor = formatFecha.format(this.tickets.get(rowIndex).getFechaYHoraApertura());
			break;
		case 3:
			SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss.SSS");
			valor = formatHora.format(this.tickets.get(rowIndex).getFechaYHoraApertura());
			break;
		case 4:
			valor = this.tickets.get(rowIndex).historialesEstado.get(0).legajoCreador;
			break;
		case 5:
			valor = this.tickets.get(rowIndex).getClasificacion().nombre;
			break;
		case 6:
			valor = this.tickets.get(rowIndex).getEstadoActual().nombre;
			break;
		case 7:
			List<HistorialEstadoTicketDTO> auxHistoriales = this.tickets.get(rowIndex).historialesEstado;
			valor = auxHistoriales.get(auxHistoriales.size()-1).fechaDesde;	
			break;
		case 8:
			valor = this.tickets.get(rowIndex).getGrupoAsignado().nombre;
			break;
		default:
			break;
		}	
		return valor;
	}

}