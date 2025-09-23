public class FloydWarshall {
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
        public static void imprimirMatriz(int[][] matriz) {
          for (int i = 0; i < matriz.length; i++) {
               for (int j = 0; j < matriz[i].length; j++) {
                    if (matriz[i][j] == Integer.MAX_VALUE/2) {
                         System.out.print("INF ");
                    } else {
                         System.out.print(matriz[i][j] + " ");
                    }
               }
               System.out.println();
          }
     }
}
