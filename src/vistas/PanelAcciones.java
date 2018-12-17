package vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelAcciones extends JPanel{
	
	private JLabel lblSesion;
	private JLabel lblNro;
	private JTextField txtNro;
	private JButton btnRegistroTicket;
	private JButton btnConsultarTickets;
	private JButton btnCerrar;
	private JButton btnSalir;
	
	public PanelAcciones() {
		this.setLayout(new GridBagLayout());
		this.construir();
	}
	
	public void construir() {
		
		GridBagConstraints gridConst = new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.CENTER;
		
		lblSesion = new JLabel("Sesión de " + Principal.usuarioIniciado.getGrupo().getNombre());
		lblSesion.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		gridConst.gridx = 0;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(0, 5, 15, 0);
		this.add(lblSesion, gridConst);
		
		gridConst.anchor = GridBagConstraints.LINE_END;
		
		lblNro = new JLabel("Nro. de Legajo: ");
		gridConst.gridy = 1;
		gridConst.gridwidth = 1;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblNro, gridConst);
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		txtNro = new JTextField(7);
		txtNro.setEditable(false);
		txtNro.setText(String.valueOf(Principal.usuarioIniciado.getNroLegajo()));
		gridConst.gridx = 1;
		this.add(txtNro, gridConst);
		
		gridConst.anchor = GridBagConstraints.CENTER;
		
		btnRegistroTicket = new JButton("Registrar Ticket");
		btnRegistroTicket.addActionListener(e -> {
			registrarTicket();
		});
		gridConst.gridx = 0;
		gridConst.gridy = 2;
		gridConst.gridwidth = 2;
		this.add(btnRegistroTicket, gridConst);
		
		btnConsultarTickets = new JButton("Consultar Tickets");
		btnConsultarTickets.addActionListener(e -> {
			consultarTickets();
		});
		gridConst.gridy = 3;
		this.add(btnConsultarTickets, gridConst);
		
		btnCerrar = new JButton("Cerrar Sesión");
		gridConst.gridy = 4;
		gridConst.gridwidth = 1;
		this.add(btnCerrar, gridConst);
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		btnSalir = new JButton("Salir del sistema");
		btnSalir.addActionListener(e->System.exit(99));
		gridConst.gridx = 1;
		this.add(btnSalir, gridConst);
		
		
	}

	public void registrarTicket() {
		
		JFrame newFrame = new JFrame();
		newFrame.setVisible(true);
		newFrame.setSize(500, 500);
		newFrame.setTitle("Registro Ticket");
		
		PanelRegistroTicketInicial registro = new PanelRegistroTicketInicial();
		newFrame.setContentPane(registro);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        newFrame.setLocation(dim.width/2- newFrame.getSize().width/2, dim.height/2- newFrame.getSize().height/2);
	}
	
	public void consultarTickets( ) {
		
		JFrame newFrame = new JFrame();
		newFrame.setVisible(true);
		newFrame.setSize(1400, 900);
		newFrame.setTitle("Consultar Tickets");
		
		PanelBusquedaTicket busqueda = new PanelBusquedaTicket();
		newFrame.setContentPane(busqueda);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        newFrame.setLocation(dim.width/2- newFrame.getSize().width/2, dim.height/2- newFrame.getSize().height/2);
	}
}
