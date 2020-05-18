
package TablaHash;

/**
 *
 * @author ronald
 */

public class T_Hash 
{    
    public int TAMANIO_TABLA = 7;
    ListaSimple tabla[] = new ListaSimple[TAMANIO_TABLA];

    public T_Hash() 
    {        
        for (int i = 0; i < TAMANIO_TABLA; i++) 
        {
            tabla[i] = new ListaSimple();
        }
    }
    
    public void insertarUsuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia)
    {
        int clave = funcionHash(numero_carnet);
        tabla[clave].agregarAlInicio(numero_carnet, nombre, apellido, carrera, contrasenia);       
    }
    
    public int funcionHash(int carnet)
    {
        return (carnet%TAMANIO_TABLA);
    }
    
     
    public void eliminarUsuario(int numero_carnet)
    {
        int clave = funcionHash(numero_carnet);                                   
        if (tabla[clave].esVacia()) 
        {
            System.out.println("LA CLAVE DE LA TABLA AUN NO TIENE ESE VALOR");
            
        }
        else
        {
            if(tabla[clave].buscar(numero_carnet))
            {
                tabla[clave].eliminarUsuario(numero_carnet);      
                System.out.println("ELIMINADO CON EXITO");
            }
            else
            {
                System.out.println("No existe numero de carnet almacenado en esa clave");
            }    
            
        }
                   
    }    
    
    public void editarInformacionUsuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia)
    {
        int clave = funcionHash(numero_carnet);                                   
        if (tabla[clave].esVacia()) 
        {
            System.out.println("LA CLAVE DE LA TABLA AUN NO TIENE ESE VALOR");
            
        }
        else
        {
            if(tabla[clave].buscar(numero_carnet))
            {
                tabla[clave].editarInformacionDeUsuario(numero_carnet,nombre,apellido,carrera,contrasenia);      
                System.out.println("ACTUALIZADO CON EXITO");
            }
            else
            {
                System.out.println("No existe numero de carnet almacenado en esa clave");
            }    
            
        }
    }
    
    public void recorrer()
    {
        for (int i = 0; i < TAMANIO_TABLA; i++) 
        {
            System.out.print(i+" -> ");
            if(!tabla[i].esVacia())
            {
                tabla[i].recorrerLista();
            }
            System.out.println("");            
        }        
    }    
    
}
