import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PanelAdministrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaSocios;
    private DefaultTableModel modeloTabla;

    Color fondoOscuro = new Color(30, 30, 30);
    Color azulGym = new Color(0, 212, 255);
    Color grisInput = new Color(45, 45, 45);

    public PanelAdministrador() {
        setTitle("GymStats - Panel de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(fondoOscuro);

        add(crearSidebar(), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
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

        menu.add(crearBotonMenu("Inicio"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(crearBotonMenu("Socios"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(crearBotonMenu("Personal"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(crearBotonMenu("Actividades"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(crearBotonMenu("Acceso"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(crearBotonMenu("Configuración"));

        sidebar.add(titulo, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);

        return sidebar;
    }
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        boton.setPreferredSize(new Dimension(220, 58));
        boton.setMargin(new Insets(8, 15, 8, 15));

        boton.setBackground(grisInput);
        boton.setForeground(Color.WHITE);

        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBorderPainted(true);

        Border borde = BorderFactory.createCompoundBorder(
                new LineBorder(azulGym, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        );
        boton.setBorder(borde);

        return boton;
    }

    private JButton crearBotonAccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(140, 38));

        boton.setBackground(azulGym);
        boton.setForeground(Color.BLACK);

        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBorderPainted(true);
        boton.setBorder(new LineBorder(azulGym, 1, true));

        return boton;
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
        botones.add(crearBotonAccion("Añadir"));
        botones.add(crearBotonAccion("Modificar"));
        botones.add(crearBotonAccion("Eliminar"));

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
        scroll.setBorder(new LineBorder(azulGym, 1));

        contenido.add(scroll, BorderLayout.CENTER);

        return contenido;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        PanelAdministrador pa = new PanelAdministrador();
        pa.setVisible(true);
    }
}