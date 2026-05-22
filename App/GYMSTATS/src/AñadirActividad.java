import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AñadirActividad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboEntrenador, comboClase, comboZona;
	private JTextField txtHora, txtDescripcion;

	public AñadirActividad(PanelAdministrador p) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				p.setVisible(true);
			}
		});
		
		setTitle("GymStats - Programar Actividad");
		setBounds(100, 100, 400, 580);
		setLocationRelativeTo(null);
		setResizable(false);

		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("NUEVA ACTIVIDAD");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		comboEntrenador = new JComboBox<>();
		comboEntrenador.setBounds(40, 80, 300, 45);
		darEstiloCampo(comboEntrenador, "Entrenador", fondoOscuro);
		contentPane.add(comboEntrenador);

		comboClase = new JComboBox<>();
		comboClase.setBounds(40, 140, 300, 45);
		darEstiloCampo(comboClase, "Tipo de Clase", fondoOscuro);
		contentPane.add(comboClase);

		comboZona = new JComboBox<>();
		comboZona.setBounds(40, 200, 300, 45);
		darEstiloCampo(comboZona, "Zona de Entreno", fondoOscuro);
		contentPane.add(comboZona);

		txtHora = new JTextField();
		txtHora.setBounds(40, 260, 300, 45);
		darEstiloCampo(txtHora, "Hora (HH:MM)", fondoOscuro);
		contentPane.add(txtHora);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(40, 320, 300, 45);
		darEstiloCampo(txtDescripcion, "Descripción breve", fondoOscuro);
		contentPane.add(txtDescripcion);

		cargarDatosCombos();

		JButton btnGuardar = new JButton("PROGRAMAR CLASE");
		btnGuardar.setBounds(40, 400, 300, 45);
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
		btnCancelar.setBounds(40, 455, 300, 30);
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

	private void cargarDatosCombos() {
		try {
			Connection con = Main.getConectar();
			
			String sqlEnt = "SELECT e.dni, e.nombre FROM Empleados e, Entrenador en WHERE e.dni = en.tipo_empleados";
			PreparedStatement pstEnt = con.prepareStatement(sqlEnt);
			ResultSet rsEnt = pstEnt.executeQuery();
			while(rsEnt.next()) {
				comboEntrenador.addItem(rsEnt.getString("dni") + " - " + rsEnt.getString("nombre"));
			}
			rsEnt.close();
			pstEnt.close();

			String sqlClas = "SELECT codigo, tipo FROM Clases";
			PreparedStatement pstClas = con.prepareStatement(sqlClas);
			ResultSet rsClas = pstClas.executeQuery();
			while(rsClas.next()) {
				comboClase.addItem(rsClas.getInt("codigo") + " - " + rsClas.getString("tipo"));
			}
			rsClas.close();
			pstClas.close();

			String sqlZon = "SELECT id, tipo FROM Zona_Entrenos";
			PreparedStatement pstZon = con.prepareStatement(sqlZon);
			ResultSet rsZon = pstZon.executeQuery();
			while(rsZon.next()) {
				comboZona.addItem(rsZon.getInt("id") + " - " + rsZon.getString("tipo"));
			}
			rsZon.close();
			pstZon.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void guardarEnBBDD(PanelAdministrador p) {
		if (txtHora.getText().trim().isEmpty() || comboEntrenador.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "Rellena los campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			String[] entParts = comboEntrenador.getSelectedItem().toString().split(" - ");
			String dniEntrenador = entParts[0];

			String[] clasParts = comboClase.getSelectedItem().toString().split(" - ");
			int codClase = Integer.parseInt(clasParts[0]);

			String[] zonParts = comboZona.getSelectedItem().toString().split(" - ");
			int idZona = Integer.parseInt(zonParts[0]);

			String hora = txtHora.getText().trim() + ":00";
			String descripcion = txtDescripcion.getText().trim();

			Connection con = Main.getConectar();
			String sql = "INSERT INTO Realizar (dni_entrenador, codigo_clases, id_zona_entrenos, hora, descripcion) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dniEntrenador);
			pstmt.setInt(2, codClase);
			pstmt.setInt(3, idZona);
			pstmt.setTime(4, Time.valueOf(hora));
			pstmt.setString(5, descripcion);

			pstmt.executeUpdate();
			pstmt.close();

			JOptionPane.showMessageDialog(this, "Actividad programada con éxito.");
			
			p.cargarDatosActividades(); 
			dispose(); 
			p.setVisible(true);

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, "La hora debe tener formato HH:MM (ejemplo 16:30).", "Error de formato", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: Ese entrenador ya tiene asignada esa clase. Borra la anterior primero.", "Error SQL", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}