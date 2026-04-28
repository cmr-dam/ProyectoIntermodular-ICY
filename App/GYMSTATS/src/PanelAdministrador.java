
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PanelAdministrador() {
		setTitle("Gymstats - Panel Administrador");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		setLocationRelativeTo(null);

	}
}
