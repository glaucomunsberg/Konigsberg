/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
public class Grafo{
    private Vertice raiz;
    private Vertice vertice;
    private Scanner leitor;
    private int numMaximoDeVertice;
    private int numDeVertices;
    
    public Grafo(){    
        leitor = new Scanner(System.in);
        numMaximoDeVertice = Integer.MIN_VALUE;
        numDeVertices = 0;
    }
    
    /**
     * Método para a inicialização do grafo
     * 
     * @return void
     */
    public void inicialize(){
        raiz = new Vertice();
    }
    
    /**
     * Respeitando o JSON esse método retorna a string associada ao vértice de número ID
     *  se este existir, ou indica a ocorrência de falha
     * @param int vertice
     * 
     * @return String verticeFormatoJSON
     */
    public String getJSONid(int verticeID){
        vertice = raiz.getVerticeExists(verticeID);
        if( vertice != null ){
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"%s\", \"resposta\":\"sucesso\"}}", verticeID,vertice.getNomeVertice() );
        }else{
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"\", \"resposta\":\"falha\"}}", verticeID);
        }
    }
    
    /**
     * Método que remove da matriz o vertice, respeitando o modelo JSON
     * @param int vertice
     * @return String verticeRemovidoFormatoJSON
     */
    public String deleteJSONid(int verticeID){        
        if( raiz.removeVertice(verticeID) ){
            return String.format("{\"delete\":{\"ID\":%d,\"resposta\":\"sucesso\"}}", verticeID);
        }else{
            return String.format("{\"delete\":{\"ID\":%d,\"resposta\":\"falha\"}}", verticeID);
        }
    }
    
    /**
     * Método que retorna os vizinhos de vertice no formato JSON
     * @param int vertice
     * 
     * @return String vizinhosFormatoJSON
     */
    public String getVizinhoJSONid(int verticeID){
        vertice = raiz.getVerticeExists(verticeID);
        if( vertice != null){
            String retorno = String.format("{\"vizinhos\":{\"ID\":%d, \"resposta\":\"sucesso\", \"vizinhos\":[", verticeID);
            int[] vizinho = raiz.getVizinhos(verticeID);
            for(int a=0; a < vizinho.length;a++){
                retorno+=String.format("%d", vizinho[a]);
                if( a+1 < vizinho.length ){
                    retorno+=String.format(",", vizinho[a]);
                }
            }
            retorno += "]}}";
            return retorno;
        }else{
            return String.format("{\"vizinhos\":{\"ID\":%d, \"resposta\":\"falha\", \"vizinhos\":[]}}", verticeID);
        }
    }
    
    /**
     * Método que verifica se há conexão e retorna no formado JSON
     * @param int verticeOrigemId
     * @param int verticeDestinoId
     * @return String formatoJSON
     */
    public String getConexaoJSONconexaoid(int verticeOrigemId, int verticeDestinoId){
        Vertice verticeA = raiz.getVerticeExists(verticeOrigemId);
        Vertice verticeB = raiz.getVerticeExists(verticeDestinoId);
        if( verticeA != null && verticeB != null){
            Aresta arestaA = verticeA.getArestaExists(verticeDestinoId);
            if( arestaA != null)
            {
                return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"sucesso\", \"conexao\":\"sim\"}}", verticeOrigemId, verticeDestinoId);
            }else{
                return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"sucesso\", \"conexao\":\"não\"}}", verticeOrigemId, verticeDestinoId);
            }
        }else{
            return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"falha\", \"conexao\":\"\"}}", verticeOrigemId, verticeDestinoId);
        }
    }
    
    
    /**
     * Método que retorna a ordemTopologicaJSON
     * @return String formatoJSON
     */
    public String getOrdemTopologicaJSON(){
        int[] ordem = raiz.getOrdemTopologica();
        String retorno = "{\"ordemtop\":[";
            for(int a=0; a < ordem.length;a++){
                retorno+=String.format("%d", ordem[a]);
                if( a+1 < ordem.length ){
                    retorno+=String.format(",", ordem[a]);
                }
            }
        return retorno+="]}\n"; 
    }
    
    public String removeArestaJSONid(int id1, int id2){
        if( raiz.removeAresta(id1, id2)){
            return "{\"remove\":{\"ID1:"+id1+", \"ID2\":"+id2+",\"resposta\":\"sucesso\"}}";
        }
        else{
            return "{\"remove\":{\"ID1:"+id1+", \"ID2\":"+id2+",\"resposta\":\"falha\"}}";
        }
    }
    
    
    /**
     * Método que faz a leitura de dados e faz o retonro
     *  de cada comando inserido
     *  Para ver mais sobre 'comandos' leia o README
     */
    public void lerComandos(){
        
        /**
         * tipoComando
         *  0 = Queries
         *  1 = Arcs ou edgues
         *  2 = Vertices
         */
        int tipoComando=0;
        String comando = leitor.nextLine();
        boolean leuSeEDirecionado = false;
        boolean tipoLido;
        
        String nomeQueRecebera;
        int idQueRecebera;
        int valorQueRecebera;
        int inicioDoNome;

        HashMap<String, Integer> caseHash = new HashMap<String, Integer>();
        caseHash.put("get", 1);  
        caseHash.put("delete", 2);  
        caseHash.put("vizinhos", 3);
        caseHash.put("conexao", 4);
        caseHash.put("ordemtopologica", 5);
        caseHash.put("arvoreminima", 6);
        caseHash.put("menorcaminho", 7);
        caseHash.put("remove", 8);
        caseHash.put("modografico", 9);
        
        /**
         * Enquando não houver condição de parada prosegue
         */
        while( !comando.equals("@")){
            comando = comando.toLowerCase();
            tipoLido = false;     
            String parteComando[] = comando.split(" ");
            
            //Se for apenas uma linha quer dizer que mudará o tipo de comando
            // os que seguirá são desse tipo
            if(parteComando.length == 1){
                if(parteComando[0].equals("*queries")){
                    tipoComando = 0;
                    tipoLido = true;
                }else{
                    if(parteComando[0].equals("*arcs") || parteComando[0].equals("*edges") ){
                        tipoComando = 2;
                        if(!leuSeEDirecionado){
                            leuSeEDirecionado = true;
                            tipoLido = true;
                            if(parteComando[0].equals("*arcs")){
                                Vertice.setItsDirecionado(true);
                            }else{
                                Vertice.setItsDirecionado(false);
                            }
                        }
                    }else{
                        if( parteComando[0].equals("*vertices")){
                            tipoComando = 1;
                            tipoLido = true;                                
                        }
                    }
                }
            }else{
                
                if( parteComando[0].equals("*vertices")){
                    tipoComando = 1;
                    tipoLido = true;
                    if(numMaximoDeVertice == Integer.MIN_VALUE){
                        int numeroMax = Integer.parseInt(parteComando[1]);

                        if(numeroMax >= 0){

                            numMaximoDeVertice = numeroMax;
                        }
                    }                                
                }
            }
            
            //Em posse do tipo de comando segue-se o entendimento do comando
            if(tipoLido == false){
                switch(tipoComando){
                    case 0:
                        //QUERIE
                        //try{
                           switch (caseHash.containsKey(parteComando[0]) ? caseHash.get(parteComando[0]) : -1) { 
                            case 1: {
                                // Get Vertice
                                System.out.println(this.getJSONid(Integer.parseInt(parteComando[1])));
                                break;  
                            }  
                            case 2: {
                                // Remove Vértice
                                System.out.println(this.deleteJSONid(Integer.parseInt(parteComando[1])));
                                break;  
                            }  
                            case 3: {
                                // Vizinhos
                                System.out.println(this.getVizinhoJSONid(Integer.parseInt(parteComando[1])));
                                break;  
                            }
                            case 4: {
                                // Conexão
                                System.out.println(this.getConexaoJSONconexaoid( Integer.parseInt(parteComando[1]),Integer.parseInt(parteComando[2])));
                                break;  
                            }
                            case 5: {
                                // Ordem Topológica
                                System.out.printf(this.getOrdemTopologicaJSON());
                                break;  
                            }
                            case 6: {
                                System.out.printf(this.getArvoreMinimaJSON());
                                break;  
                            }
                            case 7: {
                                // Menor Caminho
                                System.out.println(this.getMenorCaminhoJSON( Integer.parseInt(parteComando[1]),Integer.parseInt(parteComando[2])));
                                break;  
                            }
                            case 8: {
                                // Remover Aresta
                                System.out.println(this.removeArestaJSONid(Integer.parseInt(parteComando[1]),Integer.parseInt(parteComando[2])));
                                break;  
                            }
                            case 9: {
                                // Modo Grafico
                            }
                            default:{  

                            }  
                            }  
                        //}
                        //catch(Exception eee){
                            
                        //}
                        break;
                    case 1:
                        //VERTICE
                        try{
                            if( numDeVertices >= numMaximoDeVertice){
                                break;
                            }else{
                                idQueRecebera = Integer.parseInt(parteComando[0]);
                                if(idQueRecebera >= 0){
                                    inicioDoNome = comando.indexOf("\"");
                                    nomeQueRecebera = comando.substring(inicioDoNome, comando.length()-1);
                                    raiz.setNovoVertice(idQueRecebera, nomeQueRecebera);
                                    numDeVertices++;
                                }
                                
                            }
                            
                        }catch(Exception eeee){    
                        }
                        break;
                    case 2:
                        //ARESTA
                        try{
                            valorQueRecebera = Integer.parseInt(parteComando[2]);
                            if(valorQueRecebera >= 0)
                                raiz.setNovaAresta(Integer.parseInt(parteComando[0]),Integer.parseInt(parteComando[1]),valorQueRecebera);
                        }catch(Exception eee){
                        }
                        break;
                }
            }
            comando = leitor.nextLine();        
        }

    }
    
    /**
     * Método que retonra o árvore mínima no formado
     *  JSON em forma de estring
     * 
     * @return String arvoreMinima
     */
    public String getArvoreMinimaJSON(){
        
        int[] arvore = Grafo.getArvoreMinima(raiz);
        String parte= "{\"arvoreminima\":{\"arestas\":[";
        if(arvore.length != 0){
           for(int a=0; a < arvore.length-1;a++){
               parte+=String.format("(%d,%d)", arvore[a],arvore[++a]);
               if( a < arvore.length-2){
                   parte+=",";
               }
           }
           parte+= String.format("], \"custo\":%d}}\n", arvore[arvore.length-1]);
        }
        else{
           parte+= "], \"custo\":}}\n";
        }
        
        return parte;
    }
    
    /**
     * Método que retorna uma string contendo o menor
     *  caminho no formato JSON
     * @param int vID1 - vertice de origem
     * @param int vID2 - vertice de destino
     * 
     * @return String menorCaminho
     */
    public String getMenorCaminhoJSON(int vID1, int vID2){
        int[] arvore = Grafo.getMenorCaminho(raiz, vID1, vID2);
        
        String parte= String.format("{\"menorcaminho\":{\"ID1\":%d,\"ID2\":%d,\"caminho\":[", vID1,vID2);
        if(arvore.length != 0){
           for(int a=0; a < arvore.length-1;a++){
               parte+=String.format("%d", arvore[a]);
               if( a < arvore.length-2){
                   parte+=",";
               }
           }
           parte+= String.format("], \"custo\":%d}}", arvore[arvore.length-1]);
        }
        else{
           parte+= "], \"custo\":}}";
        }
        
        return parte;
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
                origem[n] = temp.getID();
                destino[n] = arestaTemp.getVerticeID();
                valor[n] = arestaTemp.getValor();
                arestaTemp = arestaTemp.getProximaAresta();
            }
            temp = temp.getProximoVertice();
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
                    conjunto[++a] = temp.getID();
                    conjunto[++a]= arestaTemp.getVerticeID();
                    custo += arestaTemp.getValor();
                    arestaTemp = arestaTemp.getProximaAresta();
                }
                temp = temp.getProximoVertice();
            }
        }
        conjunto[++a] = custo;
        return conjunto;
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
            retorno[a++] = Integer.parseInt(i.next().toString());
        }
        retorno[a]=distancia[posicaoVerticeID2];
        
        return retorno;
    }
    
    public static void main(String args[]){
        Grafo grafo = new Grafo();
        grafo.inicialize();
        grafo.lerComandos();
    }
    
}