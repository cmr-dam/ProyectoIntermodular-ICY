import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ModificarEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtNomina, txtExtra, txtUsuario, txtPassword;
	private JComboBox<String> comboTipo;
	private String dniOriginal;

	public ModificarEmpleado(PanelAdministrador p, String dniOriginal) {
		this.dniOriginal = dniOriginal;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		
		setTitle("GymStats - Modificar Empleado");
		Main.setIconoApp(this);
		setBounds(100, 100, 400, 770);
		setLocationRelativeTo(null);
		setResizable(false);

		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("MODIFICAR EMPLEADO");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		txtDni = new JTextField();
		txtDni.setBounds(40, 80, 300, 45);
		txtDni.setEditable(false); // No se debería poder cambiar el DNI (clave primaria)
		darEstiloCampo(txtDni, "DNI del Empleado (Fijo)", fondoOscuro);
		contentPane.add(txtDni);

		txtNombre = new JTextField();
		txtNombre.setBounds(40, 140, 300, 45);
		darEstiloCampo(txtNombre, "Nombre", fondoOscuro);
		contentPane.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setBounds(40, 200, 300, 45);
		darEstiloCampo(txtApellido, "Apellido", fondoOscuro);
		contentPane.add(txtApellido);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(40, 260, 300, 45);
		darEstiloCampo(txtTelefono, "Teléfono", fondoOscuro);
		contentPane.add(txtTelefono);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(40, 320, 300, 45);
		darEstiloCampo(txtUsuario, "Usuario", fondoOscuro);
		contentPane.add(txtUsuario);

		txtPassword = new JTextField();
		txtPassword.setBounds(40, 380, 300, 45);
		darEstiloCampo(txtPassword, "Contraseña", fondoOscuro);
		contentPane.add(txtPassword);

		txtNomina = new JTextField();
		txtNomina.setBounds(40, 440, 300, 45);
		darEstiloCampo(txtNomina, "Nómina (€)", fondoOscuro);
		contentPane.add(txtNomina);

		String[] tipos = {"Entrenador", "Recepcionista", "Limpiador"};
		comboTipo = new JComboBox<>(tipos);
		comboTipo.setBounds(40, 500, 300, 45);
		comboTipo.setEnabled(false); // No permitimos cambiar de puesto fácilmente para no liar las relaciones
		darEstiloCampo(comboTipo, "Puesto (Fijo)", fondoOscuro);
		contentPane.add(comboTipo);

		txtExtra = new JTextField();
		txtExtra.setBounds(40, 560, 300, 45);
		darEstiloCampo(txtExtra, "Especialidad / Turno", fondoOscuro);
		contentPane.add(txtExtra);

		JButton btnGuardar = new JButton("ACTUALIZAR EMPLEADO");
		btnGuardar.setBounds(40, 630, 300, 45);
		btnGuardar.setBackground(azulGym);
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEnBBDD(p); 
			}
		});
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 685, 300, 30);
		btnCancelar.setBackground(fondoOscuro);
		btnCancelar.setForeground(Color.GRAY);
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCancelar.setBorder(null);
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
				p.setVisible(true);
			}
		});
		contentPane.add(btnCancelar);

		cargarDatosOriginales();
	}

	private void darEstiloCampo(JComponent campo, String titulo, Color fondo) {
		campo.setBackground(fondo);
		campo.setForeground(Color.WHITE);
		if (campo instanceof JTextField) {
			((JTextField) campo).setCaretColor(new Color(0, 212, 255));
		}
		campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
	}

	private void cargarDatosOriginales() {
		try {
			Connection con = Main.getConectar();
			String sql = "SELECT e.*, en.tipo as ent_tipo, r.turno as rec_turno, l.turno as lim_turno " +
						 "FROM Empleados e " +
						 "LEFT JOIN Entrenador en ON e.dni = en.tipo_empleados " +
						 "LEFT JOIN Recepcionista r ON e.dni = r.tipo_empleados " +
						 "LEFT JOIN Limpiador l ON e.dni = l.tipo_empleados " +
						 "WHERE e.dni = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dniOriginal);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				txtDni.setText(rs.getString("dni"));
				txtNombre.setText(rs.getString("nombre"));
				txtApellido.setText(rs.getString("apellido"));
				txtTelefono.setText(rs.getString("telefono"));
				txtNomina.setText(rs.getString("nomina"));
				txtUsuario.setText(rs.getString("usuario"));
				txtPassword.setText(rs.getString("contraseña"));
				
				if (rs.getString("ent_tipo") != null) {
					comboTipo.setSelectedItem("Entrenador");
					txtExtra.setText(rs.getString("ent_tipo"));
				} else if (rs.getString("rec_turno") != null) {
					comboTipo.setSelectedItem("Recepcionista");
					txtExtra.setText(rs.getString("rec_turno"));
				} else if (rs.getString("lim_turno") != null) {
					comboTipo.setSelectedItem("Limpiador");
					txtExtra.setText(rs.getString("lim_turno"));
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al cargar los datos del empleado.");
			ex.printStackTrace();
		}
	}

	private void actualizarEnBBDD(PanelAdministrador p) {
		if (txtNombre.getText().trim().isEmpty() || txtNomina.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Rellena los campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			double nomina = Double.parseDouble(txtNomina.getText().trim());
			String tipo = comboTipo.getSelectedItem().toString();
			
			Connection con = Main.getConectar();
			
			String sqlEmpleados = "UPDATE Empleados SET telefono = ?, nomina = ?, nombre = ?, apellido = ?, usuario = ?, contraseña = ? WHERE dni = ?";
			PreparedStatement pstmt1 = con.prepareStatement(sqlEmpleados);
			pstmt1.setString(1, txtTelefono.getText().trim());
			pstmt1.setDouble(2, nomina);
			pstmt1.setString(3, txtNombre.getText().trim());
			pstmt1.setString(4, txtApellido.getText().trim());
			pstmt1.setString(5, txtUsuario.getText().trim());
			pstmt1.setString(6, txtPassword.getText().trim());
			pstmt1.setString(7, dniOriginal);
			pstmt1.executeUpdate();
			pstmt1.close();

			PreparedStatement pstmt2 = null;
			if (tipo.equals("Entrenador")) {
				String sqlEntrenador = "UPDATE Entrenador SET tipo = ? WHERE tipo_empleados = ?";
				pstmt2 = con.prepareStatement(sqlEntrenador);
				pstmt2.setString(1, txtExtra.getText().trim());
				pstmt2.setString(2, dniOriginal);
			} else if (tipo.equals("Recepcionista")) {
				String sqlRecep = "UPDATE Recepcionista SET turno = ? WHERE tipo_empleados = ?";
				pstmt2 = con.prepareStatement(sqlRecep);
				pstmt2.setString(1, txtExtra.getText().trim());
				pstmt2.setString(2, dniOriginal);
			} else if (tipo.equals("Limpiador")) {
				String sqlLimp = "UPDATE Limpiador SET turno = ? WHERE tipo_empleados = ?";
				pstmt2 = con.prepareStatement(sqlLimp);
				pstmt2.setString(1, txtExtra.getText().trim());
				pstmt2.setString(2, dniOriginal);
			}
			
			if (pstmt2 != null) {
				pstmt2.executeUpdate();
				pstmt2.close();
			}

			JOptionPane.showMessageDialog(this, "Empleado actualizado con éxito.");
			
			p.cargarDatosPersonal(); 
			
			dispose(); 
			p.setVisible(true);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La nómina debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al actualizar en la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
