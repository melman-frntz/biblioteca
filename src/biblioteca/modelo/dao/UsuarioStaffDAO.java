package biblioteca.modelo.dao;

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.UsuarioStaff;
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
    
}