package vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import auxiliares.TablaVerDetalleTicket;
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
	
	private TablaVerDetalleTicket tablaDetalleTicket;
	
	private JTable tabla;
	
	public PanelVerDetalleTicket (TicketDTO ticketDTO) {
		
		this.setLayout(new GridBagLayout());
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
		
		txtNro = new JTextField(7);
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
		
		txtNroLegajo = new JTextField(7);
		txtNroLegajo.setEditable(false);
		txtNroLegajo.setText(String.valueOf(cliente.nroLegajo));
		gridConst.gridx = 1;
		this.add(txtNroLegajo, gridConst);
		
		lblTelefono = new JLabel("Tel. Directo: ");
		gridConst.gridx = 2;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblTelefono, gridConst);
		
		txtTelefono = new JTextField(7);
		txtTelefono.setEditable(false);
		txtTelefono.setText(String.valueOf(cliente.telefono));
		gridConst.gridx = 3;
		this.add(txtTelefono, gridConst);
		
		lblApellidoNombre = new JLabel("Apellido y Nombre: ");
		gridConst.gridy = 4;
		gridConst.gridx = 0;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblApellidoNombre, gridConst);
		
		txtApellidoNombre = new JTextField(7);
		txtApellidoNombre.setEditable(false);
		txtApellidoNombre.setText(String.valueOf(cliente.nombre));
		gridConst.gridx = 1;
		this.add(txtApellidoNombre, gridConst);
		

				
		
	}
	

	
}
