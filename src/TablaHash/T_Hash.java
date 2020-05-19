
package TablaHash;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ronald
 */

public class T_Hash 
{    
    public int TAMANIO_TABLA = 45;
    ListaSimple tabla[] = new ListaSimple[TAMANIO_TABLA];

    
    // CONSTRUCTURO DE LA CLASE HASH
    public T_Hash() 
    {        
        for (int i = 0; i < TAMANIO_TABLA; i++) 
        {
            tabla[i] = new ListaSimple();
        }
    }
    
    //INSERTAR UN NUEVO USUARIO A LA TABLA HASH
    public boolean insertarUsuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia)
    {
        boolean agregado;
        int clave = funcionHash(numero_carnet);
        
        if (tabla[clave].esVacia()) 
        {
            //se puede insertar xq esta vacia esa clave de la tabla    
            String passCifrada = cifrarContraseniaMd5(contrasenia);
            tabla[clave].agregarAlInicio(numero_carnet, nombre, apellido, carrera, passCifrada);               
            agregado = true;
        }
        else
        {
            if(tabla[clave].buscar(numero_carnet))
            {
                //no puedo ingresar ese carnet porque ya existe en esa lista de esa clave        
                agregado = false;                
            }
            else //de lo contrario en esa lista no existe el carnet y lo puedo ingresar
            {
                //si puedo insertar ese carnet
                String passCifrada = cifrarContraseniaMd5(contrasenia);
                tabla[clave].agregarAlInicio(numero_carnet, nombre, apellido, carrera, passCifrada);                               
                agregado = true;
            }                
        }
        return agregado;            
    }
    
    public Usuario buscar(int carnet) throws Exception
    {
        Usuario respuesta;
        int clave = funcionHash(carnet);        
        
        if (tabla[clave].esVacia()) 
        {
            //la clave no tiene nada aun
            respuesta = null;            
        }
        else
        {
            if(tabla[clave].buscar(carnet))
            {
                //s eecnontro el carnet en esa clave
                respuesta=tabla[clave].buscarUser(carnet);  
                String desencriptarpass = desencriptar(respuesta.contrasenia);
                respuesta.contrasenia=desencriptarpass;
                
            }
            else //de lo contrario en esa lista no existe el carnet 
            {
                respuesta=null;
            }                
        }
        return respuesta;        
    }
            
    
    //FUNCION HASH QUE NOS DEVUELVE UNA CLAVE DE LA TABLA
    public int funcionHash(int carnet)
    {
        return (carnet%TAMANIO_TABLA);
    }
    
    //ELIMINAR UN USUARIO DE LA TABLA HASH     
    public boolean eliminarUsuario(int numero_carnet)
    {
        boolean eliminado;
        int clave = funcionHash(numero_carnet);                                   
        if (tabla[clave].esVacia()) 
        {
            eliminado = false;
            //System.out.println("LA CLAVE DE LA TABLA AUN NO TIENE ESE VALOR");            
        }
        else
        {
            if(tabla[clave].buscar(numero_carnet))
            {
                tabla[clave].eliminarUsuario(numero_carnet);      
                eliminado = true;
                //System.out.println("ELIMINADO CON EXITO");
            }
            else
            {
                //System.out.println("No existe numero de carnet almacenado en esa clave");
                eliminado = false;
            }    
            
        }
        return eliminado;                   
    }    
    
    //EDITAR LA INFORMACION DE UN USUARIO YA INGRESADO
    public boolean editarInformacionUsuario(int numero_carnet, String nombre, String apellido, String carrera, String contrasenia)
    {
        boolean editado;
        int clave = funcionHash(numero_carnet);                                   
        if (tabla[clave].esVacia()) 
        {
            //System.out.println("LA CLAVE DE LA TABLA AUN NO TIENE ESE VALOR");
            editado=false;            
        }
        else
        {
            if(tabla[clave].buscar(numero_carnet))
            {
                String passCifrada = cifrarContraseniaMd5(contrasenia);
                tabla[clave].editarInformacionDeUsuario(numero_carnet,nombre,apellido,carrera,passCifrada);      
                //System.out.println("ACTUALIZADO CON EXITO");
                editado=true;
            }
            else
            {
                //System.out.println("No existe numero de carnet almacenado en esa clave");
                editado=false;
            }                
        }
        return editado;
    }
    
    //GRAFICAR LA TABLA HASH EN UNA IMAGEN JPG
    public void graficarTablaHash()
    {
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("tablaHash.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoTablaHash());            
            fichero.close();
            try
            {
                Runtime rt = Runtime.getRuntime();
                rt.exec( "dot -Tjpg -o tablaHash.jpg tablaHash.dot");
                //Esperamos medio segundo para dar tiempo a que la imagen se genere.
                //Para que no sucedan errores en caso de que se decidan graficar varios
                //árboles sucesivamente.
                Thread.sleep(500);
            } 
            catch (Exception ex) 
            {
            System.err.println("Error al generar la imagen para el archivo tablaHash.dot");
            }  
        } 
        catch (Exception e)
        {
            System.err.println("Error al escribir el archivo tablaHash.dot");
        }   
    }
    
    private String getCodigoTablaHash()
    {
        String script_hash = "";
        script_hash += "digraph G {\n nodesep=.05;\n rankdir=LR;\n node[shape=record];\n";
        script_hash +="node0[label=\"";
        
        for (int i = 0; i < TAMANIO_TABLA; i++) 
        {
            if(i==TAMANIO_TABLA-1)
            {
                script_hash += "<f";
                script_hash += i + ">" +i;                            
            }
            else
            {
                script_hash += "<f";
                script_hash += i + ">" +i +"|";                            
            }            
        }       
        script_hash +="\", height=25]; \n node[width=1.5];\n";       
        
        for (int i = 0; i < TAMANIO_TABLA; i++) 
        {
            int a = i+1;
           
            if(!tabla[i].esVacia())
            {
                script_hash += "node"+Integer.toString(a)+"[label=\"{<n> ";                
                Usuario aux = tabla[i].primero;
                while (aux.siguiente != null)
                {
                    script_hash += Integer.toString(aux.numero_carnet)+"-";
                    script_hash += aux.nombre+"-";                 
                    script_hash += aux.apellido+"-";
                    script_hash += aux.carrera+"\\"+"n";
                    script_hash += aux.contrasenia+"|";                    
                    aux = aux.siguiente;
                }
                script_hash += Integer.toString(aux.numero_carnet)+"-";
                script_hash += aux.nombre+"-";                 
                script_hash += aux.apellido+"-";
                script_hash += aux.carrera+"\\"+"n";
                script_hash += aux.contrasenia;     
                script_hash += "}\"];\n";                
                script_hash += "node0:f"+Integer.toString(i)+"->"+"node"+Integer.toString(a)+":n;\n";                
            }
            else
            {
                script_hash +="nodenull"+Integer.toString(i)+"[label=\"null\"];\n";
                script_hash += "node0:f"+Integer.toString(i)+"->nodenull"+Integer.toString(i)+";\n";
                
                
            }
            
        }
        script_hash += "}";
        return script_hash;
    }          
    
    
    //METODO PARA RECORRER Y MOSTRAR EN CONSOLA LA TABLA HASH    
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
    
    //METODO PARA CIFRAR CONTRASEÑA CON MD5
    public static String cifrarContraseniaMd5(String pass) 
    {
        String secretKey = "ronaldEDD"; //llave para encriptar datos    
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = pass.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
        }
        catch (Exception ex) 
        {
        }
        return base64EncryptedString;
    }  
    
    
    //METODO PARA DESENCRIPTAR UNA CONTRASEÑA
    public static String desencriptar(String textoEncriptado) throws Exception 
    {
        String secretKey = "ronaldEDD"; //llave para desenciptar datos
        String base64EncryptedString = "";
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            base64EncryptedString = new String(plainText, "UTF-8");
        }
        catch (Exception ex) 
        {
        }
        return base64EncryptedString;
    }
    
    
    
    /*
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException 
    {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));        
        
        String s = "ronald1234"; 
        String passMD5 = getMd5(s);
        
        System.out.println("Ingrese su Carnet");//Se pide un dato al usuario
        String carnet_entrada = entrada.readLine();
        
        int carnet = Integer.parseInt(carnet_entrada);
        
        if(carnet==201314564)
        {
            System.out.println("Ingrese su Contraseña");//Se pide un dato al usuario
            String pass_entrada = entrada.readLine();
            String convert= getMd5(pass_entrada);
            if(convert.equalsIgnoreCase(passMD5))
            {
                System.out.println("USUARIO VALIDO");                           
            }
            else
            {
                System.out.println("CONTRASEÑA NO VALIDA");                      
            }
            
        }
        else
        {
            System.out.println("Carnet invalido");
        }           
        
    } */
            
    
    
}
