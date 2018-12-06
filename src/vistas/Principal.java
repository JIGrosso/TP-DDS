package vistas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoTicketDTO;
import clasesDTO.EstadoIntervencionDTO;
import gestores.GestorBD;
import gestores.GestorDeClasificacion;
import gestores.GestorDeIntervencion;
import gestores.GestorDeTicket;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;

public class Principal {

	public static Soporte usuarioIniciado;

	public static ArrayList<EstadoTicketDTO> estadosTicket;

	public static ArrayList<EstadoIntervencionDTO> estadosIntervencion;

//	public static ArrayList<ClasificacionDTO> clasificaciones;

	public static void main(String[] args) {

		cargarEstados();

//		cargarClasificaciones();

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
		frame.setTitle("Inicio de Sesión");

		PanelInicio inicio = new PanelInicio();
		frame.setContentPane(inicio);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2- frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
        
	}

	public static void cargarEstados() {

		estadosTicket = GestorDeTicket.mapearEstadosDTO();
		estadosIntervencion = GestorDeIntervencion.mapearEstadosIntervencionDTO();
	}

//	public static void cargarClasificaciones() {
//		
//		clasificaciones = GestorDeClasificacion.getClasificaciones();
//	}
}
