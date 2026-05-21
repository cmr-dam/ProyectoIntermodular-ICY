import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class PanelLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    //VARIABLES PRIVADAS
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel  lblMensaje;

    public PanelLogin() {
        setTitle("GymStats - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 401);
        setResizable(false);
        setLocationRelativeTo(null);

        //Asignamos colores base
        Color fondoOscuro = new Color(30, 30, 30);
        Color azulGym = new Color(0, 212, 255);
        Color grisInput = new Color(45, 45, 45);

        contentPane = new JPanel();
        contentPane.setBackground(fondoOscuro);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //TÍTULO
        JLabel lblTitulo = new JLabel("GYMSTATS");
        lblTitulo.setForeground(azulGym);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.ITALIC, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(49, 22, 300, 50);
        contentPane.add(lblTitulo);

        //CAMPO USUARIO
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

        //CAMPO CONTRASEÑA
        JLabel lblPass = new JLabel("CONTRASEÑA");
        lblPass.setForeground(Color.GRAY);
        lblPass.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        lblPass.setBounds(49, 167, 100, 20);
        contentPane.add(lblPass);

        txtPassword = new JPasswordField();
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

        //BOTÓN LOGIN
        JButton btnLogin = new JButton("ACCEDER");
        btnLogin.addActionListener(new ActionListener() {
        	@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
        		
        		try {
        		    String user = txtUsuario.getText(); 
        		    String passwd = txtPassword.getText(); 
        		        
        		    //Comprobamos que no estén vacíos
        		    if(passwd.isEmpty() || user.isEmpty()) { 
        		        lblMensaje.setText("Debes rellenar todos los campos.");
        		        return; //Salimos del método para que salte el error
        		    } else {
        		        lblMensaje.setText("");
        		    }
        		    
        		    // Conectamos a la BBDD
        		    Connection con = Main.getConectar();
        		    
        		    //Ejecutamos la consulta con PreparedStatement
        		    String sql = "SELECT dni, nombre FROM Cliente WHERE usuario = ? AND contraseña = ?"; //Usamos el ? para que se rellene el parametro
        		    PreparedStatement pstmt = con.prepareStatement(sql);
        		    pstmt.setString(1, user);
        		    pstmt.setString(2, passwd);
        		    
        		    ResultSet rs = pstmt.executeQuery(); 
        		    
        		    //Si rs.next() es true, ese usuario existira en la base
        		    //Primero buscamos clientes
        		    if(rs.next()) {
        		        String dniCliente = rs.getString("dni");
        		        String nombreCliente = rs.getString("nombre"); //Recogemos el nombre
        		        
        		        String sqlAcceso = "UPDATE Cliente SET ultimo_acceso = CURRENT_TIMESTAMP WHERE dni = ?";
        		        PreparedStatement pstmtAcceso = con.prepareStatement(sqlAcceso);
        		        pstmtAcceso.setString(1, dniCliente);
        		        pstmtAcceso.executeUpdate();
        		        pstmtAcceso.close();
        		        
        		        // Abrimos la ventana del usuario pasándole su nombre
        		        PanelUsuario panelUser = new PanelUsuario(dniCliente, nombreCliente, PanelLogin.this);
        		        panelUser.setVisible(true);
        		        
        		        //Cerramos la ventana de login
        		        dispose();
        		    } else {
        		        //Si no es cliente, buscamos en la tabla de administrador
        		        String sqlAdmin = "SELECT nombre FROM Administrador WHERE usuario = ? AND contraseña = ?";
        		        PreparedStatement pstmtAdmin = con.prepareStatement(sqlAdmin);
        		        pstmtAdmin.setString(1, user);
        		        pstmtAdmin.setString(2, passwd);
        		        
        		        ResultSet rsAdmin = pstmtAdmin.executeQuery();
        		        
        		        if(rsAdmin.next()) {
        		            //Si entra aquí, es que es un admin
        		            PanelAdministrador panelAdmin = new PanelAdministrador(PanelLogin.this);
        		            panelAdmin.setVisible(true);
        		            
        		            txtUsuario.setText("");
        		            txtPassword.setText("");
        		            //Cerramos la ventana de login y vaciamos los campos de texto
        		            dispose();
        		        } else {
        		            //Si no hay coincidencia, las credenciales son incorrectas
        		            lblMensaje.setText("Usuario/contraseña incorrectos.");
        		            txtPassword.setText(""); //Limpiamos la contraseña
        		        }
        		        
        		        //Cerramos el ResultSet y el PreparedStatement del admin
        		        rsAdmin.close();
        		        pstmtAdmin.close();
        		    }
        		    
        		    //Cerramos el preparedStarement y el ResultSet
        		    rs.close();
        		    pstmt.close();
        		    
        		} catch(SQLException ex) {
        		    JOptionPane.showMessageDialog(contentPane, "Error de conexión con la base de datos.");
        		    ex.printStackTrace();
        		}
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