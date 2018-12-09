package auxiliares;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import clasesDTO.TicketDTO;

public class TablaVerDetalleTicket extends AbstractTableModel {

	private List<TicketDTO> tickets;
	private String[] columnas = {"Fecha Cambio Estado", "Hora Cambio Estado", "Operador", "Estado", "Grupo de Resolución", "Observaciones", "Clasificacion"};
	
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
			valor = this.tickets.get(rowIndex).getFechaYHoraApertura();
			break;
		case 3:
			valor = this.tickets.get(rowIndex).getFechaYHoraApertura();
			break;
		case 4:
			// Obtener el primer historial y de ahi el soporte que abrio el ticket
			valor = "soporte";
			break;
		case 5:
			valor = this.tickets.get(rowIndex).getClasificacion().nombre;
			break;
		case 6:
			valor = this.tickets.get(rowIndex).getEstadoActual().nombre;
			break;
		case 7:
			// Obtener el ultimo historial de cambio de estado
			valor = "ultimo cambio";
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