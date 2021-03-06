import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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
    private static boolean isDirecionado;
    private static boolean isDirecionadoInseridoValor;
    private Object object;
    private int[] xy;

    /**
     * Criador do vertice raiz
     */
    public Vertice(){
        nome = "";
        ID = -1;
        proximoVertice = null;
        this.object = null;
        xy = new int[2];
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
        this.object = null;
        this.xy = new int[2];
    }

    /**
     * Construtor de cópia
     * @param Vertice vertice
     */
    public Vertice(Vertice vertice){
        this.ID = vertice.ID;
        this.nome = vertice.nome;
        this.aresta = vertice.aresta;
        this.proximoVertice = vertice.proximoVertice;
        this.xy = new int[2];
        this.xy = vertice.xy;
        this.object = vertice.object;
    }
    
    /**
     * Método que retorna o objecto
     *  sendo ele do tipo mxCell usado no
     *  mxGraph
     * @return object 
     */
    public Object getObject(){
        return this.object;
    }
    
    /**
     * Retorna posição x do vertice no plano cartesiano
     * 
     * @return int x
     */
    public int getX(){
        return xy[0];
    }
    
    /**
     * Retorna posição y do vertice no plano cartesiano
     * 
     * @return int y
     */    
    public int getY(){
        return xy[1];
    }
    
    
   /**
     * Insere object, sendo esse representando
     * o vertice usado no mxGraph
     * 
     * @return int x
     */
    public void setObject(Object o){
        this.object = o;
    }
    
   /**
     * Insere posição xy do vertice no plano cartesiano
     * @param int[]xy
     */
    public void setXY( int[]xy){
        this.xy = xy;
    }
    
    /**
     * Insere a posição x do vertice no plano carteisiano
     * @param x 
     */
    public void setX(int x){
        this.xy[0] = x;
    }
    
    /**
     *  Insere a posição y do vertice no plano cartesiano
     * @param y 
     */
    public void setY(int y){
        this.xy[1] = y;
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
     * Método de retorna aresta vinculada esse vertice
     *
     * @return Aresta aresta
     */
    public Aresta getProximaAresta(){
        return this.aresta;
    }

    /**
     * Método de retorno no nome do vertice
     * @return String nome
     */
    public String getNomeVertice(){
        return this.nome;
    }

    /**
     * Método que retorna o próximmo vertice vinculado
     *  a esse vertice
     * @return Vertice proximoVertice
     */
    public Vertice getProximoVertice(){
        return proximoVertice;
    }

    /**
     * Retorna o total vertices que possúi
     *  partir deste. Tenha o cuidado para que sempre
     *  use a raiz do grafo para ter a contagem certa
     * @return int numDeVertices
     */
    public int getNumeroDeVertices(){
        int numDeVertices=0;
        Vertice temp = this.getProximoVertice();
        while(temp != null){
            temp = temp.getProximoVertice();
            numDeVertices++;
        }
        return numDeVertices;
    }

    /**
     * Retorna se o grafo é direcionado ou não
     * @return boolean { true - se é direcionado || false - Senão é direcionado }
     */
    public static boolean getIsDirecionado(){
        return isDirecionado;
    }

    /**
     * Método que insere se o grafo é direcionado ou não.
     *  nota: apenas executado uma única vez
     * @param boolean direcao
     */
    public static void setItsDirecionado(boolean direcao){
        if(!isDirecionadoInseridoValor){
            isDirecionadoInseridoValor = true;
            isDirecionado = direcao;
        }
    }

    /**
     * Método que insere novo vertice
     *  deslocando o próximo deste para o novo
     *  vertice que está sendo inserido agora
     *
     * @param Vertice vertice
     */
    protected void setProximoVertice(String nome, int verticeID){
        this.proximoVertice = new Vertice(nome, verticeID, null, this.proximoVertice);
    }

    /**
     * Método que insere uma nova aresta
     * @param aresta
     */
    private void setProximaAresta(int VerticeDestinoID, int valor){
        this.aresta = new Aresta(VerticeDestinoID, valor, this.aresta);
    }

    /**
     * Método que insere uma nova aresta
     * @param aresta
     */
    public void setProximaAresta(Aresta a){
        if( this.getProximaAresta() == null){
            this.aresta = a;
        }else{
            Aresta arest = this.aresta;
            while( arest != null){
                if( arest.proximo == null){
                    arest.proximo = a;
                    break;
                }else{
                    arest = arest.proximo;
                }
            }

        }
    }


    /**
     * Método que retorna a aresta se ela existe
     *  nesse vertice, caso contrário retorna null
     * @param int verticeID
     *
     * @return {Aresta || null }
     */
    public Aresta getArestaExists(int verticeID){
        Aresta arestaTemp = this.aresta;
        while( arestaTemp != null ){
            if( arestaTemp.getVerticeID() == verticeID ){
                if(arestaTemp.getValor() >= 0)
                    return arestaTemp;
                else
                    return null;
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
     * @return boolean {true || false}
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
     * Método que retorna o vertice ID se ele existe
     *  a partir deste ou null caso contrário
     * @param int verticeID
     *
     * @return boolean {Vertice || null}
     */
    public Vertice getVerticeExists(int verticeID){
        Vertice verticeTemp = this;
        while(verticeTemp != null){
            if( verticeTemp.getID() == verticeID)
            {
                if(verticeTemp.ID >=0)
                    return verticeTemp;
                else
                    return null;
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }
        return null;

    }

    /**
     *  Método que retorna a lista de ID de vertices que há no grafo
     *
     *  @return int[] arrayDeInt;
     */
    public int[] getListaDeVerticesID(){
        ArrayList<Integer> listaDeVertices = new ArrayList<Integer>();
        Vertice vertice = this.proximoVertice;
        int[] arrayDeInt;
        if(vertice != null){
            while(vertice != null){
                listaDeVertices.add( vertice.getID() );
                vertice = vertice.getProximoVertice();
            }
            arrayDeInt = new int[listaDeVertices.size()];
            for (int i=0; i < listaDeVertices.size(); i++)
            {
                arrayDeInt[i]=listaDeVertices.get(i);
            }
            return arrayDeInt;
        }else{
            return new int[0];
        }
    }

    /**
     * Método que retorna a array de vertices do grafo que não
     *  possúem arestas de entrada.
     *
     * @return int[] vertices
     */
    public int[] getListaDeVerticesSemEntrada(){
        ArrayList<Integer> arrayDeVerices = new ArrayList<Integer>();
        int[] vertices = this.getListaDeVerticesID();
        Integer integerTemp;
        for(int a=0; a < vertices.length;a++){
            arrayDeVerices.add((Integer)vertices[a]);
        }
        Vertice vertice = this.proximoVertice;
        while( vertice != null){
            Aresta arestaTemp = vertice.getProximaAresta();
            while( arestaTemp != null){
                integerTemp = (Integer)arestaTemp.getVerticeID();
                arrayDeVerices.remove(integerTemp);
                arestaTemp = arestaTemp.getProximaAresta();
            }
            vertice = vertice.getProximoVertice();
        }
        vertices = new int[arrayDeVerices.size()];
        for(int a=0; a < arrayDeVerices.size();a++){
            vertices[a]=arrayDeVerices.get(a);
        }
        java.util.Collections.sort(arrayDeVerices);
        return vertices;
    }

    /**
     * Método que retorna uma linkedList de ID's de vertices do grafo
     *  que não possúem arestas de entrada.
     *
     * @return int[] vertices
     */
    // @SuppressWarnings("unchecked")
    private LinkedList getlistaLinkedListDeVerticesSemEntrada(){
        LinkedList<Integer> arrayDeVerices = new LinkedList<Integer>();
        int[] vertices = this.getListaDeVerticesID();
        Integer integerTemp;
        for(int a=0; a < vertices.length;a++){
            arrayDeVerices.add((Integer)vertices[a]);
        }
        Vertice vertice = this.proximoVertice;
        while( vertice != null){
            Aresta arestaTemp = vertice.getProximaAresta();
            while( arestaTemp != null){
                integerTemp = (Integer)arestaTemp.getVerticeID();
                arrayDeVerices.remove(integerTemp);
                arestaTemp = arestaTemp.getProximaAresta();
            }
            vertice = vertice.getProximoVertice();
        }

        java.util.Collections.sort(arrayDeVerices);
        return arrayDeVerices;
    }

    /**
     * Retorna a lista de vertices que não possúem grau de entrada
     *
     * @return int[] vertices
     */
    public int[] getVerticesSemSaida(){
        int[] vertices;
        Vertice vertice = this.proximoVertice;
        ArrayList<Integer> arrayVerices = new ArrayList<Integer>();
        while(vertice != null){
            if(vertice.getProximaAresta() == null ){
                arrayVerices.add(vertice.getID());
                vertice = vertice.getProximoVertice();
            }
            else{
                vertice = vertice.getProximoVertice();
            }
        }
        vertices = new int[arrayVerices.size()];
        for(int a=0; a < arrayVerices.size();a++){
            vertices[a]=arrayVerices.get(a);
        }
        return vertices;
    }

    /**
     * Retorna a ordem topológica caso esse não tenha um ciclo
     */
    @SuppressWarnings("unchecked")
    public int[] getOrdemTopologica(){
        if(!Vertice.getIsDirecionado()){
            return new int[0];
        }
        Vertice clone = this.clone(this.getProximoVertice());

        LinkedList<Integer> semEntrada = clone.getlistaLinkedListDeVerticesSemEntrada();
        LinkedList<Integer> semEntradaTemp = new LinkedList<Integer>(semEntrada);
        while(clone.getNumeroDeVertices() != 0){
            Iterator interator = semEntradaTemp.iterator();
            while(interator.hasNext()){
                clone.removeVertice( Integer.parseInt(interator.next().toString()) );
            }
            semEntradaTemp = clone.getlistaLinkedListDeVerticesSemEntrada();
            Iterator interatorDois = semEntradaTemp.iterator();
            while(interatorDois.hasNext()){
                semEntrada.add((Integer)interatorDois.next());
            }
        }
        Iterator a = semEntrada.iterator();
        int[] retorno = new int[semEntrada.size()];
        int b=0;
        while(a.hasNext()){
            retorno[b] = Integer.parseInt(a.next().toString());
            b++;
        }

        return retorno;
    }


    /**
     * Método para deletar um vertice
     *
     * @param int verticeID
     * @return { true - se deletou || false- se não achou}
     */
    public boolean removeVertice(int verticeID){
        if(verticeID < 0)
            return false;
        Vertice verticeAnterior = this.getProximoVertice();
        if(verticeAnterior == null){
            return false;
        }
        Vertice vertice = verticeAnterior.proximoVertice;
        boolean jaDeletou = false;
        //verifica se é o primeiro que deve ser deletado
        if(verticeAnterior.getID() == verticeID){
            this.proximoVertice = vertice;
            jaDeletou = true;

        }

        /**
         * Remove o vertice se encontra-o ou então
         *  passa para o próximo
         */
        while( vertice != null){
            if( vertice.getID() == verticeID ){
                verticeAnterior.proximoVertice = vertice.proximoVertice;
                jaDeletou = true;
                vertice = null;
            }
            else{
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
                    vertice.aresta = estaAresta;
                }
                while(estaAresta != null){
                    if(estaAresta.getVerticeID() == verticeID){
                        arestaAnterior.setProximaAresta(estaAresta.getProximaAresta());
                        estaAresta = null;
                    }
                    else{
                        arestaAnterior = estaAresta;
                        estaAresta = estaAresta.getProximaAresta();
                    }
                }
            }
            vertice = vertice.getProximoVertice();
        }
       return jaDeletou;
    }


    /**
     * Insere um novo Vertice
     *
     * @param int verticeID
     * @param String nome
     */
    public void setNovoVertice(int verticeID, String nome){
        this.setProximoVertice(nome, verticeID);
    }

    /**
     * Método que insere uma nova aresta ao vertice
     * @param int verticeOrigemID
     * @param int verticeDestinoID
     * @param int valor
     */
    public void setNovaAresta(int verticeOrigemID, int verticeDestinoID, int valor){
        Vertice vertice = this.getVerticeExists(verticeOrigemID);
        if( vertice != null )
        {
            aresta = vertice.getArestaExists(verticeOrigemID);
            if( aresta != null){
                    return;
            }
            vertice.setProximaAresta(verticeDestinoID,valor);
        }

        if( !Vertice.getIsDirecionado() ){
            vertice = this.getVerticeExists(verticeDestinoID);
            if( vertice != null )
            {
                aresta = vertice.getArestaExists(verticeOrigemID);
                if( aresta != null){
                        return;
                }
                vertice.setProximaAresta(verticeOrigemID,valor);
            }
        }
    }

    /**
     * Método que retorna o numero de arestas do vertice que está
     *  sendo manipulado.
     *
     * @return int numArestas
     */
    public int getNumeroDeArestas(){
        int numArestas=0;
        Aresta arestaTemp =  this.getProximaAresta();
        while( arestaTemp != null){
            numArestas++;
            arestaTemp = arestaTemp.proximo;
        }
        return numArestas;
    }

    /**
     * Método que remove uma aresta de um vertice existente
     * @param int origem - Vertice Origem
     * @param int destino - Vertice Destino
     *
     * @return {true - se removeu || false - se não existe }
     */
    public boolean removeAresta(int origem, int destino){
        if(origem < 0 || destino < 0)
            return false;
        Vertice vertice = this.getVerticeExists(origem);
        if(vertice != null){
            return vertice.removeAresta(destino);
        }
        return false;
    }

    /**
     * Método que remove a aresta, se existe, deste vertice
     * @param int verticeID -  vertice Destino
     *
     * @return {true - se removeu || false - se não existe }
     */
    private boolean removeAresta(int verticeID){
        Aresta arestaAnterior = this.getProximaAresta();
        if(arestaAnterior == null){
            return false;
        }
        Aresta arestaTemp;

        arestaTemp = arestaAnterior.getProximaAresta();

        //verifica se é o primeiro que deve ser deletado
        if(arestaAnterior.getVerticeID() == verticeID){
            if(arestaTemp == null){
                this.aresta = null;
            }
            else{
                arestaAnterior = arestaTemp;
            }

            return true;
        }

        /**
         * Remove o vertice se encontra-o ou então
         *  passa para o próximo
         */
        while( arestaTemp != null){
            if( arestaTemp.getVerticeID() == verticeID ){
                //System.err.printf("Encontrado o para deletar\n");
                arestaAnterior.proximo = arestaTemp.getProximaAresta();
                return true;
            }
            else{
                //System.err.printf("Não encontrou para deletar\n");
                arestaAnterior = arestaTemp;
                arestaTemp = arestaTemp.getProximaAresta();
            }
        }

        return false;

    }

    /**
     * Método que retorna um array com os vertices vizinhos do
     *  verticeID
     * @param int vertice
     *
     * @return int[]vizinho
     */
    public int[] getVizinhos(int verticeID){

        Vertice vertice = this.getProximoVertice();
        ArrayList<Integer> array = new ArrayList<Integer>();
        while( vertice != null){
             if( vertice.getID() == verticeID){
                 aresta = vertice.getProximaAresta();
                 while( aresta != null)
                 {
                     array.add( aresta.getVerticeID());
                     aresta = aresta.getProximaAresta();
                 }
                 int[] arrayDeInt = new int[array.size()];
                 for (int i=0; i < array.size(); i++)
                 {
                    arrayDeInt[i]=array.get(i);
                 }
                 return arrayDeInt;
             }
             else{
                 vertice = vertice.getProximoVertice();
             }
         }

        return new int[0];
    }

    /**
     * Método que retornar um clone do grafo na sua atual
     *  situação
     * @param Vertice raiz
     *
     * @return Vertice clone
     */
    public Vertice clone(Vertice raiz)
    {
        Vertice clone = new Vertice();

        Vertice verticeTemp = this.getProximoVertice();
        Aresta arestaTemp;

        //Primeiro copia todos os vertices, pois para se criar uma aresta ambas as
        //  os vertices devem estar presentes
        while(verticeTemp != null)
        {

            clone.setNovoVertice(verticeTemp.getID(), verticeTemp.getNomeVertice());
            verticeTemp = verticeTemp.getProximoVertice();

        }

        //Copia as arestas da raiz para o clone
        verticeTemp = this.getProximoVertice();
        while( verticeTemp != null)
        {
            arestaTemp = verticeTemp.getProximaAresta();
            while( arestaTemp != null )
            {
                clone.setNovaAresta(verticeTemp.ID, arestaTemp.getVerticeID(), arestaTemp.getValor());
                arestaTemp = arestaTemp.getProximaAresta();
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }

        return clone;
    }

    public void setProximoVertice(Vertice v){
        if( this.getProximoVertice() == null){
            this.proximoVertice = v;
        }else{
            Vertice vert = this.proximoVertice;
            while( vert != null){
                if( vert.proximoVertice == null){
                    vert.proximoVertice = v;
                    break;
                }else{
                    vert = vert.proximoVertice;
                }
            }
        }
    }

}