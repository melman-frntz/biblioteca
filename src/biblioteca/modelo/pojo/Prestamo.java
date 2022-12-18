package biblioteca.modelo.pojo;

/*
    @author Jesús Enrique Fernández González
    Fecha creación: 10/12/2022
    Fecha de última modificación: 16/12/2022
    Último modificador: Jesús Enrique Fernández González
*/

import java.sql.Date;

public class Prestamo {
    private int id;
    private Date fechaInicio;
    private Date fechaEntrega;
    private String destino;
    private String origen;
    private String tipoPrestamo;
    private int vecesPrestado;
    private int idRecurso;
    private String idUsuarioBiblioteca;

    public Prestamo() {
    }
    
    public Prestamo(int id, Date fechaInicio, Date fechaEntrega, String destino, String origen, String tipoPrestamo, int vecesPrestado, int idRecurso, String idUsuarioBiblioteca) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.destino = destino;
        this.origen = origen;
        this.tipoPrestamo = tipoPrestamo;
        this.vecesPrestado = vecesPrestado;
        this.idRecurso = idRecurso;
        this.idUsuarioBiblioteca = idUsuarioBiblioteca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public int getVecesPrestado() {
        return vecesPrestado;
    }

    public void setVecesPrestado(int vecesPrestado) {
        this.vecesPrestado = vecesPrestado;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getIdUsuarioBiblioteca() {
        return idUsuarioBiblioteca;
    }

    public void setIdUsuarioBiblioteca(String idUsuarioBiblioteca) {
        this.idUsuarioBiblioteca = idUsuarioBiblioteca;
    }
    
    public String toString(){
        return ""+fechaEntrega;
    }
}
