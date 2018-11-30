package vistas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gestores.GestorBD;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;
import usuarios.GrupoDeResolucion;
import usuarios.Soporte;
import produccion.Clasificacion;

public class Principal implements GestorBD{
	
	public static Soporte usuarioIniciado;
	
	public static EstadoTicket abiertoMA;
	public static EstadoTicket abiertoD;
	public static EstadoTicket solucionadoOK;
	public static EstadoTicket cerrado;
	
	public static EstadoIntervencion asignada;
	public static EstadoIntervencion activa;
	public static EstadoIntervencion espera;
	public static EstadoIntervencion cerrada;
	
	public static ArrayList<Clasificacion> clasificaciones;
	
	public static List<GrupoDeResolucion> gruposMapeados = new ArrayList<GrupoDeResolucion>();
	public static List<Soporte> soportesMapeados = new ArrayList<Soporte>();

	public static void main(String[] args) {
		
		cargarEstados();
		
		cargarClasificaciones();
		
		
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
		frame.setTitle("Inicio de Sesi�n");
		
		PanelInicio inicio = new PanelInicio();
		frame.setContentPane(inicio);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2- frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
        
	}
	
	public static void cargarEstados() {
		
		abiertoMA = GestorBD.mapearEstadoTicket("ABIERTO_MA");
		abiertoD = GestorBD.mapearEstadoTicket("ABIERTO_D");
		solucionadoOK = GestorBD.mapearEstadoTicket("SOLUCIONADO_OK");
		cerrado = GestorBD.mapearEstadoTicket("CERRADO");

		asignada = GestorBD.mapearEstadoIntervencion("ASIGNADA");
		activa = GestorBD.mapearEstadoIntervencion("ACTIVA");
		espera = GestorBD.mapearEstadoIntervencion("ESPERA");
		cerrada = GestorBD.mapearEstadoIntervencion("CERRADA");
	}
	
	public static void cargarClasificaciones() {
		
		clasificaciones = GestorBD.mapearClasificaciones();
		
	}
}
