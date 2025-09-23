# TP02 – Problema dos K-Centros

Este trabalho prático tem como objetivo **resolver o problema dos k-centros** em grafos completos com custos nas arestas. Foram implementadas duas abordagens: uma solução **exata (força bruta)** e uma solução **aproximada (algoritmo de Gonzalez)**.

📄 O relatório completo pode ser consultado em [`docs/TP02.pdf`](docs/TP02.pdf).

---

## 📖 Descrição do problema
Dado um conjunto de vértices **V**, as distâncias entre eles e um inteiro **k**, o problema consiste em encontrar um subconjunto **C** com *k* vértices que minimize o **raio da solução**, definido como a maior distância entre qualquer vértice e o centro mais próximo.

- **Solução Exata**: usa força bruta para testar todas as combinações possíveis de centros.  
- **Solução Aproximada**: baseada no algoritmo de Gonzalez, que escolhe vértices de forma gulosa.  

---

## 📊 Resultados

O método exato (KcentrosExato.java) encontra a solução ótima, mas é inviável para instâncias grandes devido à explosão combinatória.

O método aproximado (KCentrosAprox) gera soluções de qualidade em frações de segundo, mesmo em grafos grandes.

Exemplo:

Exato (pmed1.txt, 100 vértices, k=5): raio = 127 (90s de execução).

Aproximado (pmed16.txt, 400 vértices, k=5): raio ≈ 84 (0,9ms de execução).
