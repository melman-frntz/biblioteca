package biblioteca.modelo.dao;

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
                
                PreparedStatement consultaPrestamo = conexionBD.prepareStatement(consulta);
                consultaPrestamo.setInt(1, idRecurso);
                int numeroFilas = consultaPrestamo.executeUpdate();
                
                //¿La consulta fue exitosa?
                if(numeroFilas > 0){
                    prestamosBD.setError(false);
                    prestamosBD.setMensaje("Préstamo eliminado");
                }else{
                    prestamosBD.setMensaje("No se pudo eliminar el préstamo");
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
    
    public static ArrayList<Prestamo> obtenerFechaEntrega(int idRecurso) throws SQLException{
        ArrayList<Prestamo> prestamosBD = null;
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        
        if(conexionBD != null){
            try{
                String consulta = "SELECT Prestamo.fechaEntrega FROM Prestamo INNER JOIN RecursoDocumental ON "
                        + "Prestamo.idRecurso = RecursoDocumental.id WHERE RecursoDocumental.id = ?";
                
                PreparedStatement consultaPrestamo = conexionBD.prepareStatement(consulta);
                consultaPrestamo.setInt(1, idRecurso);
                ResultSet resultadoConsulta = consultaPrestamo.executeQuery();
                prestamosBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Prestamo temp = new Prestamo();
                    temp.setFechaEntrega(resultadoConsulta.getDate("fechaEntrega"));
                    prestamosBD.add(temp);
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
    
    public static ResultadoOperacion registrarPrestamoADomicilio(UsuarioBiblioteca usuario, int idRecurso) throws SQLException{
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        if(conexionBD != null){
            try {

                String consulta = "INSERT INTO prestamo (fechaInicio, fechaEntrega, destino, origen, tipoPrestamo, "
                        + "idRecurso, idUsuarioBiblioteca) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
                System.out.println("INSERTANDO ID RECURSO");
                prepararSentencia.setInt(6, idRecurso);
                System.out.println("ID RECURSO INSERTADO");
                prepararSentencia.setString(7, usuario.getIdUsuarioBiblioteca());
                System.out.println(idRecurso);
                int filasAfectadas = prepararSentencia.executeUpdate();
                System.out.println("UPDATE HECHO");
                System.out.println(filasAfectadas);
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(filasAfectadas);
                }
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
          return respuesta;  
    }
    
    public static ArrayList<Prestamo> obtenerPrestamosPorIdUsuario(String idUsuario) throws SQLException{
        Connection conexionBD = ConexionBaseDatos.abrirConexionBaseDatos();
        ArrayList<Prestamo> prestamosBD = null;
        try {
            String consulta = "SELECT * FROM prestamo WHERE idUsuarioBiblioteca = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setString(1, idUsuario);
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
                prepararSentencia.setString(7, prestamo.getIdUsuarioBiblioteca());
                
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
