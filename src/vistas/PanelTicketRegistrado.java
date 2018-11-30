package vistas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import produccion.Ticket;

public class PanelTicketRegistrado extends JPanel{

	private Ticket ticket;
	
	private JLabel lblTitulo;
	private JLabel lblObservaciones;
	private JLabel lblGrupo;
	private JButton btnObservaciones;
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
		this.add(lblTitulo, gridConst);

		
	}
}
