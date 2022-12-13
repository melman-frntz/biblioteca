package biblioteca.modelo.dao;

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.RecursoDocumental;
import biblioteca.modelo.pojo.ResultadoOperacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @autor Froylan De Jesus Alvarez Rodriguez
 * @fecha 11/12/2022
 */
public class RecursoDocumentalDAO {
    public static ArrayList<RecursoDocumental> buscarRecursosPorNombre(String nombre) throws SQLException{
        ArrayList<RecursoDocumental> recursosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM recursodocumental WHERE nombre = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                recursosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental recursoTemporal = new RecursoDocumental();
                    recursoTemporal.setIdRecurso(resultadoConsulta.getInt("id"));
                    recursoTemporal.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursoTemporal.setFolio(resultadoConsulta.getString("folio"));
                    recursoTemporal.setNombre(resultadoConsulta.getString("nombre"));
                    recursoTemporal.setAutor(resultadoConsulta.getString("autor"));
                    recursoTemporal.setDescripcion(resultadoConsulta.getString("descripcion"));
                    recursoTemporal.setSeccion(resultadoConsulta.getString("seccion"));
                    recursoTemporal.setEstado(resultadoConsulta.getString("estado"));
                    recursoTemporal.setProcedencia(resultadoConsulta.getString("procedencia"));
                    recursoTemporal.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoTemporal.setEditorial(resultadoConsulta.getString("editorial"));
                    recursoTemporal.setIsbn(resultadoConsulta.getString("isbn"));
                    recursoTemporal.setPeso(resultadoConsulta.getDouble("peso"));
                    recursoTemporal.setDuracion(resultadoConsulta.getInt("duracion"));
                    recursosBD.add(recursoTemporal);
                }
                
            }catch(SQLException sqlExcepcion){
                sqlExcepcion.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return recursosBD;
    }
    
    public static ArrayList<RecursoDocumental> recuperarRecursos() throws SQLException{
        ArrayList<RecursoDocumental> recursosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM recursodocumental";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();

                recursosBD = new ArrayList<>();

                while(resultadoConsulta.next()){
                    RecursoDocumental recursoTemporal = new RecursoDocumental();
                    recursoTemporal.setIdRecurso(resultadoConsulta.getInt("id"));
                    recursoTemporal.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursoTemporal.setFolio(resultadoConsulta.getString("folio"));
                    recursoTemporal.setNombre(resultadoConsulta.getString("nombre"));
                    recursoTemporal.setAutor(resultadoConsulta.getString("autor"));
                    recursoTemporal.setDescripcion(resultadoConsulta.getString("descripcion"));
                    recursoTemporal.setSeccion(resultadoConsulta.getString("seccion"));
                    recursoTemporal.setEstado(resultadoConsulta.getString("estado"));
                    recursoTemporal.setProcedencia(resultadoConsulta.getString("procedencia"));
                    recursoTemporal.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoTemporal.setEditorial(resultadoConsulta.getString("editorial"));
                    recursoTemporal.setIsbn(resultadoConsulta.getString("isbn"));
                    recursoTemporal.setPeso(resultadoConsulta.getDouble("peso"));
                    recursoTemporal.setDuracion(resultadoConsulta.getInt("duracion"));
                    recursosBD.add(recursoTemporal);
                }
            }catch(SQLException sqlExcepcion){
                sqlExcepcion.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return recursosBD;
    }
    
    public static ResultadoOperacion registrarRecursoDaniado(String estado, int idRecurso) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE recursodocumental SET estado = ? WHERE id = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, estado);
                prepararSentencia.setInt(2, idRecurso);
                int filasAfectadas = prepararSentencia.executeUpdate();
                
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(filasAfectadas);
                }
            }catch(SQLException sqlExcepcion){
                respuesta.setMensaje(sqlExcepcion.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("No hay conexión a la base de datos.");
        }
        
        return respuesta;
    }
}
