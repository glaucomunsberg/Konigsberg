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
    private void setValorMatriz(int linha, int coluna, int valor){
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
    private void setNomeVertice(int vertice, String nome){
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
     * Respeitando o JSON esse método retorna a string associada ao vértice de número ID
     * se este existir, ou indica a ocorrência de falha
     * @param int vertice
     * 
     * @return String verticeFormatoJSON
     */
    private String getJSONid(int vertice){
        
        if( vertice < this.tamanhoMaximo )
        {
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"%s\", \"resposta\":\"sucesso\"}}", vertice+1,this.verticeNome[vertice] );
        }else
        {
            return String.format("{\"vertice\":{\"ID\":%d, \"dado\":\"\", \"resposta\":\"falha\"}}", vertice);
        }
    }
}
