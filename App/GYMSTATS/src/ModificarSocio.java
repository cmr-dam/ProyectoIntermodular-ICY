import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ModificarSocio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni, txtNombre, txtApellido, txtUsuario, txtDiasAcceso;
	private JPasswordField txtPass;
	private String dniOriginal; //Guardamos el DNI original

	private PanelAdministrador pAdmin;
	private PanelEmpleado pEmp;

	public ModificarSocio(PanelAdministrador p, String dniSeleccionado) {
		this.pAdmin = p;
		this.dniOriginal = dniSeleccionado;
		inicializar();
	}

	public ModificarSocio(PanelEmpleado p, String dniSeleccionado) {
		this.pEmp = p;
		this.dniOriginal = dniSeleccionado;
		inicializar();
	}

	private void inicializar() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				if (pAdmin != null) pAdmin.setVisible(true);
				else if (pEmp != null) pEmp.setVisible(true);
			}
		});
		
		setTitle("GymStats - Modificar Socio");
		Main.setIconoApp(this);
		setBounds(100, 100, 400, 620); 
		setLocationRelativeTo(null); 
		setResizable(false);

		//Colores
		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//LABEL TITULO
		JLabel lblTitulo = new JLabel("MODIFICAR SOCIO");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		//TEXTFIELDS
		txtDni = new JTextField();
		txtDni.setBounds(40, 80, 300, 45);
		darEstiloCampo(txtDni, "DNI del Socio (No modificable)", fondoOscuro);
		txtDni.setEditable(false); // BLOQUEAMOS EL DNI
		txtDni.setForeground(Color.GRAY);
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

		txtDiasAcceso = new JTextField();
		txtDiasAcceso.setBounds(40, 380, 300, 45);
		darEstiloCampo(txtDiasAcceso, "Días de Acceso Restantes", fondoOscuro);
		contentPane.add(txtDiasAcceso);

		//CARGAMOS LOS DATOS DEL SOCIO ANTES DE MOSTRAR LA VENTANA
		cargarDatosSocio();

		//BOTN ACTUALIZAR
		JButton btnActualizar = new JButton("ACTUALIZAR DATOS");
		btnActualizar.setBounds(40, 460, 300, 45);
		btnActualizar.setBackground(azulGym);
		btnActualizar.setForeground(Color.BLACK);
		btnActualizar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
		btnActualizar.setFocusPainted(false);
		btnActualizar.setBorder(null);
		btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEnBBDD(); 
			}
		});
		contentPane.add(btnActualizar);

		//BOTON CANCELAR
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 515, 300, 30);
		btnCancelar.setBackground(fondoOscuro);
		btnCancelar.setForeground(Color.GRAY);
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCancelar.setBorder(null);
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (pAdmin != null) pAdmin.setVisible(true);
				else if (pEmp != null) pEmp.setVisible(true);
			}
		});
		contentPane.add(btnCancelar);
	}

	private void darEstiloCampo(JTextField campo, String titulo, Color fondo) {
		campo.setBackground(fondo);
		campo.setForeground(Color.WHITE);
		campo.setCaretColor(new Color(0, 212, 255));
		campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
	}

	// MÉTODO PARA TRAER LOS DATOS DE LA BBDD A LOS TEXTFIELDS
	private void cargarDatosSocio() {
		try {
			Connection con = Main.getConectar();
			String sql = "SELECT * FROM Cliente WHERE dni = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dniOriginal);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				txtDni.setText(rs.getString("dni"));
				txtNombre.setText(rs.getString("nombre"));
				txtApellido.setText(rs.getString("apellido"));
				txtUsuario.setText(rs.getString("usuario"));
				txtPass.setText(rs.getString("contraseña"));
				
				Date sqlDate = rs.getDate("fecha_compra");
				int diasQuedan = 0;
				if (sqlDate != null) {
					java.time.LocalDate fechaCompra = sqlDate.toLocalDate();
					java.time.LocalDate hoy = java.time.LocalDate.now();
					long diasPasados = java.time.temporal.ChronoUnit.DAYS.between(fechaCompra, hoy);
					diasQuedan = (int) (30 - diasPasados);
					if (diasQuedan < 0) diasQuedan = 0;
				}
				txtDiasAcceso.setText(String.valueOf(diasQuedan));
			}
			
			rs.close();
			pstmt.close();
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al cargar los datos del socio.");
			ex.printStackTrace();
		}
	}

	// MÉTODO PARA HACER EL UPDATE EN POSTGRESQL
	private void actualizarEnBBDD() {
		String nombre = txtNombre.getText().trim();
		String apellido = txtApellido.getText().trim();
		String usuario = txtUsuario.getText().trim();
		String pass = new String(txtPass.getPassword()).trim();

		if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || pass.isEmpty() || txtDiasAcceso.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos deben estar rellenos.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int nuevosDias = 0;
		try {
			nuevosDias = Integer.parseInt(txtDiasAcceso.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Los días de acceso deben ser un número.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			Connection con = Main.getConectar();
			
			// Calculamos nueva fecha_compra
			java.time.LocalDate hoy = java.time.LocalDate.now();
			java.time.LocalDate nuevaFecha = hoy.minusDays(30 - nuevosDias);
			java.sql.Date sqlNuevaFecha = java.sql.Date.valueOf(nuevaFecha);

			//Hacemos un UPDATE filtrando por el DNI
			String sql = "UPDATE Cliente SET nombre = ?, apellido = ?, usuario = ?, contraseña = ?, fecha_compra = ? WHERE dni = ?";
			PreparedStatement pstmt = con.prepareStatement(sql); //Ejecutamos el update
			
			pstmt.setString(1, txtNombre.getText().trim());
			pstmt.setString(2, txtApellido.getText().trim());
			pstmt.setString(3, txtUsuario.getText().trim());
			pstmt.setString(4, new String(txtPass.getPassword()));
			pstmt.setDate(5, sqlNuevaFecha);
			pstmt.setString(6, dniOriginal); 

			pstmt.executeUpdate();
			pstmt.close();

			JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
			
			//Recargamos la tabla del panel administrador
			if (pAdmin != null) {
				pAdmin.cargarDatosSocios();
				pAdmin.setVisible(true);
			} else if (pEmp != null) {
				pEmp.cargarDatosSocios();
				pEmp.setVisible(true);
			}
			
			dispose();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al actualizar. ¿El usuario ya existe?", "Error SQL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}