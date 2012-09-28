/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;

public class GrafoGrafico extends Grafo{

    public GrafoGrafico(){    
        super();
        
    }

    /**
     * Método que faz a leitura de dados e faz o retonro
     *  de cada comando inserido
     *  Para ver mais sobre 'comandos' leia o README
     */
    @Override
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
                                int a, b;
                                try{
                                   a = Integer.parseInt(parteComando[1]);
                                   b = Integer.parseInt(parteComando[2]);
                                   if(a < 600)
                                       a = 600;
                                   if(b < 800)
                                       b = 800;
                                }catch(Exception e){

                                }
                                    a = 1204;
                                    b = 768;
                                this.modoGrafico(raiz,a,b);
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
     * Método que cria o grafo segundo o seu estado atual
     * @param Vertice grafo - rais do grafo
     * @param int width
     * @param int height 
     */
    public void modoGrafico(Vertice grafo, int width, int height){
                InterfaceGrafica frame = new InterfaceGrafica(grafo,width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
    }
    
    public static void main(String args[]){
        GrafoGrafico grafo = new GrafoGrafico();
        grafo.inicialize();
        grafo.lerComandos();
    }
    
}
/**
 * Classe do JFrame do Grafo
 *  serve apenas para a geração do grafo
 * @author glaucoroberto
 */
class InterfaceGrafica extends JFrame {

    private static final long serialVersionUID = -2707712944901661771L;

    public InterfaceGrafica(Vertice g, int width, int height) {
        super("Grafos");

        /**
         * Inicia o grafo
         */
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        
        /**
         * Inicia apoios
         */

        int totalVertices = g.getNumeroDeVertices();
        int[][] posicao= new int[totalVertices][3];
        int pos = -1;
        int verticeID1, verticeID2= 0;
        Object vertice1, vertice2;
        String[] nomes = new String[totalVertices];
        LinkedList<Object> aux = new LinkedList<Object>();
        Random random = new Random();
        Vertice verticeTemp = g.getProximoVertice();
        Aresta arestaTemp;
        
        /**
         * Insere os vertices
         */
        while(verticeTemp != null){
            pos++;
            nomes[pos] = verticeTemp.getNomeVertice();
            posicao[pos][0] = verticeTemp.getID();
            posicao[pos][1] = random.nextInt(width);
            posicao[pos][2] = random.nextInt(height);
            verticeTemp = verticeTemp.getProximoVertice();
            Object o = graph.insertVertex(parent, null, nomes[pos],posicao[pos][1], posicao[pos][2], 30, 30, "shape=ellipse;");
        }
        
        /**
         * Insere aresta
         */
        verticeTemp = g.getProximoVertice();
        while(verticeTemp != null){
            
            arestaTemp = verticeTemp.getProximaAresta();
            verticeID1 = verticeTemp.getID();
            while(arestaTemp != null){
                
                verticeID2 = arestaTemp.getVerticeID();
                //vertice1 = aux.get(verticeID1);
                //vertice2 = aux.get(verticeID2);
                
                
                arestaTemp = arestaTemp.getProximaAresta();
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }
        
        
        
        
        
        
                
        /**
         * Finaliza o grafo para exibição
         */        
        graph.getModel().endUpdate();
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graph.setMultigraph(false);
        graph.setAllowDanglingEdges(false);
        graphComponent.setConnectable(false);
        graphComponent.setToolTips(false);
        getContentPane().add(graphComponent);

    }
    
}