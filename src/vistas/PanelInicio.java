package vistas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gestores.GestorBD;
import gestores.GestorDeSoporte;

public class PanelInicio extends JPanel{

	private JLabel lblNombre;
	private JLabel lblTitulo;
	private JLabel lblNro;
	private JLabel lblPass;
	private JTextField txtNro;
	private JPasswordField txtPass;
	private JButton btnIniciar;
	private JButton btnSalir;
	
	public PanelInicio() {
		this.setLayout(new GridBagLayout());
		this.construir();
	}
	
	public void construir() {
		
		GridBagConstraints gridConst = new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.CENTER;
		
		lblNombre = new JLabel("La Llamita S.A.");
		lblNombre.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		gridConst.gridx = 1;
		gridConst.gridy = 1;
		gridConst.insets = new Insets(15, 5, 20, 5);
		this.add(lblNombre, gridConst);
		
		lblTitulo = new JLabel("Inicio de Sesión");
		gridConst.gridy = 2;
		gridConst.insets = new Insets(0, 5, 15, 5);
		this.add(lblTitulo, gridConst);
		
		lblNro = new JLabel("Nro. de Legajo");
		gridConst.gridy = 3;
		this.add(lblNro, gridConst);
		
		txtNro = new JTextField();
		txtNro.setColumns(10);
		gridConst.gridy = 4;
		this.add(txtNro, gridConst);
		
		lblPass = new JLabel("Contraseña");
		gridConst.gridy = 5;
		this.add(lblPass, gridConst);
		
		txtPass = new JPasswordField();
		txtPass.setColumns(10);
		gridConst.gridy = 6;
		this.add(txtPass, gridConst);
		
		btnIniciar = new JButton("Iniciar");
		
		btnIniciar.addActionListener(e -> {
			try {
				
				Integer nroLegajo = Integer.valueOf(txtNro.getText());
				String password = String.valueOf(txtPass.getPassword());
				
				if(!GestorDeSoporte.validarSoporte(nroLegajo, password)) {
					throw new Exception();
				}
				else {
					
					Principal.usuarioIniciado = GestorDeSoporte.mapearSoporte(nroLegajo);
			
					JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
					this.setVisible(false);
			
					PanelAcciones acciones = new PanelAcciones();
					frame.setContentPane(acciones);
					frame.setTitle("Usuario: "+Principal.usuarioIniciado.getNombre());
				}
			
			} catch (Exception er) {
				JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.gridy = 7;
		this.add(btnIniciar, gridConst);
		
		btnSalir = new JButton("Salir");
		gridConst.gridy = 8;
		btnSalir.addActionListener(e->System.exit(99));
		this.add(btnSalir, gridConst);
		
	}
	
	
	
}
