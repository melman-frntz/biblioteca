package biblioteca.modelo.pojo;

/**
 *
 * @author franz
 */
public class Campus {
    
    private int idCampus;
    private String clave;
    private String nombre;
    private String zona;

    public Campus() {
    }
    
    public Campus(int idCampus, String clave, String nombre, String zona) {
        this.idCampus = idCampus;
        this.clave = clave;
        this.nombre = nombre;
        this.zona = zona;
    }

    public int getIdCampus() {
        return idCampus;
    }

    public void setIdCampus(int idCampus) {
        this.idCampus = idCampus;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
    
    @Override
    public String toString() {
        return "-" + nombre;
    }
}
