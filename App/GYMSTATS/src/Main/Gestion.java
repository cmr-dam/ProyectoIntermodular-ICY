package Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Gestion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField dni;
	private JTextField nom;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gestion frame = new Gestion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Gestion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Titulo = new JLabel("GYMSTATS");
		Titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		Titulo.setBounds(10, 11, 114, 22);
		contentPane.add(Titulo);
		
		JLabel txt_altaUser = new JLabel("Alta Cliente:");
		txt_altaUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		txt_altaUser.setBounds(10, 44, 104, 14);
		contentPane.add(txt_altaUser);
		
		JLabel txt_dni = new JLabel("DNI:");
		txt_dni.setBounds(10, 66, 29, 14);
		contentPane.add(txt_dni);
		
		dni = new JTextField();
		dni.setBounds(39, 63, 86, 20);
		contentPane.add(dni);
		dni.setColumns(10);
		
		JLabel txt_nom = new JLabel("Nombre:");
		txt_nom.setBounds(10, 94, 48, 14);
		contentPane.add(txt_nom);
		
		nom = new JTextField();
		nom.setColumns(10);
		nom.setBounds(68, 91, 86, 20);
		contentPane.add(nom);

	}
}
