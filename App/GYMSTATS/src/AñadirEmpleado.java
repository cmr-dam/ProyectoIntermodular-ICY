import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AñadirEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtNomina, txtExtra;
	private JComboBox<String> comboTipo;

	public AñadirEmpleado(PanelAdministrador p) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		
		setTitle("GymStats - Añadir Empleado");
		setBounds(100, 100, 400, 650);
		setLocationRelativeTo(null);
		setResizable(false);

		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("NUEVO EMPLEADO");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		txtDni = new JTextField();
		txtDni.setBounds(40, 80, 300, 45);
		darEstiloCampo(txtDni, "DNI del Empleado", fondoOscuro);
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

		txtNomina = new JTextField();
		txtNomina.setBounds(40, 320, 300, 45);
		darEstiloCampo(txtNomina, "Nómina (€)", fondoOscuro);
		contentPane.add(txtNomina);

		String[] tipos = {"Entrenador", "Recepcionista", "Limpiador"};
		comboTipo = new JComboBox<>(tipos);
		comboTipo.setBounds(40, 380, 300, 45);
		darEstiloCampo(comboTipo, "Puesto", fondoOscuro);
		contentPane.add(comboTipo);

		txtExtra = new JTextField();
		txtExtra.setBounds(40, 440, 300, 45);
		darEstiloCampo(txtExtra, "Especialidad / Turno", fondoOscuro);
		contentPane.add(txtExtra);

		JButton btnGuardar = new JButton("GUARDAR EMPLEADO");
		btnGuardar.setBounds(40, 510, 300, 45);
		btnGuardar.setBackground(azulGym);
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarEnBBDD(p); 
			}
		});
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 565, 300, 30);
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
	}

	private void darEstiloCampo(JComponent campo, String titulo, Color fondo) {
		campo.setBackground(fondo);
		campo.setForeground(Color.WHITE);
		if (campo instanceof JTextField) {
			((JTextField) campo).setCaretColor(new Color(0, 212, 255));
		}
		campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
	}

	private void guardarEnBBDD(PanelAdministrador p) {
		if (txtDni.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || txtNomina.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Rellena los campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			double nomina = Double.parseDouble(txtNomina.getText().trim());
			String tipo = comboTipo.getSelectedItem().toString();
			
			Connection con = Main.getConectar();
			
			String sqlEmpleados = "INSERT INTO Empleados (dni, telefono, nomina, nombre, apellido) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt1 = con.prepareStatement(sqlEmpleados);
			pstmt1.setString(1, txtDni.getText().trim());
			pstmt1.setString(2, txtTelefono.getText().trim());
			pstmt1.setDouble(3, nomina);
			pstmt1.setString(4, txtNombre.getText().trim());
			pstmt1.setString(5, txtApellido.getText().trim());
			pstmt1.executeUpdate();
			pstmt1.close();

			PreparedStatement pstmt2 = null;
			if (tipo.equals("Entrenador")) {
				String sqlEntrenador = "INSERT INTO Entrenador (tipo_empleados, tipo) VALUES (?, ?)";
				pstmt2 = con.prepareStatement(sqlEntrenador);
				pstmt2.setString(1, txtDni.getText().trim());
				pstmt2.setString(2, txtExtra.getText().trim());
			} else if (tipo.equals("Recepcionista")) {
				String sqlRecep = "INSERT INTO Recepcionista (tipo_empleados, turno) VALUES (?, ?)";
				pstmt2 = con.prepareStatement(sqlRecep);
				pstmt2.setString(1, txtDni.getText().trim());
				pstmt2.setString(2, txtExtra.getText().trim());
			} else if (tipo.equals("Limpiador")) {
				String sqlLimp = "INSERT INTO Limpiador (tipo_empleados, turno) VALUES (?, ?)";
				pstmt2 = con.prepareStatement(sqlLimp);
				pstmt2.setString(1, txtDni.getText().trim());
				pstmt2.setString(2, txtExtra.getText().trim());
			}
			
			if (pstmt2 != null) {
				pstmt2.executeUpdate();
				pstmt2.close();
			}

			JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.");
			
			p.cargarDatosPersonal(); 
			
			dispose(); 
			p.setVisible(true);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La nómina debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar: Comprueba que el DNI no exista ya", "Error SQL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}