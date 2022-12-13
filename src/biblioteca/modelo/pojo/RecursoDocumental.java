package biblioteca.modelo.pojo;

/**
 * @autor Froylan De Jesus Alvarez Rodriguez
 * @Fecha 11/12/2022
 */
public class RecursoDocumental {
    private int idRecurso;
    private String nombre;
    private String autor;
    private String descripcion;
    private String seccion;
    private String editorial;
    private double isbn;
    private double peso;
    private int duracion;
    private int idBiblioteca;

    public RecursoDocumental() {
    }

    public RecursoDocumental(int idRecurso, String nombre, String autor, String descripcion, String seccion, String editorial, double isbn, double peso, int duracion, int idBiblioteca) {
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

    public double getIsbn() {
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

    public void setIsbn(double isbn) {
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
}
