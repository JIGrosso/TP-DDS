package vistas;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import database_console.DBConnect;

public class Principal {

	public static void main(String[] args) {
		
		DBConnect.connectDB();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500, 500);
		
//		PanelInicio inicio = new PanelInicio();
//		frame.setContentPane(inicio);
		
		PanelRegistroTicketInicial registro = new PanelRegistroTicketInicial();
		frame.setContentPane(registro);
		
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2- frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
	}
}
