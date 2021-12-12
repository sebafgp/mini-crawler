import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraper {

    private final int profundidad;
    private final String fuente;

    private final DirectedGraph<String, DefaultEdge> grafo;

    public Scraper(DirectedGraph<String, DefaultEdge> grafo, String fuente, int profundidad){
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
        System.out.println(urlBase);
        if (++iteracionLocal > profundidad) return;
        try {
            Document doc = Jsoup.connect(urlBase).userAgent("Mozilla")
                    .get();
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
            // catches
            // errores en certificados SSL
        } catch (IllegalArgumentException ex){
            // catches
            // javascript:PicLensLite.start({feedUrl:'http://ubiobio.cl/culturaubb/wp-content/plugins/nextgen-gallery/xml/media-rss.php?gid=208&mode=gallery'});
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
