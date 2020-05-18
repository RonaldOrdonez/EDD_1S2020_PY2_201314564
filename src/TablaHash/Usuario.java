package TablaHash;

/**
 *
 * @author ronald
 */

public class Usuario 
{
    public int numero_carnet;
    public String nombre;
    public String apellido;
    public String carrera;
    public String contrasenia;
    public Usuario siguiente;

    public Usuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia) 
    {
        this.numero_carnet = numero_carnet;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.contrasenia = contrasenia;
        this.siguiente = null;
    }
    
    
    

    
    
    
    
    
}
