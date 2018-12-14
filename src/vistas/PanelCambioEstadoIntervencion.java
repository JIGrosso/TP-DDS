package vistas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import clasesDTO.GrupoDTO;
import clasesDTO.IntervencionDTO;
import clasesDTO.TicketDTO;
import gestores.GestorDeClasificacion;
import gestores.GestorDeIntervencion;
import produccion.EstadoIntervencion;
import produccion.EstadoTicket;

public class PanelCambioEstadoIntervencion extends JPanel {
	
	private JLabel lblTitulo;
	private JLabel lblGrupoDeResolucion;
	private JTextField txtGrupoDeResolucion;
	private JLabel lblIdIntervencion;
	private JTextField txtIdIntervencion;
	private JLabel lblEstadoActual;
	private JTextField txtEstadoActual;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JLabel lblNuevoEstado;
	private JComboBox cmbNuevoEstado;
	private JLabel lblClasificacionDelTicket;
	private JComboBox cmbClasificacionDelTicket;
	private JLabel lblObservaciones;
	private JTextArea txtObservaciones;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	

	
	public PanelCambioEstadoIntervencion(IntervencionDTO intervencion) {
		this.setLayout(new GridBagLayout());
		this.construir(intervencion);
	}
	
	
	public void construir(IntervencionDTO intervencion) {
		TicketDTO ticket = GestorDeIntervencion.recuperarTicketDTO(intervencion.getId());
		GridBagConstraints gridConst =  new GridBagConstraints();
		
		gridConst.anchor = GridBagConstraints.LINE_START;

		// Título
		
		lblTitulo = new JLabel("Actualizar Estado de Intervención");
		lblTitulo.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		gridConst.gridx = 1;
		gridConst.gridy = 0;
		gridConst.insets = new Insets(1, 5, 10, 0);
		this.add(lblTitulo, gridConst);
		
		//Fila 1
		
		lblGrupoDeResolucion = new JLabel("Grupo de Resolución relacionado: ");
		gridConst.gridy = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblGrupoDeResolucion, gridConst);
		
		txtGrupoDeResolucion = new JTextField();
		txtGrupoDeResolucion.setEditable(false);
		txtGrupoDeResolucion.setText(GestorDeIntervencion.getGrupoIntervencion(intervencion).getNombre());
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtGrupoDeResolucion, gridConst);
		
		//Fila 2
		
		lblIdIntervencion = new JLabel("Id Intervención: ");
		gridConst.gridx = 1;
		gridConst.gridy = 2;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblIdIntervencion, gridConst);
		
		txtIdIntervencion = new JTextField();
		txtIdIntervencion.setEditable(false);
		txtIdIntervencion.setText(intervencion.getId().toString());
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtIdIntervencion, gridConst);
		
		//Fila 3
		
		lblEstadoActual = new JLabel("Estado actual: ");
		gridConst.gridx = 1;
		gridConst.gridy = 3;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblEstadoActual, gridConst);
		
		txtEstadoActual = new JTextField();
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setText(intervencion.getEstado().getNombre());
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(txtEstadoActual, gridConst);
		
		//Fila 4
		
		lblDescripcion = new JLabel("Descripción del problema: ");
		gridConst.gridx = 1;
		gridConst.gridy = 4;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblDescripcion, gridConst);
		
		//Fila 5
		
		txtDescripcion = new JTextArea(4, 40);
		txtDescripcion.setEditable(false);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.append(ticket.getDescripcion());
		gridConst.gridy = 5;
		gridConst.gridwidth = 40;
		gridConst.gridheight = 4;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(txtDescripcion, gridConst);
				
		//Fila 6
		
		lblNuevoEstado = new JLabel("Nuevo estado:");
		gridConst.gridy = 11;
		gridConst.gridwidth = 1;
		gridConst.gridheight = 1;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblNuevoEstado, gridConst);
		
		ArrayList<EstadoIntervencion> auxEst = GestorDeIntervencion.getEstadosDistintosIntervencion(intervencion.getEstado());
		EstadoIntervencion[] auxEstados = auxEst.toArray(new EstadoIntervencion[auxEst.size()]);
		cmbNuevoEstado = new JComboBox<EstadoIntervencion>(auxEstados);
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(cmbNuevoEstado, gridConst);
		
		//Fila 7
		
		lblClasificacionDelTicket = new JLabel("Clasificación de Ticket: ");
		gridConst.gridx = 1;
		gridConst.gridy = 12;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblClasificacionDelTicket, gridConst);
		
		ArrayList<ClasificacionDTO> clasificaciones = GestorDeClasificacion.getClasificaciones();
		ClasificacionDTO[] auxClasificaciones = clasificaciones.toArray(new ClasificacionDTO[clasificaciones.size()]);
		cmbClasificacionDelTicket = new JComboBox<ClasificacionDTO>(auxClasificaciones);
		cmbClasificacionDelTicket.setSelectedItem(ticket.getClasificacion());
		gridConst.gridx = 2;
		gridConst.insets = new Insets(5, 0, 5, 5);
		this.add(cmbClasificacionDelTicket, gridConst);
		
		//Fila 8
		
		lblObservaciones = new JLabel("Observaciones: ");
		gridConst.gridx = 1;
		gridConst.gridy = 13;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(lblDescripcion, gridConst);
		
		//Fila 9
		
		txtObservaciones = new JTextArea(4,40);
		txtObservaciones.setLineWrap(true);
		gridConst.gridheight = 4;
		gridConst.gridwidth = 40;
		gridConst.gridy = 14;
		gridConst.insets = new Insets(5, 5, 5, 0);
		this.add(txtObservaciones, gridConst);
		
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
		
		//Fila 10 - Botones
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(e -> {
			ClasificacionDTO clasificacionDto = (ClasificacionDTO) cmbClasificacionDelTicket.getSelectedItem();
			EstadoIntervencion nuevoEstadoIntervencion = (EstadoIntervencion) cmbNuevoEstado.getSelectedItem();
			String observaciones = txtDescripcion.getText();
			if (validarCamposNoVacios(clasificacionDto, nuevoEstadoIntervencion, observaciones)) {
				GestorDeIntervencion.actualizarEstadoIntervencion(ticket, intervencion, clasificacionDto, nuevoEstadoIntervencion, observaciones);
				JOptionPane.showMessageDialog(null,"Las modificaciones en su intervencion han sido realizadas exitosamente", "Éxito", JOptionPane.OK_OPTION);
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

	private boolean validarCamposNoVacios(ClasificacionDTO clasificacionDto, EstadoIntervencion estadoIntervencion, String observaciones) {
		if(clasificacionDto != null || estadoIntervencion != null || observaciones != null) {
			return true;
		}else {
			return false;
		}
	}
}
