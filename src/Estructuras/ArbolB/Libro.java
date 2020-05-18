package Estructuras.ArbolB;

/**
 *
 * @author ronald
 */

public class Libro 
{
    public int isbn;
    public String titulo;
    public String Autor;
    public String editorial;
    public int anio;
    public String edicion;
    public String categoria;
    public String idioma;
    public int carnet_usuario;

    public Libro(int isbn, String titulo, String Autor, String editorial, int anio, String edicion, String categoria, String idioma, int carnet_usuario) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.Autor = Autor;
        this.editorial = editorial;
        this.anio = anio;
        this.edicion = edicion;
        this.categoria = categoria;
        this.idioma = idioma;
        this.carnet_usuario = carnet_usuario;
    }
    
    
    
}
