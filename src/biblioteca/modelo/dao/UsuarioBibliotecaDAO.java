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
    
    public static UsuarioBiblioteca obtenerUsuarioPorId(String idUsuario) throws SQLException{
        UsuarioBiblioteca usuarioBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM UsuarioBiblioteca WHERE idUsuarioBiblioteca = ?";
                
                PreparedStatement consultaUsuario = conexionBD.prepareStatement(consulta);
                consultaUsuario.setString(1, idUsuario);
                ResultSet resultadoConsulta = consultaUsuario.executeQuery();
                usuarioBD = new UsuarioBiblioteca();
                
                while(resultadoConsulta.next()){
                    usuarioBD.setIdUsuarioBiblioteca(resultadoConsulta.getString("idUsuarioBiblioteca"));
                    usuarioBD.setNombre(resultadoConsulta.getString("nombre"));
                    usuarioBD.setGenero(resultadoConsulta.getString("genero"));
                    usuarioBD.setCorreo(resultadoConsulta.getString("correo"));
                    usuarioBD.setDomicilio(resultadoConsulta.getString("domicilio"));
                    usuarioBD.setTelefono(resultadoConsulta.getString("telefono"));
                    usuarioBD.setPeriodo(resultadoConsulta.getString("periodo"));
                    usuarioBD.setTipoUsuario(resultadoConsulta.getString("tipoUsuario"));
                    usuarioBD.setCarrera(resultadoConsulta.getString("carrera"));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Falló la conexión con la base de datos.\nInténtelo más tarde", Alert.AlertType.ERROR);
        }
        return usuarioBD;
    }
    
    public static ArrayList<UsuarioBiblioteca> buscarUsuarioPorId(String idUsuarioBiblioteca) throws SQLException{
        ArrayList<UsuarioBiblioteca> usuariosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM usuariobiblioteca "
                        + "WHERE idUsuarioBiblioteca = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, idUsuarioBiblioteca);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                usuariosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    UsuarioBiblioteca usuarioTemporal = new UsuarioBiblioteca();
                    usuarioTemporal.setIdUsuarioBiblioteca(resultadoConsulta.getString("idUsuarioBiblioteca"));
                    usuarioTemporal.setNombre(resultadoConsulta.getString("nombre"));
                    usuarioTemporal.setGenero(resultadoConsulta.getString("genero"));
                    usuarioTemporal.setTelefono(resultadoConsulta.getString("telefono"));
                    usuarioTemporal.setDomicilio(resultadoConsulta.getString("domicilio"));
                    usuarioTemporal.setCorreo(resultadoConsulta.getString("correo"));
                    usuariosBD.add(usuarioTemporal);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return usuariosBD;
    }
}