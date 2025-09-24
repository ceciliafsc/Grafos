import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
public class Grafo {
    private Map<Integer, Map<Integer, Integer>> grafo;

    public Grafo() {
        this.grafo = new HashMap<>();
    }

    public Grafo(Map<Integer, Map<Integer, Integer>> grafo) {
        this.grafo = grafo;
    }

    public void adicionarVertice(int v) {
        grafo.putIfAbsent(v, new HashMap<>());
    }
    // Adiciona uma aresta com capacidade
    public void adicionarAresta(int origem, int destino, int capacidade) {
        grafo.putIfAbsent(origem, new HashMap<>());
        grafo.putIfAbsent(destino, new HashMap<>()); // garante que o destino também exista no grafo
        grafo.get(origem).put(destino, capacidade);
    }

    // Remove uma aresta
    public void removerAresta(int origem, int destino) {
        if (grafo.containsKey(origem)) {
            grafo.get(origem).remove(destino);
        }
    }

    // Retorna os sucessores de um nó
    public Set<Integer> getSucessores(int vertice) {
        if (grafo.containsKey(vertice)) {
            return grafo.get(vertice).keySet();
        }
        return new HashSet<>();
    }

    // Retorna a capacidade da aresta (origem -> destino)
    public int getCapacidade(int origem, int destino) {
        if (grafo.containsKey(origem) && grafo.get(origem).containsKey(destino)) {
            return grafo.get(origem).get(destino);
        }
        return 0;
    }

    // Atualiza a capacidade de uma aresta
    public void setCapacidade(int origem, int destino, int capacidade) {
        grafo.putIfAbsent(origem, new HashMap<>());
        grafo.get(origem).put(destino, capacidade);
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
           
            Map<Integer, Map<Integer, Integer>> listaAdjacencia = new HashMap<>();
            String linha;

            // Lendo as arestas do arquivo
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split("\\s+");
                    if (partes.length == 2) {
                        try {
                            int origem = Integer.parseInt(partes[0]);
                            int destino = Integer.parseInt(partes[1]);
                            listaAdjacencia.putIfAbsent(origem, new HashMap<>());
                            listaAdjacencia.get(origem).put(destino, 1); // Peso 1 por padrão
                            listaAdjacencia.putIfAbsent(destino, new HashMap<>()); 
                        } catch (NumberFormatException e) {
                            System.err.println("Erro ao ler aresta: " + linha + " - " + e.getMessage());
                        }
                    } else {
                        System.err.println("Formato de linha incorreto: " + linha);
                    }
                }
            }
            return new Grafo(listaAdjacencia);
        } finally {
            br.close();
        }
    }
}
