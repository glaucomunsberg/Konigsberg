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
                aresta = vertice.getAresta();
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
        vertice = raiz.getProximoVertice();
        while( vertice != null ){
            if( vertice.getID() == verticeOrigemID){
                aresta = vertice.getAresta();
                while( aresta != null){
                    if( aresta.getVerticeID() == verticeDestinoID){
                        return aresta.getValor();
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
                 aresta = vertice.getAresta();
                 while( aresta != null)
                 {
                     array.add( aresta.getVerticeID());
                     aresta = aresta.getProximaAresta();
                 }
                 int[] arrayDeInt = new int[array.size()];
                 for (int i=0; i < array.size(); i++)
                 {
                    arrayDeInt[i] = array.get(i).intValue();
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
     * Retorna a ordem topologica da matriz em questão
     * 
     * @return int[] ordemTopologica 
     */
    private int[] getOrdemTopologica(){
        int[] ordem = new int[1];
        return ordem;
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
        vertice = raiz.getVerticeExists(verticeOrigemId);
        aresta = vertice.getArestaExists(verticeDestinoId);
        if( vertice != null && aresta != null){
            if( this.getValorMatriz(verticeOrigemId, verticeDestinoId) >= 0)
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
        int[] ordem = this.getOrdemTopologica();
            for(int a=0; a < ordem.length;a++){
                retorno+=String.format("%d", ordem[a]);
                if( a+1 < ordem.length ){
                    retorno+=String.format(",", ordem[a]);
                }
            }
        return retorno+="]}"; 
    }
    
    /**
     * Insere um novo Vertice
     * 
     * @param int verticeID
     * @param String nome 
     */
    private void setNovoVertice(int verticeID, String nome){
        vertice = raiz.getProximoVertice();
        while( vertice != null ){
            if(vertice.getID() == verticeID ){
                return;
            }else{
                vertice = vertice.getProximoVertice();
            }
        }
        vertice.setProximoVertice(new Vertice(nome, verticeID, null, null));
        
    }
    
    public void setNovaAresta(int verticeOrigemID, int verticeDestinoID, int valor){
        Vertice vert = raiz.getVerticeExists(verticeDestinoID);
        if(vert == null){
            return;
        }
        
        vertice = raiz.getProximoVertice();
        while( vertice != null ){
            if(vertice.getID() == verticeOrigemID ){
                aresta = vertice.getAresta();
                while( aresta != null){
                    if( aresta.getVerticeID() == verticeDestinoID){
                        aresta.setValor(valor);
                        return;
                    }
                    else{
                        aresta = aresta.getProximaAresta();
                    }
                }
                aresta.setProximaAresta(new Aresta(verticeDestinoID,valor,null));
                return;
            }else{
                vertice = vertice.getProximoVertice();
            }
        }
    }
    
}
