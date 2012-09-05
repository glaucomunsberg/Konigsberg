import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

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
    
    /**
     * Criador do vertice raiz
     */
    public Vertice(){
        nome = ""; 
        ID = -1;
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
    private void setProximoVertice(String nome, int verticeID){
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
    private void setProximaAresta(Aresta a){
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
                clone.removeVertice( (int)interator.next() );
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
            retorno[b] = (int)a.next();
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
     *  vertice passado
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

    private ArrayList getOrigemDestinoCusto(){
        ArrayList<ArrayList> vetor = new ArrayList<ArrayList>();
        ArrayList<Integer> origem = new ArrayList<Integer>();
        ArrayList<Integer> destino = new ArrayList<Integer>();
        ArrayList<Integer> custo = new ArrayList<Integer>();
        
        Vertice vertice = this.getProximoVertice();
        Aresta arestaTemp = null;
        
        while(vertice != null){
            arestaTemp = vertice.getProximaAresta();
            while(arestaTemp != null){
                
                origem.add(vertice.getID());
                destino.add(arestaTemp.getVerticeID());
                custo.add(arestaTemp.getValor());
                arestaTemp = arestaTemp.getProximaAresta();
            }
            vertice = vertice.getProximoVertice();
        }
        
        vetor.add(origem);
        vetor.add(destino);
        vetor.add(custo);
        return vetor;
    }
    
    /**
     * Método que retorna a árvore mínima de um grafo
     *  desde que atenda a dois requesitos:
     *      Ser conexo
     *      Ser não direcionado
     *  caso não atenda necessidade retorna new int[0]
     * 
     * @return int[Vertice_origem,Vertice_destino,...,custo]
     */
    public static int[] getArvoreMinima(Vertice raiz){
        if( !Vertice.getIsDirecionado()){
            return new int[0];
        }
        
        /**
         * O primeiro momento percorre o grafo a procura 
         *  de todos as origens e destino e mais seus valores
         */
        Vertice temp = raiz.getProximoVertice();
        Aresta arestaTemp;
        int numeroDeArestas = 0;
        while( temp != null){
            numeroDeArestas += temp.getNumeroDeArestas();
            temp = temp.getProximoVertice();
        }
        int n=-1;
        int[] origem = new int[numeroDeArestas];
        int[] destino = new int[numeroDeArestas];
        int[] valor = new int[numeroDeArestas];
        temp = raiz.getProximoVertice();
        while(temp != null){
            arestaTemp = temp.getProximaAresta();
            while(arestaTemp != null){
                n++;
                origem[n] = temp.ID;
                destino[n] = arestaTemp.getVerticeID();
                valor[n] = arestaTemp.getValor();
                arestaTemp = arestaTemp.getProximaAresta();
            }
            temp = temp.proximoVertice;
        }
        
        /**
         *  No segundo momento faz a ordenação dos valores
         *      obtidos com o algoritmo Bubble Sort
         */
        
        
        int variavelAuxiliarValor;
        int variavelAuxiliarOrigem;
        int variavelAuxiliarDestino;
        boolean houveTroca = true;
 
        while (houveTroca) {
            houveTroca = false;
            for (int i = 0; i < (valor.length)-1; i++){
                            if (valor[i] > valor[i+1]){
                                    variavelAuxiliarValor = valor[i+1];
                                    variavelAuxiliarOrigem = origem[i+1];
                                    variavelAuxiliarDestino = destino[i+1];
                                    valor[i+1] = valor[i];
                                    origem[i+1] = origem[i];
                                    destino[i+1] = destino[i];
                                    valor[i] = variavelAuxiliarValor;
                                    origem[i] = variavelAuxiliarOrigem;
                                    destino[i] = variavelAuxiliarDestino;
                                    houveTroca = true;
                            }
                    }
        }
        
        
        /**
         * No terceiro momento ele gera a árvore mínima, 
         *  para isso verifica se a origem e o destino existem
         *  já na lista de vertices na lista grafo.
         *      Se ambos existe na lista:
         *          então verifica se estão em grafos diferentes
         *              se estão então une os grafos
         *      Se um dos dois existe
         *          Se origem existe
         *              insere destino como aresta na origem
         *              insere vertice destino
         *          Se destino exite
         *              insere vertice origem
         *              insere no vertice origem nova aresta destino
         *      Se não novo grafo na lista de grafos
         * 
         */
        CopyOnWriteArrayList< Vertice > grafo = new CopyOnWriteArrayList< Vertice >();
        LinkedList<Integer> verticesInseridos = new LinkedList<Integer>();
        int numDeAresta=0;
        boolean temOrigem;
        boolean temDestino;
         boolean primeiro;
         boolean segundo;
        for(int a=0; a < valor.length; a++){
            temOrigem = verticesInseridos.contains((Integer)origem[a]);
            temDestino = verticesInseridos.contains((Integer)destino[a]);
            if(temOrigem == false){
                verticesInseridos.add(origem[a]);
            }
                
            if(temDestino == false){
                verticesInseridos.add(destino[a]);
            }
            if( (temOrigem && temDestino) == true ){
                //Se ambos estiverem em arvores diferentes remova o segundo
                // e insira no primeiro grafo
                
                primeiro = false;
                segundo = false;
                int contador = 0;
                int primeroNoGrafo= 0;
                int segundoNoGrafo = 0;
                Vertice vORIGEM;
                Vertice vDESTINO;
                
                for( Iterator< Vertice > t = grafo.iterator(); t.hasNext() ; ){
                    temp = (Vertice)t.next();
                    if( !primeiro){
                        vORIGEM = temp.getVerticeExists(origem[a]);
                        if(vORIGEM != null){
                        primeiro = true;
                        primeroNoGrafo = contador;
                        }
                    }
                    if(!segundo){
                        vDESTINO = temp.getVerticeExists(destino[a]);
                        if(vDESTINO != null){
                        segundo = true;
                        segundoNoGrafo = contador;
                        }
                    }
                    if( (primeiro && segundo == true) &&  primeroNoGrafo != segundoNoGrafo  ){
                        temp = (Vertice)grafo.get(primeroNoGrafo);
                        temp.setProximoVertice( (Vertice)grafo.remove(segundoNoGrafo));
                        //System.out.printf("A - %d, B- %d.Pegar o vertice ID: %d\nTodos:",primeroNoGrafo,segundoNoGrafo,origem[a]);
                        //int verte[] = temp.getListaDeVerticesID();
                        //for(int v: verte){
                        //    System.out.printf("%d,",v);
                        //}
                        //System.out.printf("\n");
                        temp = temp.getVerticeExists(origem[a]);
                        numDeAresta++;
                        temp.setProximaAresta( new Aresta(destino[a],valor[a],null));
                    }
                    contador+=1;
                }

            }else{
                
                if((temOrigem || temDestino) == true){
                    
                    //insere uam aresta
                    //fazer um if para saber se é origem ou destino que tem
                    if(temOrigem){
                        
                        Iterator i = grafo.iterator();
                        while(i.hasNext()){
                            temp = (Vertice)i.next();
                            temp = temp.getVerticeExists(origem[a]);
                            if(temp != null){
                                temp.setProximoVertice(new Vertice("",destino[a],null,null));
                                numDeAresta++;
                                temp.setProximaAresta(new Aresta(destino[a],valor[a],null));
                                //System.out.printf("Origem %d existe colocando o destino %d como aresta\n\n", origem[a],destino[a]);
                            }
                        }
                    }
                    if(temDestino){
                        
                        Iterator i = grafo.iterator();
                        while(i.hasNext()){
                            temp = (Vertice)i.next();
                            temp = temp.getVerticeExists(destino[a]);
                            if(temp != null){
                                temp.setProximoVertice(new Vertice("",origem[a],null,null));
                                temp = temp.getVerticeExists(origem[a]);
                                numDeAresta++;
                                temp.setProximaAresta(new Aresta(destino[a],valor[a],null));
                                //System.out.printf("Destino %d existe colocando o Origem %d como aresta\n\n",destino[a], origem[a]);
                            }
                        }  
                    }
                    
                }else{
                    //insere uma nova arvore
                    //System.out.printf("Não existe nenhum inserindo origem e destino como aresta\n\n");
                    numDeAresta++;
                    grafo.add( new Vertice("",origem[a],new Aresta(destino[a], valor[a], null),new Vertice("",destino[a],null,null)));
                }
                    
                
            }
        }
        
        //Se possúi mais de um grafo então ele é desconexo
        if(grafo.size() != 1){
            return new int[0];
        }
        
        /**
         * Quarta etapa então é a criação de um array que comporte
         *  toda as origem,destino e por último o custo total
         */
        int[] conjunto = new int[(2*numDeAresta)+1];
        int custo=0;
        Iterator aba= grafo.iterator();
        int a=-1;
        while(aba.hasNext()){
            temp = (Vertice)aba.next();
            while(temp != null){
                arestaTemp = temp.getProximaAresta();
                while(arestaTemp != null){
                    conjunto[++a] = temp.ID;
                    conjunto[++a]= arestaTemp.getVerticeID();
                    custo += arestaTemp.getValor();
                    arestaTemp = arestaTemp.getProximaAresta();
                }
                temp = temp.proximoVertice;
            }
        }
        conjunto[++a] = custo;
        return conjunto;
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
    
    /**
     * Retorna o menor caminho entre dois vertices no grafo
     * @param Vertice raiz - raiz do grafo que se deseja procurar
     * @param int vertID1 - Início do caminho no grafo
     * @param int vertID2 - Fim do caminho no grafo
     * 
     * @return ?
     */
    public static int[] getMenorCaminho(Vertice raiz, int vertID1, int vertID2){
        Vertice vOrigem = raiz.getVerticeExists(vertID1);
        Vertice vDestino = raiz.getVerticeExists(vertID2);
        
        /**
         * Se ambos não existem então retorna vazio
         */
        if( vOrigem == null || vDestino == null){
            return new int[0];
        }
        
        /**
         * distancia[]  representa o menor caminho de i a j
         * vertices[]   todos os vertices
         * precedente[] informa qual é o vertice precente
         * visitado[]   informa se o vértice já foi visitado 
         */
        int numDeVertices = raiz.getNumeroDeVertices();
        int[] distancia = new int[numDeVertices];
        int[] precedente = new int[numDeVertices];
        int[] vertices =raiz.getListaDeVerticesID();
        boolean[] visitado = new boolean[numDeVertices];
        
        /**
         * Nenhum foi visitado
         * A distancia é a máxima
         * Precendente é -1 por padrão
         */
        java.util.Arrays.fill(visitado,Boolean.FALSE);
        java.util.Arrays.fill(distancia,Integer.MAX_VALUE);  
        java.util.Arrays.fill(precedente,-1);
            
        Aresta arestaTemp;
        Vertice verticeTemp;
        
        int posicaoVerticeID2=0;
        for(int a=0; a < vertices.length; a++){
            if( vertices[a] == vertID1){
                distancia[a] =0;
            }
            if(vertices[a] == vertID2){
                posicaoVerticeID2 = a;
            }
            
        }
        
        int valorAnteriorDaDistancia;
        
        /**
         * Varre todo o grafo para que seja possível
         *  analisar e estipular o peso.
         */
        while(true){
            
            /**
             * ENTENDA O QUE SE FAZ AQUI:
             * Enquanto há vertice não visitados
             *      pega o vertice N não visitado cuja estimativa seja a menor entre todos não visitados
             *      visita o vertice N
             *      Para toda vertice adjacencia I que seja sucessor do vertice N
             *          some a estimativa do vertice N com o custo do arco N,I
             *          Caso esta soma seja melhor que a estimavia anterior para I, substitua e anote N como precedente de I
             */
            
            int n = -1;

            for(int i = 0; i < numDeVertices; i++){
                if (!visitado[i] && (n < 0 || distancia[i] < distancia[n]))
                    n = i;
            }

            if(n < 0)
                break;
            
            visitado[n] = true;
            verticeTemp = raiz.getVerticeExists(vertices[n]);
            for (int i = 0; i < numDeVertices; i++){
                
                arestaTemp = verticeTemp.getArestaExists(vertices[i]);
                
                if( arestaTemp != null && ( distancia[i] > distancia[n]+arestaTemp.getValor() ) ){
                    
                    valorAnteriorDaDistancia = distancia[i];
                    distancia[i] = distancia[n] + arestaTemp.getValor();
                    
                    if( valorAnteriorDaDistancia > distancia[i]){
                        precedente[i] = verticeTemp.getID(); 
                    }
                    
                }
            }      
        }
        
        
        /**
         * Preparando dados para a saída
         * Pegando do final para o início segundo
         * os vertices precedente
         */
        LinkedList<Integer> valores = new LinkedList<Integer>();
        
        int vertice = vertID2;
        valores.addLast(vertice);
        boolean continua=true;
        while(continua){
            
            for(int a=0; a < numDeVertices;a++){
                if( vertices[a] == vertice){
                    vertice = precedente[a];
                    //System.out.printf(" %d-->",vertice);
                    if(vertice == -1){
                        continua = false;
                    }else{
                        valores.addFirst(vertice);
                    }
                } 
            }
        }
        
        //Transforma o linkedList em um array
        int[] retorno = new int[valores.size()+1];
        int a=0;
        Iterator i = valores.iterator();
        while(i.hasNext()){
            retorno[a++] = (int)i.next();
        }
        retorno[a]=distancia[posicaoVerticeID2];
        
        return retorno;
    }
    
}