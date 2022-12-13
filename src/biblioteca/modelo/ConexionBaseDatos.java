package biblioteca.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static String BASEDATOS = "biblioteca";
    private static String IP = "localhost";
    private static String PUERTO = "3306";
    private static String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BASEDATOS + "?allowPublicKeyRetrieval=true&useSSL=false";
    
    private static String USUARIO = "";
    private static String CONTRASENIA = "";
    
    public static Connection abrirConexionBaseDatos(){
        Connection dbConnection = null;
        try {
            Class.forName(CONTROLADOR);
            dbConnection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return dbConnection;
    }
}