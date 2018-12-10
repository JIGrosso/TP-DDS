package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import clasesDTO.ClasificacionDTO;
import clasesDTO.EstadoTicketDTO;
import clasesDTO.GrupoDTO;
import clasesDTO.TicketDTO;
import gestores.GestorDeClasificacion;
import gestores.GestorDeGrupo;
import gestores.GestorDeTicket;
import produccion.EstadoTicket;
import produccion.Ticket;
import usuarios.Soporte;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelDerivacionTicket extends JPanel{

	private JLabel lblTitulo;
	private JLabel lblNroTicket;
	private JTextField txtNroTicket;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JLabel lblEstadoActual;
	private JTextField txtEstadoActual;
	private JLabel lblNuevoEstado;
	private JTextField txtNuevoEstado;
	private JLabel lblClasificacion;
	private JComboBox cmbClasificacion;
	private JLabel lblGrupo;
	private JComboBox cmbGrupo;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private JLabel lblObservaciones;
	private JTextArea txtObservaciones;
	
	public PanelDerivacionTicket(TicketDTO ticket) {
		this.setLayout(new GridBagLayout());
		this.construir(ticket);
	}
	
	
	public void construir(TicketDTO ticket) {
	
		GridBagConstraints gridConst =  new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;
		
		// Título
		
		lblTitulo = new JLabel("Derivación Ticket");
		lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		gridConst.gridx = 1;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(1, 5, 10, 0);
		this.add(lblTitulo, gridConst);
		
		// Fila 1
		
		lblNroTicket = new JLabel("Nro. Ticket: ");
		gridConst.gridy = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblNroTicket, gridConst);
		Integer nroTicket = ticket.getNroTicket();
		txtNroTicket = new JTextField(""+nroTicket);
		txtNroTicket.setEditable(false);
		txtNroTicket.setColumns(10);
		txtNroTicket.setForeground(Color.BLUE);
		txtNroTicket.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtNroTicket, gridConst);
		
		// Fila 2
		
		lblEstadoActual = new JLabel("Estado Actual: ");
		gridConst.gridy = 2;
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblEstadoActual, gridConst);

		txtEstadoActual = new JTextField();
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setColumns(20);
		EstadoTicketDTO estado = ticket.getEstadoActual();
		txtEstadoActual.setText(estado.getNombre());	
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtEstadoActual, gridConst);
		
		// Fila 3
		
		lblDescripcion = new JLabel("Descripción: ");
		gridConst.gridy = 3;
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblDescripcion, gridConst);
		
		// Fila 4
		
		txtDescripcion = new JTextArea(4, 40);
		txtDescripcion.setEditable(false);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.append(ticket.getDescripcion());
		gridConst.gridheight = 5;
		gridConst.gridwidth = 40;
		gridConst.gridy = 4;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(txtDescripcion, gridConst);
		
		// Fila 5
		
		lblNuevoEstado = new JLabel("Nuevo Estado: ");
		gridConst.gridheight = 1;
		gridConst.gridwidth = 1;
		gridConst.gridy = 10;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblNuevoEstado, gridConst);
		
		txtNuevoEstado = new JTextField();
		txtNuevoEstado.setEditable(false);
		EstadoTicketDTO nuevoEstado = GestorDeTicket.devolverEstadoDTO("ABIERTO_D");
		txtNuevoEstado.setText(nuevoEstado.getNombre());
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtNuevoEstado, gridConst);
		
		// Fila 6
		
		lblClasificacion = new JLabel("Clasificación de Ticket: ");
		gridConst.gridx = 1;
		gridConst.gridy = 11;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblClasificacion, gridConst);

		ArrayList<ClasificacionDTO> auxC = GestorDeClasificacion.getClasificaciones();
		ClasificacionDTO[] auxClasificaciones = auxC.toArray(new ClasificacionDTO[auxC.size()]);
		cmbClasificacion = new JComboBox<ClasificacionDTO>(auxClasificaciones);	
		cmbClasificacion.setEditable(false);
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(cmbClasificacion, gridConst);
		
		// Fila 7
		
		lblGrupo = new JLabel("Grupo de Resolución: ");
		gridConst.gridx = 1;
		gridConst.gridy = 12;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblGrupo, gridConst);
		
		ArrayList<GrupoDTO> auxG = GestorDeGrupo.getAll();
		GrupoDTO[] auxGrupos = auxG.toArray(new GrupoDTO[auxG.size()]);
		cmbGrupo = new JComboBox<GrupoDTO>(auxGrupos);
		cmbGrupo.setEditable(false);
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(cmbGrupo, gridConst);
		
		// Fila 8
		
		lblObservaciones = new JLabel("Observaciones: ");
		gridConst.gridy = 13;
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 5, 5, 5);
		this.add(lblObservaciones, gridConst);
		
		// Fila 9
		
		txtObservaciones = new JTextArea(4, 40);
		txtObservaciones.setLineWrap(true);
		gridConst.gridheight = 5;
		gridConst.gridwidth = 40;
		gridConst.gridy = 14;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(txtObservaciones, gridConst);
		
		//Salir de textArea apretando la tecla TAB
		
		txtObservaciones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiers() > 0) {
                    	txtObservaciones.transferFocusBackward();
                    } else {
                    	txtObservaciones.transferFocus();
                    }
                    e.consume();
                }
            }
        });
		
		// Fila 10

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(e -> {
			ClasificacionDTO clasificacionDto = (ClasificacionDTO) cmbClasificacion.getSelectedItem();
			GrupoDTO grupoDto = (GrupoDTO) cmbGrupo.getSelectedItem();
			String observaciones = txtDescripcion.toString();
			
			//hacer validación estatica si no se usa interfaz dinámica
			
			if (validarCamposNoVacios(clasificacionDto, grupoDto, observaciones)) {
				GestorDeTicket.derivarTicket(ticket, nuevoEstado, clasificacionDto, grupoDto, observaciones);
				JOptionPane.showMessageDialog(null,"El ticket se ha derivado correctamente", "Éxito", JOptionPane.OK_OPTION);
			}
			else {
				JOptionPane.showMessageDialog(null,"Ningún campo puede ser vacío", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		gridConst.anchor = GridBagConstraints.LINE_START;
		gridConst.gridwidth = 1;
		gridConst.gridheight = 1;
		gridConst.gridx = 2;
		gridConst.gridy = 20;
		gridConst.insets = new Insets(5, 20, 5, 5);
		this.add(btnConfirmar, gridConst);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> {
			JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
			frame.dispose();
		});
		gridConst.anchor = GridBagConstraints.LINE_END;
		gridConst.gridx = 1;
		gridConst.insets = new Insets(5, 20, 5, 5);
		this.add(btnCancelar, gridConst);
	}


	private boolean validarCamposNoVacios(ClasificacionDTO clasificacionDto,GrupoDTO grupoDto, String observaciones) {
		if(clasificacionDto != null || grupoDto != null || observaciones != "") {
			return true;
		}
		return false;
	}
}
