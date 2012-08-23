/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import java.util.HashMap;
import java.util.Scanner;
public class Grafo{
    private Vertice raiz;
    private Vertice vertice;
    private Scanner leitor;
    
    public Grafo(){    
        leitor = new Scanner(System.in);
    }
    
    /**
     * Método para a inicialização da matriz de adjacencia,
     *  e da string contendo o nome de cada vertice
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
        //vertice = raiz.getVerticeExists(verticeID);
        if( raiz.deleteVertice(verticeID) ){
            
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

        HashMap<String, Integer> caseHash = new HashMap<String, Integer>();
        caseHash.put("get", 1);  
        caseHash.put("delete", 2);  
        caseHash.put("vizinhos", 3);
        caseHash.put("conexao", 4);
        caseHash.put("ordemtop", 5);
        caseHash.put("arvoreminima", 6);
        caseHash.put("menorcaminho", 7);
        caseHash.put("remove", 8);  
        
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
                                break;  
                            }
                            case 8: {
                                // Remover Aresta
                                System.out.println(this.removeArestaJSONid(Integer.parseInt(parteComando[1]),Integer.parseInt(parteComando[2])));
                                break;  
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
                            int inicioDoNome = comando.indexOf("\"");
                            raiz.setNovoVertice(Integer.parseInt(parteComando[0]), comando.substring(inicioDoNome, comando.length()-1));
                        }catch(Exception eeee){    
                        }
                        break;
                    case 2:
                        //ARESTA
                        try{
                            raiz.setNovaAresta(Integer.parseInt(parteComando[0]),Integer.parseInt(parteComando[1]),Integer.parseInt(parteComando[2]));
                        }catch(Exception eee){
                        }
                        break;
                }
            }
            comando = leitor.nextLine();        
        }

    }
    
    public String getArvoreMinimaJSON(){
        
        
        raiz.getArvoreMinima();
        return "";
    }
    
    public static void main(String args[]){
        Grafo grafo = new Grafo();
        grafo.inicialize();
        grafo.lerComandos();
    }
    
}