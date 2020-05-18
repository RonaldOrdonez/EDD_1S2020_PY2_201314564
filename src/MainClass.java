
import Estructuras.ArbolAVL.ArbolAVL;
import GUI.IniciarSesion;
import TablaHash.T_Hash;
import java.text.Collator;
import java.util.Locale;
import javax.swing.JFrame;


/**
 *
 * @author ronald
 */

public class MainClass {


    public static void main(String[] args) 
    {
        
        /*//FUNCIONAMIENTO DE LA TABLA HASH
        T_Hash tablahash = new T_Hash();        
        tablahash.insertarUsuario(15, "juan", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(11, "maria", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(27, "felipe", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(8, "zara", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(12, "manu", "lopez", "industrial", "1234");
        tablahash.insertarUsuario(10, "coral", "lopez", "industrial", "1234");        
        //tablahash.insertarUsuario(7, "VICENTE", "lopez", "industrial", "1234");
        tablahash.recorrer();
        System.out.println("");
        tablahash.editarInformacionUsuario(11, "maria eunice", "Galvez", "Sistemas", "1234");                
        //tablahash.eliminarUsuario(15);
        System.out.println("");
        tablahash.recorrer();*/
        
        
        //PARA INICIAR EN EL MENU DE INICIAR SESION
        /*
        IniciarSesion ini = new IniciarSesion();
        ini.setVisible(true);
        ini.setLocationRelativeTo(null);
        ini.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
        
        
        
        
        /*PRUEBAS PARA EL ARBOL AVL EN CATEGORIAS
        SIN TENER AUN NUNA INSTANCIA DE ARBOL B EN ELLA*/
        ArbolAVL arbol = new ArbolAVL();
        
        arbol.insertarCategoria("dibujo");
        arbol.insertarCategoria("esgrima");
        arbol.insertarCategoria("fisica");
        arbol.insertarCategoria("algebra");        
        arbol.insertarCategoria("biologia");
        arbol.insertarCategoria("contabilidad");
        arbol.insertarCategoria("matematica");        
        arbol.insertarCategoria("logica");        
        arbol.insertarCategoria("quimica");        
        arbol.inorden();
        System.out.println("");
        arbol.graficar();
//        arbol.eliminarCategoria("fisica");
//        System.out.println("despues de eliminar fisica");
//        arbol.inorden();
//        
    } 

}
