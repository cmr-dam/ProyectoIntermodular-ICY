import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ModificarAcceso extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni, txtFecha, txtEntrada, txtSalida;
	private String dniOri, fechaOri, entradaOri, salidaOri;

	public ModificarAcceso(PanelAdministrador p, String dni, String fecha, String hEntrada, String hSalida) {
		this.dniOri = dni;
		this.fechaOri = fecha;
		this.entradaOri = hEntrada;
		this.salidaOri = hSalida.equals("Dentro") ? "" : hSalida;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				p.setVisible(true);
			}
		});
		
		setTitle("GymStats - Modificar Acceso");
		setBounds(100, 100, 400, 480); 
		setLocationRelativeTo(null); 
		setResizable(false);

		Color fondoOscuro = new Color(30, 30, 30);
		Color azulGym = new Color(0, 212, 255);

		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("MODIFICAR ACCESO");
		lblTitulo.setForeground(azulGym);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 20, 384, 40);
		contentPane.add(lblTitulo);

		txtDni = new JTextField(dniOri);
		txtDni.setBounds(40, 80, 300, 45);
		darEstiloCampo(txtDni, "DNI Socio (No modificable)", fondoOscuro);
		txtDni.setEditable(false);
		txtDni.setForeground(Color.GRAY);
		contentPane.add(txtDni);

		txtFecha = new JTextField(fechaOri);
		txtFecha.setBounds(40, 140, 300, 45);
		darEstiloCampo(txtFecha, "Fecha (AAAA-MM-DD)", fondoOscuro);
		contentPane.add(txtFecha);

		txtEntrada = new JTextField(entradaOri);
		txtEntrada.setBounds(40, 200, 300, 45);
		darEstiloCampo(txtEntrada, "Hora Entrada (HH:MM:SS)", fondoOscuro);
		contentPane.add(txtEntrada);

		txtSalida = new JTextField(salidaOri);
		txtSalida.setBounds(40, 260, 300, 45);
		darEstiloCampo(txtSalida, "Hora Salida (Vacío si sigue dentro)", fondoOscuro);
		contentPane.add(txtSalida);

		JButton btnActualizar = new JButton("ACTUALIZAR FICHAJE");
		btnActualizar.setBounds(40, 330, 300, 45);
		btnActualizar.setBackground(azulGym);
		btnActualizar.setForeground(Color.BLACK);
		btnActualizar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
		btnActualizar.setFocusPainted(false);
		btnActualizar.setBorder(null);
		btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarFichaje(p); 
			}
		});
		contentPane.add(btnActualizar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(40, 385, 300, 30);
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

	private void darEstiloCampo(JTextField campo, String titulo, Color fondo) {
		campo.setBackground(fondo);
		campo.setForeground(Color.WHITE);
		campo.setCaretColor(new Color(0, 212, 255));
		campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
	}

	private void actualizarFichaje(PanelAdministrador p) {
	    if (txtFecha.getText().trim().isEmpty() || txtEntrada.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "La fecha y la hora de entrada son obligatorias.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try {
	        Connection con = Main.getConectar();
	        
	        //Convertimos fechas de pantalla (dd/MM/yyyy) a formato SQL (yyyy-MM-dd)
	        String nuevaFechaSQL = convertirAFormatoSQL(txtFecha.getText().trim());
	        String fechaOriSQL = convertirAFormatoSQL(fechaOri); // Aseguramos que la original también esté bien

	        String nuevaEntrada = txtEntrada.getText().trim();
	        String nuevaSalida = txtSalida.getText().trim();

	        //Actualizar Entrada
	        String sqlEntrada = "UPDATE Registrar_Entrada SET fecha = ?, hora = ? WHERE dni_cliente = ? AND fecha = ? AND hora = ?";
	        PreparedStatement pstmtE = con.prepareStatement(sqlEntrada);
	        pstmtE.setDate(1, Date.valueOf(nuevaFechaSQL));
	        pstmtE.setTime(2, Time.valueOf(nuevaEntrada));
	        pstmtE.setString(3, dniOri);
	        pstmtE.setDate(4, Date.valueOf(fechaOriSQL));
	        pstmtE.setTime(5, Time.valueOf(entradaOri));
	        pstmtE.executeUpdate();
	        pstmtE.close();

	        //Lógica de Salida
	        if (nuevaSalida.isEmpty()) {
	            String sqlDelSalida = "DELETE FROM Registrar_Salida WHERE dni_cliente = ? AND fecha = ?";
	            PreparedStatement pstmtDS = con.prepareStatement(sqlDelSalida);
	            pstmtDS.setString(1, dniOri);
	            pstmtDS.setDate(2, Date.valueOf(nuevaFechaSQL));
	            pstmtDS.executeUpdate();
	            pstmtDS.close();
	        } else {
	            String sqlCheckSalida = "SELECT 1 FROM Registrar_Salida WHERE dni_cliente = ? AND fecha = ?";
	            PreparedStatement pstmtCheck = con.prepareStatement(sqlCheckSalida);
	            pstmtCheck.setString(1, dniOri);
	            pstmtCheck.setDate(2, Date.valueOf(nuevaFechaSQL));
	            ResultSet rsCheck = pstmtCheck.executeQuery();

	            if (rsCheck.next()) {
	                String sqlSalidaUpdate = "UPDATE Registrar_Salida SET fecha = ?, hora = ? WHERE dni_cliente = ? AND fecha = ?";
	                PreparedStatement pstmtS = con.prepareStatement(sqlSalidaUpdate);
	                pstmtS.setDate(1, Date.valueOf(nuevaFechaSQL));
	                pstmtS.setTime(2, Time.valueOf(nuevaSalida));
	                pstmtS.setString(3, dniOri);
	                pstmtS.setDate(4, Date.valueOf(nuevaFechaSQL));
	                pstmtS.executeUpdate();
	                pstmtS.close();
	            } else {
	                String sqlGetCod = "SELECT codigo_registro_acceso FROM Registrar_Entrada WHERE dni_cliente = ? AND fecha = ?";
	                PreparedStatement pstmtCod = con.prepareStatement(sqlGetCod);
	                pstmtCod.setString(1, dniOri);
	                pstmtCod.setDate(2, Date.valueOf(nuevaFechaSQL));
	                ResultSet rsCod = pstmtCod.executeQuery();
	                
	                if (rsCod.next()) {
	                    int codigo = rsCod.getInt("codigo_registro_acceso");
	                    String sqlInsSalida = "INSERT INTO Registrar_Salida (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES (?, ?, ?, ?)";
	                    PreparedStatement pstmtIS = con.prepareStatement(sqlInsSalida);
	                    pstmtIS.setInt(1, codigo);
	                    pstmtIS.setString(2, dniOri);
	                    pstmtIS.setDate(3, Date.valueOf(nuevaFechaSQL));
	                    pstmtIS.setTime(4, Time.valueOf(nuevaSalida));
	                    pstmtIS.executeUpdate();
	                    pstmtIS.close();
	                }
	                rsCod.close();
	                pstmtCod.close();
	            }
	            rsCheck.close();
	            pstmtCheck.close();
	        }

	        JOptionPane.showMessageDialog(this, "Fichaje actualizado correctamente.");
	        p.cargarDatosAcceso();
	        dispose();
	        p.setVisible(true);

	    } catch (IllegalArgumentException ex) {
	        JOptionPane.showMessageDialog(this, "Formato de fecha (dd/MM/yyyy) u hora (HH:MM:SS) incorrecto.", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error en la base de datos al actualizar.", "Error SQL", JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    }
	}

	private String convertirAFormatoSQL(String fecha) {
        if (fecha != null && fecha.contains("/")) {
            String[] partes = fecha.split("/");
           
            return partes[2] + "-" + partes[1] + "-" + partes[0];
        }
        return fecha;
    }
}