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

    JFrame janela;
    
    public GrafoGrafico(){    
        super();
        janela = new JFrame();
        janela.setVisible(false);
        janela.setSize(1204, 686);
        janela.setTitle("Grafico Gráfico!");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                                this.modoGrafico(raiz);
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
    
    
    
    
    public void modoGrafico(Vertice g){
        
        
        mxGraph graph = new mxGraph();
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        Object parent = graph.getDefaultParent();
        Random random = new Random();
        
        graph.getModel().beginUpdate();
        LinkedList<Object> aux = new LinkedList<Object>();
        
        //int totalVertices = g.getNumeroDeVertices();
        
        int verticesID[] = g.getListaDeVerticesID();
        float[][][] posicao= new float[verticesID.length][2][1];
        
        for(int a=0; a < verticesID.length;a++){
            
        }
        
        
        
        int condicaoParada = 0;
        janela.setVisible(true);
        boolean parar = true;
        
        Vertice verticeDaVez;
        Vertice verticeVizinho;
        while(parar){
                try {
                        graph = new mxGraph();
                        graph.getModel().beginUpdate();
                        
                        if(condicaoParada == 0){
                            
                            for (int i = 0; i < verticesID.length; i++) {
                                posicao[i][0][0] = random.nextInt(1014);
                                posicao[i][1][0] = random.nextInt(748);
                                Object o = graph.insertVertex(parent, null, g.getVerticeExists(verticesID[i]).getNomeVertice(), posicao[i][0][0], posicao[i][1][0], 30, 30, "shape=ellipse;");
                            }
                            condicaoParada++;
                        }else{
                            for(int a=0; a < verticesID.length; a++){
                                
                                int[] visinhosID = g.getVizinhos(verticesID[a]);
                                for(int i =0; i < visinhosID.length;i++){
                                    
                                }
                            }
                        }

            } finally {

                graph.getModel().endUpdate();
                graphComponent.setGraph(graph);
                graph.setMultigraph(false);
                graph.setAllowDanglingEdges(false);
                graphComponent.setConnectable(false);
                graphComponent.setToolTips(false);
                janela.getContentPane().add(graphComponent);
            }
        }
        

        
    }
    
    public static void main(String args[]){
        GrafoGrafico grafo = new GrafoGrafico();
        grafo.inicialize();
        grafo.lerComandos();
    }
    
}