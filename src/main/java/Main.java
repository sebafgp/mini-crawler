import java.util.HashSet;
import java.util.Set;

public class Main {

    public static Set<String> uniqueURL = new HashSet<String>();
    public static String my_site;

    public static void main(String[] args) {

        int profundidad = 0; // profundidad = [0, x[, con 0 siendo la raiz
        String url = "http://ubiobio.cl/";
        String destino = "";
        Scraper scraper = new Scraper(url, profundidad);

        for (String link : scraper.findLinks(url)) {
            System.out.println(link);
        }
        System.out.println(scraper.getCantidadDeAristasSalientes(url));
    }
}
