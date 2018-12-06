package vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import auxiliares.TablaBusquedaTicket;
import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoTicketDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.TicketDTO;
import gestores.GestorDeTicket;
import produccion.EstadosTicket;

public class PanelBusquedaTicket extends JPanel{

	//Búsqueda
	private JLabel lblTitulo;
	private JLabel lblNroTicket;
	private JLabel lblNroLegajo;
	private JLabel lblClasificacion;
	private JLabel lblEstado;
	private JLabel lblFechaApertura;
	private JLabel lblFechaUltimoCambio;
	private JLabel lblGrupo;
	private JTextField txtNroTicket;
	private JTextField txtNroLegajo;
	private JDateChooser txtFechaApertura;
	private JDateChooser txtFechaUltimoCambio;
	private JComboBox cmbClasificacion;
	private JComboBox cmbEstado;
	private JComboBox cmbGrupo;
	private JButton btnConsultar;
	
	// Resultados
	private JLabel lblResultados;
	private JTable tabla;
	private TablaBusquedaTicket tablaResultados;
	private JButton btnVerDetalle;
	private JButton btnReporte;
	private JButton btnDerivar;
	private JButton btnCerrar;
	private JButton btnCancelar;
	
	
	
	public PanelBusquedaTicket() {
		this.setLayout(new GridBagLayout());
		this.tablaResultados = new TablaBusquedaTicket();
		this.setResultado(new ArrayList<>(), false);
		this.construir();
	}

	private void construir() {
		
		GridBagConstraints gridConst = new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		lblTitulo = new JLabel("Consultar Ticket");
		lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(0, 5, 15, 0);
		this.add(lblTitulo, gridConst);
		
		lblNroTicket = new JLabel("Nro. Ticket");
		gridConst.gridy = 1;
		gridConst.insets = new Insets(0, 5, 5, 10);
		this.add(lblNroTicket, gridConst);
		
		txtNroTicket = new JTextField(15);
		gridConst.gridy = 2;
		this.add(txtNroTicket, gridConst);
		
		lblNroLegajo = new JLabel("Nro. Legajo");
		gridConst.gridy = 3;
		this.add(lblNroLegajo, gridConst);
		
		txtNroLegajo = new JTextField(15);
		gridConst.gridy=4;
		this.add(txtNroLegajo, gridConst);
		
		lblClasificacion = new JLabel("Clasificación Ticket");
		gridConst.gridy = 5;
		this.add(lblClasificacion, gridConst);
		
		cmbClasificacion = new JComboBox();
		cmbClasificacion.setEditable(false);
		cmbClasificacion.setPreferredSize(new Dimension(180, 20));
		gridConst.gridy = 6;
		this.add(cmbClasificacion, gridConst);
		
		lblEstado = new JLabel("Estado Ticket");
		gridConst.gridy = 7;
		this.add(lblEstado, gridConst);
		
		cmbEstado = new JComboBox();
		cmbEstado.setEditable(false);
		cmbEstado.setPreferredSize(new Dimension(180, 20));
		gridConst.gridy = 8;
		this.add(cmbEstado, gridConst);
		
		lblFechaApertura = new JLabel("Fecha de Apertura");
		gridConst.gridx = 1;
		gridConst.gridy = 1;
		this.add(lblFechaApertura, gridConst);

		txtFechaApertura = new JDateChooser();
		txtFechaApertura.setPreferredSize(new Dimension(180, 20));
		gridConst.gridy = 2;
		this.add(txtFechaApertura, gridConst);
		
		lblFechaUltimoCambio = new JLabel("Fecha Último Cambio de Estado");
		gridConst.gridy = 3;
		this.add(lblFechaUltimoCambio, gridConst);

		txtFechaUltimoCambio = new JDateChooser();
		txtFechaUltimoCambio.setPreferredSize(new Dimension(180, 20));
		gridConst.gridy = 4;
		this.add(txtFechaUltimoCambio, gridConst);
		
		lblGrupo = new JLabel("Grupo de Resolución Asignado");
		gridConst.gridy = 5;
		this.add(lblGrupo, gridConst);
		
		ClasificacionDTO[] clasificaciones = {};
		cmbGrupo = new JComboBox(clasificaciones);
		cmbGrupo.setEditable(false);
		cmbGrupo.setPreferredSize(new Dimension(180, 20));
		gridConst.gridy = 6;
		this.add(cmbGrupo, gridConst);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(e -> {
			Integer nroTicket = Integer.valueOf(txtNroTicket.getText());
			Integer nroLegajo = Integer.valueOf(txtNroLegajo.getText());
			Integer idClasificacion = ((ClasificacionDTO) cmbClasificacion.getSelectedItem()).idClasificacion;
			EstadosTicket idEstado = ((EstadoTicketDTO) cmbEstado.getSelectedItem()).idEstadoTicket;
			Date fechaApertura = txtFechaApertura.getDate();
			Date fechaUltimoCambio = txtFechaUltimoCambio.getDate();
			Integer idGrupo = ((GrupoDTO) cmbGrupo.getSelectedItem()).idGrupo;
			
			GestorDeTicket.buscarTickets(nroTicket, nroLegajo, idClasificacion, idEstado, fechaApertura, fechaUltimoCambio, idGrupo);
		});
		gridConst.gridy = 8;
		this.add(btnConsultar, gridConst);
		
		lblResultados = new JLabel("Resultados");
		lblResultados.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 9;
		this.add(lblResultados, gridConst);
		
		gridConst.fill = GridBagConstraints.BOTH;
		tabla = new JTable(tablaResultados);
		tabla.setFillsViewportHeight(true);
		tabla.setRowSelectionAllowed(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		gridConst.gridy = 10;
		gridConst.gridwidth = 9;
		this.add(scrollPane, gridConst);
		gridConst.fill = GridBagConstraints.NONE;
		
		btnVerDetalle = new JButton("Ver Detalle");
		gridConst.gridy = 11;
		gridConst.gridwidth = 1;
		this.add(btnVerDetalle, gridConst);
		
		btnReporte = new JButton("Configurar Reporte");
		gridConst.gridx = 1;
		this.add(btnReporte, gridConst);
		
		btnDerivar = new JButton("Derivar Ticket");
		gridConst.gridx = 2;
		this.add(btnDerivar, gridConst);
		
		
		btnCerrar = new JButton("Cerrar Ticket");
		gridConst.gridx = 3;
		this.add(btnCerrar, gridConst);
		
		//Empty
		Box empty = new Box(1);
		Component c1 = empty.createGlue();
		c1.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 4;
		this.add(c1, gridConst);
		
		Component c2 = empty.createGlue();
		c2.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 5;
		this.add(c2, gridConst);
		
		Component c3 = empty.createGlue();
		c3.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 6;
		this.add(c3, gridConst);
		
		Component c4 = empty.createGlue();
		c4.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 7;
		this.add(c4, gridConst);
		
		btnCancelar = new JButton("Cancelar");
		gridConst.gridx = 8;
		this.add(btnCancelar, gridConst);
		
	}
	
	public void setResultado(List<TicketDTO> listaResultado, boolean actualizar) {
		this.tablaResultados.setTickets(listaResultado);
		if(actualizar) {
			this.tablaResultados.fireTableDataChanged();
		}
	}
}
