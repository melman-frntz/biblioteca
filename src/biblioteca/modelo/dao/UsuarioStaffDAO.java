package biblioteca.modelo.dao;

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioStaff;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioStaffDAO {
    
    public static UsuarioStaff verificarUsuario(String numeroDePersonal, String contraseña) throws SQLException{
        UsuarioStaff usuarioSesion = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try {
                String consulta = "SELECT numeroPersonal, password FROM usuarioStaff "
                        + "WHERE numeroPersonal = ? AND password = ?";
                PreparedStatement consultaLogin = conexionBD.prepareStatement(consulta);
                consultaLogin.setString(1, numeroDePersonal);
                consultaLogin.setString(2, contraseña);
                ResultSet resultadoConsulta = consultaLogin.executeQuery();
                usuarioSesion = new UsuarioStaff();
                if(resultadoConsulta.next()){
                    usuarioSesion.setNumeroDePersonal(numeroDePersonal);
                    usuarioSesion.setContraseña(contraseña);
                }else{
                    usuarioSesion.setNumeroDePersonal(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        
        return usuarioSesion;
    }
    
    public static ResultadoOperacion registrarUsuarioStaff(UsuarioStaff usuarioStaff, File foto) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO usuarioStaff (numeroPersonal, password, tipoUsuario, fotoPerfil) VALUES (?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, usuarioStaff.getNumeroDePersonal());
                prepararSentencia.setString(2, usuarioStaff.getContraseña());
                prepararSentencia.setString(3, usuarioStaff.getTipoUsuario());
                FileInputStream fotoUsuarioStaff = new FileInputStream(foto);
                prepararSentencia.setBlob(4, fotoUsuarioStaff);
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Usuario staff registrado correctamente.");
                    respuesta.setFilasAfectadas(filasAfectadas);
                }else{
                    respuesta.setMensaje("No se pudo registrar la informacion del usuario staff.");
                }
            }catch(FileNotFoundException ex){
                respuesta.getMensaje();
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión a la base de datos.");
            respuesta.setFilasAfectadas(-1);
        }
        
        return respuesta;
    }
    
}