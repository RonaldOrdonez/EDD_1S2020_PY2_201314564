package Estructuras.ArbolAVL;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.Collator;

/**
 *
 * @author ronald
 */
public class ArbolAVL 
{
    public NodoAVL raiz;    
    private String recorreAVL;  
    private int numNodos;
    private int countVisit;
    
    //constructor que inicializa su raiz con null
    public ArbolAVL() 
    {
        raiz=null;
        recorreAVL="";
        numNodos=0;
        countVisit=0;
    }

    //obtiene el nodo raiz del arbol
    public NodoAVL obtenerRaiz()
    {
        return raiz;
    }
    
    //verifica si el arbol esta vacio o no
    public boolean estaVacio()
    {
        return raiz==null;
    }
    
    //metodo para obtener la altura de un nodo
    public int alturaNodo(NodoAVL n)
    {
        if(n==null)
            return 0;        
        return n.altura;                   
    }
    
    //metodo para ver que categoria va antes o despues de la raiz
    public int comparaAlturas(int a, int b)
    {
        return (a>b)?a:b;
    }
    
    //rotacion derecha
    public NodoAVL rotacionDerecha(NodoAVL y)
    {
        NodoAVL x = y.ramaIzq;
        NodoAVL T2 = x.ramaDer;
        
        //realizando la rotacion
        x.ramaDer=y;
        y.ramaIzq=T2;
        
        //actualizar alturas        
        y.altura = comparaAlturas (alturaNodo(y.ramaIzq), alturaNodo(y.ramaDer))+1;
        x.altura = comparaAlturas (alturaNodo(x.ramaIzq), alturaNodo(x.ramaDer))+1;
        
        //retornar la nueva raiz
        return x;       
    }
    
    //rotacion izquierda
    public NodoAVL rotacionIzquierda(NodoAVL x)
    {
        NodoAVL y = x.ramaDer;
        NodoAVL T2 = y.ramaIzq;
        
        //realizando la rotacion
        y.ramaIzq = x;
        x.ramaDer = T2;
        
        //actualizar alturas        
        x.altura = comparaAlturas (alturaNodo(x.ramaIzq), alturaNodo(x.ramaDer))+1;
        y.altura = comparaAlturas (alturaNodo(y.ramaIzq), alturaNodo(y.ramaDer))+1;
        
        //retornar la nueva raiz
        return y;         
    } 
    
    // obtener el factor de balance de un nodo 
    public int obtenerBalanceo(NodoAVL nodo) 
    { 
        if (nodo == null) 
            return 0; 
  
        return alturaNodo(nodo.ramaIzq) - alturaNodo(nodo.ramaDer); 
    } 
    
       
    //insertar una categoria en el arbol
    public void insertarCategoria(String categoria)
    {
        raiz=insertarCategoria(raiz, categoria);
    }
    
    //compara los string de las categorias    
    private int compararCategorias(String categoria_raiz, String categoria_nueva)
    {
        String cat_new = categoria_nueva; //CATEGORIA NUEVA
        String cat_root = categoria_raiz; //CATEGORIA RAIZ
        Collator comparador =  Collator.getInstance();
        
        // Para no distinguir entre mayusculas, minusculas y letras con acentos.
        comparador.setStrength(Collator.PRIMARY);
        
        if (comparador.compare(cat_new, cat_root) == 0)
        {
            //System.out.println("Las cadenas son iguales");
            return 0;
        }
        else
        {
            if (comparador.compare(cat_new, cat_root) < 0)
            {
                return 1;
                //System.out.println(cat_new+" va antes de "+cat_root);
            }
            else
            {
                return 2;
                //System.out.println(cat_new+" va despues de "+cat_root);
            }
        }
    }
    
    
    //metodo recursivo de insercion de una categoria
    private NodoAVL insertarCategoria(NodoAVL raiz_, String categoria)
    {
        //1) realizar una insercion normal en un BST
        if (raiz_==null) 
        {
            return (new NodoAVL(categoria)); //crea un nuevo raiz_ si no hay nada creado aun
        }        
        else if (compararCategorias(raiz_.categoria, categoria)==1)
        {
            raiz_.ramaIzq = insertarCategoria(raiz_.ramaIzq, categoria);            
        }           
        else if(compararCategorias(raiz_.categoria, categoria)==2)
        {
            raiz_.ramaDer = insertarCategoria(raiz_.ramaDer, categoria);
        }        
        else //la categoria ya ha sido incluida
            return raiz_;
        
        //2) actualizar altura de este raiz_ ancestro
        raiz_.altura = 1 + comparaAlturas(alturaNodo(raiz_.ramaIzq), alturaNodo(raiz_.ramaDer));
        
        //3) obtener la altura del antepasado para ver si ese raiz_ se desequilibro
        int factor_equilibrio = obtenerBalanceo(raiz_);
        
        //si el factor de equilibrio se desbalanceo, aplicar casos de balanceo
        
        if (factor_equilibrio>1 &&  (compararCategorias(raiz_.ramaIzq.categoria, categoria)==1)) 
            return rotacionDerecha(raiz_);
        
        if(factor_equilibrio<-1 && (compararCategorias(raiz_.ramaDer.categoria, categoria)==2))
            return rotacionIzquierda(raiz_);
        
        if(factor_equilibrio>1 && (compararCategorias(raiz_.ramaIzq.categoria, categoria)==2))
        {
            raiz_.ramaIzq=rotacionIzquierda(raiz_.ramaIzq);
            return rotacionDerecha(raiz_);
        }
        if(factor_equilibrio < -1 && (compararCategorias(raiz_.ramaDer.categoria, categoria)==1))
        {
            raiz_.ramaDer = rotacionDerecha(raiz_.ramaDer);
            return rotacionIzquierda(raiz_);
        }
        
        return raiz_;     
    }
    
    //metodo para eliminar una categoria, pasando el nombre de esta como parametro
    public void eliminarCategoria(String categoria)
    {
        deleteNode(raiz, categoria);
    }
    
    ///metodo para saber el valor minimo de un subarbol para subirlo a la raiz
    private NodoAVL minValueNode(NodoAVL node)  
    {  
        NodoAVL current = node;  
  
        /* loop down to find the leftmost leaf */
        while (current.ramaIzq != null)  
            current = current.ramaIzq;           
        return current;  
    }    
      
    
    private NodoAVL deleteNode(NodoAVL raiz, String categoria)  
    {  
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (raiz == null)  
            return raiz;  
  
        // If the categoria to be deleted is smaller than  
        // the raiz's categoria, then it lies in left subtree  
        else if (compararCategorias(raiz.categoria, categoria)==1)
            raiz.ramaIzq = deleteNode(raiz.ramaIzq, categoria);  
  
        // If the categoria to be deleted is greater than the  
        // raiz's categoria, then it lies in right subtree  
        else if (compararCategorias(raiz.categoria, categoria)==2)
            raiz.ramaDer = deleteNode(raiz.ramaDer, categoria);  
  
        // if categoria is same as raiz's categoria, then this is the node  
        // to be deleted  
        else
        {  
  
            // node with only one child or no child  
            if ((raiz.ramaIzq == null) || (raiz.ramaDer == null))  
            {  
                NodoAVL temp = null;  
                if (temp == raiz.ramaIzq)  
                    temp = raiz.ramaDer;  
                else
                    temp = raiz.ramaIzq;  
  
                // No child case  
                if (temp == null)  
                {  
                    temp = raiz;  
                    raiz = null;  
                }  
                else // One child case  
                    raiz = temp; // Copy the contents of  
                                // the non-empty child  
            }  
            else
            {  
  
                // node with two children: Get the inorder  
                // successor (smallest in the left subtree)  
                NodoAVL temp = minValueNode(raiz.ramaIzq);  
  
                // Copy the inorder successor's data to this node  
                raiz.categoria = temp.categoria;  
  
                // Delete the inorder successor  
                raiz.ramaIzq = deleteNode(raiz.ramaIzq, temp.categoria);  
            }  
        }  
  
        // If the tree had only one node then return  
        if (raiz == null)  
            return raiz;  
  
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        raiz.altura = comparaAlturas(alturaNodo(raiz.ramaIzq), alturaNodo(raiz.ramaDer)) + 1;  
  
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = obtenerBalanceo(raiz);  
  
        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && obtenerBalanceo(raiz.ramaIzq) >= 0)  
            return rotacionDerecha(raiz);  
  
        // Left Right Case  
        if (balance > 1 && obtenerBalanceo(raiz.ramaIzq) < 0)  
        {  
            raiz.ramaIzq = rotacionIzquierda(raiz.ramaIzq);  
            return rotacionDerecha(raiz);  
        }  
  
        // Right Right Case  
        if (balance < -1 && obtenerBalanceo(raiz.ramaDer) <= 0)  
            return rotacionIzquierda(raiz);  
  
        // Right Left Case  
        if (balance < -1 && obtenerBalanceo(raiz.ramaDer) > 0)  
        {  
            raiz.ramaDer = rotacionDerecha(raiz.ramaDer);  
            return rotacionIzquierda(raiz);  
        }  
  
        return raiz;  
    }  
    
    //Recorrido del arbol AVL en preorden
    public void preorden()
    {
        recorridoPreorden(raiz);        
        System.out.println("Numero de Nodos Visitados: "+numNodos);
    }        
    private void recorridoPreorden(NodoAVL nodo)
    {
        if(nodo!=null)
        {
            System.out.println(nodo.categoria+" ");                  
            numNodos++;
            recorridoPreorden(nodo.ramaIzq);               
            recorridoPreorden(nodo.ramaDer);
       }
    }
    
    //Recorrido del arbol AVL en inorden
    public void inorden()
    {
        recorridoInorden(raiz);
        System.out.println("Numero de Nodos Visitados: "+numNodos);
    }
    private void recorridoInorden(NodoAVL nodo)
    {
        if(nodo!=null)
        {                            
            recorridoInorden(nodo.ramaIzq);                                              
            System.out.println(nodo.categoria+" ");  
            numNodos++;
            recorridoInorden(nodo.ramaDer);
       }
    }
    
    //Recorrido del arbol AVL en postorden
    public void postorden()
    {
        recorridoPostorden(raiz);
        System.out.println("Numero de Nodos Visitados: "+numNodos);
    }    
    private void recorridoPostorden(NodoAVL nodo)
    {
        if(nodo!=null)
        {            
            recorridoPostorden(nodo.ramaIzq);                                              
            recorridoPostorden(nodo.ramaDer);
            System.out.println(nodo.categoria+" ");   
            numNodos++;
       }
    }
   
    
    //GRAFICAR UN ARBOL AVL
    public void graficar() {
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("arbolAVL.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoGraphviz());
            recorreAVL="";
            fichero.close();
            try
            {
                Runtime rt = Runtime.getRuntime();
                rt.exec( "dot -Tjpg -o arbolAVL.jpg arbolAVL.dot");
                //Esperamos medio segundo para dar tiempo a que la imagen se genere.
                //Para que no sucedan errores en caso de que se decidan graficar varios
                //árboles sucesivamente.
                Thread.sleep(500);
            } 
            catch (Exception ex) 
            {
            System.err.println("Error al generar la imagen para el archivo arbolAVL.dot");
            }  
        } 
        catch (Exception e)
        {
            System.err.println("Error al escribir el archivo arbolAVL.dot");
        }
                  
    }
    
    private String getCodigoGraphviz() {
        String recorridoAVL = "";
        recorridoAVL += "digraph arbolAVL{\n";
        recorridoAVL += "rankdir=TB;\n";
        recorridoAVL += "node[shape = record, style=filled, fillcolor=seashell2];\n";
        getCodigoInterno(raiz);
        recorridoAVL += recorreAVL;
        recorridoAVL +=  "}\n";
        
        return recorridoAVL;
    }
    
    private void getCodigoInterno(NodoAVL root_)
    {
        if (root_ != null)
        {
            recorreAVL += root_.categoria + "[label = \"<f0> |<f1> " + root_.categoria + "|<f2>\"]; \n";
            if (root_.ramaIzq != null)
            {
                recorreAVL += "\"" + root_.categoria + "\"" + ":f0 -> \"" + root_.ramaIzq.categoria + "\":f1; \n";                
            }
            if (root_.ramaDer != null)
            {
                recorreAVL += "\"" + root_.categoria + "\"" + ":f2 -> \"" + root_.ramaDer.categoria + "\":f1; \n";                
            }
            //CALL RECURSIVE FUNCTION TO TRAVERSE LEFT SUBTREE
            getCodigoInterno(root_.ramaIzq);
            //CALL RECURSIVE FUNCTION TO TRAVERSE RIGHT SUBTREE
            getCodigoInterno(root_.ramaDer);
        }
    }
    
    
    private void recorreArbol(NodoAVL raiz_)
    {
        if(raiz_!=null)
        {            
            numNodos++;
            recorreArbol(raiz_.ramaIzq);               
            recorreArbol(raiz_.ramaDer);
       }        
    }
    
    //GRAFICAR AVL EN PRE-ORDEN
    public void graficarPreOrden()
    {
        recorreArbol(raiz);
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("avlPreorden.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoPreorden());
            recorreAVL="";
            countVisit = 0;
            numNodos = 0;
            fichero.close();
            try
            {
                Runtime rt = Runtime.getRuntime();
                rt.exec( "dot -Tjpg -o avlPreorden.jpg avlPreorden.dot");
                //Esperamos medio segundo para dar tiempo a que la imagen se genere.
                //Para que no sucedan errores en caso de que se decidan graficar varios
                //árboles sucesivamente.
                Thread.sleep(500);
            } 
            catch (Exception ex) 
            {
            System.err.println("Error al generar la imagen para el archivo avlPreorden.dot");
            }  
        } 
        catch (Exception e)
        {
            System.err.println("Error al escribir el archivo avlPreorden.dot");
        }        
    }
    
    private String getCodigoPreorden()
    {
        String recorridoAVLPre = "";
        recorridoAVLPre += "digraph arbolAVLPreOrden{\n";
        recorridoAVLPre += "rankdir=LR;";
        recorridoAVLPre += "node[shape = box, style=filled, fillcolor=seashell2, fontsize=20];\n";
        preOrdenRecursivo(raiz);
        recorridoAVLPre += recorreAVL;
        recorridoAVLPre += ";\nlabel=\"Recorrido Preorden\";\nfontsize=25;\n";
        recorridoAVLPre +=  "}\n";
        
        return recorridoAVLPre;
        
    }
    
    private void preOrdenRecursivo(NodoAVL raiz_)
    {
        if (raiz_!=null)
        {   
            if(countVisit==numNodos-1)
            {
                recorreAVL += raiz_.categoria;
            }
            else
            {
                recorreAVL += raiz_.categoria+"->";
                countVisit++;
                preOrdenRecursivo(raiz_.ramaIzq);
                preOrdenRecursivo(raiz_.ramaDer);                
            }
        }        
        
    }
    
    
    //GRAFICAR AVL EN IN-ORDEN
    public void graficarInOrden()
    {
        recorreArbol(raiz);
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("avlInorden.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoInorden());
            recorreAVL="";
            countVisit = 0;
            numNodos = 0;
            fichero.close();
            try
            {
                Runtime rt = Runtime.getRuntime();
                rt.exec( "dot -Tjpg -o avlInorden.jpg avlInorden.dot");
                //Esperamos medio segundo para dar tiempo a que la imagen se genere.
                //Para que no sucedan errores en caso de que se decidan graficar varios
                //árboles sucesivamente.
                Thread.sleep(500);
            } 
            catch (Exception ex) 
            {
            System.err.println("Error al generar la imagen para el archivo avlInorden.dot");
            }  
        } 
        catch (Exception e)
        {
            System.err.println("Error al escribir el archivo avlInorden.dot");
        }   
     
    }
    
    private String getCodigoInorden()
    {
        String recorridoAVLIn = "";
        recorridoAVLIn += "digraph arbolAVLInOrden{\n";
        recorridoAVLIn += "rankdir=LR;";
        recorridoAVLIn += "node[shape = box, style=filled, fillcolor=seashell2, fontsize=20];\n";
        InOrdenRecursivo(raiz);
        recorridoAVLIn += recorreAVL;
        recorridoAVLIn += ";\nlabel=\"Recorrido Inorden\";\nfontsize=25;\n";
        recorridoAVLIn +=  "}\n";
        
        return recorridoAVLIn;        
    }
    
    private void InOrdenRecursivo(NodoAVL raiz_)
    {
        if (raiz_!=null)
        {   
            InOrdenRecursivo(raiz_.ramaIzq);
            if(countVisit==numNodos-1)
            {
                recorreAVL += raiz_.categoria;
            }
            else
            {
                recorreAVL += raiz_.categoria+"->";
                countVisit++;                                
            }
            InOrdenRecursivo(raiz_.ramaDer);                
        }      
    }    
    
    
    //GRAFICAR AVL EN POST-ORDEN
    public void graficarPostOrden()
    {
        recorreArbol(raiz);
        FileWriter fichero;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("avlPostorden.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoPostorden());
            recorreAVL="";
            countVisit = 0;
            numNodos = 0;
            fichero.close();
            try
            {
                Runtime rt = Runtime.getRuntime();
                rt.exec( "dot -Tjpg -o avlPostorden.jpg avlPostorden.dot");
                //Esperamos medio segundo para dar tiempo a que la imagen se genere.
                //Para que no sucedan errores en caso de que se decidan graficar varios
                //árboles sucesivamente.
                Thread.sleep(500);
            } 
            catch (Exception ex) 
            {
            System.err.println("Error al generar la imagen para el archivo avlPostorden.dot");
            }  
        } 
        catch (Exception e)
        {
            System.err.println("Error al escribir el archivo avlPostorden.dot");
        }   
        
    }
    
    private String getCodigoPostorden()
    {
        String recorridoAVLPost = "";
        recorridoAVLPost += "digraph arbolAVLPostOrden{\n";
        recorridoAVLPost += "rankdir=LR;";
        recorridoAVLPost += "node[shape = box, style=filled, fillcolor=seashell2, fontsize=20];\n";
        postOrdenRecursivo(raiz);
        recorridoAVLPost += recorreAVL;
        recorridoAVLPost += ";\nlabel=\"Recorrido Postorden\";\nfontsize=25;\n";
        recorridoAVLPost +=  "}\n";
        
        return recorridoAVLPost;
        
    }
    
    private void postOrdenRecursivo(NodoAVL raiz_)
    {
        if (raiz_!=null)
        {   
            postOrdenRecursivo(raiz_.ramaIzq);
            postOrdenRecursivo(raiz_.ramaDer);    
            
            if(countVisit==numNodos-1)
            {
                recorreAVL += raiz_.categoria;
            }
            else
            {
                recorreAVL += raiz_.categoria+"->";
                countVisit++;                                
            }
                        
        }      
    }
    
}
