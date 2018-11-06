package vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelRegistroTicketInicial extends JPanel {

	private JLabel lblTitulo;
	private JLabel lblNroTicket;
	private JLabel lblNroLegajo;
	private JLabel lblClasificacion;
	private JLabel lblFechaApertura;
	private JLabel lblHoraApertura;
	private JLabel lblDescripcion;
	private JTextField txtNroTicket;
	private JTextField txtNroLegajo;
	private JTextArea txtDescripcion;
	private JComboBox cmbClasificacion;
	private JTextField txtFechaApertura;
	private JTextField txtHoraApertura;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	
	public PanelRegistroTicketInicial() {
		this.setLayout(new GridBagLayout());
		this.construir();
	}
	
	
	public void construir() {
		GridBagConstraints gridConst =  new GridBagConstraints();
		
		// Título
		
		lblTitulo = new JLabel("Registro Ticket");
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.gridheight = 1;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(lblTitulo, gridConst);
		
		// Fila 1
		
		lblNroTicket = new JLabel("Nro. Ticket: ");
		gridConst.gridx = 0;
		gridConst.gridy = 1;
		gridConst.gridwidth = 5;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblNroTicket, gridConst);
		
		lblNroLegajo = new JLabel("Nro. Legajo: ");
		gridConst.gridx = 1;
		gridConst.gridy = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblNroLegajo, gridConst);
		
		txtNroTicket = new JTextField();
		gridConst.gridx = 0;
		gridConst.gridy = 2;
		gridConst.insets = new Insets(5, 5, 10, 5);
		this.add(txtNroTicket, gridConst);
		
		txtNroLegajo = new JTextField();
		gridConst.gridx = 1;
		gridConst.gridy = 2;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtNroLegajo, gridConst);
		
		// Fila 2
		
		lblClasificacion = new JLabel("Clasificacion de Ticket: ");
		gridConst.gridx = 0;
		gridConst.insets = new Insets(5, 5, 5, 0);
		
		cmbClasificacion = new JComboBox();
		gridConst.gridy = 4;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(cmbClasificacion, gridConst);
		
		
		// Fila 3
		
		lblFechaApertura = new JLabel("Fecha Apertura: ");
		gridConst.gridy = 5;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblFechaApertura, gridConst);
		
		lblHoraApertura = new JLabel("Hora Apertura: ");
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblHoraApertura, gridConst);
		
		txtFechaApertura = new JTextField();
		gridConst.gridx = 0;
		gridConst.gridy = 6;
		gridConst.insets = new Insets(5, 5, 10, 5);
		this.add(txtFechaApertura, gridConst);
		
		txtHoraApertura = new JTextField();
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtHoraApertura, gridConst);
		
		
		// Fila 4
		
		lblDescripcion = new JLabel("Descripcion: ");
		gridConst.gridx = 0;
		gridConst.gridy = 7;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblDescripcion, gridConst);
		
		txtDescripcion = new JTextArea();
		gridConst.gridy = 8;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtDescripcion, gridConst);
		
		//Fila 5
		
		btnConfirmar = new JButton("Confirmar");
		gridConst.gridy = 9;
		gridConst.insets = new Insets(5, 20, 20, 20);
		this.add(btnConfirmar, gridConst);
		
		btnCancelar = new JButton("Cancelar");
		gridConst.gridx = 1;
		gridConst.gridy = 9;
		gridConst.insets = new Insets(5, 20, 5, 5);
		this.add(btnCancelar, gridConst);
				
	}
	
}
