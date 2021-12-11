import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;

public class Scraper {

    private final int profundidad;
    private final String fuente;

    private final DirectedGraph<String, DefaultEdge> grafo
            = new DefaultDirectedGraph<>(DefaultEdge.class);

    public Scraper(String fuente, int profundidad){
        //Explora todos los links de la fuente sin buscar
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
            Document doc = Jsoup.connect(urlBase).userAgent("Mozilla").get();
            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                String link = element.attr("href");
                link = limpiaLink(urlBase, link);
                if(link.contains("http")) {
                    grafo.addVertex(link);
                    grafo.addEdge(urlBase, link);
                    exploradorRecursivo(link, iteracionLocal);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String limpiaLink(String urlBase, String link) {
        if(link.charAt(0) == '/') link = urlBase + link;
        if(link.contains("https")){
            link = link.replace("https", "http");
        }
        if(link.contains("www.")){
            link = link.replace("www.", "");
        }
        return link;

    }

    public int getCantidadDeAristasSalientes(String pagina){
        return grafo.outgoingEdgesOf(pagina).size();
    }
}
