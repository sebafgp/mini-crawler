import org.jgrapht.graph.DefaultEdge;

public class Main {

    public static void main(String[] args) {

        int profundidad = 2; // profundidad = [0, x], con 0 siendo la raiz
        String urlBase = "http://ubiobio.cl/";
        String destino = "";
        Scraper scraper = new Scraper(urlBase, profundidad);
        scraper.exploraLinks();

        System.out.println(scraper.getCantidadDeAristasSalientes(urlBase));
    }
}
