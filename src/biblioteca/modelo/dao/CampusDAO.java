package biblioteca.modelo.dao;

/*
*@author franz
*Fecha creacion: 20/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: franz
*/

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.Campus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author franz
 */
public class CampusDAO {
    
    public static ArrayList<Campus> obtenerCampus() throws SQLException{
        ArrayList<Campus> campusBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM campus";
                PreparedStatement consultaObtenerCampus = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = consultaObtenerCampus.executeQuery();
                campusBD = new ArrayList<>();
                while(resultadoConsulta.next()){
                    Campus campus = new Campus();
                    campus.setIdCampus(resultadoConsulta.getInt("idCampus"));
                    campus.setClave(resultadoConsulta.getString("clave"));
                    campus.setNombre(resultadoConsulta.getString("nombre"));
                    campus.setZona(resultadoConsulta.getString("zona"));
                    campusBD.add(campus);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return campusBD;
    }
    
}
