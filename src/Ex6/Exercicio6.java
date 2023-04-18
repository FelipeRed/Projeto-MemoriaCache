package Ex6;

import Ex6.Cache.CacheConjunto;
import Ex6.Memoria.MemoriaPrincipal;

import java.util.Scanner;

public class Exercicio6 {
    public static void main(String[] args) { //AINDA FALTA ESTRUTURAR O EXERCÍCIO 6
        MemoriaPrincipal memoriaPrincipal = new MemoriaPrincipal();
        int n_blocos = escolherN_Blocos();
        int metodo_substituicao = escolherMetodo_Substituicao();
        CacheConjunto cache = new CacheConjunto(2);
        cache.addBlocoCache("0000", memoriaPrincipal);
        cache.addBlocoCache("0110", memoriaPrincipal);
        memoriaPrincipal.printarMemoria();
        cache.printarCache();

        //exemplos com endereços prontos para testagem
        //ainda falta fazer um programinha padrão para testagem
        /*
            CARREGAR o valor de exemplo1[i]
            CARREGAR o valor de exemplo1[i]
            ALTERAR o valor de exemplo1[i] para X
            CARREGAR o valor de exemplo1[i] para X
        */
        String[] exemplo1 = {"0000", "0001", "0010", "0011", "0001", "0101", "0110", "0111"};
        String[] exemplo2 = {"0000", "0001", "0010", "0010", "1010" , "1000", "0010", "0001", "0000", "0011", "0100"};
        String[] exemplo3 = {"0000", "0001", "0010", "1000", "0101" , "0001", "1010", "1000", "0011", "1001", "0010"};
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
            System.out.print("Insira quantos blocos você deseja que tenha em cada conjunto: ");
            escolha = teclado.nextInt();
        }
        return escolha;
    }
    public static int escolherMetodo_Substituicao() { //método que irá retornar o tipo de substituição a ser usada
        Scanner teclado = new Scanner(System.in);
        int escolha = 0;
        while ((escolha != 1) && (escolha != 2) && (escolha != 3)) {
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
    public static void limparTela() { //método para limpar a tela do terminal
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
