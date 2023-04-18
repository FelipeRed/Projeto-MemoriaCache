package Ex6.Cache;

import Ex6.Memoria.LinhaDeMemoria;
import Ex6.Memoria.MemoriaPrincipal;
import java.util.ArrayList;
import java.util.TreeMap;

/*
    Essa classe será formada sempre pelo par de linhas de memórias e seus dados alocados
    0000 e 0001;
    1010 e 1011;
    0110 e 0111;
    etc
*/

public class Bloco {
    private TreeMap<String, Integer> bloco;
    private int dirty_bit = 0; //atributo que irá dizer se foi modificado algum valor dentro do bloco ou não

    public Bloco() { //cria um bloco padrão com valores nulos
        //este construtor é usado na hora de criar a memória cache, pois ela inicialmente é vazia
        bloco = new TreeMap<>();
        bloco.put("-", -1);
        bloco.put("- ", -1);
    }
    public Bloco(String ender_alvo, MemoriaPrincipal mp) {
        /*
          1- Este método recebrá em seu parâmetro qual o endereço de memória que se deseja trazer para a cache
          2- Irá varrer a memória principal buscando as duas linhas que tenham os três primeiros bits iguais
             aos do endereço desejado
          3- Então irá alocá-las dentro do atributo bloco atráves do bloco.put(endereco, dado)
          4- Substring(0,3) captura os 3 primeiros caracteres da String
        */
        bloco = new TreeMap<>();
        ArrayList<LinhaDeMemoria> memoriaPrincipal = mp.getMemoriaPrincipal();
        ender_alvo = ender_alvo.substring(0,3);
        for (LinhaDeMemoria linha : memoriaPrincipal) {
            String primeiros3Bits = linha.getEndereco().substring(0, 3);
            if (ender_alvo.equals(primeiros3Bits)) {
                bloco.put(linha.getEndereco(), linha.getDado());
            }
        }
    }
    public TreeMap<String, Integer> getBloco() {
        return bloco;
    }
    public void printBloco() { //função para imprimir o bloco da memória cache
        int i = 0;
        for (String s : bloco.keySet()) {
            if (i == 0) {
                if (s.equals("-")) {
                    System.out.println("    " + dirty_bit + "    |    " + s + "    |  " + bloco.get(s) + "    |");
                } else {
                    System.out.println("    " + dirty_bit + "    |  " + s + "   |  " + bloco.get(s) + "   |");
                }
            } else {
                if (s.equals("- ")) {
                    System.out.println("|                 |    " + dirty_bit + "    |    " + s + "   |  " + bloco.get(s) + "    |");
                } else {
                    System.out.println("|                 |    " + dirty_bit + "    |  " + s + "   |  " + bloco.get(s) + "   |");
                }
            }
            i++;
        }
    }
    public void alterarValor(String endereco, int valor) {
        bloco.put(endereco, valor);
        dirty_bit = 1;
    }
    public int isDirty_bit() {
        return dirty_bit;
    }
}
