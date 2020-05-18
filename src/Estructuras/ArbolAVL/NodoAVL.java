package Estructuras.ArbolAVL;

/**
 *
 * @author ronald
 */

public class NodoAVL 
{
    public String categoria;
    public int altura; //puede tomar los valores de equilibrio -1, 0, 1
    public NodoAVL ramaIzq;
    public NodoAVL ramaDer;
    
    //contructor que recibe un solo la categoria
    public NodoAVL(String categoria) 
    {
        this.categoria = categoria;
        this.altura = 1;
        this.ramaDer = null;
        this.ramaIzq = null;
    }   
}
