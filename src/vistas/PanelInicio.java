package vistas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelInicio extends JPanel{

	private JLabel lblNombre;
	private JLabel lblTitulo;
	private JLabel lblNro;
	private JLabel lblPass;
	private JTextField txtNro;
	private JTextField txtPass;
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
		gridConst.gridx = 1;
		gridConst.gridy = 1;
		this.add(lblNombre, gridConst);
		
		lblTitulo = new JLabel("Inicio de Sesión");
		gridConst.gridy = 2;
		this.add(lblTitulo, gridConst);
		
		lblNro = new JLabel("Nro. de Legajo");
		gridConst.gridy = 3;
		this.add(lblNro, gridConst);
		
		txtNro = new JTextField();
		txtNro.setColumns(15);
		gridConst.gridy = 4;
		this.add(txtNro, gridConst);
		
		lblPass = new JLabel("Contraseña");
		gridConst.gridy = 5;
		this.add(lblPass, gridConst);
		
		txtPass = new JTextField();
		txtPass.setColumns(15);
		gridConst.gridy = 6;
		this.add(txtPass, gridConst);
		
		btnIniciar = new JButton("Iniciar");
		gridConst.gridy = 7;
		this.add(btnIniciar, gridConst);
		
		btnSalir = new JButton("Salir");
		gridConst.gridy = 8;
		btnSalir.addActionListener(e->System.exit(99));
		this.add(btnSalir, gridConst);
		
	}
	
	
	
}
