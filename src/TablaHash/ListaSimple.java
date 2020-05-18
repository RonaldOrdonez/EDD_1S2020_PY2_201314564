package TablaHash;

/**
 *
 * @author ronald
 */

public class ListaSimple 
{
    public Usuario primero;
    public int tamanio;

    public ListaSimple() 
    {
        primero=null;
        tamanio = 0;
    }
    
    public int obtenerTamanio()
    {
        return tamanio;
    }
    
    public boolean esVacia()
    {
        return primero==null;
    }
    
    public void agregarAlInicio(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia)
    {        
        Usuario nuevo = new Usuario(numero_carnet, nombre, apellido, carrera, contrasenia);                
        
        if (esVacia()) 
        {            
            primero = nuevo;
        
        }
        else
        {
            nuevo.siguiente = primero;
            primero = nuevo;            
        }        
        tamanio++;
    }
    
    public boolean buscar(int carnet){        
        
        Usuario aux = primero;
        
        // Bandera para indicar si el valor existe.
        boolean encontrado = false;
        
        // Recorre la lista hasta encontrar el elemento o hasta 
        // llegar al final de la lista.
        while(aux != null && encontrado != true)
        {
            // Consulta si el valor del nodo es igual al de referencia.
            if (carnet == aux.numero_carnet)
            {
                // Canbia el valor de la bandera.
                encontrado = true;
            }
            else
            {
                // Avanza al siguiente. nodo.
                aux = aux.siguiente;
            }
        }
        // Retorna el resultado de la bandera.
        return encontrado;
    }
    
    public void editarInformacionDeUsuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia){
        
        // Consulta si el valor existe en la lista
        if (buscar(numero_carnet)) 
        {
            // Crea ua copia de la lista.
            Usuario aux = primero;
            
            // Recorre la lista hasta llegar al nodo que tenga el numero de carnet.
            while(aux.numero_carnet != numero_carnet)
            {
                aux = aux.siguiente;
            }
            // Actualizamos el valor del nodo
            aux.nombre = nombre;
            aux.apellido = apellido;
            aux.carrera = carrera;
            aux.contrasenia = contrasenia;
        }
    }
    
    
     public void eliminarUsuario(int numero_carnet){
        
        // Consulta si el valor de referencia existe en la lista.
        if (buscar(numero_carnet)) 
        {            
            // Consulta si el nodo a eliminar es el pirmero
            if (primero.numero_carnet == numero_carnet) 
            {
                // El primer nodo apunta al siguiente.
                primero = primero.siguiente;
            }
            else
            {
                // Crea ua copia de la lista.
                Usuario aux = primero;
                // Recorre la lista hasta llegar al nodo anterior 
                // al de referencia.
                while(aux.siguiente.numero_carnet != numero_carnet)
                {
                    aux = aux.siguiente;
                }
                // Guarda el nodo siguiente del nodo a eliminar.
                Usuario siguiente = aux.siguiente.siguiente;
                // Enlaza el nodo anterior al de eliminar con el 
                // sguiente despues de el.
                aux.siguiente = siguiente;                       
            }
            // Disminuye el contador de tamaño de la lista.
            tamanio--;
        }
    }
     
    public void recorrerLista()
    {
        // Verifica si la lista contiene elementoa.
        if (!esVacia()) 
        {
            // Crea una copia de la lista.
            Usuario aux = primero;          
            
            // Recorre la lista hasta el final.
            while(aux.siguiente!= null)
            {
                // Imprime en pantalla el valor del nodo.
                System.out.print("("+aux.numero_carnet+","+aux.nombre+","+aux.apellido+","+aux.carrera+") -> ");
                // Avanza al siguiente nodo.
                aux = aux.siguiente;                
            }
             System.out.print("("+aux.numero_carnet+","+aux.nombre+","+aux.apellido+","+aux.carrera+")");            
        }   
    }    
    
}
