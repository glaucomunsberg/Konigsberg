/**
 * Classe que representa as Arestas do grafo
 * 
 * @author glaucoroberto
 */
public class Aresta 
{
    private int verticeID;
    private int valor;
    private Aresta proximo;
    
    /**
     * Construtor padrão tudo zero e null
     */
    public Aresta(){
        verticeID =0;
        valor=0;
        proximo = null;
    }
    
    /**
     * Construtor de cópia
     * @param nodo 
     */
    public Aresta(Aresta nodo){
        this.valor = nodo.valor;
        this.verticeID = nodo.verticeID;
        this.proximo = nodo.proximo; 
    }
    
    /**
     * Construtor que recebe por parametro
     * @param Vertice vertice
     * @param int valor
     * @param Aresta proximo 
     */
    public Aresta(int vertice, int valor, Aresta proximo){
        this.verticeID = vertice;
        this.valor = valor;
        this.proximo = proximo;
    }
    
    /**
     * Método que retorno do valor da Aresta
     * 
     * @return int valor
     */
    public int getValor(){
        return this.valor;
    }
    
    /**
     * Método de retorno do ID do vertice
     * 
     * @return int verticeID; 
     */
    public int getVerticeID(){
        return this.verticeID;
    }
    
    /**
     * Método que retorna a próxima Aresta
     * 
     * @return Aresta proxima
     */
    public Aresta getProximaAresta(){
        return this.proximo;
    }
    
    /**
     * Método que insere o valor da aresta
     * @param int valor 
     */
    public void setValor(int valor){
        this.valor = valor;
    }
    
    public void setProximaAresta(Aresta a){
        this.proximo = a;
    }
    
}
