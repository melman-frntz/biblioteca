package biblioteca.modelo.dao;

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.RecursoDocumental;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioBiblioteca;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import utilidades.Utilidades;

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
                String consulta = "SELECT * FROM recursodocumental WHERE nombre LIKE ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, "%"+nombre+"%");
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
    
    public static RecursoDocumental buscarRecursoPorFolio(String folio) throws SQLException{
        RecursoDocumental recursoBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM recursodocumental WHERE folio = ? AND estado = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, folio);
                prepararSentencia.setString(2, "Disponible");
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                recursoBD = new RecursoDocumental();
                
                if(resultadoConsulta.next()){
                    recursoBD.setIdRecurso(resultadoConsulta.getInt("id"));
                    recursoBD.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursoBD.setFolio(resultadoConsulta.getString("folio"));
                    recursoBD.setNombre(resultadoConsulta.getString("nombre"));
                    recursoBD.setAutor(resultadoConsulta.getString("autor"));
                    recursoBD.setDescripcion(resultadoConsulta.getString("descripcion"));
                    recursoBD.setSeccion(resultadoConsulta.getString("seccion"));
                    recursoBD.setEstado(resultadoConsulta.getString("estado"));
                    recursoBD.setProcedencia(resultadoConsulta.getString("procedencia"));
                    recursoBD.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoBD.setEditorial(resultadoConsulta.getString("editorial"));
                    recursoBD.setIsbn(resultadoConsulta.getString("isbn"));
                    recursoBD.setPeso(resultadoConsulta.getDouble("peso"));
                    recursoBD.setDuracion(resultadoConsulta.getInt("duracion"));
                }else{
                    recursoBD = null;
                }
                
            }catch(SQLException sqlExcepcion){
                sqlExcepcion.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return recursoBD;
    }
    
    public static ArrayList<RecursoDocumental> obtenerNombreRecursoPrestamosRenovacion(UsuarioBiblioteca usuario) throws SQLException{
        ArrayList<RecursoDocumental> recursosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT recursoDocumental.nombre, recursoDocumental.id, recursodocumental.idBiblioteca "
                        + "FROM recursoDocumental INNER JOIN Prestamo ON recursoDocumental.id = Prestamo.idRecurso INNER "
                        + "JOIN UsuarioBiblioteca ON Prestamo.idUsuarioBiblioteca = UsuarioBiblioteca.id WHERE "
                        + "UsuarioBiblioteca.nombre = ? AND Prestamo.fechaEntrega > ?";
                
                Date fechaUtil = new Date();
                java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                
                PreparedStatement consultaPrestamosUsuario = conexionBD.prepareStatement(consulta);
                consultaPrestamosUsuario.setString(1, usuario.getNombre());
                consultaPrestamosUsuario.setDate(2, fechaSQL);
                ResultSet resultadoConsulta = consultaPrestamosUsuario.executeQuery();
                recursosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental temp = new RecursoDocumental();
                    temp.setIdRecurso(resultadoConsulta.getInt("id"));
                    temp.setNombre(resultadoConsulta.getString("nombre"));
                    temp.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursosBD.add(temp);
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
        return recursosBD;
    }
    
    public static ArrayList<RecursoDocumental> obtenerNombreRecursoPrestamos(UsuarioBiblioteca usuario) throws SQLException{
        ArrayList<RecursoDocumental> recursosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT recursoDocumental.nombre, recursoDocumental.id, recursodocumental.idBiblioteca "
                        + "FROM recursoDocumental INNER JOIN Prestamo ON recursoDocumental.id = Prestamo.idRecurso INNER "
                        + "JOIN UsuarioBiblioteca ON Prestamo.idUsuarioBiblioteca = UsuarioBiblioteca.id WHERE "
                        + "UsuarioBiblioteca.nombre = ?";
                
                PreparedStatement consultaPrestamosUsuario = conexionBD.prepareStatement(consulta);
                consultaPrestamosUsuario.setString(1, usuario.getNombre());
                ResultSet resultadoConsulta = consultaPrestamosUsuario.executeQuery();
                recursosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental temp = new RecursoDocumental();
                    temp.setIdRecurso(resultadoConsulta.getInt("id"));
                    temp.setNombre(resultadoConsulta.getString("nombre"));
                    temp.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursosBD.add(temp);
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
        return recursosBD;
    }
    
    public static RecursoDocumental buscarRecursoDocumentalPorId(int idRecursoDocumental) throws SQLException{
        RecursoDocumental recursoDoc = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT id, idBiblioteca, nombre, autor, tipoRecurso, seccion, estado FROM recursodocumental WHERE idBiblioteca = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idRecursoDocumental);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                recursoDoc = new RecursoDocumental();
                if(resultadoConsulta.next()){
                    recursoDoc.setIdRecurso(resultadoConsulta.getInt("id"));
                    recursoDoc.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    recursoDoc.setNombre(resultadoConsulta.getString("nombre"));
                    recursoDoc.setAutor(resultadoConsulta.getString("autor"));
                    recursoDoc.setSeccion(resultadoConsulta.getString("seccion"));
                    recursoDoc.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoDoc.setEstado(resultadoConsulta.getString("estado"));
                }
                
            }catch(SQLException sqlExcepcion){
                sqlExcepcion.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return recursoDoc;
    }
    
    public static int obtenerIdRecursoDocumental(String folio) throws SQLException{
        int idRecurso = -1;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT id FROM recursodocumental WHERE folio = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, folio);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                
                if(resultadoConsulta.next()){
                    idRecurso = resultadoConsulta.getInt("id");
                }else{
                    idRecurso = -1;
                }
                
            }catch(SQLException sqlExcepcion){
                sqlExcepcion.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexion", "No hay conexion con la base de datos.", Alert.AlertType.ERROR);
        }
        
        return idRecurso;
    }
    
    public static ResultadoOperacion actualizarEstadoRecursoDocumental(String folio) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE recursodocumental SET estado = ? WHERE folio = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, "Prestado");
                prepararSentencia.setString(2, folio);
                
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
            respuesta.setMensaje("No hay conexión con la base de datos.");
        }
        
        return respuesta;
    }
}
