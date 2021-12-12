import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        final DefaultDirectedGraph<String, DefaultEdge> grafo
                = new DefaultDirectedGraph<>(DefaultEdge.class);

        int profundidad = 2; // profundidad = [0, x], con 0 siendo la raiz
                             // para profundidad 3 puede tardar ~10 minutos en ejecutarse
        String urlBase = "http://ubiobio.cl/";
        String destino = "http://mevacuno.gob.cl/";
        Scraper scraper = new Scraper(grafo, urlBase, profundidad);
        scraper.exploraLinks();

        BFSShortestPath bfs = new BFSShortestPath(grafo);
        try {
            GraphPath camino = bfs.getPath(urlBase, destino);
            List<String> nodos = camino.getVertexList();

            System.out.println("Ruta encontrada:");

            for (String link : nodos) {
                System.out.println("-> " + link);
            }
        }catch (IllegalArgumentException e){
            System.out.println("La pagina destino no se encuentra en el grafo.");
        }


    }
}
