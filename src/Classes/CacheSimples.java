package Classes;
import java.util.HashMap;

public class CacheSimples {
    private int hits;
    private int misses;
    private int acessosMP;
    private final int tamanho = 5;
    private HashMap<Integer, Integer> cache = new HashMap<>();

    public CacheSimples() {
        //cria uma memória cache de 5 posições
        hits = 0;
        misses = 0;
        acessosMP = 0;
        for (int i = 0; i < tamanho; i++) {
            cache.put(i, -1);
        }
    }
    public void imprimirCache() {
        //percorerá todas as linhas do HashMap e para cada linha imprimirá a sua chave + o valor atribuído
        System.out.println("Tamanho da cache: " + tamanho);
        System.out.println("-----------");
        for (int chave = 0; chave < tamanho; chave++) {
            int linha = cache.get(chave);
            if (linha == -1) {
                System.out.println("| " + chave + "  | " + linha + " |");
            } else {
                System.out.println("| " + chave + "  |  " + linha + " |");
            }
        }
        System.out.println("-----------\n");
    }
    public String procurarPosicaoCache(int x) {
        //procura o valor X na cache, caso encontre aumenta os hits em 1 e retorna a String "Hit"
        //caso não encontre, irá setar o valor X em uma posição da cache chamando o método sePosicaoCache(),
        //aumentar o valor de misses em 1 e retornar a String "Miss"
        for (int i = 0; i < tamanho; i++) {
            int linha = cache.get(i);
            if (linha == x) {
                hits++;
                return "Hit";
            }
        }
        setPosicaoCache(x);
        misses++;
        acessosMP++;
        return "Miss";
    }
    public void setPosicaoCache(int x) {
        //regra para determinar em qual posição da cache salvar o valor X
        int posicaoCache = x % tamanho;
        cache.put(posicaoCache, x);
    }
    public void printResumo(int linhas_codigo) {
        float taxaHits = ((float) hits/ (float) linhas_codigo)*100;
        System.out.println("------> RESUMO <------");
        System.out.println("Memórias acessadas: " + acessosMP);
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.printf("Taxa de hits: %.2f%%\n", taxaHits);
        System.out.println("----------------------");
    }
}
