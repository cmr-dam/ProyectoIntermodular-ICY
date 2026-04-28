import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    //VARIABLES PRIVADAS
    private JTextField txtUsuario;
    private JTextField txtPassword;
    private JLabel  lblMensaje;

    public PanelLogin() {
        setTitle("GymStats - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 401);
        setResizable(false);
        setLocationRelativeTo(null);

        // Asignamos colores base
        Color fondoOscuro = new Color(30, 30, 30);
        Color azulGym = new Color(0, 212, 255);
        Color grisInput = new Color(45, 45, 45);

        contentPane = new JPanel();
        contentPane.setBackground(fondoOscuro);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("GYMSTATS");
        lblTitulo.setForeground(azulGym);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.ITALIC, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(49, 22, 300, 50);
        contentPane.add(lblTitulo);

        // --- CAMPO USUARIO ---
        JLabel lblUser = new JLabel("USUARIO");
        lblUser.setForeground(Color.GRAY);
        lblUser.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        lblUser.setBounds(49, 82, 100, 20);
        contentPane.add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBackground(grisInput);
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(azulGym);
        txtUsuario.setBorder(new EmptyBorder(0, 10, 0, 10));
        txtUsuario.setBounds(49, 107, 300, 40);
        contentPane.add(txtUsuario);

        // --- CAMPO CONTRASEÑA ---
        JLabel lblPass = new JLabel("CONTRASEÑA");
        lblPass.setForeground(Color.GRAY);
        lblPass.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        lblPass.setBounds(49, 167, 100, 20);
        contentPane.add(lblPass);

        txtPassword = new JTextField();
        txtPassword.setBackground(grisInput);
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(azulGym);
        txtPassword.setBorder(new EmptyBorder(0, 10, 0, 10));
        txtPassword.setBounds(49, 192, 300, 40);
        contentPane.add(txtPassword);
        
        //MENSAJE INFORMACION
        lblMensaje = new JLabel("");
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(125, 262, 155, 12);
        contentPane.add(lblMensaje);

        // --- BOTÓN ACCEDER ---
        JButton btnLogin = new JButton("ACCEDER");
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String user = txtUsuario.getText(); //Recogemos el usuario
        		String passwd = txtPassword.getText(); //Recogemos la contraseña
        			
        		if(passwd.isEmpty() || user.isEmpty()) { //Si los campos están vacios, mostramos el error en el lblMensaje
        			lblMensaje.setText("Debes rellenar todos los campos.");
        		} else {
        			lblMensaje.setText("");
        		}
        		
        		
        		//Una vez iniciamos sesion, lo dejamos en blanco
        		txtUsuario.setText("");
        		txtPassword.setText("");
        	}
        });
        btnLogin.setBackground(azulGym);
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(null);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBounds(49, 284, 300, 45);
        contentPane.add(btnLogin);
       
    }
}