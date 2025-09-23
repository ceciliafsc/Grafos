import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Grafo {
    private Map<Integer, Map<Integer, Integer>> grafo;
    private int nVertices;
    private int nArestas;
    private int k;
    private int[][] dist;

    public Grafo() {
        this.grafo = new HashMap<>();
        this.nVertices = 0;
        this.nArestas = 0;
        this.dist = new int[1][1];
    }

    public Grafo(Map<Integer, Map<Integer, Integer>> grafo) {
        this.grafo = grafo;
        this.nVertices = grafo.size();
        
    }
    public int getnVertices() {
        return nVertices;
    }
    public int getnArestas() {
        return nArestas;
    }
    public int getK() {
        return k;
    }
    public void adicionarVertice(int v) {
        grafo.putIfAbsent(v, new HashMap<>());
        nVertices++;
    }
    public int getDist(int i, int j) {
        return dist[i][j];
    }

    // Retorna a peso da aresta (origem -> destino)
    public int getPeso(int origem, int destino) {
        if (grafo.containsKey(origem) && grafo.get(origem).containsKey(destino)) {
            return grafo.get(origem).get(destino);
        }
        return 0;
    }

    // Retorna o mapa completo do grafo
    public Map<Integer, Map<Integer, Integer>> getGrafo() {
        return grafo;
    }

    public static Grafo criarGrafo(String arq) throws Exception {
        FileReader fr = new FileReader(arq);
        BufferedReader br = new BufferedReader(fr);

        try {
            String primeiraLinha = br.readLine();
            String[] partes = primeiraLinha.split("\\s+");

            int n = Integer.parseInt(partes[0].trim()); // número de vértices
            int m = Integer.parseInt(partes[1].trim()); // número de arestas
            int k = Integer.parseInt(partes[2].trim()); // k

            Map<Integer, Map<Integer, Integer>> listaAdjacencia = new HashMap<>();
            String linha;

            // Lendo as arestas do arquivo
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    partes = linha.split("\\s+");
                    if (partes.length == 3) {
                        try {
                            int origem = Integer.parseInt(partes[0])-1; // ajusta para 0-indexed
                            int destino = Integer.parseInt(partes[1])-1;
                            int peso = Integer.parseInt(partes[2]);

                            listaAdjacencia.putIfAbsent(origem, new HashMap<>());
                            listaAdjacencia.putIfAbsent(destino, new HashMap<>());
                            listaAdjacencia.get(origem).put(destino, peso);
                            listaAdjacencia.get(destino).put(origem, peso); // grafo não direcionado

                        } catch (NumberFormatException e) {
                            System.err.println("Erro ao ler aresta: " + linha + " - " + e.getMessage());
                        }
                    } else {
                        System.err.println("Formato de linha incorreto: " + linha);
                    }
                }
            }
            Grafo grafo = new Grafo(listaAdjacencia);
            grafo.nVertices = n;
            grafo.nArestas = m;
            grafo.k = k;
            grafo.dist = floydWarshall(grafo);
            return grafo;
        } finally {
            br.close();
        }
    }

     public static int[][] floydWarshall(Grafo grafo) {
          int n = grafo.getnVertices();          
          int[][] dist = new int[n][n];
    
          // Inicializa a matriz de distâncias k=0
          for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
                    if (i == j) {
                         dist[i][j] = 0;
                    } else if (grafo.getPeso(i,j) != 0) {
                         dist[i][j] = grafo.getPeso(i,j);
                    } else {
                         dist[i][j] = Integer.MAX_VALUE/2; //infinito
                    }
               }
          }
     
          // Algoritmo de Floyd-Warshall
          for (int k = 0; k < n; k++) {
               for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                         if (dist[i][k] + dist[k][j] < dist[i][j]) {
                         dist[i][j] = dist[i][k] + dist[k][j];
                         }
                    }
               }
          }
     
          return dist;
     } 
     
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : grafo.entrySet()) {
            int origem = entry.getKey();
            for (Map.Entry<Integer, Integer> subEntry : entry.getValue().entrySet()) {
                int destino = subEntry.getKey();
                int peso = subEntry.getValue();
                sb.append(String.format("Aresta: %d -> %d (Peso: %d)%n", origem, destino, peso));
            }
        }
        return sb.toString();
    }
}
