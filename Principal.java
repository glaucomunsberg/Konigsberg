/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import java.util.Scanner;
public class Principal{
    private String[] verticeNome;
    private int tamanhoMaximo;
    private int matriz[][];
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
        int dimensaoMaxima = 10;
        tamanhoMaximo = dimensaoMaxima;
        verticeNome = new String[dimensaoMaxima];
        matriz = new int[dimensaoMaxima][dimensaoMaxima];
    }
    
    /**
     * Método para inserir o valor na matriz, recebe como parametros
     *  a linha, coluna e valor
     * @param int linha
     * @param int coluna
     * @param int valor
     * 
     * @return void
     */
    public void setValorMatriz(int linha, int coluna, int valor){
        this.matriz[linha][coluna] = valor;
    }
    
    /**
     * Método de obtenção do peso na tabela. Recebe como parametro
     *  a linha e a coluna para se obter o valor
     * @param int linha
     * @param int coluna
     * 
     * @return int peso
     */
    private int getValorMatriz(int linha, int coluna){
        return matriz[linha][coluna];
    }
    
    /**
     * Insere o nome do vertice recebe o vertice e valors
     * @param int vertice
     * @param String nome
     * 
     * @return void
     */
    public void setNomeVertice(int vertice, String nome){
        verticeNome[vertice] = nome;
    }
    
    /**
     * Método que retorna o nome do vertice
     * @param int vertice
     * 
     * @return String nomeDoVertice
     */
    private String getNomeVertice(int vertice){
        return this.verticeNome[vertice];
    }
    
    /**
     * Método que retorna um array com os vertices vizinhos do
     *  vertice passado
     * @param int vertice
     * 
     * @return int[]vizinho
     */
    private int[] getVizinhosDaMatriz(int vertice){
        int[] vizinhos = new int[1];
        return vizinhos;
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
    public String getJSONid(int vertice){
        
        if( vertice < this.tamanhoMaximo ){
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"%s\", \"resposta\":\"sucesso\"}}", vertice+1,this.verticeNome[vertice] );
        }else{
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"\", \"resposta\":\"falha\"}}", vertice);
        }
    }
    
    /**
     * Método que remove da matriz o vertice, respeitando o modelo JSON
     * @param int vertice
     * @return String verticeRemovidoFormatoJSON
     */
    public String deleteJSONid(int vertice){
        
        if( vertice < this.tamanhoMaximo ){
            return String.format("{\"delete\":{\"ID\":%d,\"resposta\":\"sucesso\"}}", vertice+1);
        }else{
            return String.format("{\"delete\":{\"ID\":%d,\"resposta\":\"falha\"}}", vertice+1);
        }
    }
    
    /**
     * Método que retorna os vizinhos de vertice no formato JSON
     * @param int vertice
     * 
     * @return String vizinhosFormatoJSON
     */
    public String vizinhoJSONid(int vertice){
        
        if( vertice < this.tamanhoMaximo){
            String retorno = String.format("{\"vizinhos\":{\"ID\":%d, \"resposta\":\"sucesso\", \"vizinhos\":[", vertice+1);
            int[] vizinho = this.getVizinhosDaMatriz(vertice);
            for(int a=0; a < vizinho.length;a++){
                retorno+=String.format("%d", vizinho[a]);
                if( a+1 < vizinho.length ){
                    retorno+=String.format(",", vizinho[a]);
                }
            }
            retorno += "]}}";
            return retorno;
        }else{
            return String.format("{\"vizinhos\":{\"ID\":%d, \"resposta\":\"falha\", \"vizinhos\":[]}}", vertice+1);
        }
    }
    
    public String conexaoJSONconexaoid(int id1, int id2){
        if( id1 < this.tamanhoMaximo && id2 < this.tamanhoMaximo){
            if( this.getValorMatriz(id1, id2) >= 0)
            {
                return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"sucesso\", \"conexao\":\"sim\"}}", id1+1, id2+1);
            }else{
                return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"sucesso\", \"conexao\":\"não\"}}", id1+1, id2+1);
            }
        }else{
            return String.format("{\"conexao\":{\"ID1\":%d, \"ID2\":%d, \"resposta\":\"falha\", \"conexao\":\"\"}}", id1+1, id2+1);
        }
    }
    
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
}
