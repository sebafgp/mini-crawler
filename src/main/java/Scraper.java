import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Scraper {

    private int profundidad;
    private String destino;

    private DirectedGraph<String, DefaultEdge> grafo
            = new DefaultDirectedGraph<>(DefaultEdge.class);

    public Scraper(String fuente, int profundidad){
        //Explora todos los links de la fuente sin buscar
        this.profundidad = profundidad;
        grafo.addVertex(fuente);
    }

    public Scraper(String fuente, String destino, int profundidad){
        this.profundidad = profundidad;
        this.destino = destino;
        grafo.addVertex(fuente);
    }

    public Set<String> findLinks(String url) {
        try {
            Set<String> links = new HashSet<>();

            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                String link = element.attr("href");
                link = limpiaLink(url, link);

                if(link.contains("http")) {
                    links.add(link);
                    grafo.addVertex(link);
                    grafo.addEdge(url, link);
                }
            }

            return links;

        } catch (IOException ex) {
            return new HashSet<>();
        }
    }

    private String limpiaLink(String base, String link) {
        if(link.charAt(0) == '/') link = base + link;
        if(link.contains("https")){
            link.replace("https", "http");
        }
        if(link.contains("www.")){
            link.replace("www.", "");
        }
        return link;

    }

    public int getCantidadDeAristasSalientes(String pagina){
        return grafo.outgoingEdgesOf(pagina).size();
    }
}
