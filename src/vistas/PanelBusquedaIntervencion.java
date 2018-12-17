package vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import auxiliares.TablaBusquedaIntervencion;
import clasesDTO.GrupoDTO;
import clasesDTO.IntervencionDTO;
import gestores.GestorDeIntervencion;
import produccion.EstadoIntervencion;
import produccion.EstadosIntervencion;

public class PanelBusquedaIntervencion extends JPanel {
	//Búsqueda
		private JLabel lblTitulo;
		private JLabel lblNroTicket;
		private JLabel lblNroLegajo;
		private JLabel lblEstado;
		private JLabel lblFechaDesde;
		private JLabel lblFechaHasta;
		private JTextField txtNroTicket;
		private JTextField txtNroLegajo;
		private JDateChooser txtFechaDesde;
		private JDateChooser txtFechaHasta;
		private JComboBox cmbEstado;
		private JButton btnConsultar;
		
		// Resultados
		private JLabel lblResultados;
		private JTable tabla;
		private TablaBusquedaIntervencion tablaResultados;
		private JButton btnModificarEstado;
		private JButton btnModificarComentarios;
		private JButton btnCancelar;
		
		public PanelBusquedaIntervencion() {
			this.setLayout(new GridBagLayout());
			this.tablaResultados = new TablaBusquedaIntervencion();
			this.setResultado(new ArrayList<>(), false);
			this.construir();
		}

		private void construir() {
			
			GridBagConstraints gridConst = new GridBagConstraints();
			
			gridConst.anchor = GridBagConstraints.LINE_START;
			
			lblTitulo = new JLabel("Consultar Intervenciones");
			lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
			gridConst.gridx = 0;
			gridConst.gridy = 0;
			gridConst.insets = new Insets(0, 5, 15, 0);
			this.add(lblTitulo, gridConst);
			
			lblEstado = new JLabel("Estado Ticket");
			gridConst.gridy = 1;
			this.add(lblEstado, gridConst);
			
			ArrayList<EstadoIntervencion> estados = GestorDeIntervencion.mapearEstadosIntervencion();
			estados.add(new EstadoIntervencion(null, "Todos", null));
			EstadoIntervencion[] auxEstados = estados.toArray(new EstadoIntervencion[estados.size()]);
			
			cmbEstado = new JComboBox<EstadoIntervencion>(auxEstados);
			cmbEstado.setEditable(false);
			cmbEstado.setPreferredSize(new Dimension(180, 20));
			cmbEstado.setSelectedItem(GestorDeIntervencion.asignada);
			gridConst.gridx = 1;
			this.add(cmbEstado, gridConst);
			
			lblFechaDesde = new JLabel("Fecha Desde");
			gridConst.gridx = 0;
			gridConst.gridy = 2;
			this.add(lblFechaDesde, gridConst);

			txtFechaDesde = new JDateChooser();
			txtFechaDesde.setPreferredSize(new Dimension(180, 20));
			gridConst.gridx = 1;
			this.add(txtFechaDesde, gridConst);
			
			lblFechaHasta = new JLabel("Fecha Hasta");
			gridConst.gridx = 0;
			gridConst.gridy = 3;
			this.add(lblFechaHasta, gridConst);

			txtFechaHasta = new JDateChooser();
			txtFechaHasta.setPreferredSize(new Dimension(180, 20));
			gridConst.gridx = 1;
			this.add(txtFechaHasta, gridConst);
			
			lblNroTicket = new JLabel("Nro. Ticket");
			gridConst.gridx = 0;
			gridConst.gridy = 4;
			gridConst.insets = new Insets(5, 5, 5, 0);
			this.add(lblNroTicket, gridConst);
			
			txtNroTicket = new JTextField(15);
			gridConst.gridx = 1;
			gridConst.insets = new Insets(5, 0, 5, 5);
			this.add(txtNroTicket, gridConst);
			
			lblNroLegajo = new JLabel("Nro. Legajo");
			gridConst.gridx = 0;
			gridConst.gridy = 5;
			gridConst.insets = new Insets(5, 5, 5, 0);
			this.add(lblNroLegajo, gridConst);
			
			txtNroLegajo = new JTextField(15);
			gridConst.gridx = 1;
			gridConst.insets = new Insets(5, 0, 5, 5);
			this.add(txtNroLegajo, gridConst);
			//Busqueda 
			
			btnConsultar = new JButton("Consultar");
			btnConsultar.addActionListener(e -> {
				Integer nroTicket = null;
				Integer nroLegajo = null;
				Date fechaDesde = null;
				Date fechaHasta = null;
				EstadoIntervencion estado = null;
				
				if(!txtNroTicket.getText().isEmpty()) {
					nroTicket = Integer.valueOf(txtNroTicket.getText());
				}
				if(!txtNroLegajo.getText().isEmpty()) {
					nroLegajo = Integer.valueOf(txtNroLegajo.getText());
				}
				
				if(!(txtFechaDesde.getDate() == null)) {
					fechaDesde = txtFechaDesde.getDate();
				}
				if(!(txtFechaHasta.getDate() == null)) {
					fechaHasta = txtFechaHasta.getDate();
				}
				if(((EstadoIntervencion)cmbEstado.getSelectedItem()).getNombre() != "Todos") {
					estado = ((EstadoIntervencion) cmbEstado.getSelectedItem());
				}
				
				setResultado(GestorDeIntervencion.buscarIntervenciones(nroTicket, nroLegajo, estado, fechaDesde, fechaHasta), true);
			});
		}
		public void setResultado(List<IntervencionDTO> listaResultado, boolean actualizar) {
			this.tablaResultados.setIntervenciones(listaResultado);
			if(actualizar) {
				this.tablaResultados.fireTableDataChanged();
			}
		}
}
