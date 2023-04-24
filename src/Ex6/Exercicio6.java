package Ex6;

import Ex6.Cache.CacheConjunto;
import Ex6.Memoria.MemoriaPrincipal;

import java.util.Scanner;

public class Exercicio6 {
    private static MemoriaPrincipal memoriaPrincipal;
    private static CacheConjunto cache;
    public static void main(String[] args) {
        int n_blocos = escolherN_Blocos();
        //int metodo_substituicao = escolherMetodo_Substituicao();
        memoriaPrincipal = new MemoriaPrincipal();
        cache = new CacheConjunto(n_blocos);

        /* TESTE A SER APLICADO
            LOAD r0 0001
            LOAD r2 1001
              SW r2 0001
              SW r2 1101
            LOAD r0 1100
            LOAD r1 0010
              SW r0 0011
            LOAD r2 0001
            LOAD r3 1010
        */

        int r0 = -1;
        int r1 = -1;
        int r2 = -1;
        int r3 = -1;

        limparTela();
        printarExecucao("ESTADO INICIAL", r0, r1, r2, r3);
        r0 = cache.load("0001", memoriaPrincipal, 0);
        printarExecucao("LOAD r0 0001", r0, r1, r2, r3);
        r2 = cache.load("1001", memoriaPrincipal, 0);
        printarExecucao("LOAD r2 1001", r0, r1, r2, r3);
        //sw(r2, "0001");
        //printarExecucao("SW r2 0001", r0, r1, r2, r3);
        //sw(r2, "1101");
        //printarExecucao("SW r2 1101", r0, r1, r2, r3);
        r0 = cache.load("1100", memoriaPrincipal, 0);
        printarExecucao("LOAD r0 1100", r0, r1, r2, r3);
        r1 = cache.load("0010", memoriaPrincipal, 0);
        printarExecucao("LOAD r1 0010", r0, r1, r2, r3);
        //sw(r0, "0011");
        //printarExecucao("SW r0 0011", r0, r1, r2, r3);
        r2 = cache.load("0001", memoriaPrincipal, 0);
        printarExecucao("LOAD r2 0001", r0, r1, r2, r3);
        r3 = cache.load("1010", memoriaPrincipal, 0);
        printarExecucao("LOAD r3 1010", r0, r1, r2, r3);

        System.out.println("\nHits  : " + cache.getHits());
        System.out.println("Misses: " + cache.getMisses());
    }
    public static int escolherN_Blocos() { //metódo que ira retornar quantos blocos o usuário quer em cada conjunto
        Scanner teclado = new Scanner(System.in);
        int escolha = 0;
        while ((escolha != 1) && (escolha != 2) && (escolha != 4) && (escolha != 8) && (escolha != 16)) {
            limparTela();
            System.out.print("""
                    OPÇÕES:
                    - 1 bloco
                    - 2 blocos
                    - 4 blocos
                    - 8 blocos
                    - 16 blocos
                    """);
            System.out.print("Insira o número de blocos você deseja que tenha em cada conjunto: ");
            escolha = teclado.nextInt();
        }
        return escolha;
    }
    public static int escolherMetodo_Substituicao() { //método que irá retornar o tipo de substituição a ser usada
        Scanner teclado = new Scanner(System.in);
        int escolha = 0;
        while ((escolha < 1) || (escolha > 3)) {
            limparTela();
            System.out.print("""
                OPÇÕES:
                -1 LRU
                -2 LFU
                -3 FIFO
                """);
            System.out.print("Insira qual método de substituição deseja utilizar: ");
            escolha = teclado.nextInt();
        }
        return escolha; //POR ENQUANTO SÓ ESTÁ IMPLEMENTADA A LÓGICA LRU
    }
    public static void printarExecucao(String comando, int r0, int r1, int r2, int r3) {
        //imprimirá os estado das memórias a cada comando
        System.out.println("\n\n" + comando);
        memoriaPrincipal.print();
        cache.print();
        System.out.println("VAR  |  VALOR");
        System.out.println("r0   |  " + r0);
        System.out.println("r1   |  " + r1);
        System.out.println("r2   |  " + r2);
        System.out.println("r3   |  " + r3);
    }
    public static void limparTela() { //método para limpar a tela do terminal
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
