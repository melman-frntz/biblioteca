package biblioteca.modelo.dao;

/*
    @author Jesús Enrique Fernández González
    Fecha creación: 10/12/2022
    Fecha de última modificación: 16/12/2022
    Último modificador: Jesús Enrique Fernández González
*/

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.UsuarioBiblioteca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import utilidades.Utilidades;

public class UsuarioBibliotecaDAO {
    public static ArrayList<UsuarioBiblioteca> obtenerUsuario(String nombreUsuario) throws SQLException{
        ArrayList<UsuarioBiblioteca> usuariosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT nombre, correo, domicilio, telefono FROM UsuarioBiblioteca WHERE nombre = ?";
                
                PreparedStatement consultaUsuario = conexionBD.prepareStatement(consulta);
                consultaUsuario.setString(1, nombreUsuario);
                ResultSet resultadoConsulta = consultaUsuario.executeQuery();
                usuariosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    UsuarioBiblioteca temp = new UsuarioBiblioteca();
                    temp.setNombre(resultadoConsulta.getString("nombre"));
                    temp.setCorreo(resultadoConsulta.getString("correo"));
                    temp.setDomicilio(resultadoConsulta.getString("domicilio"));
                    temp.setTelefono(resultadoConsulta.getString("telefono"));
                    usuariosBD.add(temp);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Falló la conexión con la base de datos.\nInténtelo más tarde", 
                    Alert.AlertType.ERROR);
        }
        return usuariosBD;
    }
}