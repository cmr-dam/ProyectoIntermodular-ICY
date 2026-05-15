import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AñadirSocio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni, txtNombre, txtApellido, txtUsuario;
	private JPasswordField txtPass;

	public AñadirSocio(PanelAdministrador p) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		
		setTitle("GymStats - Añadir Socio");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setBounds(100, 100, 400, 550);
		setLocationRelativeTo(null);
		setResizable(false);

		// COLORES DEL TEMA GYMSTATS
		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//JLABEL
		JLabel lblTitulo = new JLabel("NUEVO SOCIO");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		//TEXTFIELDS
		txtDni = new JTextField();
		txtDni.setBounds(40, 80, 300, 45);
		darEstiloCampo(txtDni, "DNI del Socio", fondoOscuro);
		contentPane.add(txtDni);

		txtNombre = new JTextField();
		txtNombre.setBounds(40, 140, 300, 45);
		darEstiloCampo(txtNombre, "Nombre", fondoOscuro);
		contentPane.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setBounds(40, 200, 300, 45);
		darEstiloCampo(txtApellido, "Apellido", fondoOscuro);
		contentPane.add(txtApellido);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(40, 260, 300, 45);
		darEstiloCampo(txtUsuario, "Usuario (Login)", fondoOscuro);
		contentPane.add(txtUsuario);

		txtPass = new JPasswordField();
		txtPass.setBounds(40, 320, 300, 45);
		darEstiloCampo(txtPass, "Contraseña", fondoOscuro);
		contentPane.add(txtPass);

		//BOTON GUARDAR
		JButton btnGuardar = new JButton("GUARDAR SOCIO");
		btnGuardar.setBounds(40, 400, 300, 45);
		btnGuardar.setBackground(azulGym);
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarEnBBDD(p); //Llamamos al método de abajo pasándole el panel padre
			}
		});
		contentPane.add(btnGuardar);

		// --- BOTÓN CANCELAR ---
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 455, 300, 30);
		btnCancelar.setBackground(fondoOscuro);
		btnCancelar.setForeground(Color.GRAY);
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCancelar.setBorder(null);
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //Si cancelamos libreamos memoria usando dispsose
				p.setVisible(true);
			}
		});
		contentPane.add(btnCancelar);
	}

	//Metodo para ponerle el estilo a los demas campos sin tener que repetirlo todo el rato
	private void darEstiloCampo(JTextField campo, String titulo, Color fondo) {
		campo.setBackground(fondo);
		campo.setForeground(Color.WHITE);
		campo.setCaretColor(new Color(0, 212, 255));
		campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
	}

	// MÉTODO PARA INSERTAR EN POSTGRESQL
	private void guardarEnBBDD(PanelAdministrador p) {
		//Validamos que no dejen el DNI o el usuario en blanco
		if (txtDni.getText().trim().isEmpty() || txtUsuario.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Rellena los campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			Connection con = Main.getConectar();
			String sql = "INSERT INTO Cliente (dni, nombre, apellido, usuario, contraseña) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, txtDni.getText().trim());
			pstmt.setString(2, txtNombre.getText().trim());
			pstmt.setString(3, txtApellido.getText().trim());
			pstmt.setString(4, txtUsuario.getText().trim());
			pstmt.setString(5, new String(txtPass.getPassword()));

			pstmt.executeUpdate();
			pstmt.close();

			JOptionPane.showMessageDialog(this, "Socio registrado con éxito.");
			
			//Una vez hecho actualizamos los datos de los socios llamando a el pètodo de el PanelAdministrador de cargarDatosSocios.
			p.cargarDatosSocios(); 
			
			dispose(); //Cerramos esta ventana y liberamos memoria
			p.setVisible(true); //Abrimos el panel principal de nuevo

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: El DNI o Usuario ya existen en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}