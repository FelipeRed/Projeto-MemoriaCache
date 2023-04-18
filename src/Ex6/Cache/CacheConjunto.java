package Ex6.Cache;

import Ex6.Memoria.MemoriaPrincipal;

import java.util.*;

public class CacheConjunto {
    private int n_blocos;
    private final TreeMap<Integer, Queue<Bloco>> cache = new TreeMap<>(); // Atributo que representará o conjunto, que guardará dentro dele um HashMap de inteiros que será o bloco.

    public CacheConjunto(int n_blocos){
        /*  ALGORITMO
            1- O contrutor irá criar dois conjuntos (0 e 1), em cada um dos conjuntos terá uma fila de Blocos
            2- O número de blocos para cada conjunto é n_blocos enviado por parâmtero
        */
        this.n_blocos = n_blocos;
        for (int i = 0; i < 2; i++) {
            Queue<Bloco> blocos = new LinkedList<>();
            for (int j = 0; j < n_blocos; j++) {
                Bloco b = new Bloco();
                blocos.add(b);
            }
            cache.put(i, blocos);
        }
    }
    public void addBlocoCache(String alvo, MemoriaPrincipal mp) {
        /*  ALGORITMO
            1- Irá pegar o endereço alvo e criar dois blocos (um para o encereço alvo com terceiro bit 0
               e um para o endereço alvo com bit 1)
            2- E irá adicioná-los aos seus respectivos conjuntos (0 ou 1)
        */
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                alvo = alvo.substring(0,2) + '0' + alvo.substring(3); //muda o terceiro bit do alvo para 0
            } else {
                alvo = alvo.substring(0,2) + '1' + alvo.substring(3); //muda o terceiro bit do alvo para 1
            }
            Queue<Bloco> fila = cache.get(i);
            fila.remove();
            Bloco bloco = new Bloco(alvo, mp);
            fila.add(bloco);
        }
    }
    public void procurarPosicaoCache(String alvo, MemoriaPrincipal mp) {
        /*  ALGORITMO
            1- Irá procurar um alvo na memória cache
            2- Caso ENCONTRE jogará o bloco que conter esse alvo para o final da fila
            3- Caso NÃO ENCONTRE irá retirar o primeiro bloco da fila e colocar o alvo no lugar
        */
        Queue<Bloco> fila = cache.get(0);
        if (alvo.charAt(2) == '1') {
            cache.get(1);
        }

        boolean encontrado = false;
        Bloco bloco = null;
        for (int i = 0; i < fila.size(); i++) {
            Bloco b1 = fila.poll();
            TreeMap<String, Integer> b2 = fila.poll().getBloco();
            for (String key : b2.keySet()) {
                if (key.equals(alvo)) {
                    encontrado = true;
                    bloco = b1;
                }
            }
            if (!encontrado) {
                fila.add(b1);
            }
            encontrado = false;
        }
        if (!(bloco == null)) { //ou seja, se o alvo for encontrado -> adicione o bloco ao final da fila
            fila.add(bloco);
        } else {
            addBlocoCache(alvo, mp); //se não, chame addBlocoCache(alvo) para colocar o bloco alvo no final da fila
        }
    }
    public void printarCache() {
        System.out.println("\n                CACHE CONJUNTO");
        System.out.println("| Conj  |  Bloco  |  D_Bit  |  Linha  |  Dado  |");
        for (int i = 0; i < 2; i++) {
            Queue<Bloco> fila = cache.get(i);
            if (i == 1) {
                System.out.println("|----------------------------------------------|");
            }
            for (int j = 0; j < fila.size(); j++) {
                System.out.print("|   " + i + "   |");
                System.out.print("    " + j + "    |");
                Bloco b = fila.poll();
                b.printBloco();
                fila.add(b);
            }
        }
        System.out.println("------------------------------------------------");
    }
}
