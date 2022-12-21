package biblioteca.modelo.dao;

/*
*@author manuel
*Fecha creacion: 10/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: manuel
*/

import biblioteca.modelo.ConexionBaseDatos;
import biblioteca.modelo.pojo.Prestamo;
import biblioteca.modelo.pojo.ResultadoOperacion;
import biblioteca.modelo.pojo.UsuarioBiblioteca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import utilidades.Utilidades;

public class PrestamoDAO {
    
    public static ResultadoOperacion registrarDevolucion(int idRecurso) throws SQLException{
        ResultadoOperacion prestamosBD = new ResultadoOperacion();
        prestamosBD.setError(true);
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        //¿Hay conexión a la base de datos?
        if(conexionBD != null){
            try{
                String consulta = "DELETE FROM Prestamo WHERE Prestamo.idRecurso = ?";
                String consulta2 = "UPDATE RecursoDocumental SET Estado = 'Disponible' WHERE id = ?";
                
                PreparedStatement consultaPrestamo = conexionBD.prepareStatement(consulta);
                consultaPrestamo.setInt(1, idRecurso);
                int numeroFilas = consultaPrestamo.executeUpdate();
                
                PreparedStatement consultaPrestamo2 = conexionBD.prepareStatement(consulta2);
                consultaPrestamo2.setInt(1, idRecurso);
                int numeroFilas2 = consultaPrestamo2.executeUpdate();
                
                //¿La consulta fue exitosa?
                if(numeroFilas > 0){
                    prestamosBD.setError(false);
                    prestamosBD.setMensaje("Préstamo eliminado");
                }else{
                    prestamosBD.setMensaje("No se pudo eliminar el préstamo");
                }
                
                if(numeroFilas2 > 0){
                    prestamosBD.setError(false);
                    prestamosBD.setMensaje("Recurso documental disponible");
                }else{
                    prestamosBD.setMensaje("No se pudo cambiar el estado del recurso");
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
        return prestamosBD;
    }
    
    public static ResultadoOperacion registrarRenovacion(int idRecurso) throws SQLException{
        ResultadoOperacion prestamosBD = new ResultadoOperacion();
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        prestamosBD.setError(true);
        
        if(conexionBD != null){
            try{
                String consulta = "UPDATE Prestamo SET Prestamo.fechaEntrega = DATE_ADD(Prestamo.fechaEntrega, INTERVAL 7 DAY) "
                        + "WHERE Prestamo.idRecurso = ?";
                
                PreparedStatement consultaPrestamo = conexionBD.prepareStatement(consulta);
                consultaPrestamo.setInt(1, idRecurso);
                int numeroFilas = consultaPrestamo.executeUpdate();
                
                //¿La consulta fue exitosa?
                if(numeroFilas > 0){
                    prestamosBD.setError(false);
                }else{
                    prestamosBD.setMensaje("No se puede renovar el préstamo");
                }
            }catch(SQLException | NullPointerException e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            prestamosBD.setMensaje("Falló la conexión con la base de datos.\nInténtelo más tarde");
        }
        return prestamosBD;
    }
    
    public static java.sql.Date obtenerFechaEntrega(int idRecurso) throws SQLException{
        java.sql.Date fechaNuevaEntrega = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT Prestamo.fechaEntrega FROM Prestamo INNER JOIN RecursoDocumental ON "
                        + "Prestamo.idRecurso = RecursoDocumental.id WHERE RecursoDocumental.id = ?";
                
                PreparedStatement consultaPrestamo = conexionBD.prepareStatement(consulta);
                consultaPrestamo.setInt(1, idRecurso);
                ResultSet resultadoConsulta = consultaPrestamo.executeQuery();
                
                while(resultadoConsulta.next()){
                    fechaNuevaEntrega = resultadoConsulta.getDate("fechaEntrega");
                }
            }catch(SQLException | NullPointerException e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Falló la conexión con la base de datos.\nInténtelo más tarde", 
                    Alert.AlertType.ERROR);
        }
        return fechaNuevaEntrega;
    }
    
    public static ResultadoOperacion registrarPrestamoADomicilio(UsuarioBiblioteca usuario, int idRecurso) throws SQLException{
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        if(conexionBD != null){
            try {

                String consulta = "INSERT INTO prestamo (fechaInicio, fechaEntrega, destino, origen, tipoPrestamo, "
                        + "idRecurso, idUsuarioBiblioteca) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String consulta2 = "UPDATE RecursoDocumental SET Estado = 'Prestado' WHERE id = ?";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                LocalDate fechaInicioLD = LocalDate.now();
                String fechaInicioString = fechaInicioLD.toString();
                LocalDate fechaEntregaLD = fechaInicioLD.plusDays(7);
                String fechaEntregaString = fechaEntregaLD.toString();
                prepararSentencia.setString(1, fechaInicioString);
                prepararSentencia.setString(2, fechaEntregaString);
                prepararSentencia.setString(3, usuario.getDomicilio());
                prepararSentencia.setString(4, "Facultad de Economía, Estadística e Informática");
                prepararSentencia.setString(5, "A domicilio");
                prepararSentencia.setInt(6, idRecurso);
                prepararSentencia.setInt(7, usuario.getId());
                int filasAfectadas = prepararSentencia.executeUpdate();
                
                PreparedStatement consultaPrestamo2 = conexionBD.prepareStatement(consulta2);
                consultaPrestamo2.setInt(1, idRecurso);
                int numeroFilas2 = consultaPrestamo2.executeUpdate();
                
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(filasAfectadas);
                }
                
                if(numeroFilas2 > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Recurso documental disponible");
                }else{
                    respuesta.setMensaje("No se pudo cambiar el estado del recurso");
                }
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
          return respuesta;  
    }
    
    public static ArrayList<Prestamo> obtenerPrestamosPorIdUsuario(int idUsuario) throws SQLException{
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        ArrayList<Prestamo> prestamosBD = null;
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM prestamo WHERE idUsuarioBiblioteca = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultadoConsulta = prepararSentencia.executeQuery();
                prestamosBD = new ArrayList<>();

                while(resultadoConsulta.next()){
                    Prestamo prestamoTemporal = new Prestamo();
                    prestamoTemporal.setId(resultadoConsulta.getInt("id"));
                    prestamosBD.add(prestamoTemporal);
                }
            
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }       
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexión", "No hay conexión con la base de datos.", Alert.AlertType.ERROR);
        }
         
        return prestamosBD;
    }
    
    public static ResultadoOperacion registrarPrestamoInterbibliotecario(Prestamo prestamo) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO prestamo(fechaInicio, fechaEntrega, destino, origen, tipoPrestamo, idRecurso, idUsuarioBiblioteca) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setDate(1, prestamo.getFechaInicio());
                prepararSentencia.setDate(2, prestamo.getFechaEntrega());
                prepararSentencia.setString(3,prestamo.getDestino());
                prepararSentencia.setString(4, prestamo.getOrigen());
                prepararSentencia.setString(5, prestamo.getTipoPrestamo());
                prepararSentencia.setInt(6, prestamo.getIdRecurso());
                prepararSentencia.setInt(7, prestamo.getIdUsuarioBiblioteca());
                
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
