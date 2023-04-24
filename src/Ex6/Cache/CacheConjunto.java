package Ex6.Cache;

import Ex6.Memoria.MemoriaPrincipal;

import java.util.*;

public class CacheConjunto {
    private int hits;
    private int misses;
    private final TreeMap<Integer, Queue<Bloco>> cache = new TreeMap<>(); // Atributo que representará o conjunto, que guardará dentro dele um HashMap de inteiros que será o bloco.

    public CacheConjunto(int n_blocos){
        /*  ALGORITMO
            1- O contrutor irá criar dois conjuntos (0 e 1), em cada um dos conjuntos terá uma fila de Blocos
            2- O número de blocos para cada conjunto é n_blocos enviado por parâmtero
        */
        for (int i = 0; i < 2; i++) {
            Queue<Bloco> blocos = new LinkedList<>();
            for (int j = 0; j < n_blocos; j++) {
                Bloco b = new Bloco();
                blocos.add(b);
            }
            cache.put(i, blocos);
        }
        hits = 0;
        misses = 0;
    }
    public void add_Bloco_Na_Cache(String alvo, MemoriaPrincipal mp) {
        /*  ALGORITMO
            1- Caso o bloco que tirarmos da fila tiver seu Dirty Bit = 1 iremos atualizar o valor da memória principal
            2- Irá pegar o endereço alvo e criar seu bloco (com as linhas de memória XXX0 e XXX1)
            3- E irá adicioná-lo ao seu respectivo conjunto (0 ou 1) a partir do seu terceiro bit (alvo.charAt(2))
        */
        Queue<Bloco> fila;
        if (alvo.charAt(2) == '0') {
            fila = cache.get(0);
        } else {
            fila = cache.get(1);
        }
        fila.poll();
        /*if(dirty_bit do bloco removido == 1) {
            atualizar valores na memória principal;
        }*/
        Bloco bloco = new Bloco(alvo, mp); //passo 2
        fila.add(bloco); //passo 3
        misses++;
    }
    public int load(String alvo, MemoriaPrincipal mp, int x) {
        /*  ALGORITMO
            1- Irá procurar um alvo na memória cache
            2- Caso ENCONTRE jogará o bloco alvo para o final da fila e retornará o dado daquela linha de memória
            3- Caso NÃO ENCONTRE irá retirar o primeiro bloco da fila e colocar o bloco alvo no lugar
        */
        Queue<Bloco> fila;
        if (alvo.charAt(2) == '0') {
            fila = cache.get(0);
        } else {
            fila = cache.get(1);
        }

        boolean encontrado = false;
        Bloco bloco = null;
        int result = 0; //receberá o dado na posição de memória alvo
        for (int i = 0; i < fila.size(); i++) {
            Bloco b1 = fila.poll();
            TreeMap<String, Integer> b2 = b1.getBloco();
            for (String key : b2.keySet()) {
                if (key.equals(alvo)) {
                    encontrado = true;
                    result = b2.get(alvo);
                    bloco = b1;
                    break;
                }
            }
            if (!encontrado) { //caso não tenha encontrado o alvo, adicionar o bloco a fila para não alterar a ordem
                fila.add(b1);
            }
            encontrado = false;
        }
        if (!(bloco == null)) { //se o alvo for encontrado -> adicione o bloco ao final da fila
            fila.add(bloco);
            if (x == 0) {
                hits++;
            }
            return result;
        } else { //se não, chame addBlocoCache(alvo) para colocar o bloco alvo no final da fila
            add_Bloco_Na_Cache(alvo, mp);
            result = load(alvo, mp, 1); //quando chamarmos a própria função novamente o alvo será encontrado com certeza
        }
        return result;
    }
    public void sw(int valor, String alvo, MemoriaPrincipal mp) {
        //lógica para trocar um valor da memória cache
    }
    public void print() { //função que irá imprimir a cache
        System.out.println("\n                CACHE CONJUNTO");
        System.out.println("| Conj  |  Bloco  |  D_Bit  |  Linha  |  Dado  |");
        for (int i = 0; i < 2; i++) {
            Queue<Bloco> fila = cache.get(i);
            if (i == 1) {
                System.out.println("|----------------------------------------------|");
            }
            for (int j = 0; j < fila.size(); j++) {
                System.out.print("|   " + i + "   |    " + j + "    |");
                Bloco b = fila.poll();
                b.print();
                fila.add(b);
            }
        }
        System.out.println("------------------------------------------------");
    }
    public int getHits() {
        return hits;
    }
    public int getMisses() {
        return misses;
    }
}
