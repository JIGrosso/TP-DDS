package vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gestores.GestorBD;
import gestores.GestorDeTicket;

public class PanelRegistroTicketInicial extends JPanel implements GestorBD{

	private JLabel lblTitulo;
	private JLabel lblNroTicket;
	private JTextField txtNroTicket;
	private JLabel lblNroLegajo;
	private JTextField txtNroLegajo;
	private JLabel lblClasificacion;
	private JComboBox cmbClasificacion;
	private JLabel lblFechaApertura;
	private JTextField txtFechaApertura;
	private JLabel lblHoraApertura;
	private JTextField txtHoraApertura;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	
	public PanelRegistroTicketInicial() {
		this.setLayout(new GridBagLayout());
		this.construir();
	}
	
	
	public void construir() {
		
		GridBagConstraints gridConst =  new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		// Columna 1
		
		// Título
		
		lblTitulo = new JLabel("Registro Ticket");
		lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(lblTitulo, gridConst);
		
		// Fila 1
		
		lblNroTicket = new JLabel("Nro. Ticket ");
		gridConst.gridy = 1;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblNroTicket, gridConst);
		
		// Fila 2
		
		Integer nroTicket = GestorBD.nroNuevoTicket();
		
		txtNroTicket = new JTextField(""+nroTicket);
		txtNroTicket.setEditable(false);
		txtNroTicket.setColumns(10);
		txtNroTicket.setForeground(Color.BLUE);
		txtNroTicket.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		gridConst.gridy = 2;
		gridConst.insets = new Insets(5, 5, 10, 5);
		this.add(txtNroTicket, gridConst);
		
		// Fila 3
		
		lblNroLegajo = new JLabel("Nro. Legajo de Cliente");
		gridConst.gridy = 3;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblNroLegajo, gridConst);
		
		// Fila 4

		txtNroLegajo = new JTextField();
		txtNroLegajo.setColumns(10);
		gridConst.gridy = 4;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtNroLegajo, gridConst);
		
		// Fila 5
		
		lblClasificacion = new JLabel("Clasificacion de Ticket: ");
		gridConst.gridy = 5;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblClasificacion, gridConst);
		
		// Fila 6
		
		cmbClasificacion = new JComboBox();
		gridConst.gridy = 6;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(cmbClasificacion, gridConst);
		
		// Fila 7
		
		Date fechaActual = new Date();
		
		DateFormat dateFormatFecha = new SimpleDateFormat("yyyy/MM/dd");
		String fechaApertura = dateFormatFecha.format(fechaActual);
		
		DateFormat dateFormatHora = new SimpleDateFormat("HH:mm:ss");
		String horaApertura = dateFormatHora.format(fechaActual);
		
		lblFechaApertura = new JLabel("Fecha Apertura: ");
		gridConst.gridy = 7;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblFechaApertura, gridConst);
		
		// Fila 8
		
		txtFechaApertura = new JTextField(fechaApertura);
		txtFechaApertura.setColumns(10);
		txtFechaApertura.setEditable(false);
		gridConst.gridy = 8;
		gridConst.insets = new Insets(5, 5, 10, 5);
		this.add(txtFechaApertura, gridConst);
		
		// Fila 9
		
		lblHoraApertura = new JLabel("Hora Apertura: ");
		gridConst.gridy = 9;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblHoraApertura, gridConst);
		
		// Fila 10
		
		txtHoraApertura = new JTextField(horaApertura);
		txtHoraApertura.setColumns(10);
		txtHoraApertura.setEditable(false);
		gridConst.gridy = 10;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtHoraApertura, gridConst);
		
		
		// Columna 2 y 3
		
		// Fila 1
		
		lblDescripcion = new JLabel("Descripcion: ");
		gridConst.gridx = 1;
		gridConst.gridy = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblDescripcion, gridConst);
		
		// Fila 2
		
		txtDescripcion = new JTextArea(10, 20);
		gridConst.gridheight = 6;
		gridConst.gridwidth = 2;
		gridConst.gridy = 2;
		gridConst.insets = new Insets(5, 5, 10, 0);
		this.add(txtDescripcion, gridConst);
		
		//Fila 3
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(e -> {
			boolean noVacios = validarCamposNoVacios(txtNroLegajo.getText(), cmbClasificacion.getSelectedItem().toString(), txtDescripcion.getText());
			if(noVacios) {
				Integer nroLegajo = Integer.valueOf(txtNroLegajo.getText());
				String descripcion = txtDescripcion.getText();
				Integer idClasificacion = 1;
				GestorDeTicket.crearTicket(Principal.usuarioIniciado, nroTicket, nroLegajo, fechaActual, idClasificacion, descripcion);
			}
			else {
				JOptionPane.showMessageDialog(null,"Ningún campo puede ser vacío", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridy = 9;
		gridConst.gridx = 2;
		gridConst.gridwidth = 1;
		gridConst.gridheight = 1;
		gridConst.insets = new Insets(5, 20, 20, 5);
		this.add(btnConfirmar, gridConst);
		
		btnCancelar = new JButton("Cancelar");
		gridConst.anchor = GridBagConstraints.LINE_END;
		gridConst.gridx = 1;
		gridConst.gridy = 9;
		gridConst.insets = new Insets(5, 20, 20, 5);
		this.add(btnCancelar, gridConst);
				
	}

	private boolean validarCamposNoVacios(String nroLegajo, String clasificacion, String descripcion) {
		if(nroLegajo.isEmpty() || clasificacion.toString().isEmpty() || descripcion.isEmpty()) {
			return false;
		}
		return true;
	}
	
}
