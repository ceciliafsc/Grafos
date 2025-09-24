import java.util.*;
import java.io.IOException;

public class FordFulkerson {

     private Grafo grafo;
     private int s;// origem
     private int t;// destino

     public FordFulkerson(Grafo g, int origem, int destino) {
          this.grafo = g;
          this.s = origem;
          this.t = destino;
     }

     // Rede Residual
     public Grafo criarRedeResidual() {
          Grafo redeResidual = new Grafo();
          for (int origem : grafo.getGrafo().keySet()) {
               redeResidual.adicionarVertice(origem); // add todos os vertices
               for (int destino : grafo.getSucessores(origem)) {
                    int capacidade = grafo.getCapacidade(origem, destino);
                    if (capacidade > 0) {
                         redeResidual.adicionarAresta(origem, destino, capacidade); // Aresta original
                         redeResidual.adicionarAresta(destino, origem, 0); // Aresta reversa com capacidade inicial 0
                    }
               }
          }
          return redeResidual;
     }

     // Encontrar um caminho aumentante com BFS
     public List<Integer> encontrarCaminho(Grafo redeResidual) {
          Map<Integer, Integer> pai = new HashMap<>();
          Queue<Integer> fila = new LinkedList<>();
          Set<Integer> visitado = new HashSet<>();

          fila.add(s);
          visitado.add(s);
          pai.put(s, -1);

          while (!fila.isEmpty()) {
               int atual = fila.poll();

               for (int vizinho : redeResidual.getSucessores(atual)) {
                    int capacidade = redeResidual.getCapacidade(atual, vizinho);

                    if (!visitado.contains(vizinho) && capacidade > 0) {
                         fila.add(vizinho);
                         visitado.add(vizinho);
                         pai.put(vizinho, atual);

                         // Chegou no destino
                         if (vizinho == t) {
                              // Reconstruir o caminho
                              List<Integer> caminho = new ArrayList<>();
                              int v = t;
                              while (v != -1) {
                                   caminho.add(0, v); // Insere no início da lista
                                   v = pai.get(v);
                              }
                              return caminho;
                         }
                    }
               }
          }

          // Não encontrou caminho
          return null;
     }

     public Grafo atualizarResidual(Grafo redeResidual, List<Integer> caminho) {
          int fluxo = Integer.MAX_VALUE; // Para capacidades unitárias, o fluxo será sempre 1

          for (int i = 0; i < caminho.size() - 1; i++) {
               int u = caminho.get(i);
               int v = caminho.get(i + 1);
               fluxo = Math.min(fluxo, redeResidual.getCapacidade(u, v));
          }

          for (int i = 0; i < caminho.size() - 1; i++) {
               int u = caminho.get(i);
               int v = caminho.get(i + 1);

               // Diminui capacidade da aresta direta
               int capacidadeUV = redeResidual.getCapacidade(u, v);
               redeResidual.setCapacidade(u, v, capacidadeUV - fluxo);
               if (redeResidual.getCapacidade(u, v) == 0) {
                    redeResidual.removerAresta(u, v);
               }

               // Aumenta capacidade da aresta reversa
               int capacidadeVU = redeResidual.getCapacidade(v, u);
               redeResidual.setCapacidade(v, u, capacidadeVU + fluxo);
          }
          return redeResidual;
     }

     public List<List<Integer>> FF(Grafo g) {
          List<List<Integer>> caminhos = new ArrayList<>();
          Grafo redeResidual = criarRedeResidual();
          List<Integer> caminhoAumentante;
          while ((caminhoAumentante = encontrarCaminho(redeResidual)) != null) {
               caminhos.add(caminhoAumentante);
               redeResidual = atualizarResidual(redeResidual, caminhoAumentante);
          }
          return caminhos;
     }

     public static void main(String[] args) throws Exception {
          Scanner scanner = new Scanner(System.in);

          Grafo grafo = new Grafo();
          try {
               grafo = Grafo.criarGrafo("denso10.txt");
          } catch (IOException e) {
               System.out.println("Erro ao criar o grafo: " + e.getMessage());
               scanner.close();
               return;
          }

          // Escolha da origem e destino
          System.out.println("Digite o nó de origem (s):");
          int origem = scanner.nextInt();

          System.out.println("Digite o nó de destino (t):");
          int destino = scanner.nextInt();

          // Medir o tempo de execução
          long inicio = System.nanoTime();

          // Executa o algoritmo
          FordFulkerson ff = new FordFulkerson(grafo, origem, destino);
          List<List<Integer>> caminhos = ff.FF(grafo);

          long fim = System.nanoTime();
          long duracao = fim - inicio;

          System.out.println("\nTempo de execução: " + (duracao / 1_000_000.0) + " milissegundos");

          // caminhos disjuntos
          System.out.println("\nCaminhos disjuntos de " + origem + " até " + destino + ":");
          for (List<Integer> caminho : caminhos) {
               System.out.println(caminho);
          }

          System.out.println("\nQuantidade de caminhos disjuntos: " + caminhos.size());

          scanner.close();
     }
}
