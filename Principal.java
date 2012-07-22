/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import java.util.ArrayList;
import java.util.Scanner;
public class Principal{
    private Vertice raiz;
    private Vertice vertice;
    private Aresta aresta;
    private Scanner leitor;
    
    public Principal(){    
        leitor = new Scanner(System.in);
    }
    
    /**
     * Método para a inicialização da matriz de adjacencia,
     *  e da string contendo o nome de cada vertice
     * 
     * @return void
     */
    public void inicialize(){
        raiz = new Vertice("RAIZ", -999, null, null);
    }
    
    /**
     * Método para inserir o valor na matriz, recebe como parametros
     *  a linha, coluna e valor
     * @param int verticeOrigemID
     * @param int verticeDestinoD
     * @param int valor
     * 
     * @return void
     */
    public boolean setValorMatriz(int verticeOrigemID, int verticeDestinoD, int valor){
        vertice = raiz.getProximoVertice();
        while( vertice != null )
        {
            if( vertice.getID() == verticeOrigemID){
                aresta = vertice.getProximaAresta();
                while( aresta != null)
                {
                    if( aresta.getVerticeID() == verticeDestinoD){
                        aresta.setValor(valor);
                        return true;
                    }
                    else{
                        aresta = aresta.getProximaAresta();
                    }
                }
            }
            else{
                vertice = vertice.getProximoVertice();
            } 
        }
        return false;
    }
    
    /**
     * Método de obtenção do peso na tabela. Recebe como parametro
     *  a linha e a coluna para se obter o valor
     * @param int linha
     * @param int coluna
     * 
     * @return int peso
     */
    private int getValorMatriz(int verticeOrigemID, int verticeDestinoID){
        vertice = raiz.getVerticeExists(verticeOrigemID);
        
        if( vertice != null )
        {
            aresta = vertice.getArestaExists(verticeDestinoID);
            if(aresta != null)
                return aresta.getValor();
        }
        return -1;
    }
    
    /**
     * Método que retorna um array com os vertices vizinhos do
     *  vertice passado
     * @param int vertice
     * 
     * @return int[]vizinho
     */
    private int[] getVizinhosDaMatriz(int verticeID){
        
        vertice = raiz.getProximoVertice();
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
        vertice = raiz.getVerticeExists(verticeID);
        if( vertice != null ){
            raiz.deleteVerticeDaMatriz(verticeID);
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
    public String vizinhoJSONid(int verticeID){
        vertice = raiz.getVerticeExists(verticeID);
        if( vertice != null){
            String retorno = String.format("{\"vizinhos\":{\"ID\":%d, \"resposta\":\"sucesso\", \"vizinhos\":[", verticeID);
            int[] vizinho = this.getVizinhosDaMatriz(verticeID);
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
    public String conexaoJSONconexaoid(int verticeOrigemId, int verticeDestinoId){
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
    public String ordemTopologicaJSON(){
        String retorno = "{\"ordemtop\":[";
        //int[] ordem = raiz.getOrdemTopologica();
            //for(int a=0; a < ordem.length;a++){
                //retorno+=String.format("%d", ordem[a]);
                //if( a+1 < ordem.length ){
                //    retorno+=String.format(",", ordem[a]);
               // }
           // }
        return retorno+="]}"; 
    }
    
    /**
     * Insere um novo Vertice
     * 
     * @param int verticeID
     * @param String nome 
     */
    public void setNovoVertice(int verticeID, String nome){
        raiz.setProximoVertice(nome, verticeID);
    }
    
    /**
     * Método que insere uma nova aresta ao vertice
     * @param int verticeOrigemID
     * @param int verticeDestinoID
     * @param int valor 
     */
    public void setNovaAresta(int verticeOrigemID, int verticeDestinoID, int valor){        
        vertice = raiz.getVerticeExists(verticeOrigemID);
        if( vertice != null )
        {
            aresta = vertice.getArestaExists(verticeOrigemID);
            if( aresta != null){
                    return;
            }
            vertice.setProximaAresta(verticeDestinoID,valor);
        }
    }
    
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
            }
            
            //Em posse do tipo de comando segue-se o entendimento do comando
            if(tipoLido == false){
                switch(tipoComando){
                    case 0:
                        //QUERIE
                        if(parteComando.length == 3){
                            
                        }
                        break;
                    case 1:
                        //VERTICE
                        System.out.println("vertice");
                        break;
                }
            }
            else{
                System.out.printf("agora tipo: %s\n",parteComando[0] );
            }
            comando = leitor.nextLine();        
        }

    }
    
    public static void main(String args[]){
        Principal grafo = new Principal();
        grafo.inicialize();
        grafo.lerComandos();
        //grafo.setNovoVertice(1, "glauco");
        //grafo.setNovoVertice(2, "maria");
        //grafo.setNovoVertice(3, "tiago");
        //grafo.setNovoVertice(4, "marta");
        //grafo.setNovaAresta(1, 2, 3);
        //grafo.setNovaAresta(1, 3, 4);
        //grafo.setNovaAresta(1, 4, 5);
        //grafo.setNovaAresta(2, 3, 6);
        //grafo.setNovaAresta(2, 4, 7);
        //System.out.printf("%s\n", grafo.conexaoJSONconexaoid(1, 3));
        //System.out.printf("%s\n", grafo.conexaoJSONconexaoid(1, 2));
        //System.out.printf("%s\n", grafo.conexaoJSONconexaoid(1, 4));
        //grafo.raiz.getOrdemTopologica();
        //System.out.printf("%s\n", grafo.getJSONid(2));
    }
    
}
