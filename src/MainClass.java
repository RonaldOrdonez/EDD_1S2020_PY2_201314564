
import GUI.Administrador;
import GUI.IniciarSesion;
import javax.swing.JFrame;

/**
 *
 * @author ronald
 */

public class MainClass 
{
    
    
    public static void main(String[] args)
    {       
        
        //PANTALLA PARA INICIAR SESION "IniciarSesion.java"        
        IniciarSesion ini = new IniciarSesion();
        ini.setVisible(true);
        ini.setLocationRelativeTo(null);
        ini.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
        
        //PANTALLA DE INICIO DE ADMINISTRADOR "Administrador.java"
       /* Administrador adm = new Administrador();
        adm.setVisible(true);
        adm.setLocationRelativeTo(null);
        adm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    }
    
    //****************************************************************************************************
    //PARA LA CONTRASEÑA QUE VA A SER ENCRIPTADA
    //****************************************************************************************************

          
        
        
        /*//FUNCIONAMIENTO DE LA TABLA HASH
         //FUNCIONAMIENTO DE LA TABLA HASH
        T_Hash tablahash = new T_Hash();        
        tablahash.insertarUsuario(15, "júan", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(11, "maria", "martines", "siste,as", "as34");
        tablahash.insertarUsuario(27, "felipe", "gomez", "quimico", "1234345ffcsd2");
        tablahash.insertarUsuario(8, "zara", "perez", "mecanica", "qwezczx33");
        tablahash.insertarUsuario(12, "manu", "coles", "industrial", "zfdf");
        tablahash.insertarUsuario(10, "coral", "sebas", "ambiental", "1234tyui");        
        tablahash.insertarUsuario(17, "jose", "ramirez", "sistemas", "1234tyui"); 
        //tablahash.insertarUsuario(7, "VICENTE", "lopez", "industrial", "1234");
        tablahash.recorrer();
        tablahash.graficarTablaHash();
        //System.out.println("");
        //tablahash.editarInformacionUsuario(11, "maria eunice", "Galvez", "Sistemas", "1234");                
        //tablahash.eliminarUsuario(15);
        //System.out.println("");
        //tablahash.recorrer();*/
        
        
        
        
        
        /*PRUEBAS PARA EL ARBOL AVL EN CATEGORIAS
        SIN TENER AUN NUNA INSTANCIA DE ARBOL B EN ELLA*/
       
        /*ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertarCategoria("dibujo");
        arbol.insertarCategoria("esgrima");
        arbol.insertarCategoria("fisica");
        arbol.insertarCategoria("algebra");        
        arbol.insertarCategoria("biologia");
        arbol.insertarCategoria("contabilidad");
        arbol.insertarCategoria("matematica");        
        arbol.insertarCategoria("logica");        
        arbol.insertarCategoria("quimica");               
        arbol.insertarCategoria("estadistica");     
        arbol.insertarCategoria("deportes"); 
        arbol.eliminarCategoria("matematica");
        arbol.graficar();*/
        //arbol.eliminarCategoria("logica");
        //arbol.graficar();
        
        //System.out.println("");
        //arbol.graficar();
//        arbol.eliminarCategoria("fisica");
//        System.out.println("despues de eliminar fisica");
//        arbol.inorden();
//        
   
}
