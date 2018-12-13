package vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import clasesDTO.ClasificacionDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.TicketDTO;
import gestores.GestorDeGrupo;
import gestores.GestorDeTicket;
import produccion.Ticket;
import usuarios.GrupoDeResolucion;

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
	
	public PanelTicketRegistrado(Ticket ticketCreado) {
		this.ticket = ticketCreado;
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
			GestorDeTicket.cerrarTicket(ticket, obs);
			JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
			frame.dispose();
		});
		gridConst.gridy = 3;
		this.add(btnCerrado, gridConst);
		
		lblGrupo = new JLabel("Grupos de Resolución ");
		gridConst.gridy = 1;
		gridConst.gridx = 1;
		this.add(lblGrupo, gridConst);
		
		ArrayList<GrupoDTO> gruposClasificados = GestorDeGrupo.getGruposClasificados(ticket.clasificacion);
		GrupoDTO[] auxGrupos = gruposClasificados.toArray(new GrupoDTO[gruposClasificados.size()]);
		
		cmbGrupos = new JComboBox(auxGrupos);
		gridConst.gridy = 2;
		this.add(cmbGrupos, gridConst);
		
		btnDerivar = new JButton("Derivar");
		btnDerivar.addActionListener(e -> {
			GrupoDTO grupoAux = new GrupoDTO(ticket.grupoAsignado.idGrupo, ticket.grupoAsignado.nombre);
			ClasificacionDTO clasificacionAux = new ClasificacionDTO(ticket.clasificacion.idClasificacion, ticket.clasificacion.nombre, ticket.clasificacion.descripcionAlcance);
			TicketDTO ticketAux = new TicketDTO(ticket.nroTicket, ticket.cliente.nroLegajo, grupoAux, clasificacionAux, ticket.fechaYHoraApertura, ticket.estadoActual);
			derivarTicket(ticketAux);
		});
		gridConst.gridy = 3;
		this.add(btnDerivar, gridConst);

	}
	
	public void derivarTicket(TicketDTO ticket) {
		
		JFrame newFrame = new JFrame();
		newFrame.setVisible(true);
		newFrame.setSize(1000, 500);
		newFrame.setTitle("Derivar Ticket");
		
		PanelDerivacionTicket derivarTicket = new PanelDerivacionTicket(ticket);
		newFrame.setContentPane(derivarTicket);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        newFrame.setLocation(dim.width/2- newFrame.getSize().width/2, dim.height/2- newFrame.getSize().height/2);
		
	}
}
