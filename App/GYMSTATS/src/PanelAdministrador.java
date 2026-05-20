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

public class PanelAdministrador extends JFrame {

    private static final long serialVersionUID = 1L;
    
    //SOCIOS
    private JTable tablaSocios;
    private DefaultTableModel modeloTabla;
    
    //PERSONAL
    private JTable tablaPersonal;
    private DefaultTableModel modeloTablaPersonal;
    
    private JPanel contentArea;

    //Creamos las variables de los colores para usar los mismos
    Color fondoOscuro = new Color(30, 30, 30);
    Color azulGym = new Color(0, 212, 255);
    Color grisInput = new Color(45, 45, 45);

    public PanelAdministrador(PanelLogin p) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Al cerrarla volvemos a el panel de login
                int opcion = JOptionPane.showConfirmDialog(PanelAdministrador.this, "Seguro que quieres cerrar sesion?", "Cerrar Sesion", JOptionPane.INFORMATION_MESSAGE);
                
                if(opcion == JOptionPane.YES_OPTION) {
                    p.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Operacion cancelada", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        setTitle("GymStats - Panel de Gestión");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Usamos esto porque si lo borramos se cierra igual
        setSize(1200, 700);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(fondoOscuro);

        // Inicializamos el area de contenido
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(fondoOscuro);

        getContentPane().add(crearSidebar(), BorderLayout.WEST);
        getContentPane().add(contentArea, BorderLayout.CENTER);
        
        //Una vez se crea lo visual, mostramos el panel de inicio
        mostrarPanel(crearPanelInicio());
    }
    
    //Metodo principal para cambiar los paneles
    public void mostrarPanel(JPanel panelNuevo) {
        contentArea.removeAll();
        contentArea.add(panelNuevo, BorderLayout.CENTER);
        contentArea.revalidate(); 
        contentArea.repaint(); 
    }
    
    //Metodo para aplicar estilo a los botones
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(grisInput);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btn.setPreferredSize(new Dimension(220, 58));
        btn.setMargin(new Insets(8, 15, 8, 15));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return btn;
    }
    
    //Panel de inicio
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
    
    //Panel del personal
    private JPanel crearPanelPersonal() {
    	JPanel contenido = new JPanel(new BorderLayout(15, 15));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(fondoOscuro);

        JLabel titulo = new JLabel("Gestión de Personal");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(fondoOscuro);
        cabecera.add(titulo, BorderLayout.WEST);

        // Panel de botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(fondoOscuro);
        
        // BOTÓN AÑADIR
        JButton btnAnadir = new JButton("Añadir");
        btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setPreferredSize(new Dimension(140, 38));
        btnAnadir.setBackground(azulGym);
        btnAnadir.setForeground(Color.BLACK);
        btnAnadir.setContentAreaFilled(false);
        btnAnadir.setOpaque(true);
        btnAnadir.setBorderPainted(true);
        btnAnadir.setBorder(new LineBorder(azulGym, 1, true));
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AñadirEmpleado vEmp = new AñadirEmpleado(PanelAdministrador.this);
                    setVisible(false);
                    vEmp.setVisible(true);
                } catch(Exception crear) {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Error al abrir la ventana.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // BOTÓN MODIFICAR
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificar.setFocusPainted(false);
        btnModificar.setPreferredSize(new Dimension(140, 38));
        btnModificar.setBackground(azulGym);
        btnModificar.setForeground(Color.BLACK);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setOpaque(true);
        btnModificar.setBorderPainted(true);
        btnModificar.setBorder(new LineBorder(azulGym, 1, true));
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila = tablaPersonal.getSelectedRow();
                
                if(fila != -1) {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Falta crear la ventana ModificarEmpleado");
                } else {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Debes seleccionar un empleado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // BOTÓN ELIMINAR
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(140, 38));
        btnEliminar.setBackground(azulGym);
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(true);
        btnEliminar.setBorder(new LineBorder(azulGym, 1, true));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaPersonal.getSelectedRow();
                
                if(filaSeleccionada != -1) {
                    String dni = tablaPersonal.getValueAt(filaSeleccionada, 0).toString();
                    int confirmar = JOptionPane.showConfirmDialog(PanelAdministrador.this, "¿Seguro que quieres despedir a este empleado?", "Despedir", JOptionPane.YES_NO_OPTION);
                    
                    if(confirmar == JOptionPane.YES_OPTION) {
                        try {
                            Connection con = Main.getConectar();
                            String sql = "DELETE FROM Empleados WHERE dni = ?";
                            PreparedStatement pstmt = con.prepareStatement(sql);
                            pstmt.setString(1, dni);
                            
                            int resultado = pstmt.executeUpdate();
                            if(resultado > 0) {
                                modeloTablaPersonal.removeRow(filaSeleccionada);
                                JOptionPane.showMessageDialog(PanelAdministrador.this, "Empleado eliminado correctamente", "Borrado", JOptionPane.INFORMATION_MESSAGE);
                            }
                            pstmt.close();
                        } catch(Exception ex) {
                            JOptionPane.showMessageDialog(PanelAdministrador.this, "No se puede borrar porque está asignado a otra tabla (ej: Entrenador). Borra primero su puesto.", "Error de clave", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // BOTÓN ACTUALIZAR
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 38));
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(true);
        btnActualizar.setBorder(new LineBorder(azulGym, 1, true));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosPersonal();
            }
        });

        //Añadimos los botones al panel
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

        //Creamos la tabla de empleados
        String[] columnas = {"DNI", "Nombre", "Apellido", "Teléfono", "Nómina"};
        modeloTablaPersonal = new DefaultTableModel(columnas, 0);
        tablaPersonal = new JTable(modeloTablaPersonal);

        tablaPersonal.setRowHeight(30);
        tablaPersonal.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaPersonal.setBackground(grisInput);
        tablaPersonal.setForeground(Color.WHITE);
        tablaPersonal.setGridColor(fondoOscuro);
        tablaPersonal.setSelectionBackground(azulGym);
        tablaPersonal.setSelectionForeground(Color.BLACK);
        tablaPersonal.setBorder(new LineBorder(azulGym, 1));

        JTableHeader header = tablaPersonal.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(azulGym);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaPersonal);
        scroll.getViewport().setBackground(grisInput);
        scroll.setBackground(grisInput);
        scroll.setBorder(new LineBorder(azulGym, 1));

        contenido.add(scroll, BorderLayout.CENTER);

        //Cargamos los datos al crear el panel
        cargarDatosPersonal();

        return contenido;
    }

    //Panel de gestion de actividades
    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoOscuro);
        
        JLabel titulo = new JLabel("Gestión de Actividades (En desarrollo...)", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(azulGym);
        
        panel.add(titulo, BorderLayout.CENTER);
        return panel;
    }

    //Panel de acceso
    private JPanel crearPanelAcceso() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoOscuro);
        
        JLabel titulo = new JLabel("Control de Accesos (En desarrollo...)", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(azulGym);
        
        panel.add(titulo, BorderLayout.CENTER);
        return panel;
    }

    //Panel lateral
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

        // BOTONES CREADOS CON EL MÉTODO OPTIMIZADO
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

        JButton btnPersonal = crearBotonMenu("Personal");
        btnPersonal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearPanelPersonal());
            }
        });

        JButton btnActividades = crearBotonMenu("Actividades");
        btnActividades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearPanelActividades());
            }
        });

        JButton btnAcceso = crearBotonMenu("Acceso");
        btnAcceso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(crearPanelAcceso());
            }
        });

        //Añadimos los botones al menu
        menu.add(btnInicio);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnSocios);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnPersonal);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnActividades);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnAcceso);

        sidebar.add(titulo, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);

        return sidebar;
    }

    //Panel Principal para crear el contenido
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

        //Panel de los botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(fondoOscuro);
        
        //BOTÓN AÑADIR
        JButton btnAnadir = new JButton("Añadir");
        btnAnadir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setPreferredSize(new Dimension(140, 38));
        btnAnadir.setBackground(azulGym);
        btnAnadir.setForeground(Color.BLACK);
        btnAnadir.setContentAreaFilled(false);
        btnAnadir.setOpaque(true);
        btnAnadir.setBorderPainted(true);
        btnAnadir.setBorder(new LineBorder(azulGym, 1, true));
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AñadirSocio vSoc = new AñadirSocio(PanelAdministrador.this);
                    setVisible(false);
                    vSoc.setVisible(true);
                } catch(Exception crear) {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "No se ha podido abrir la ventana de creacion.", "Error Ventana", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
      //BOTÓN ACTUALIZAR
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 38));
        btnActualizar.setBackground(azulGym);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(true);
        btnActualizar.setBorder(new LineBorder(azulGym, 1, true));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Llama al método que vacía la tabla y vuelve a hacer el SELECT
                cargarDatosSocios();
            }
        });
        
        //BOTÓN MODIFICAR
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificar.setFocusPainted(false);
        btnModificar.setPreferredSize(new Dimension(140, 38));
        btnModificar.setBackground(azulGym);
        btnModificar.setForeground(Color.BLACK);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setOpaque(true);
        btnModificar.setBorderPainted(true);
        btnModificar.setBorder(new LineBorder(azulGym, 1, true));
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int fila = tablaSocios.getSelectedRow();
                
                if(fila != -1) {
                    //Recogemos el dni de el usuario seleccionado el cual esta en la primera columna y lo parseamos a String
                    String dniSeleccionado = modeloTabla.getValueAt(fila, 0).toString();
                    
                    ModificarSocio ventanaModificar = new ModificarSocio(PanelAdministrador.this, dniSeleccionado);
                    ventanaModificar.setVisible(true);
                    setVisible(false);  
                } else {
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "Debes seleccionar una fila de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //BOTÓN ELIMINAR
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(140, 38));
        btnEliminar.setBackground(azulGym);
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(true);
        btnEliminar.setBorder(new LineBorder(azulGym, 1, true));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "DELETE FROM Cliente WHERE dni = ?";
                
                try {
                    int filaSeleccionada = tablaSocios.getSelectedRow();
                    
                    if(filaSeleccionada != -1) {
                        String dni = tablaSocios.getValueAt(filaSeleccionada, 0).toString(); //Recogemos el dni y lo parseamos a String
                        int confirmar = JOptionPane.showConfirmDialog(PanelAdministrador.this, "Seguro que quieres eliminar a este usuario?", "Eliminar", JOptionPane.YES_NO_CANCEL_OPTION);
                        
                        if(confirmar == JOptionPane.YES_OPTION) {
                            Connection con = Main.getConectar();
                            PreparedStatement pstmt = con.prepareStatement(sql);
                            
                            pstmt.setString(1, dni); //Pasamos el dni como parametro
                            
                            int resultado = pstmt.executeUpdate();
                            
                            //Si la cantidad de usuarios borrados es > 0 mostramos confirmacion
                            if(resultado > 0) {
                                modeloTabla.removeRow(filaSeleccionada);
                                JOptionPane.showMessageDialog(PanelAdministrador.this, "Se ha borrado correctamente", "Borrado", JOptionPane.OK_OPTION);
                            }
                            pstmt.close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(PanelAdministrador.this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    
                } catch(Exception eliminar) {
                    eliminar.printStackTrace();
                    JOptionPane.showMessageDialog(PanelAdministrador.this, "No se ha podido eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Añadimos los botones al panel
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

        //Creamos las columnas de la tabla
        String[] columnas = {"DNI", "Nombre", "Apellido", "Membresía", "IMC", "Último acceso"};
        modeloTabla = new DefaultTableModel(columnas, 0); //Le asignamos las columnas
        tablaSocios = new JTable(modeloTabla); //Creamos la tabla y le ponemos el modelo

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
        scroll.setBorder(new LineBorder(azulGym, 1));

        contenido.add(scroll, BorderLayout.CENTER);

        return contenido;
    }
    
    // MÉTODO PARA RELLENAR LA TABLA DESDE LA BBDD
    public void cargarDatosSocios() {
        //Limpiamos la tabla antes de cargar datos nuevos
        modeloTabla.setRowCount(0);
        
        try {
            Connection con = Main.getConectar();
            
            //Hacemos la consulta a la tabla Cliente
            String sql = "SELECT dni, nombre, apellido, id_membresia, imc, ultimo_acceso FROM Cliente";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            // Recorremos los resultados y los almacenamos en variables
            while(rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idMembresia = rs.getInt("id_membresia");
                
                String imcStr = rs.getString("imc");
                
                if(imcStr == null || imcStr.isEmpty()) {  //Si el imc aun no ha sido calculado por el cliente ponemos --
                	imcStr = "--";
                }
                
                String accesoStr = rs.getString("ultimo_acceso"); //al igual con ultimo acceso
                if (accesoStr == null || accesoStr.isEmpty()) {
                    accesoStr = "--";
                }
                
                //Creamos un array de objetos con los datos de la fila
                Object[] fila = {
                    dni, 
                    nombre, 
                    apellido, 
                    idMembresia, 
                    imcStr, 
                    accesoStr
                };
                
                //Añadimos la fila a la tabla visual
                modeloTabla.addRow(fila);
            }
            
            rs.close();
            pstmt.close();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de socios.");
            e.printStackTrace();
        }
    }
    
    //Metodo cargar datos de el personal
    public void cargarDatosPersonal() {
        modeloTablaPersonal.setRowCount(0);
        
        try {
            Connection con = Main.getConectar();
            String sql = "SELECT dni, nombre, apellido, telefono, nomina FROM Empleados";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                double nomina = rs.getDouble("nomina");
                
                Object[] fila = {
                    dni, 
                    nombre, 
                    apellido, 
                    telefono, 
                    nomina + " €"
                };
                
                modeloTablaPersonal.addRow(fila);
            }
            
            rs.close();
            pstmt.close();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de personal.");
            e.printStackTrace();
        }
    }
}