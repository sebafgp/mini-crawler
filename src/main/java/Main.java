import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Main {

    public static void main(String[] args) {

        final DefaultDirectedGraph<String, DefaultEdge> grafo
                = new DefaultDirectedGraph<>(DefaultEdge.class);

        int profundidad = 2; // profundidad = [0, x], con 0 siendo la raiz
        String urlBase = "http://ubiobio.cl/";
        String destino = "http://mevacuno.gob.cl/";
        Scraper scraper = new Scraper(grafo, urlBase, profundidad);
        scraper.exploraLinks();

        System.out.println(scraper.getCantidadDeAristasSalientes(urlBase));
    }
}
