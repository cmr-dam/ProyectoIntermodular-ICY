import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PanelEmpleado extends JFrame {

    private static final long serialVersionUID = 1L;
    
    //SOCIOS
    private JTable tablaSocios;
    private DefaultTableModel modeloTabla;
    
    private JTable tablaAcceso;
    private DefaultTableModel modeloTablaAcceso;
    
    //ACTIVIDADES
    private JTable tablaActividades;
    private DefaultTableModel modeloTablaActividades;
    
    private JPanel contentArea;

    Color fondoOscuro = new Color(30, 30, 30);
    Color azulGym = new Color(0, 212, 255);
    Color grisInput = new Color(45, 45, 45);

    public PanelEmpleado(PanelLogin p) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(PanelEmpleado.this, "Seguro que quieres cerrar sesion?", "Cerrar Sesion", JOptionPane.INFORMATION_MESSAGE);
                
                if(opcion == JOptionPane.YES_OPTION) {
                    p.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Operacion cancelada", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        setTitle("GymStats - Panel de Empleado");
        Main.setIconoApp(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(fondoOscuro);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(fondoOscuro);

        getContentPane().add(crearSidebar(), BorderLayout.WEST);
        getContentPane().add(contentArea, BorderLayout.CENTER);
        
        mostrarPanel(crearPanelInicio());
    }
    
    public void mostrarPanel(JPanel panelNuevo) {
        contentArea.removeAll();
        contentArea.add(panelNuevo, BorderLayout.CENTER);
        contentArea.revalidate(); 
        contentArea.repaint(); 
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        btn.setBackground(azulGym);
        btn.setForeground(Color.BLACK);
        
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btn.setPreferredSize(new Dimension(220, 58));
        
        btn.setMargin(new Insets(8, 15, 8, 15));

        btn.setOpaque(true);
        btn.setBorderPainted(false);
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }
    
    private JPanel crearPanelInicio() {
        JPanel panelInicio = new JPanel(new BorderLayout());
        panelInicio.setBackground(fondoOscuro);
        
        JLabel titulo = new JLabel("Bienvenido a GymStats", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        
        JLabel subtitulo = new JLabel("Selecciona una opción del menú lateral para gestionar el gimnasio", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitulo.setForeground(azulGym);
        
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(fondoOscuro);
        
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centro.add(Box.createVerticalGlue());
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(20));
        centro.add(subtitulo);
        centro.add(Box.createVerticalGlue());
        
        panelInicio.add(centro, BorderLayout.CENTER);
        
        return panelInicio;
    }
    
    private JPanel crearPanelActividades() {
        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(fondoOscuro);

        JLabel titulo = new JLabel("Gestión de Actividades");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(fondoOscuro);
        cabecera.add(titulo, BorderLayout.WEST);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(fondoOscuro);
        
        JButton btnAnadir = new JButton("Añadir Clase");
        btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setPreferredSize(new Dimension(140, 38));
        btnAnadir.setBackground(azulGym);
        btnAnadir.setForeground(Color.BLACK);
        btnAnadir.setOpaque(true);
        btnAnadir.setBorderPainted(true);
        btnAnadir.setBorder(new LineBorder(azulGym, 1, true));
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AñadirActividad vAct = new AñadirActividad(PanelEmpleado.this); 
                    setVisible(false);
                    vAct.setVisible(true);
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Error al abrir la ventana.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnEliminar = new JButton("Eliminar Clase");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(140, 38));
        btnEliminar.setBackground(azulGym);
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(true);
        btnEliminar.setBorder(new LineBorder(azulGym, 1, true));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaActividades.getSelectedRow();
                
                if(filaSeleccionada != -1) {
                    String dniEntrenador = tablaActividades.getValueAt(filaSeleccionada, 0).toString();
                    String codClase = tablaActividades.getValueAt(filaSeleccionada, 1).toString();
                    int confirmar = JOptionPane.showConfirmDialog(PanelEmpleado.this, "¿Seguro que quieres borrar esta actividad?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    
                    if(confirmar == JOptionPane.YES_OPTION) {
                        try {
                            Connection con = Main.getConectar();
                            String sql = "DELETE FROM Realizar WHERE dni_entrenador = ? AND codigo_clases = ?";
                            PreparedStatement pstmt = con.prepareStatement(sql);
                            pstmt.setString(1, dniEntrenador);
                            pstmt.setInt(2, Integer.parseInt(codClase));
                            
                            int resultado = pstmt.executeUpdate();
                            if(resultado > 0) {
                                modeloTablaActividades.removeRow(filaSeleccionada);
                                JOptionPane.showMessageDialog(PanelEmpleado.this, "Clase eliminada", "Borrado", JOptionPane.INFORMATION_MESSAGE);
                            }
                            pstmt.close();
                        } catch(Exception ex) {
                            JOptionPane.showMessageDialog(PanelEmpleado.this, "No se puede borrar.", "Error de clave", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 38));
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(true);
        btnActualizar.setBorder(new LineBorder(azulGym, 1, true));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosActividades();
            }
        });

        botones.add(btnAnadir);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        JPanel zonaSuperior = new JPanel();
        zonaSuperior.setLayout(new BoxLayout(zonaSuperior, BoxLayout.Y_AXIS));
        zonaSuperior.setBackground(fondoOscuro);
        zonaSuperior.add(cabecera);
        zonaSuperior.add(Box.createVerticalStrut(15));
        zonaSuperior.add(botones);

        contenido.add(zonaSuperior, BorderLayout.NORTH);

        String[] columnas = {"DNI Ent.", "Cód. Clase", "Entrenador", "Actividad", "Zona", "Hora", "Descripción"};
        modeloTablaActividades = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaActividades = new JTable(modeloTablaActividades);

        tablaActividades.setRowHeight(30);
        tablaActividades.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaActividades.setBackground(grisInput);
        tablaActividades.setForeground(Color.WHITE);
        tablaActividades.setGridColor(fondoOscuro);
        tablaActividades.setSelectionBackground(azulGym);
        tablaActividades.setSelectionForeground(Color.BLACK);
        tablaActividades.setBorder(new LineBorder(azulGym, 1));

        JTableHeader header = tablaActividades.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(azulGym);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaActividades);
        scroll.getViewport().setBackground(grisInput);
        scroll.setBackground(grisInput);

        contenido.add(scroll, BorderLayout.CENTER);

        cargarDatosActividades();

        return contenido;
    }

    private JPanel crearPanelAcceso() {
        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(fondoOscuro);

        JLabel titulo = new JLabel("Control de Accesos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(fondoOscuro);
        cabecera.add(titulo, BorderLayout.WEST);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(fondoOscuro);
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 38));
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(true);
        btnActualizar.setBorder(new LineBorder(azulGym, 1, true));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosAcceso();
            }
        });

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificar.setFocusPainted(false);
        btnModificar.setPreferredSize(new Dimension(140, 38));
        btnModificar.setBackground(azulGym);
        btnModificar.setForeground(Color.BLACK);
        btnModificar.setOpaque(true);
        btnModificar.setBorderPainted(true);
        btnModificar.setBorder(new LineBorder(azulGym, 1, true));
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila = tablaAcceso.getSelectedRow();
                if (fila != -1) {
                    String dni = modeloTablaAcceso.getValueAt(fila, 0).toString();
                    String fecha = modeloTablaAcceso.getValueAt(fila, 3).toString();
                    String hEntrada = modeloTablaAcceso.getValueAt(fila, 4).toString();
                    String hSalida = modeloTablaAcceso.getValueAt(fila, 5).toString();
                    
                    ModificarAcceso ventanaModificar = new ModificarAcceso(PanelEmpleado.this, dni, fecha, hEntrada, hSalida);
                    ventanaModificar.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Debes seleccionar un registro de acceso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botones.add(btnActualizar);
        botones.add(btnModificar);

        JPanel zonaSuperior = new JPanel();
        zonaSuperior.setLayout(new BoxLayout(zonaSuperior, BoxLayout.Y_AXIS));
        zonaSuperior.setBackground(fondoOscuro);
        zonaSuperior.add(cabecera);
        zonaSuperior.add(Box.createVerticalStrut(15));
        zonaSuperior.add(botones);

        contenido.add(zonaSuperior, BorderLayout.NORTH);

        String[] columnas = {"DNI", "Nombre", "Apellido", "Fecha", "Hora Entrada", "Hora Salida"};
        
        modeloTablaAcceso = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaAcceso = new JTable(modeloTablaAcceso);

        tablaAcceso.setRowHeight(30);
        tablaAcceso.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaAcceso.setBackground(grisInput);
        tablaAcceso.setForeground(Color.WHITE);
        tablaAcceso.setGridColor(fondoOscuro);
        tablaAcceso.setSelectionBackground(azulGym);
        tablaAcceso.setSelectionForeground(Color.BLACK);
        tablaAcceso.setBorder(new LineBorder(azulGym, 1));

        JTableHeader header = tablaAcceso.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(azulGym);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaAcceso);
        scroll.getViewport().setBackground(grisInput);
        scroll.setBackground(grisInput);

        contenido.add(scroll, BorderLayout.CENTER);

        cargarDatosAcceso();

        return contenido;
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));
        sidebar.setBackground(grisInput);

        JLabel titulo = new JLabel("GymStats");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(azulGym);
        titulo.setBorder(new EmptyBorder(10, 10, 25, 10));

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(grisInput);

        JButton btnInicio = crearBotonMenu("Inicio");
        btnInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mostrarPanel(crearPanelInicio());
            }
        });
        
        JButton btnSocios = crearBotonMenu("Socios");
        btnSocios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearContenido());
                cargarDatosSocios();
            }
        });

        JButton btnActividades = crearBotonMenu("Actividades");
        btnActividades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearPanelActividades());
                cargarDatosActividades();
            }
        });

        JButton btnAcceso = crearBotonMenu("Acceso");
        btnAcceso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearPanelAcceso());
            }
        });

        menu.add(btnInicio);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnSocios);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnActividades);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnAcceso);
        
        sidebar.add(titulo, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);

        return sidebar;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(fondoOscuro);

        JLabel titulo = new JLabel("Gestión de Socios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(fondoOscuro);
        cabecera.add(titulo, BorderLayout.WEST);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(fondoOscuro);
        
        JButton btnAnadir = new JButton("Añadir");
        btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setPreferredSize(new Dimension(140, 38));
        btnAnadir.setBackground(azulGym);
        btnAnadir.setForeground(Color.BLACK);

        btnAnadir.setOpaque(true);
        btnAnadir.setBorderPainted(true);
        btnAnadir.setBorder(new LineBorder(azulGym, 1, true));
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AñadirSocio vSoc = new AñadirSocio(PanelEmpleado.this); 
                    setVisible(false);
                    vSoc.setVisible(true);
                } catch(Exception crear) {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "No se ha podido abrir la ventana de creacion.", "Error Ventana", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 38));
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);

        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(true);
        btnActualizar.setBorder(new LineBorder(azulGym, 1, true));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosSocios();
            }
        });
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificar.setFocusPainted(false);
        btnModificar.setPreferredSize(new Dimension(140, 38));
        btnModificar.setBackground(azulGym);
        btnModificar.setForeground(Color.BLACK);

        btnModificar.setOpaque(true);
        btnModificar.setBorderPainted(true);
        btnModificar.setBorder(new LineBorder(azulGym, 1, true));
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int fila = tablaSocios.getSelectedRow();
                
                if(fila != -1) {
                    String dniSeleccionado = modeloTabla.getValueAt(fila, 0).toString();
                    
                    ModificarSocio ventanaModificar = new ModificarSocio(PanelEmpleado.this, dniSeleccionado); 
                    ventanaModificar.setVisible(true);
                    setVisible(false);  
                } else {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Debes seleccionar una fila de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(140, 38));
        btnEliminar.setBackground(azulGym);
        btnEliminar.setForeground(Color.BLACK);

        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(true);
        btnEliminar.setBorder(new LineBorder(azulGym, 1, true));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaSocios.getSelectedRow();
                
                if(filaSeleccionada != -1) {
                    String dni = tablaSocios.getValueAt(filaSeleccionada, 0).toString();
                    int confirmar = JOptionPane.showConfirmDialog(PanelEmpleado.this, "¿Seguro que quieres eliminar a este usuario y todo su historial?", "Eliminar", JOptionPane.YES_NO_CANCEL_OPTION);
                    
                    if(confirmar == JOptionPane.YES_OPTION) {
                        try {
                            Connection con = Main.getConectar();
                            
                            int idCalc = 0;
                            PreparedStatement pstSelect = con.prepareStatement("SELECT id_calculadora FROM Cliente WHERE dni = ?");
                            pstSelect.setString(1, dni);
                            ResultSet rs = pstSelect.executeQuery();
                            if(rs.next()) {
                                idCalc = rs.getInt("id_calculadora");
                                if(rs.wasNull()) {
                                	idCalc = 0;
                                }
                            }
                            rs.close();
                            pstSelect.close();
                            
                            String[] tablasHijas = {
                            	    "DELETE FROM Registrar_Salida WHERE dni_cliente = ?",
                            	    "DELETE FROM Registrar_Entrada WHERE dni_cliente = ?",
                            	    "DELETE FROM Asistir WHERE dni_cliente = ?",
                            	    "DELETE FROM Atender WHERE dni_cliente = ?",
                            	    "DELETE FROM Entrenar WHERE dni_cliente = ?" 
                            	};
                            
                            for(String sqlHija : tablasHijas) {
                                PreparedStatement pstHija = con.prepareStatement(sqlHija);
                                pstHija.setString(1, dni);
                                pstHija.executeUpdate();
                                pstHija.close();
                            }
                            
                            PreparedStatement pstCliente = con.prepareStatement("DELETE FROM Cliente WHERE dni = ?");
                            pstCliente.setString(1, dni);
                            int resultado = pstCliente.executeUpdate();
                            pstCliente.close();
                            
                            if(idCalc != 0) {
                                PreparedStatement pstCalc = con.prepareStatement("DELETE FROM Calculadora WHERE id = ?");
                                pstCalc.setInt(1, idCalc);
                                pstCalc.executeUpdate();
                                pstCalc.close();
                            }
                            
                            if(resultado > 0) {
                                modeloTabla.removeRow(filaSeleccionada);
                                JOptionPane.showMessageDialog(PanelEmpleado.this, "Socio borrado correctamente.", "Borrado", JOptionPane.OK_OPTION);
                            }
                            
                        } catch(Exception eliminar) {
                            eliminar.printStackTrace();
                            JOptionPane.showMessageDialog(PanelEmpleado.this, "No se ha podido eliminar de la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelEmpleado.this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botones.add(btnAnadir);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        JPanel zonaSuperior = new JPanel();
        zonaSuperior.setLayout(new BoxLayout(zonaSuperior, BoxLayout.Y_AXIS));
        zonaSuperior.setBackground(fondoOscuro);
        zonaSuperior.add(cabecera);
        zonaSuperior.add(Box.createVerticalStrut(15));
        zonaSuperior.add(botones);

        contenido.add(zonaSuperior, BorderLayout.NORTH);

        String[] columnas = {"DNI", "Nombre", "Apellido", "Membresía", "IMC", "Último acceso"};
        modeloTabla = new DefaultTableModel(columnas, 0); 
        tablaSocios = new JTable(modeloTabla); 

        tablaSocios.setRowHeight(30);
        tablaSocios.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaSocios.setBackground(grisInput);
        tablaSocios.setForeground(Color.WHITE);
        tablaSocios.setGridColor(fondoOscuro);
        tablaSocios.setSelectionBackground(azulGym);
        tablaSocios.setSelectionForeground(Color.BLACK);
        tablaSocios.setBorder(new LineBorder(azulGym, 1));

        JTableHeader header = tablaSocios.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(azulGym);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaSocios);
        scroll.getViewport().setBackground(grisInput);
        scroll.setBackground(grisInput);

        contenido.add(scroll, BorderLayout.CENTER);

        return contenido;
    }
    
    public void cargarDatosSocios() {
        modeloTabla.setRowCount(0);
        
        try {
            Connection con = Main.getConectar();
            
            String sql = "SELECT c.dni, c.nombre, c.apellido, c.id_membresia, " +
                         "(SELECT cal.IMC FROM Calculadora cal WHERE cal.id = c.id_calculadora) AS IMC, " +
                         "c.ultimo_acceso " +
                         "FROM Cliente c";
                         
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idMembresia = rs.getInt("id_membresia");
                
                String imcStr = rs.getString("IMC");
                
                if(imcStr == null || imcStr.isEmpty()) {
                	imcStr = "--";
                }
                
                String accesoStr = rs.getString("ultimo_acceso");
                if (accesoStr == null || accesoStr.isEmpty()) {
                    accesoStr = "--";
                }
                
                Object[] fila = {
                    dni, 
                    nombre, 
                    apellido, 
                    idMembresia, 
                    imcStr, 
                    accesoStr
                };
                
                modeloTabla.addRow(fila);
            }
            
            rs.close();
            pstmt.close();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de socios.");
            e.printStackTrace();
        }
    }

    public void cargarDatosAcceso() {
        modeloTablaAcceso.setRowCount(0);
        try {
            Connection con = Main.getConectar();
            
            String sql = "SELECT e.dni_cliente, c.nombre, c.apellido, " +
                         "TO_CHAR(e.fecha, 'DD/MM/YYYY') AS fecha_formateada, " +
                         "TO_CHAR(e.hora, 'HH24:MI') AS hora_entrada_f, " +
                         "(SELECT TO_CHAR(s.hora, 'HH24:MI') FROM Registrar_Salida s WHERE e.codigo_registro_acceso = s.codigo_registro_acceso AND e.dni_cliente = s.dni_cliente AND e.fecha = s.fecha) AS hora_salida_f " +
                         "FROM Registrar_Entrada e, Cliente c " +
                         "WHERE e.dni_cliente = c.dni " +
                         "ORDER BY e.fecha DESC, e.hora DESC";
                         
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String dni = rs.getString("dni_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String fecha = rs.getString("fecha_formateada");
                String horaEntrada = rs.getString("hora_entrada_f");
                String horaSalida = rs.getString("hora_salida_f");
                
                String textoSalida = (horaSalida == null || horaSalida.isEmpty()) ? "Dentro" : horaSalida;
                
                modeloTablaAcceso.addRow(new Object[]{ dni, nombre, apellido, fecha, horaEntrada, textoSalida });
            }
            rs.close();
            pstmt.close();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los accesos.");
            e.printStackTrace();
        }
    }

    public void cargarDatosActividades() {
        modeloTablaActividades.setRowCount(0);
        try {
            Connection con = Main.getConectar();
            
            String sql = "SELECT r.dni_entrenador, r.codigo_clases, e.nombre, c.tipo AS clase, z.tipo AS zona, " +
                         "TO_CHAR(r.hora, 'HH24:MI') AS hora_formateada, r.descripcion " +
                         "FROM Realizar r, Entrenador en, Empleados e, Clases c, Zona_Entrenos z " +
                         "WHERE r.dni_entrenador = en.tipo_empleados " +
                         "AND en.tipo_empleados = e.dni " +
                         "AND r.codigo_clases = c.codigo " +
                         "AND r.id_zona_entrenos = z.id " +
                         "ORDER BY r.hora ASC";
                         
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String dni = rs.getString("dni_entrenador");
                int cod = rs.getInt("codigo_clases");
                String nombre = rs.getString("nombre");
                String clase = rs.getString("clase");
                String zona = rs.getString("zona");
                String hora = rs.getString("hora_formateada");
                String desc = rs.getString("descripcion");
                
                if(hora == null || hora.isEmpty()) hora = "--:--";
                if(desc == null || desc.isEmpty()) desc = "Sin descripción";
                
                Object[] fila = { dni, cod, nombre, clase, zona, hora, desc };
                modeloTablaActividades.addRow(fila);
            }
            rs.close();
            pstmt.close();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las actividades.");
            e.printStackTrace();
        }
    }
}
