package Ex5;

import java.util.Scanner;

public class Exercicio5 {
    public static CacheSimples cache;
    public static void main(String[] args) {
        int[] exemplo1 = {0, 1, 2, 3, 1, 4, 5, 6};
        int[] exemplo2 = {0, 1, 2, 2, 22 , 32, 42, 20, 1, 10, 11, 12, 13};
        int[] exemplo3 = {1, 6, 1, 11, 1, 16, 1, 21, 1, 26};
        int escolha = escolherExemplo(); //pede pro usuário escolher qual exemplo executar
        switch (escolha) {
            case 1:
                exec(exemplo1);
                break;
            case 2:
                exec(exemplo2);
                break;
            case 3:
                exec(exemplo3);
                break;
        }
    }

    public static void mapeamento_direto(int[] posicoes_a_acessar) {
        /*
          1- Irá imprimir a cache em seu estado inicial (vazia)
          2- Irá procurar cada valor de posicoes_a_acessar dentro da cache através do método proprocurarPosicaoCache()
                                                                                      método da classe Ex5.CacheSimples
          3- A cada vez que esse método é acionado, será incrementado os valores de hits e misses,
             bem como os valores da memória cache serão atualizados
        */
        cache = new CacheSimples();
        System.out.println();
        System.out.println("Cache inicial");
        cache.imprimirCache();

        int x = 0;
        for (int p : posicoes_a_acessar) {
            System.out.println("Linha " + x + " | Posição desejada: " + p);
            System.out.println("Status: " + cache.procurarPosicaoCache(p));
            cache.imprimirCache();
            x++;
        }
    }
    public static void exec(int[] exemplo) {
        mapeamento_direto(exemplo);
        cache.printResumo(exemplo.length);
    }
    public static int escolherExemplo() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("""
                1- [0, 1, 2, 3, 1, 4, 5, 6]
                2- [0, 1, 2, 2, 22 , 32, 42, 20, 1, 10, 11, 12, 13]
                3- [1, 6, 1, 11, 1, 16, 1, 21, 1, 26]""");
        System.out.print("Qual sequencia de passos gostaria de realizar: ");
        return teclado.nextInt();
    }
}
