import java.sql.*;
import javax.swing.UIManager;

public class Main {
	
	private static Connection con = null;
	
    public static void main(String[] args) {
        //Aplicamos el estilo
        try { 
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
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
        	String url = "jdbc:postgresql://localhost:5432/bbdd_gymstats";
            String usuario = "gymAdmin";
            String password = "1234";
        	
        	con = DriverManager.getConnection(url, usuario, password);
        	System.out.println("Se ha conectado correctamente a la base de datos.");
        	
        } catch(SQLException error) {
        	System.out.println("No se ha podido conectar a la base de datos.");
        	error.printStackTrace();
        }
        return con;
    }
}
