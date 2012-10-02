/**
 *  Projeto Konigsberg se propõem a realizar o trabalho de EDII, proposto pelo professor Ricardo Matsumura Araújo. 
 *      O repositório recebe o nome 'Königsberg' em referencia ao problema histórico relacionado aos grafos
 *      @author glaucoroberto
 *      @version 0.1
 */

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.HashMap;
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
                                   if(b < 600)
                                       b = 600;
                                   if(a < 800)
                                       a = 800;
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
                frame.inicializar();
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
    private Vertice g;
    private int width;
    private int height;
    private mxGraph graph;
    private Object parent;
    mxGraphComponent graphComponent;
    
    public InterfaceGrafica(Vertice g, int width, int height) {
        super("Grafos");
        
        this.g = g;
        this.width = width;
        this.height = height;
        
        /**
         * Inicia o grafo
         */
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        /**
         * Finaliza o grafo para exibição
         */        
        graph.getModel().endUpdate();
        graphComponent = new mxGraphComponent(graph);
        graph.setMultigraph(false);
        graph.setAllowDanglingEdges(false);
        graphComponent.setConnectable(false);
        graphComponent.setToolTips(false);
        getContentPane().add(graphComponent);

    }
    
    
    public void algoritmoAtracaoRepulsao(){
        
        this.graph.refresh();
        
        this.graph.getModel().beginUpdate();
        this.graph.getModel().endUpdate();
        this.repaint();
        
        
        graph.repaint();
        
        this.graphComponent = new mxGraphComponent(graph);
        graph.setMultigraph(false);
        graph.setAllowDanglingEdges(false);
        this.graphComponent.setConnectable(false);
        this.graphComponent.setToolTips(false);
        getContentPane().add(graphComponent);
        
        Vertice verticeTemp = g.getProximoVertice();
        Vertice verticeVizinho;
        Aresta arestaTemp;
        int[] vizinhosID;
        int newX, newY;
        int id;
        
       while(verticeTemp != null ){
            id = verticeTemp.getID();
            vizinhosID = g.getVizinhos(id);
            System.out.printf("Tem %d vizinhos",vizinhosID.length);
            for(int a=0; a < vizinhosID.length; a++){
                
                 verticeVizinho = g.getVerticeExists(vizinhosID[a]);
                 newX = (verticeTemp.getX() + verticeVizinho.getX())/2; 
                 newY = (verticeTemp.getY() + verticeVizinho.getY())/2;
                 if ((newX < verticeTemp.getX()+30) || (newX < getX()+(-30))){
                    verticeVizinho.setX(newX);
                 } else {
                    verticeVizinho.setX(verticeTemp.getX()+50);
                 }
                 if ((newY < verticeTemp.getY()+30) ||  (newY < getY()+(-30))){
                    verticeVizinho.setY(newY);
                 } else {
                    verticeVizinho.setY(verticeTemp.getX()+50);
                 }    
            }

            verticeTemp = verticeTemp.getProximoVertice();
        }

        
        
        /**
         * Insere ao vertice posições aleatorias
         */
        verticeTemp = g.getProximoVertice();
        
        while(verticeTemp != null){
            Object o = graph.insertVertex(parent, null, verticeTemp.getID() , verticeTemp.getY(),verticeTemp.getX(), 30, 30, "shape=ellipse;");
            verticeTemp.setObject(o);
            verticeTemp = verticeTemp.getProximoVertice();
            
        }
        
        /**
         * Insere aresta segundo cada vertice
         */
        verticeTemp = g.getProximoVertice();
        while(verticeTemp != null){
            
            arestaTemp = verticeTemp.getProximaAresta();
            while(arestaTemp != null){
                graph.insertEdge(parent, null, arestaTemp.getValor(), verticeTemp.getObject(), g.getVerticeExists(arestaTemp.getVerticeID()).getObject());                
                arestaTemp = arestaTemp.getProximaAresta();
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }

    }
    
    public void inicializar(){
        graph.refresh();
        graph.getModel().beginUpdate();
        graph.getModel().endUpdate();
        this.repaint();
        
        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graph.setMultigraph(false);
        graph.setAllowDanglingEdges(false);
        graphComponent.setConnectable(false);
        graphComponent.setToolTips(false);
        getContentPane().add(graphComponent);
        
        
        Random random = new Random();
        Vertice verticeTemp = g.getProximoVertice();
        Aresta arestaTemp;
        
        /**
         * Insere ao vertice posições aleatorias
         */
        while(verticeTemp != null){
            verticeTemp.setY(random.nextInt(width-30));
            verticeTemp.setX(random.nextInt(height-30));
            Object o = graph.insertVertex(parent, null, verticeTemp.getID() , verticeTemp.getY(),verticeTemp.getX(), 30, 30, "shape=ellipse;");
            verticeTemp.setObject(o);
            verticeTemp = verticeTemp.getProximoVertice();
            
        }
        
        /**
         * Insere aresta segundo cada vertice
         */
        verticeTemp = g.getProximoVertice();
        while(verticeTemp != null){
            
            arestaTemp = verticeTemp.getProximaAresta();
            while(arestaTemp != null){
                graph.insertEdge(parent, null, arestaTemp.getValor(), verticeTemp.getObject(), g.getVerticeExists(arestaTemp.getVerticeID()).getObject());                
                arestaTemp = arestaTemp.getProximaAresta();
            }
            verticeTemp = verticeTemp.getProximoVertice();
        }

    }
}
