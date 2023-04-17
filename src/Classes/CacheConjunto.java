package Classes;

import java.util.*;

public class CacheConjunto {
    private int n_blocos;
    private final HashMap<Integer, Queue<Bloco>> cache = new HashMap<>(); // Atributo que representará o conjunto, que guardará dentro dele um HashMap de inteiros que será o bloco.

    public CacheConjunto(int n_blocos){
        // O contrutor irá criar dois conjuntos (0 e 1), em cada um dos conjuntos terá uma fila de Blocos
        // O número de blocos para cada conjunto é = (n_blocos / 2)
        this.n_blocos = n_blocos;
        Queue<Bloco> blocos = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n_blocos/2; j++) {
                Bloco b = new Bloco();
                blocos.add(b);
            }
            cache.put(i, blocos);
        }
    }
    public void addBlocoCache(String alvo, MemoriaPrincipal mp) {
        /*
          - Irá pegar o endereço alvo e criar dois blocos (um para o encereço alvo com terceiro bit 0
            e um para o endereço alvo com bit 1)
          - E irá adicioná-los aos seus respectivos conjuntos (0 ou 1)
        */
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                alvo = alvo.replaceFirst(alvo.substring(0,3), "000");
            } else {
                alvo = alvo.replaceFirst(alvo.substring(0,3), "001");
            }
            Queue<Bloco> fila = cache.get(i);
            fila.remove();
            Bloco bloco = new Bloco(alvo, mp);
            fila.add(bloco);
//            System.out.println("Status da cache: ");
//            for (int j = 0; j < fila.size(); j++) {
//
//            }
        }
    }
    public void printarCache() {
//        Queue<Bloco> fila = cache.get(0);
//        HashMap<String, Integer> bloco = fila.poll().getBloco();
//        System.out.println(bloco.get("0000"));
        System.out.println("Conj  |  Bloco  |  Linha  |  Dado");
        for (int i = 0; i < 2; i++) {
            Queue<Bloco> fila = cache.get(i);
            for (int j = 0; j < fila.size(); j++) {
                System.out.print("  " + i + "   |");
                System.out.print("    " + j + "    |");
                Bloco b = fila.poll();
                HashMap<String, Integer> bloco = b.getBloco();

                // Obter o conjunto de chaves do mapa
                Set<String> c = bloco.keySet();
                List<String> keys = new ArrayList<>(c);
                String chave = keys.get(j);


                System.out.print("  " + chave + "   |");
                System.out.println("  " + bloco.get(chave));
                fila.add(b);
            }
        }
    }

//    public void printarCache() {
//        System.out.println("C | E | D"); // Explicando qual valor é qual (C = Conjunto, E = Endereco, D = Dado)
//        for (int conjunto = 0; conjunto < 2; conjunto++){ // A "cache" terá 2 conjuntos (0 e 1) onde serão aramazenados 2 endereços em cada.
//            Queue cache_conjunto = cache.get(conjunto); // Será acessado o hashmap que guarda os dois endereços (0 e 1) dos conjuntos (0 e 1).
//            for (int endereco = 0; endereco < 2; endereco++){ // É passado entre os dois endereços presentes nos conjuntos.
//                int dado = (int) cache_conjunto.get(endereco); // O valor guardado nesses 2 endereços serão guardados na variável "cache_endereco".
//                System.out.printf("%d | %d | %d%n", conjunto, endereco, dado); // O conjunto e valor do endereco serão printados na tela do usuario.
//            }
//        }
//    }
}
