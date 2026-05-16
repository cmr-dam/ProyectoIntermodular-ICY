import javax.swing.*;
import javax.swing.border.Border;
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
    private JTable tablaSocios;
    private DefaultTableModel modeloTabla;

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

        getContentPane().add(crearSidebar(), BorderLayout.WEST);
        getContentPane().add(crearContenido(), BorderLayout.CENTER);
        
        //Una vez se crea lo visual, llamamos a el metodo para cargar la tabla de los socios
        cargarDatosSocios();
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

        //BOTÓN INICIO
        JButton btnInicio = new JButton("Inicio");
        btnInicio.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnInicio.setFocusPainted(false);
        btnInicio.setHorizontalAlignment(SwingConstants.LEFT);
        btnInicio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnInicio.setPreferredSize(new Dimension(220, 58));
        btnInicio.setMargin(new Insets(8, 15, 8, 15));
        btnInicio.setBackground(grisInput);
        btnInicio.setForeground(Color.WHITE);
        btnInicio.setContentAreaFilled(false);
        btnInicio.setOpaque(true);
        btnInicio.setBorderPainted(true);
        btnInicio.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        
        //BOTÓN SOCIOS
        JButton btnSocios = new JButton("Socios");
        btnSocios.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSocios.setFocusPainted(false);
        btnSocios.setHorizontalAlignment(SwingConstants.LEFT);
        btnSocios.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnSocios.setPreferredSize(new Dimension(220, 58));
        btnSocios.setMargin(new Insets(8, 15, 8, 15));
        btnSocios.setBackground(grisInput);
        btnSocios.setForeground(Color.WHITE);
        btnSocios.setContentAreaFilled(false);
        btnSocios.setOpaque(true);
        btnSocios.setBorderPainted(true);
        btnSocios.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        //BOTÓN PERSONAL
        JButton btnPersonal = new JButton("Personal");
        btnPersonal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnPersonal.setFocusPainted(false);
        btnPersonal.setHorizontalAlignment(SwingConstants.LEFT);
        btnPersonal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnPersonal.setPreferredSize(new Dimension(220, 58));
        btnPersonal.setMargin(new Insets(8, 15, 8, 15));
        btnPersonal.setBackground(grisInput);
        btnPersonal.setForeground(Color.WHITE);
        btnPersonal.setContentAreaFilled(false);
        btnPersonal.setOpaque(true);
        btnPersonal.setBorderPainted(true);
        btnPersonal.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        //BOTÓN ACTIVIDADES
        JButton btnActividades = new JButton("Actividades");
        btnActividades.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnActividades.setFocusPainted(false);
        btnActividades.setHorizontalAlignment(SwingConstants.LEFT);
        btnActividades.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnActividades.setPreferredSize(new Dimension(220, 58));
        btnActividades.setMargin(new Insets(8, 15, 8, 15));
        btnActividades.setBackground(grisInput);
        btnActividades.setForeground(Color.WHITE);
        btnActividades.setContentAreaFilled(false);
        btnActividades.setOpaque(true);
        btnActividades.setBorderPainted(true);
        btnActividades.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        //BOTÓN ACCESO
        JButton btnAcceso = new JButton("Acceso");
        btnAcceso.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnAcceso.setFocusPainted(false);
        btnAcceso.setHorizontalAlignment(SwingConstants.LEFT);
        btnAcceso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnAcceso.setPreferredSize(new Dimension(220, 58));
        btnAcceso.setMargin(new Insets(8, 15, 8, 15));
        btnAcceso.setBackground(grisInput);
        btnAcceso.setForeground(Color.WHITE);
        btnAcceso.setContentAreaFilled(false);
        btnAcceso.setOpaque(true);
        btnAcceso.setBorderPainted(true);
        btnAcceso.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        //BOTÓN CONFIGURACIÓN
        JButton btnConfiguracion = new JButton("Configuración");
        btnConfiguracion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnConfiguracion.setFocusPainted(false);
        btnConfiguracion.setHorizontalAlignment(SwingConstants.LEFT);
        btnConfiguracion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        btnConfiguracion.setPreferredSize(new Dimension(220, 58));
        btnConfiguracion.setMargin(new Insets(8, 15, 8, 15));
        btnConfiguracion.setBackground(grisInput);
        btnConfiguracion.setForeground(Color.WHITE);
        btnConfiguracion.setContentAreaFilled(false);
        btnConfiguracion.setOpaque(true);
        btnConfiguracion.setBorderPainted(true);
        btnConfiguracion.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

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
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnConfiguracion);

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
        		
        	}
        });

        //Añadimos los botones al panel
        botones.add(btnAnadir);
        botones.add(btnModificar);
        botones.add(btnEliminar);

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
            String sql = "SELECT dni, nombre, apellido, id_membresia FROM Cliente";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            // Recorremos los resultados
            while(rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idMembresia = rs.getInt("id_membresia");
                
                //Creamos un array de objetos con los datos de la fila
                //IMC y Último acceso los dejamos con "--" por defecto
                Object[] fila = {
                    dni, 
                    nombre, 
                    apellido, 
                    idMembresia, 
                    "--", 
                    "--"
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
}