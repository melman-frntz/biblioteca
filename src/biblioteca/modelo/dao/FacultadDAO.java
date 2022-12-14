package biblioteca.modelo.dao;

/*
*@author franz
*Fecha creacion: 20/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: franz
*/

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.Facultad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacultadDAO {
    
    /*
     *Esta sección del programa filtra las facultades por campus 
    */
    public static ArrayList<Facultad> obtenerFacultadPorCampus(int idCampus) throws SQLException{
        ArrayList<Facultad> facultadesBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM facultad WHERE idCampus = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, idCampus);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                facultadesBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Facultad facultad = new Facultad();
                    facultad.setIdFacultad(resultadoConsulta.getInt("idFacultad"));
                    facultad.setClave(resultadoConsulta.getString("clave"));
                    facultad.setNombre(resultadoConsulta.getString("nombre"));
                    facultad.setIdCampus(resultadoConsulta.getInt("IdCampus"));
                    facultadesBD.add(facultad);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return facultadesBD;
    }
    
}
