public class Aresta {
     private int origem;
        private int destino;
        private int peso;

        public Aresta(int origem, int destino) {
            this.origem = origem;
            this.destino = destino;
            this.peso = 1;
        }
     

        public int getOrigem() {
            return origem;
        }
        public int geTDestino() {
            return destino;
        }
        public int getPeso() {
            return peso;
        }

       

        
        public String toString() {
            return "[" + origem + " -> " + destino + peso + "]";
        }

       

    }