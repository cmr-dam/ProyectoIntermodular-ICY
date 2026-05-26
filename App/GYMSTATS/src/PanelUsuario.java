import javax.swing.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PanelUsuario extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtPeso, txtAltura;
	private JLabel lblValorIMC, lblEstadoIMC, lblDiasRestantes;
	private JProgressBar progressMembresia;
	private String dniGlobal;

	public PanelUsuario(String dniUsuario, String nombreUsuario, PanelLogin p) {
		this.dniGlobal = dniUsuario;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//Al cerrarla volvemos a el panel de login
				int opcion = JOptionPane.showConfirmDialog(contentPane, "Seguro que quieres cerrar sesion?", "Cerrar Sesion", JOptionPane.INFORMATION_MESSAGE);
				
				if(opcion == JOptionPane.YES_OPTION) {
					p.setVisible(true);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Operacion cancelada", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		setTitle("GymStats - Mi Perfil");
		Main.setIconoApp(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Usamos esto porque si lo borramos se cierra igual
		setBounds(100, 100, 500, 500); //Aumentamos tamaño
		setLocationRelativeTo(null);
		setResizable(false);
		
		//USAMOS LOS MISMOS COLORES QUE EN EL PANEL LOGIN
		Color fondoOscuro = new Color(20, 20, 20);
		Color tarjetaGris = new Color(35, 35, 35);
		Color azulGym = new Color(0, 212, 255);
		
		contentPane = new JPanel();
		contentPane.setBackground(fondoOscuro);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TITULO CON NOMBRE
		JLabel lblSaludo = new JLabel("¡Hola, " + nombreUsuario + "!");
		lblSaludo.setForeground(Color.WHITE);
		lblSaludo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
		lblSaludo.setBounds(20, 20, 300, 35);
		contentPane.add(lblSaludo);

		// PANEL ESTADO MEMBRESIA
		JPanel cardMembresia = new JPanel();
		cardMembresia.setBackground(tarjetaGris);
		cardMembresia.setBounds(20, 75, 445, 100);
		cardMembresia.setLayout(null);
		contentPane.add(cardMembresia);

		JLabel lblTitMem = new JLabel("ESTADO DE TU MEMBRESÍA");
		lblTitMem.setForeground(azulGym);
		lblTitMem.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
		lblTitMem.setBounds(15, 10, 200, 20);
		cardMembresia.add(lblTitMem);

		int diasQuedan = 12; //HAY QUE CAMBIAR ESTE NUMERO A LOS DIAS DE LA BASE DE DATOS
		lblDiasRestantes = new JLabel("Te quedan " + diasQuedan + " días de acceso");
		lblDiasRestantes.setForeground(Color.WHITE);
		lblDiasRestantes.setBounds(15, 35, 250, 20);
		cardMembresia.add(lblDiasRestantes);

		progressMembresia = new JProgressBar(0, 30); // LA MEMBRESIA DURA MAX 30 DIAS
		progressMembresia.setValue(diasQuedan);
		progressMembresia.setForeground(azulGym);
		progressMembresia.setBackground(fondoOscuro);
		progressMembresia.setBorderPainted(false);
		progressMembresia.setBounds(15, 60, 415, 15);
		cardMembresia.add(progressMembresia);

		//PANEL VER IMC
		JPanel cardIMC = new JPanel();
		cardIMC.setBackground(tarjetaGris);
		cardIMC.setBounds(20, 190, 210, 200);
		cardIMC.setLayout(null);
		contentPane.add(cardIMC);

		JLabel lblTitIMC = new JLabel("TU IMC ACTUAL");
		lblTitIMC.setForeground(azulGym);
		lblTitIMC.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
		lblTitIMC.setBounds(15, 10, 150, 20);
		cardIMC.add(lblTitIMC);

		lblValorIMC = new JLabel("--"); //LABEL POR DEFECTO SI NO HAY IMC CALCULADO
		lblValorIMC.setForeground(Color.WHITE);
		lblValorIMC.setFont(new Font("Segoe UI Black", Font.PLAIN, 48));
		lblValorIMC.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorIMC.setBounds(0, 50, 210, 60);
		cardIMC.add(lblValorIMC);

		lblEstadoIMC = new JLabel("Calcula para ver estado");
		lblEstadoIMC.setForeground(Color.GRAY);
		lblEstadoIMC.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstadoIMC.setBounds(0, 120, 210, 20);
		cardIMC.add(lblEstadoIMC);

		//CALCULADORA
		JPanel cardCalc = new JPanel();
		cardCalc.setBackground(tarjetaGris);
		cardCalc.setBounds(255, 190, 210, 200);
		cardCalc.setLayout(null);
		contentPane.add(cardCalc);

		JLabel lblTitCalc = new JLabel("CALCULADORA");
		lblTitCalc.setForeground(azulGym);
		lblTitCalc.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
		lblTitCalc.setBounds(15, 10, 150, 20);
		cardCalc.add(lblTitCalc);

		txtPeso = new JTextField();
		txtPeso.setBounds(15, 40, 180, 45);
		txtPeso.setToolTipText("Peso en kg");
		txtPeso.setBackground(fondoOscuro);
		txtPeso.setForeground(Color.WHITE);
		txtPeso.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), "Peso (kg)", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		cardCalc.add(txtPeso);

		txtAltura = new JTextField();
		txtAltura.setBounds(15, 95, 180, 40);
		txtAltura.setToolTipText("Altura en cm");
		txtAltura.setBackground(fondoOscuro);
		txtAltura.setForeground(Color.WHITE);
		txtAltura.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), "Altura (cm)", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		cardCalc.add(txtAltura);

		JButton btnCalcular = new JButton("ACTUALIZAR");
		btnCalcular.setBounds(15, 145, 180, 35);
		btnCalcular.setBackground(azulGym);
		btnCalcular.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
		btnCalcular.setBorder(null);
		btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCalcular.addActionListener(e -> calcularIMC(dniGlobal));
		cardCalc.add(btnCalcular);

		// Pie de página
		JLabel lblFoot = new JLabel("GymStats");
		lblFoot.setForeground(new Color(70, 70, 70));
		lblFoot.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblFoot.setBounds(20, 430, 445, 20);
		contentPane.add(lblFoot);
	}

	private void calcularIMC(String dniUsuario) {
		try {
			double peso = Double.parseDouble(txtPeso.getText());
			double altura = Double.parseDouble(txtAltura.getText());
			
			double alturaCalc = altura;
			if (altura > 3.0) { 
				alturaCalc = altura / 100;
			}
			
			double imc = peso / (alturaCalc * alturaCalc);
			
			lblValorIMC.setText(String.format("%.1f", imc));
			
			if(imc < 18.5) { 
				lblEstadoIMC.setText("BAJO PESO"); 
				lblEstadoIMC.setForeground(Color.YELLOW); 
			}
			else if(imc < 25) { 
				lblEstadoIMC.setText("NORMAL"); 
				lblEstadoIMC.setForeground(Color.GREEN); 
			}
			else { 
				lblEstadoIMC.setText("SOBREPESO"); 
				lblEstadoIMC.setForeground(Color.ORANGE); 
			}
			
			Connection con = Main.getConectar();
			
			String sqlSelect = "SELECT id_calculadora FROM Cliente WHERE dni = ?";
			PreparedStatement pstmtSelect = con.prepareStatement(sqlSelect);
			pstmtSelect.setString(1, dniUsuario);
			ResultSet rs = pstmtSelect.executeQuery();

			if (rs.next()) {
			    int idCalc = rs.getInt("id_calculadora");

			    if (rs.wasNull() || idCalc == 0) {
			        Statement stmtMax = con.createStatement();
			        ResultSet rsMax = stmtMax.executeQuery("SELECT COALESCE(MAX(id), 0) + 1 AS nuevo_id FROM Calculadora");
			        rsMax.next();
			        int nuevoId = rsMax.getInt("nuevo_id");

			        PreparedStatement pstmtInsert = con.prepareStatement("INSERT INTO Calculadora (id, IMC) VALUES (?, ?)");
			        pstmtInsert.setInt(1, nuevoId);
			        pstmtInsert.setDouble(2, imc);
			        pstmtInsert.executeUpdate();

			        PreparedStatement pstmtUpdateCli = con.prepareStatement("UPDATE Cliente SET id_calculadora = ? WHERE dni = ?");
			        pstmtUpdateCli.setInt(1, nuevoId);
			        pstmtUpdateCli.setString(2, dniUsuario);
			        pstmtUpdateCli.executeUpdate();
			        
			    } else {
			        PreparedStatement pstmtUpdateCalc = con.prepareStatement("UPDATE Calculadora SET IMC = ? WHERE id = ?");
			        pstmtUpdateCalc.setDouble(1, imc);
			        pstmtUpdateCalc.setInt(2, idCalc);
			        pstmtUpdateCalc.executeUpdate();
			    }
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al calcular o guardar los datos");
		}
	}
}