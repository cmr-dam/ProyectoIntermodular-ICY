
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PanelUsuario extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public PanelUsuario() {
		setTitle("Gymstats - Panel Usuario");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Bienvenido " + );
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(0, 212, 255));
		lblTitulo.setFont(new Font("Segoe UI Black", Font.ITALIC, 32));
		lblTitulo.setBounds(0, 0, 300, 50);
		contentPane.add(lblTitulo);
		
	}
}
