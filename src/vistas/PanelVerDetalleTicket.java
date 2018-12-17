package vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import auxiliares.TablaBusquedaTicket;
import auxiliares.TablaVerDetalleTicket;
import clasesDTO.HistorialEstadoTicketDTO;
import clasesDTO.TicketDTO;
import gestores.GestorDeCliente;
import usuarios.Cliente;

public class PanelVerDetalleTicket extends JPanel {
	
	private JLabel lblDetalleTicket;
	private JLabel lblNro;
	private JTextField txtNro;
	private JLabel lblDatosDelUsuario;
	private JLabel lblNroLegajo;
	private JTextField txtNroLegajo;
	private JLabel lblTelefono;
	private JTextField txtTelefono;
	private JLabel lblApellidoNombre;
	private JTextField txtApellidoNombre;
	private JLabel lblCargo;
	private JTextField txtCargo;
	private JLabel lblTelefonoInterno;
	private JTextField txtTelefonoInterno;
	private JLabel lblUbicacion;
	private JTextField txtUbicacion;
	private JLabel lblDetalleHistorico;
	private JTextField txtDetalleHistorico;
	
	private TablaVerDetalleTicket tablaDetalleTicket;
	
	private JTable tabla;
	
	public PanelVerDetalleTicket (TicketDTO ticketDTO) {
		
		this.setLayout(new GridBagLayout());
		this.tablaDetalleTicket = new TablaVerDetalleTicket();
		this.setResultado(new ArrayList<>(), false);
		this.construir(ticketDTO);
	}
	
	public void construir(TicketDTO ticketDTO) {
		
		GridBagConstraints gridConst = new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		Cliente cliente = GestorDeCliente.mapearCliente(ticketDTO.nroLegajoCliente);
		
		lblDetalleTicket = new JLabel("Detalle Ticket");
		lblDetalleTicket.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(0, 5, 15, 0);
		this.add(lblDetalleTicket, gridConst);
		
		lblNro = new JLabel("Número: ");
		gridConst.gridy = 1;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblNro, gridConst);
		
		txtNro = new JTextField(10);
		txtNro.setEditable(false);
		txtNro.setText(String.valueOf(ticketDTO.nroTicket));
		gridConst.gridx = 1;
		this.add(txtNro, gridConst);
		
		lblDatosDelUsuario = new JLabel("Datos del Usuario");
		lblDatosDelUsuario.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 2;
		gridConst.insets = new Insets(0, 5, 15, 0);
		this.add(lblDatosDelUsuario, gridConst);
		
		lblNroLegajo = new JLabel("Nro. Legajo: ");
		gridConst.gridy = 3;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblNroLegajo, gridConst);
		
		txtNroLegajo = new JTextField(10);
		txtNroLegajo.setEditable(false);
		txtNroLegajo.setText(String.valueOf(cliente.nroLegajo));
		gridConst.gridx = 1;
		this.add(txtNroLegajo, gridConst);
		
		lblTelefono = new JLabel("Tel. Directo: ");
		gridConst.gridx = 2;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblTelefono, gridConst);
		
		txtTelefono = new JTextField(13);
		txtTelefono.setEditable(false);
		txtTelefono.setText(String.valueOf(cliente.telefono));
		gridConst.gridx = 3;
		this.add(txtTelefono, gridConst);
		
		lblApellidoNombre = new JLabel("Apellido y Nombre: ");
		gridConst.gridy = 4;
		gridConst.gridx = 0;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblApellidoNombre, gridConst);
		
		txtApellidoNombre = new JTextField(10);
		txtApellidoNombre.setEditable(false);
		txtApellidoNombre.setText(String.valueOf(cliente.nombre));
		gridConst.gridx = 1;
		this.add(txtApellidoNombre, gridConst);

		lblCargo = new JLabel("Cargo: ");
		gridConst.gridx = 2;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblCargo, gridConst);
		
		txtCargo = new JTextField(13);
		txtCargo.setEditable(false);
		txtCargo.setText(String.valueOf(cliente.cargo));
		gridConst.gridx = 3;
		this.add(txtCargo, gridConst);
		
		lblTelefonoInterno = new JLabel("Telefono Interno: ");
		gridConst.gridy = 5;
		gridConst.gridx = 0;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblTelefonoInterno, gridConst);
		
		txtTelefonoInterno = new JTextField(10);
		txtTelefonoInterno.setEditable(false);
		txtTelefonoInterno.setText(String.valueOf(cliente.telefonoInterno));
		gridConst.gridx = 1;
		this.add(txtTelefonoInterno, gridConst);	
		
		lblUbicacion = new JLabel("Ubicación: ");
		gridConst.gridx = 2;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblUbicacion, gridConst);
		
		txtUbicacion = new JTextField(13);
		txtUbicacion.setEditable(false);
		txtUbicacion.setText(String.valueOf(cliente.ubicacion));
		gridConst.gridx = 3;
		this.add(txtUbicacion, gridConst);
		
		lblDetalleHistorico = new JLabel("Detalle Histórico del Reclamo: ");
		lblDetalleHistorico.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 6;
		this.add(lblDetalleHistorico, gridConst);
		
		gridConst.fill = GridBagConstraints.BOTH;
		tabla = new JTable(tablaDetalleTicket);
		tabla.setFillsViewportHeight(true);
		tabla.setRowSelectionAllowed(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		gridConst.gridy = 7;
		gridConst.gridx = 0;	
		gridConst.gridwidth = 10;
		this.add(scrollPane, gridConst);
		gridConst.fill = GridBagConstraints.NONE;
		
		Box empty = new Box(1);
		Component c1 = empty.createGlue();
		c1.setPreferredSize(new Dimension(80, 20));
		gridConst.gridwidth = 1;
		gridConst.gridy = 8;
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
		
		Component c5 = empty.createGlue();
		c5.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 7;
		this.add(c5, gridConst);
		
		Component c6 = empty.createGlue();
		c6.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 8;
		this.add(c6, gridConst);
		
		Component c7 = empty.createGlue();
		c7.setPreferredSize(new Dimension(80, 20));
		gridConst.gridx = 9;
		this.add(c7, gridConst);
	}
	
	public void setResultado(List<HistorialEstadoTicketDTO> listaResultado, boolean actualizar) {
		this.tablaDetalleTicket.setHistoriales(listaResultado);
		if(actualizar) {
			this.tablaDetalleTicket.fireTableDataChanged();
		}
	}

	
}
