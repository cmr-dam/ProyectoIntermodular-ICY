import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
	
	private static Connection con = null;
	
    public static void main(String[] args) {
        //Aplicamos el estilo
        try { 
        	UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
        	System.out.println("No se ha podido aplicar el estilo.");
        	e.printStackTrace();
        }
        
    	//Abrimos las ventanas
   		PanelLogin login = new PanelLogin();
   		login.setVisible(true);
    }
    
    public static Connection getConectar() {
    	//CONEXION BASE DE DATOS
        try {
        	String url = "jdbc:postgresql://192.168.56.101:5432/GymstatsFinal";
            String usuario = "postgres";
            String password = "1234";
        	
        	con = DriverManager.getConnection(url, usuario, password);
        	System.out.println("Se ha conectado correctamente a la base de datos.");
        	
        } catch(SQLException error) {
        	System.out.println("No se ha podido conectar a la base de datos.");
        	error.printStackTrace();
        }
        return con;
    }
    
    
    public static void setIconoApp(JFrame ventana) {
        try {
            ImageIcon icono = new ImageIcon(Main.class.getResource("/img/logo-gymstats-recortado.jpeg"));
            ventana.setIconImage(icono.getImage());
        } catch (Exception e) {
            System.out.println("Icono no encontrado.");
        }
    }
}
