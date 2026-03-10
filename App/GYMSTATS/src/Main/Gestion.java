package Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.*;
import java.awt.GridLayout;

public class Gestion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		try {
			 FlatDarkLaf.setup();
				Gestion frame = new Gestion();
				frame.setVisible(true);
		} catch(Exception pantalla) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar, vuelve a intentarlo.");
		}
	}

	public Gestion() {
		setTitle("Menú Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//LABEL TITULO
		JLabel Titulo = new JLabel("GYMSTATS");
		Titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		Titulo.setBounds(52, 10, 114, 22);
		contentPane.add(Titulo);
		
		//PANEL DE BOTONES
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(30, 43, 143, 175);
		contentPane.add(panelBotones);
		panelBotones.setLayout(new GridLayout(5, 1, 0, 0));
		
		//BOTON CREAR EMPLEADO
		JButton btnCrearEmpleado = new JButton("Nuevo Empleado");
		btnCrearEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panelBotones.add(btnCrearEmpleado);
		
		//BOTON MOSTRAR MEMBRESIAS
		JButton btnMostrarMembresias = new JButton("Mostrar Membresias");
		panelBotones.add(btnMostrarMembresias);
		btnMostrarMembresias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//BOTON CREAR CLIENTE
		JButton btnCrearCliente = new JButton("Nuevo Cliente");
		panelBotones.add(btnCrearCliente);
		btnCrearCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	}
}
