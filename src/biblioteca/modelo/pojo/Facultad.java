package biblioteca.modelo.pojo;

/**
 *
 * @author franz
 */
public class Facultad {
    private int idFacultad;
    private String clave;
    private String nombre;
    private int idCampus;

    public Facultad() {
    }
    
    public Facultad(int idFacultad, String clave, String nombre, int idCampus) {
        this.idFacultad = idFacultad;
        this.clave = clave;
        this.nombre = nombre;
        this.idCampus = idCampus;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCampus() {
        return idCampus;
    }

    public void setIdCampus(int idCampus) {
        this.idCampus = idCampus;
    }

    @Override
    public String toString() {
        return "(" + clave + ")" + nombre;
    }
}
