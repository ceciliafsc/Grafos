import java.util.ArrayList;
import java.util.List;

public class KcentrosExato {
     private Grafo grafo;
     private double melhorRaioGlobal; 
     private int[] melhoresCentrosGlobais; // Array para os melhores centros

     // calcular raio = maior distancia entre as menores até um centro
     public int calcularRaioParaCentros( int[] centros) {
          
          int maior = Integer.MIN_VALUE;

          for (int i = 0; i < grafo.getnVertices(); i++) {
               int menor = Integer.MAX_VALUE;
               for (int j = 0; j < grafo.getK(); j++) {
                    if (grafo.getDist(i, centros[j]) < menor) {
                         menor = grafo.getDist(i, centros[j]); // salvar menor distancia ente v e um dos centros
                    }
               }
               if (menor > maior) {
                    maior = menor;
               }
          }
          return maior;
     }

    // gerar todas as combinações de k centros
    private void gerarEValidarCombinacoes(int verticeInicioBusca, int kCentrosFaltando, int[] combinacaoAtual, int indiceDePreenchimento) {
        if (kCentrosFaltando == 0) {
            double raioCalculado = calcularRaioParaCentros(combinacaoAtual);

            // Se o raio for o melhor até agora, salvar
            if (raioCalculado < this.melhorRaioGlobal) {
                this.melhorRaioGlobal = raioCalculado;
                System.arraycopy(combinacaoAtual, 0, this.melhoresCentrosGlobais, 0, this.grafo.getK());
            }
            return;
        }

        int limiteLoop = this.grafo.getnVertices() - kCentrosFaltando;
        for (int i = verticeInicioBusca; i <= limiteLoop; i++) {
            combinacaoAtual[indiceDePreenchimento] = i;
            gerarEValidarCombinacoes(i + 1, kCentrosFaltando - 1, combinacaoAtual, indiceDePreenchimento + 1);
        }
    }

    public static class SolucaoExata {
        public final List<Integer> centros;
        public final double raio;

        public SolucaoExata(List<Integer> centros, double raio) {
            this.centros = new ArrayList<>(centros);
            this.raio = raio;
        }

        @Override
        public String toString() {
            return "Solucao Exata -> Raio: " + String.format("%.2f", raio) + ", Centros: " + centros;
        }
    }

    public SolucaoExata resolver(Grafo grafo) {
        this.grafo = grafo;
        this.melhorRaioGlobal = Double.POSITIVE_INFINITY;
        this.melhoresCentrosGlobais = new int[grafo.getK()];

        int k = grafo.getK();
        if (k <= 0 || k > grafo.getnVertices()) {
            System.err.println("Valor de k é inválido");
            return new SolucaoExata(new ArrayList<>(), Double.POSITIVE_INFINITY);
        }

        int[] combinacaoAtual = new int[k];
        
        // geração recursiva de combinações
        gerarEValidarCombinacoes(0, k, combinacaoAtual, 0);

        // Converte o array da melhor solução para uma lista
        List<Integer> listaMelhoresCentros = new ArrayList<>();
        for (int centro : this.melhoresCentrosGlobais) {
            listaMelhoresCentros.add(centro);
        }

        return new SolucaoExata(listaMelhoresCentros, this.melhorRaioGlobal);
    }

     public static void main(String[] args) {
        String nomeArquivo = "pmed2.txt"; 

        try {
            //Criar grafo
            Grafo grafo = Grafo.criarGrafo(nomeArquivo);
            System.out.println("   Grafo com " + grafo.getnVertices() + " vértices.");
            System.out.println("   K: " + grafo.getK());

            long tempoInicio = System.currentTimeMillis(); // Medir o tempo

            KcentrosExato resolvedorExato = new KcentrosExato();
            KcentrosExato.SolucaoExata solucao = resolvedorExato.resolver(grafo);

            long tempoFim = System.currentTimeMillis();
            double tempoTotalSegundos = (tempoFim - tempoInicio) / 1000.0;
            System.out.println("   Busca finalizada em " + String.format("%.3f", tempoTotalSegundos) + " segundos.");

            //Exibir o resultado final.
            System.out.println("\nResultado Exato:");
            if (solucao.raio == Double.POSITIVE_INFINITY) {
                System.out.println("Nenhuma solução foi encontrada.");
            } else {
                System.out.println(solucao);
            }

        } catch (Exception e) {
            System.err.println("\nOcorreu um erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
