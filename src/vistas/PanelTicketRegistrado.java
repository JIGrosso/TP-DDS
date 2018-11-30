package vistas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import produccion.Ticket;

public class PanelTicketRegistrado extends JPanel{

	private Ticket ticket;
	
	private JLabel lblTitulo;
	private JLabel lblObservaciones;
	private JLabel lblGrupo;
	private JTextArea txtObservaciones;
	private JComboBox cmbGrupos;
	private JButton btnCerrado;
	private JButton btnDerivar;
	private JButton btnSalir;
	
	public PanelTicketRegistrado(Ticket ticket) {
		this.ticket = ticket;
		this.setLayout(new GridBagLayout());
		this.construir();
	}

	private void construir() {
		
		GridBagConstraints gridConst = new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		lblTitulo = new JLabel("Registro Ticket - Nro. " + ticket.nroTicket);
		lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.gridwidth = 2;
		gridConst.insets = new Insets(0, 5, 20, 5);
		this.add(lblTitulo, gridConst);

		lblObservaciones = new JLabel("Observaciones ");
		gridConst.gridy = 1;
		gridConst.gridwidth = 1;
		gridConst.insets = new Insets(0, 5, 5, 5);
		this.add(lblObservaciones, gridConst);
		
		txtObservaciones = new JTextArea(10, 15);
		gridConst.gridy = 2;
		this.add(txtObservaciones, gridConst);
		
		btnCerrado = new JButton("Marcar como cerrado");
		btnCerrado.addActionListener(e -> {
			String obs = txtObservaciones.getText();
			// GestorDeTicket.cerrarTicket(obs);
		});
		gridConst.gridy = 3;
		this.add(btnCerrado, gridConst);
		
		lblGrupo = new JLabel("Grupos de Resolución ");
		gridConst.gridy = 1;
		gridConst.gridx = 1;
		this.add(lblGrupo, gridConst);
		
		String[] grupos = {"Grupo 1", "Grupo 2", "Grupo 3"};
		
		cmbGrupos = new JComboBox(grupos);
		gridConst.gridy = 2;
		this.add(cmbGrupos, gridConst);
		
		btnDerivar = new JButton("Derivar");
		gridConst.gridy = 3;
		this.add(btnDerivar, gridConst);
		
		
		
		
	}
}
