import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gestion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    //VARIABLES PRIVADAS
    private JTextField txtUsuario;
    private JTextField txtPassword;
    private JLabel  lblMensaje;

    public static void main(String[] args) {
        // Aplicamos el estilo LookAndFeel para que se vea mas bonito
        try { 
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {}
        Gestion frame = new Gestion();
        frame.setVisible(true);
    }

    public Gestion() {
        setTitle("GymStats - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 500);
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
        lblTitulo.setBounds(50, 40, 300, 50);
        contentPane.add(lblTitulo);

        // --- CAMPO USUARIO ---
        JLabel lblUser = new JLabel("USUARIO");
        lblUser.setForeground(Color.GRAY);
        lblUser.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        lblUser.setBounds(50, 130, 100, 20);
        contentPane.add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBackground(grisInput);
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(azulGym);
        txtUsuario.setBorder(new EmptyBorder(0, 10, 0, 10));
        txtUsuario.setBounds(50, 155, 300, 40);
        contentPane.add(txtUsuario);

        // --- CAMPO CONTRASEÑA ---
        JLabel lblPass = new JLabel("CONTRASEÑA");
        lblPass.setForeground(Color.GRAY);
        lblPass.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        lblPass.setBounds(50, 215, 100, 20);
        contentPane.add(lblPass);

        txtPassword = new JTextField();
        txtPassword.setBackground(grisInput);
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(azulGym);
        txtPassword.setBorder(new EmptyBorder(0, 10, 0, 10));
        txtPassword.setBounds(50, 240, 300, 40);
        contentPane.add(txtPassword);
        
        //FOOTER
        JLabel lblFoot = new JLabel("Login Administrador");
        lblFoot.setForeground(new Color(70, 70, 70));
        lblFoot.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFoot.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoot.setBounds(50, 430, 300, 20);
        contentPane.add(lblFoot);
        
        //MENSAJE INFORMACION
        lblMensaje = new JLabel("");
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(122, 290, 155, 12);
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
        btnLogin.setBounds(50, 340, 300, 45);
        contentPane.add(btnLogin);
       
    }
}