package biblioteca.modelo.pojo;

/*
*@author froylan
*Fecha creacion: 10/12/2022
*Fecha de ultima modificacion: 20/12/2022
*Ultimo modificador: franz
*/

public class RecursoDocumental {
    private int idRecurso;
    private String nombre;
    private String autor;
    private String descripcion;
    private String seccion;
    private String editorial;
    private String isbn;
    private double peso;
    private int duracion;
    private int idBiblioteca;
    private String estado;
    private String folio;
    private String procedencia;
    private String tipoRecurso;

    public RecursoDocumental() {
    }

    public RecursoDocumental(int idRecurso, String nombre, String autor, String descripcion, String seccion, String editorial, String isbn, double peso, int duracion, int idBiblioteca, String estado, String folio, String procedencia, String tipoRecurso) {
        this.idRecurso = idRecurso;
        this.nombre = nombre;
        this.autor = autor;
        this.descripcion = descripcion;
        this.seccion = seccion;
        this.editorial = editorial;
        this.isbn = isbn;
        this.peso = peso;
        this.duracion = duracion;
        this.idBiblioteca = idBiblioteca;
        this.estado = estado;
        this.folio = folio;
        this.procedencia = procedencia;
        this.tipoRecurso = tipoRecurso;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPeso() {
        return peso;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public String getEstado() {
        return estado;
    }

    public String getFolio() {
        return folio;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
}
