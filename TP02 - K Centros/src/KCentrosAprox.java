import java.util.ArrayList;
import java.util.List;

public class KCentrosAprox {

    private Grafo grafo;
    private int k; //número de centros
    private int[] centros;
    private double[] distanciasMinimas; 

    public static class SolucaoAproximada {
        public final List<Integer> centros;
        public final double raio;

        public SolucaoAproximada(List<Integer> centros, double raio) {
            this.centros = new ArrayList<>(centros);
            this.raio = raio;
        }
        
        @Override
        public String toString() {
            List<Integer> centrosExibicao = new ArrayList<>();
            for(Integer c : centros) centrosExibicao.add(c + 1);
            return "Solucao Aproximada -> Raio: " + String.format("%.2f", raio) + ", Centros: " + centrosExibicao;
        }
    }

    public KCentrosAprox(Grafo grafo, int k) {
        this.grafo = grafo;
        this.k = k;
        this.centros = new int[k];
        this.distanciasMinimas = new double[grafo.getnVertices()]; 
    }
    
    public SolucaoAproximada resolver() {
        centros[0] = 0;
        
        // Calcula a distância de todos os v para o primeiro centro
        for (int i = 0; i < grafo.getnVertices(); i++) {
            distanciasMinimas[i] = grafo.getDist(i, centros[0]);
        }

        //encontrar k centros
        for(int i = 1; i < k; i++){
            // Encontrar o vértice mais distante dos centros atuais
            int proxC = -1; 
            double maior = -1.0; 
            
            for(int j = 0; j < grafo.getnVertices(); j++){
                if(distanciasMinimas[j] > maior){
                    maior = distanciasMinimas[j];
                    proxC = j; 
                }
            }
            centros[i] = proxC;

            // Atualizar distâncias mínimas com o novo centro encontrado
            for(int l = 0; l < grafo.getnVertices(); l++){
                double disNovoC = grafo.getDist(l, centros[i]);
                if(disNovoC < distanciasMinimas[l]){
                    distanciasMinimas[l] = disNovoC;
                }
            }
        }

        // calcular raio
        double raio = 0.0; 
        for(int l = 0; l < grafo.getnVertices(); l++){
            if(distanciasMinimas[l] > raio){
                raio = distanciasMinimas[l];
            }
        }
        
        List<Integer> listaCentrosFinais = new ArrayList<>();
        for (int centro : this.centros) {
            listaCentrosFinais.add(centro);
        }
        
        return new SolucaoAproximada(listaCentrosFinais, raio);
    }


public static void main(String[] args) {
    String nomeArquivo = "pmed6.txt"; 

    try {
        // Criar o grafo 
        Grafo grafo = Grafo.criarGrafo(nomeArquivo);
        System.out.println("    Grafo com " + grafo.getnVertices() + " vértices.");
        System.out.println("    K: " + grafo.getK());

        
        long tempoInicio = System.nanoTime();

        KCentrosAprox resolvedorAprox = new KCentrosAprox(grafo, grafo.getK());
        SolucaoAproximada solucao = resolvedorAprox.resolver();

        long tempoFim = System.nanoTime();

        double tempoTotalMilisegundos = (tempoFim - tempoInicio) / 1_000_000.0;
        
        System.out.println("   Busca finalizada em " + String.format("%.4f", tempoTotalMilisegundos) + " milissegundos.");

        System.out.println("\n Resultado Aproximado:");
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