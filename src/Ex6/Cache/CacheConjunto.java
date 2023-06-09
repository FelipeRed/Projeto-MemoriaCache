package Ex6.Cache;

import Ex6.Memoria.LinhaDeMemoria;
import Ex6.Memoria.MemoriaPrincipal;
import com.sun.source.tree.Tree;

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
        Queue<Bloco> conjunto; // Tipo: Fila :)
        if (alvo.charAt(2) == '0') { // Verifica de qual o conjunto que o alvo pertence
            conjunto = cache.get(0); // Traz o conjunto 0
        } else {
            conjunto = cache.get(1); // Traz o conjunto 1
        }

        Bloco b_retirado = conjunto.poll(); // Retira o bloco com LRU da cache
        if(b_retirado.getDirty_bit() == 1){ // Verificação do dirtybit relacionado ao bloco retirado.
            System.out.println("Colocando o bloco que tem dirtybit 1 na memória principal");
            b_retirado.atualiza_Memoria_Principal(mp);
            Bloco bloco = new Bloco(alvo, mp, true);
            conjunto.add(bloco); //passo 3
        } else{
            Bloco bloco = new Bloco(alvo, mp, false);
            conjunto.add(bloco); //passo 3
        }

        // Bloco bloco = new Bloco(alvo, mp, true); //passo 2 // AQUI É ONDE O BLOCO PERDE O DIRTY BIT, pois o valor do novo dirty bit não é passado para a cópia com o novo valor!! (acho)
        // conjunto.add(bloco); //passo 3
    }
    public int load(String alvo, MemoriaPrincipal mp, int x) {
        /*  ALGORITMO
            1- Irá procurar um alvo na memória cache
            2- Caso ENCONTRE jogará o bloco alvo para o final da fila e retornará o dado daquela linha de memória
            3- Caso NÃO ENCONTRE irá retirar o primeiro bloco da fila e colocar o bloco alvo no lugar
        */
        Queue<Bloco> conjunto;
        if (alvo.charAt(2) == '0') {
            conjunto = cache.get(0);
        } else {
            conjunto = cache.get(1);
        }

        boolean encontrado = false;
        Bloco b = null; //caso o alvo seja encontrado essa variável receberá o bloco alvo
        int result = 0; //receberá o dado na posição de memória alvo
        for (int i = 0; i < conjunto.size(); i++) {
            Bloco bloco = conjunto.poll(); //captura o primeiro bloco da fila
            for (String key : bloco.getLinhas().keySet()) { //captura os endereços desse bloco
                if (key.equals(alvo)) { //verifica se o endereço capturado é igual ao do alvo
                    encontrado = true;
                    result = bloco.getLinhas().get(alvo);
                    b = bloco;
                    break;
                }
            }
            if (!encontrado) { //se não encontrou o alvo, adicionamos o bloco novamente a fila para não alterar a ordem
                conjunto.add(bloco);
            }
            encontrado = false;
        }
        if (!(b == null)) { //se o alvo for encontrado, adicionamos o bloco somente agora (ao final da fila)
            conjunto.add(b);
            if (x == 0) { //serve para controlar a incrementação de hits por conta da recursividade
                hits++;
            }
            return result;
        } else { //se não, chame addBlocoCache(alvo) para colocar o bloco alvo no final da fila
            misses++;
            add_Bloco_Na_Cache(alvo, mp);
            result = load(alvo, mp, 1); //chamamos a própria função novamente e o alvo será encontrado com certeza
        }
        return result;
    }
    public void sw(int valor, String alvo, MemoriaPrincipal mp) {
        /*
            1- Verficar na cache se o nosso alvo está presente
            1.1- Caso não esteja na cache, então iremos primeiro adicionar o bloco alvo na cache
            2- Caso ele esteja na cache, então iremos alterar o seu dado

            1- Verificar dentro da memória cache em qual bloco está localizado o endereço referenciado como "alvo".
            1.1 - Caso não seja encontrado o endereço referenciado, é utilizada a funcao add_Bloco_Na_Cache(alvo, mp)
            2- Utilizar a funcao 'alterar_Valor_Bloco(alvo, valor)' para alterar o dado do endereco solicitado.
            3-
        */
        if (alvo.charAt(2) == '0') {
            int quantidade_blocos = 2;
            Queue<Bloco> conjunto = cache.get(0);
            for(Bloco bloco : conjunto){
                for(String endereco_chave_de_linha : bloco.getLinhas().keySet()){
                    if(endereco_chave_de_linha == alvo){
                        bloco.alterar_Valor_Bloco(alvo, valor);
                    }
                }
                quantidade_blocos -= 1;
                if (quantidade_blocos == 0){
                    add_Bloco_Na_Cache(alvo, mp);
                }
            }
        } else {
            int quantidade_blocos = 2;
            Queue<Bloco> conjunto = cache.get(1);
            for(Bloco bloco : conjunto){
                for(String endereco_chave_de_linha : bloco.getLinhas().keySet()){
                    if(endereco_chave_de_linha == alvo){
                        bloco.alterar_Valor_Bloco(alvo, valor);
                    }
                }
                quantidade_blocos -= 1;
                if (quantidade_blocos == 0){
                    add_Bloco_Na_Cache(alvo, mp);
                }
            }
        }
    }
    public void print() { //função que irá imprimir a cache
        System.out.println("\n                CACHE CONJUNTO");
        System.out.println("| Conj  |  Bloco  |  D_Bit  |  Linha  |  Dado  |");
        for (int i = 0; i < 2; i++) { //para cada conjunto
            Queue<Bloco> fila = cache.get(i);
            if (i == 1) {
                System.out.println("|----------------------------------------------|");
            }
            for (int j = 0; j < fila.size(); j++) { //imprimirá todos os blocos do conjunto
                if(j == fila.size()/2) {
                    System.out.print("|  " + i + "    |    " + j + "    |");
                } else{
                    System.out.print("|       |    " + j + "    |");
                }
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
