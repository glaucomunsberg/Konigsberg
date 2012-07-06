/**
 * Classe que representa os vertices do grafo
 * 
 * @author glaucoroberto
 */
public class Vertice {
    
    private String nome;
    private int ID;
    private Aresta aresta;
    private Vertice proximoVertice;
    
    /**
     * Construtor base com tudo zero e null
     */
    public Vertice(){
        nome = ""; 
        ID = 0;
        proximoVertice = null;
    }
    
    /**
     * Construtor que recebe os valores para o vertice
     * @param String nome
     * @param int ID
     * @param Aresta aresta
     * @param Vertice proximoVertice 
     */
    public Vertice(String nome, int ID, Aresta aresta, Vertice proximoVertice){
        this.nome = nome;
        this.ID = ID;
        this.aresta = aresta;
        this.proximoVertice = proximoVertice;
    }
    
    /**
     * Construtor de cópia
     * @param Vertice vertice 
     */
    public Vertice(Vertice vertice){
        this.ID = vertice.ID;
        this.nome = vertice.nome;
        this.aresta = vertice.aresta;
        this. proximoVertice = vertice.proximoVertice;
    }
    
    /**
     * Método de retorno do ID do vertice
     * 
     * @return int ID
     */
    public int getID(){
        return this.ID;
    }
    
    /**
     * Método de retorno a aresta vinculada
     * 
     * @return Aresta aresta
     */
    public Aresta getAresta(){
        return this.aresta;
    }
    
    /**
     * Método de retorno no nome do vertice
     * @return 
     */
    public String getNomeVertice(){
        return this.nome;
    }
    
    /**
     * Método que retorna o próximmo vertice
     * @return Vertice proximoVertice
     */
    public Vertice getProximoVertice(){
        return proximoVertice;
    }
    
    /**
     * Método que insere novo vertice
     * 
     * @param Vertice vertice 
     */
    public void setProximoVertice(Vertice vertice){
        this.proximoVertice = vertice;
    }
    
    /**
     * Método que retorna a aresta se ela existe
     *  nesse vertice, caso contrário retorna null
     * @param int verticeID
     * 
     * @return {Aresta || null }
     */
    public Aresta getArestaExists(int verticeID){
        Aresta arestaTemp = this.getAresta();
        while( arestaTemp != null ){
            if( arestaTemp.getVerticeID() == verticeID ){
                return arestaTemp;
            }else{
                arestaTemp = arestaTemp.getProximaAresta();
            }
        }
        return null;  
    }
    
    /**
     * Método que retorna true se existe um ID a partir
     * dele ou false caso contrário
     * @param int verticeID
     * 
     * @return {true || false}
     */
    public boolean verticeExists(int verticeID){
        Vertice vertice = this.getProximoVertice();
        while( vertice != null ){
            if( vertice.getID() == verticeID){
                return true;
            }else{
                vertice = vertice.getProximoVertice();
            }
        }
        return false;
    }
    
    /**
     * Método que retonra o vertice ID se ele existe
     *  a partir deste ou null caso contrário
     * @param int verticeID
     * 
     * @return {Vertice || null}
     */
    public Vertice getVerticeExists(int verticeID){
        Vertice verticeTemp = this.getProximoVertice();
        while(verticeTemp != null){
            if( verticeTemp.getID() == verticeID)
            {
                return verticeTemp;
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }
        return null;
        
    }
}
