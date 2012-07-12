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
    public Aresta getProximaAresta(){
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
     *  deslocando o próximo deste para o novo
     *  vertice
     * 
     * @param Vertice vertice 
     */
    public void setProximoVertice(String nome, int verticeID){
        this.proximoVertice = new Vertice(nome, verticeID, null, this.proximoVertice);
    }
   
    /**
     * Método que insere uma nova aresta
     * @param aresta 
     */
    public void setProximaAresta(int VerticeDestinoID, int valor){
        this.aresta = new Aresta(VerticeDestinoID, valor, this.aresta);    
    }
    /**
     * Método que retorna a aresta se ela existe
     *  nesse vertice, caso contrário retorna null
     * @param int verticeID
     * 
     * @return {Aresta || null }
     */
    public Aresta getArestaExists(int verticeID){
        Aresta arestaTemp = this.getProximaAresta();
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
    
    /**
     * Método para deletar um vertice se consta
     *  a partir de ele e suas referencias
     * 
     * @param int verticeID
     * @return { true - se deletou || false- se não achou}
     */
    public void deleteVerticeDaMatriz(int verticeID){
        //System.err.printf("DELETAR VERTICE\n");
        Vertice verticeAnterior = this.proximoVertice;
        Vertice vertice = verticeAnterior.proximoVertice;
        
        //verifica se é o primeiro que deve ser deletado
        if(verticeAnterior.getID() == verticeID){
            //System.err.printf("Encontrado o para deletar\n");
            this.proximoVertice = vertice;
 
        }
        
        /**
         * Remove o vertice se encontra-o ou então
         *  passa para o próximo
         */
        while( vertice != null){
            if( vertice.getID() == verticeID ){
                //System.err.printf("Encontrado o para deletar\n");
                verticeAnterior.proximoVertice = vertice.proximoVertice;
                vertice = null;
            }
            else{
                //System.err.printf("Não encontrou para deletar\n");
                verticeAnterior = vertice;
                vertice = vertice.proximoVertice;
            }
        }
        
        /**
         * Depois de remover o vertice, se o achou
         *  então ele procura por arestas com o mesmo sentido
         */
        //System.err.printf("PROCURAR ARESTAS\n");
        vertice = this.proximoVertice;
        while(vertice != null){
            Aresta arestaAnterior = vertice.aresta;
            if(arestaAnterior != null){
                Aresta estaAresta = arestaAnterior.getProximaAresta();
                if( arestaAnterior.getVerticeID() == verticeID){
                    //System.err.printf("Encontrado Aresta o para deletar\n");
                    vertice.aresta = estaAresta;
                }
                while(estaAresta != null){
                    if(estaAresta.getVerticeID() == verticeID){
                        //System.err.printf("Encontrado Aresta o para deletar\n");
                        arestaAnterior.setProximaAresta(estaAresta.getProximaAresta());
                        estaAresta = null;
                    }
                    else{
                        //System.err.printf("Aresta não encontrou para deletar\n");
                        arestaAnterior = estaAresta;
                        estaAresta = estaAresta.getProximaAresta();
                    }
                }
            }
            vertice = vertice.getProximoVertice();
        }
        
    }
}
