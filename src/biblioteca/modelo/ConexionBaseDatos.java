package biblioteca.modelo;

/*
*@author franz
*Fecha creacion: 07/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: franz
*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static String BASEDATOS = "biblioteca";
    private static String IP = "localhost";
    private static String PUERTO = "3306";
    private static String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BASEDATOS + "?allowPublicKeyRetrieval=true&useSSL=false";
    
    private static String USUARIO = "bibliotecaBD";
    private static String CONTRASENIA = "construccion";
    
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