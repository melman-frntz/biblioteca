package biblioteca.modelo.pojo;

/*
    @author Jesús Enrique Fernández González
    Fecha creación: 10/12/2022
    Fecha de última modificación: 11/12/2022
    Último modificador: Jesús Enrique Fernández González
*/

public class UsuarioBiblioteca {
    private String idUsuarioBiblioteca;
    private String nombre;
    private String genero;
    private String correo;
    private String domicilio;
    private String telefono;
    private String periodo;
    private String tipoUsuario;
    private String carrera;

    public UsuarioBiblioteca() {
    }
    
    public UsuarioBiblioteca(String idUsuarioBiblioteca, String nombre, String genero, String correo, String domicilio, String telefono, String periodo, String tipoUsuario, String carrera) {
        this.idUsuarioBiblioteca = idUsuarioBiblioteca;
        this.nombre = nombre;
        this.genero = genero;
        this.correo = correo;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.periodo = periodo;
        this.tipoUsuario = tipoUsuario;
        this.carrera = carrera;
    }

    public String getIdUsuarioBiblioteca() {
        return idUsuarioBiblioteca;
    }

    public void setIdUsuarioBiblioteca(String idUsuarioBiblioteca) {
        this.idUsuarioBiblioteca = idUsuarioBiblioteca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
}