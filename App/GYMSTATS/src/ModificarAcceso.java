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

    private PanelAdministrador pAdmin;
    private PanelEmpleado pEmp;

    public ModificarAcceso(PanelAdministrador p, String dni, String fecha, String hEntrada, String hSalida) {
        this.pAdmin = p;
        this.dniOri = dni;
        this.fechaOri = fecha;
        this.entradaOri = hEntrada;
        this.salidaOri = hSalida.equals("Dentro") ? "" : hSalida;
        inicializar();
    }

    public ModificarAcceso(PanelEmpleado p, String dni, String fecha, String hEntrada, String hSalida) {
        this.pEmp = p;
        this.dniOri = dni;
        this.fechaOri = fecha;
        this.entradaOri = hEntrada;
        this.salidaOri = hSalida.equals("Dentro") ? "" : hSalida;
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
        
        setTitle("GymStats - Modificar Acceso");
        Main.setIconoApp(this);
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
        darEstiloCampo(txtFecha, "Fecha (dd/MM/yyyy)", fondoOscuro);
        contentPane.add(txtFecha);

        txtEntrada = new JTextField(entradaOri);
        txtEntrada.setBounds(40, 200, 300, 45);
        darEstiloCampo(txtEntrada, "Hora Entrada (HH:MM)", fondoOscuro);
        contentPane.add(txtEntrada);

        txtSalida = new JTextField(salidaOri);
        txtSalida.setBounds(40, 260, 300, 45);
        darEstiloCampo(txtSalida, "Hora Salida (HH:MM o vacío)", fondoOscuro);
        contentPane.add(txtSalida);

        JButton btnActualizar = new JButton("ACTUALIZAR FICHAJE");
        btnActualizar.setBounds(40, 330, 300, 45);
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(new Font("Segoe UI Bold", Font.PLAIN, 14));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(null);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizarFichaje());
        contentPane.add(btnActualizar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(40, 385, 300, 30);
        btnCancelar.setBackground(fondoOscuro);
        btnCancelar.setForeground(Color.GRAY);
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancelar.setBorder(null);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> {
            dispose();
            if (pAdmin != null) pAdmin.setVisible(true);
            else if (pEmp != null) pEmp.setVisible(true);
        });
        contentPane.add(btnCancelar);
    }

    private void darEstiloCampo(JTextField campo, String titulo, Color fondo) {
        campo.setBackground(fondo);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(new Color(0, 212, 255));
        campo.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), titulo, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
    }

    private void actualizarFichaje() {
        try {
            String nuevaFechaSQL = convertirAFormatoSQL(txtFecha.getText().trim());
            String nuevaEntrada = normalizarHora(txtEntrada.getText().trim());
            String nuevaSalida = normalizarHora(txtSalida.getText().trim());

            Connection con = Main.getConectar();

            // Actualizamos la entrada
            String sqlEntrada = "UPDATE Registrar_Entrada " +
                                "SET fecha = ?, hora = ?::time " +
                                "WHERE dni_cliente = ? " +
                                "AND fecha = ? " +
                                "AND TO_CHAR(hora, 'HH24:MI') = ?";

            PreparedStatement pstmtEntrada = con.prepareStatement(sqlEntrada);

            pstmtEntrada.setDate(1, Date.valueOf(nuevaFechaSQL));
            pstmtEntrada.setString(2, nuevaEntrada);
            pstmtEntrada.setString(3, dniOri);
            pstmtEntrada.setDate(4, Date.valueOf(convertirAFormatoSQL(fechaOri)));
            pstmtEntrada.setString(5, entradaOri);

            int filas = pstmtEntrada.executeUpdate();
            pstmtEntrada.close();

            // Si hay hora de salida
            if(!nuevaSalida.isEmpty()) {

                // Miramos si ya existe salida
                String comprobar = "SELECT * FROM Registrar_Salida " +
                                   "WHERE dni_cliente = ? AND fecha = ?";

                PreparedStatement pstCheck = con.prepareStatement(comprobar);
                pstCheck.setString(1, dniOri);
                pstCheck.setDate(2, Date.valueOf(convertirAFormatoSQL(fechaOri)));

                ResultSet rs = pstCheck.executeQuery();

                if(rs.next()) {

                    //Actualizamos la salida existente
                    String sqlUpdateSalida =
                        "UPDATE Registrar_Salida " +
                        "SET hora = ?::time " +
                        "WHERE dni_cliente = ? AND fecha = ?";

                    PreparedStatement pstUpdate = con.prepareStatement(sqlUpdateSalida);

                    pstUpdate.setString(1, nuevaSalida);
                    pstUpdate.setString(2, dniOri);
                    pstUpdate.setDate(3, Date.valueOf(convertirAFormatoSQL(fechaOri)));

                    pstUpdate.executeUpdate();
                    pstUpdate.close();

                } else {

                    //Hacemos un slcect de el codigo_registro_acceso
                    String sqlCodigo =
                        "SELECT codigo_registro_acceso " +
                        "FROM Registrar_Entrada " +
                        "WHERE dni_cliente = ? AND fecha = ?";

                    PreparedStatement pstCodigo = con.prepareStatement(sqlCodigo);

                    pstCodigo.setString(1, dniOri);
                    pstCodigo.setDate(2, Date.valueOf(nuevaFechaSQL));

                    ResultSet rsCodigo = pstCodigo.executeQuery();

                    int codigo = 0;

                    if(rsCodigo.next()) {
                        codigo = rsCodigo.getInt("codigo_registro_acceso");
                    }

                    rsCodigo.close();
                    pstCodigo.close();

                    //Insertamos salida nueva
                    String sqlInsert =
                        "INSERT INTO Registrar_Salida " +
                        "(codigo_registro_acceso, dni_cliente, fecha, hora) " +
                        "VALUES (?, ?, ?, ?::time)";

                    PreparedStatement pstInsert = con.prepareStatement(sqlInsert);

                    pstInsert.setInt(1, codigo);
                    pstInsert.setString(2, dniOri);
                    pstInsert.setDate(3, Date.valueOf(nuevaFechaSQL));
                    pstInsert.setString(4, nuevaSalida);

                    pstInsert.executeUpdate();
                    pstInsert.close();
                }

                rs.close();
                pstCheck.close();
            }

            if(filas > 0) {
                JOptionPane.showMessageDialog(this, "Fichaje actualizado");
                if (pAdmin != null) {
                    pAdmin.cargarDatosAcceso();
                    pAdmin.setVisible(true);
                } else if (pEmp != null) {
                    pEmp.cargarDatosAcceso();
                    pEmp.setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro original.");
            }

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String convertirAFormatoSQL(String fecha) {
        if (fecha != null && fecha.contains("/")) {
            String[] p = fecha.split("/");
            return p[2] + "-" + p[1] + "-" + p[0];
        }
        return fecha;
    }

    private String normalizarHora(String hora) {
        if (hora == null || hora.isEmpty()) return "";
        //Si el usuario pone 14:00, lo convertimos a 14:00:00
        return hora.length() == 5 ? hora + ":00" : hora;
    }
}