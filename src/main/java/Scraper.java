import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraper {

    private final int profundidad;
    private final String fuente;

    private final DefaultDirectedGraph<String, DefaultEdge> grafo;

    public Scraper(DefaultDirectedGraph<String, DefaultEdge> grafo, String fuente, int profundidad){
        //Explora todos los links de la fuente sin buscar
        this.grafo = grafo;
        this.fuente = fuente;
        this.profundidad = profundidad;
        grafo.addVertex(fuente);
    }

    public void exploraLinks() {
        exploradorRecursivo(fuente, 0);
    }

    private void exploradorRecursivo(String urlBase, int iteracionLocal){
        if (++iteracionLocal > profundidad) return;
        try {
            Document doc = Jsoup.connect(urlBase).userAgent("Mozilla")
                    .get();
            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                String link = element.attr("href");
                link = limpiaLink(urlBase, link);
                if(link.startsWith("http")) {
                    grafo.addVertex(link);
                    grafo.addEdge(urlBase, link);
                    exploradorRecursivo(link, iteracionLocal);
                }
            }

        } catch (IOException ex) {
            // catches
            // errores en certificados SSL
        }

    }

    private String limpiaLink(String urlBase, String link) {
        if(!link.isBlank()) {
            if (link.charAt(0) == '/') link = urlBase + link;
            if (link.contains("https")) {
                link = link.replace("https", "http");
            }
            if (link.contains("www.")) {
                link = link.replace("www.", "");
            }
            return link;
        } else return "";

    }

    public int getCantidadDeAristasSalientes(String pagina){
        return grafo.outgoingEdgesOf(pagina).size();
    }
}
