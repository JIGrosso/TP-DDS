package auxiliares;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.TicketDTO;

public class TablaVerDetalleTicket extends AbstractTableModel {

	private List<HistorialEstadoTicketDTO> historiales;
	private String[] columnas = {"Fecha Cambio Estado", "Hora Cambio Estado", "Operador", "Estado", "Grupo de Resolución", "Observaciones", "Clasificacion"};
	
	public List<HistorialEstadoTicketDTO> getHistoriales() {
		return historiales;
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
		return historiales.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		switch(columnIndex) {
		case 0:
			valor = this.historiales.get(rowIndex).fechaDesde;
			break;
		case 1:
			valor = "hora?";
			break;
		case 2:
			valor = this.historiales.get(rowIndex).legajoCreador;
			break;
		case 3:
			valor = this.historiales.get(rowIndex).estado;
			break;
		case 4:
			// A traves del soporte encontrar el grupo
			valor = "grupo?";
			break;
		case 5:
			valor = "observaciones?";
			break;
		case 6:
			valor = "clasificacion?";
			break;
		default:
			break;
		}	
		return valor;
	}

	public void setHistoriales (List<HistorialEstadoTicketDTO> historiales) {
		this.historiales = historiales;
	}

}