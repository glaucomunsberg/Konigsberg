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
    
    public Vertice(){
        nome = "";
        ID = 0;
        proximoVertice = null;
    }
    
    public Vertice(String nome, int ID, Aresta aresta, Vertice proximoVertice){
        this.nome = nome;
        this.ID = ID;
        this.aresta = aresta;
        this.proximoVertice = proximoVertice;
    }
    
    public Vertice(Vertice vertice){
        this.ID = vertice.ID;
        this.nome = vertice.nome;
        this.aresta = vertice.aresta;
        this. proximoVertice = vertice.proximoVertice;
    }
    public int getID(){
        return this.ID;
    }
    
    public Aresta getAresta(){
        return this.aresta;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public Vertice getProximoVertice(){
        return proximoVertice;
    }
    
    public void setProximoVertice(Vertice vertice){
        this.proximoVertice = vertice;
    }
    
}
