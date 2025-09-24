# TP04 – Fluxo Máximo (Ford-Fulkerson)

Este trabalho prático tem como objetivo **encontrar todos os caminhos disjuntos em arestas** em um grafo direcionado, utilizando o **algoritmo de Ford-Fulkerson**.

📄 O relatório completo pode ser consultado em [`docs/TP04.pdf`](docs/TP04.pdf).

---

## 📖 Descrição do problema
Dado um grafo direcionado e um par de vértices (origem e destino), o objetivo é determinar:
- A **quantidade de caminhos disjuntos em arestas** entre origem e destino;  
- A listagem de cada caminho encontrado.

O algoritmo de Ford-Fulkerson foi usado porque, para grafos em que todas as arestas têm capacidade **1**, o **fluxo máximo equivale ao número de caminhos disjuntos**.

---

## 📊 Resultados

- Em grafos bipartidos (mais esparsos), poucos ou nenhum caminho disjunto foi encontrado.

- Em grafos densos, o número de caminhos cresceu bastante, chegando a 22 caminhos em um grafo de 23 vértices e 250 arestas.

- O tempo de execução manteve-se baixo (até ~5ms nos maiores testes).