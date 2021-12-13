# Tarea_2_IA

Este proyecto considera dos clases: `Main.java` y `Scraper.java`. La idea principal del proyecto es llenar un grafo con páginas web como nodos y una URL inicio indicada en la clase Main como raíz, y luego buscar una conexión a una página destino utilizando el algoritmo de búsqueda BFS (Breadth First Search o Búsqueda por Anchura)

## Clases

### Main.java

Esta clase es la clase controladora del proyecto. Indica el grafo a utilizar y su profundidad y las URL de inicio y destino del problema.

#### Funcionamiento

1. Declara las variables del problema.
2. Declara un objeto `Scraper` para que inicialice internamente el grafo con los datos del problema.
3. Le indica a `Scraper` que busque los links para llenar el grafo.
4. Luego de que se llene el grafo, llama una búsqueda BFS para verificar si hay una conexión entre el nodo de inicio y el final.

### Scraper.java

Esta clase se encarga de llenar el grafo recursivamente, con una profundidad indicada por la clase `Main` al ser invocado. Profundidad en este caso significa cuántos links debe adentrarse la raíz dentro de las páginas, por ejemplo, para una profundidad 2 se le indica al grafo que la raíz debe guardar todos los links apuntados por ésta, y luego también guardar todos los links apuntados por cada una de las páginas guardadas.

Al reconocer un link, intentará formatearlo para mantener una consistencia en los datos guardados. Los cambios se indican a continuación:

1. Cambia los enlaces relativos a enlaces absolutos. Por ejemplo, si la página es <http://ubiobio.cl/> y el link relativo llama a `/admision` el link a guardar será <http://ubiobio.cl/admision>
2. Cambia todos los links `https` a `http`, y elimina el subdominio `www.` por simplicidad. Esto significa que, por ejemplo, las variaciones <http://ubiobio.cl/>, <https://ubiobio.cl/>, <http://www.ubiobio.cl/> y <https://www.ubiobio.cl/> apuntarán siempre a <http://ubiobio.cl/>.
3. Limpia espacios blancos. Por ejemplo `http://example.com/esto es una prueba` cambia a <http://example.com/esto%20es%20una%20prueba>
4. Agrega un `/` al final de las páginas que no lo contengan por consistencia. Por ejemplo si se quiere guardar <http://ubiobio.cl> se guardará <http://ubiobio.cl/>

#### Funcionamiento

1. Conecta a la página con URL dado para obtener su archivo.
2. Encuentra todos los elementos que sean links (`href`).
3. Formatea el link.
4. Si sigue siendo un link válido agregar el vértice al grafo y conectarlo con el link base.
5. Itera la función sobre el link encontrado.

## Ejemplo

### Si existe una ruta

Dadas las siguientes condiciones: 
| Variable    | Valor |
| ----------- | ----------- |
| profundidad | 2       |
| urlBase  | http://ubiobio.cl/ |
| destino  | http://mevacuno.gob.cl/ |

La salida mostrada por consola será:

```
Ruta encontrada:
-> http://ubiobio.cl/
-> http://educacionsuperior.mineduc.cl/
-> http://mevacuno.gob.cl/
```

### Si no existe una ruta

Dadas las siguientes condiciones: 
| Variable    | Valor |
| ----------- | ----------- |
| profundidad | 2       |
| urlBase  | http://ubiobio.cl/ |
| destino  | http://google.cl/ |

La salida mostrada por consola será:

```
La pagina destino no se encuentra en el grafo.
```

## Consideraciones

- Se asume que los links `urlBase` y `destino` en `Main` están formateados correctamente
- El programa no reconocerá los links que puedan mostrarse luego de cargar un script
- El programa considera links que estén contenidos en `<a href>`

## Dependencias

Para este proyecto se utilizó [JgraphT](https://github.com/jgrapht/jgrapht) para manejar el grafo y la búsqueda en éste, y [Jsoup](https://github.com/jhy/jsoup/) para manejar las conexiones a las páginas y el scraping de sus documentos. Las siguientes dependencias se incluyen en el archivo `pom.xml`:

### JgraphT

```
<dependency>
    <groupId>org.jgrapht</groupId>
    <artifactId>jgrapht-core</artifactId>
    <version>1.5.1</version>
</dependency>
```

### Jsoup

```
<dependency>
    <!-- jsoup HTML parser library @ http://jsoup.org/ -->
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
```
