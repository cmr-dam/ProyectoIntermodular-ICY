import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;

public class Gestion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		Gestion frame = new Gestion();
		frame.setVisible(true);
	}

	public Gestion() {
		setTitle("GymStats");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 335);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//PANELES
		JPanel pnl_Opciones = new JPanel();
		pnl_Opciones.setBackground(Color.GRAY);
		pnl_Opciones.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnl_Opciones.setBounds(10, 112, 460, 176);
		contentPane.add(pnl_Opciones);
		
		//LABELS
		JLabel lblTitulo = new JLabel("GymStats");
		lblTitulo.setFont(new Font("Segoe UI", Font.ITALIC, 28));
		lblTitulo.setBackground(Color.WHITE);
		lblTitulo.setBounds(10, 10, 238, 49);
		contentPane.add(lblTitulo);
		
	}
}
