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
    private TreeMap<String, Integer> linhas;
    private int dirty_bit; //atributo que irá dizer se foi modificado algum valor dentro do bloco ou não

    public Bloco() { //cria um bloco padrão com valores nulos
        //este construtor é usado na hora de criar a memória cache, pois ela inicialmente é vazia
        linhas = new TreeMap<>();
        linhas.put("vazio", -1);
        linhas.put("vazio ", -1);
        this.dirty_bit = 0;
    }
    public Bloco(String ender_alvo, MemoriaPrincipal mp, boolean alterado) {
        /*
          1- Este método recebrá em seu parâmetro qual o endereço de memória que se deseja trazer para a cache
          2- Irá varrer a memória principal buscando as duas linhas que tenham os três primeiros bits iguais
             aos do endereço desejado
          3- Então irá alocá-las dentro do atributo bloco atráves do bloco.put(endereco, dado)
          4- Substring(0,3) captura os 3 primeiros caracteres da String
        */
        linhas = new TreeMap<>();
        ArrayList<LinhaDeMemoria> memoriaPrincipal = mp.getMemoriaPrincipal();
        ender_alvo = ender_alvo.substring(0,3);
        for (LinhaDeMemoria linha : memoriaPrincipal) {
            String primeiros3Bits = linha.getEndereco().substring(0, 3);
            if (ender_alvo.equals(primeiros3Bits)) {
                linhas.put(linha.getEndereco(), linha.getDado());
            }
        }
        if (alterado == true){ // Caso o bloco tenha sido alterado pelo comando sw, o dirty bit se tornará (1).
            setDirty_bit(1);
        } else {
            setDirty_bit(0);
        }
    }
    public TreeMap<String, Integer> getLinhas() {
        return linhas;
    }
    public void print() { //função para imprimir o bloco da memória cache
        int i = 0; //indicará a linha do bloco a ser impressa
        int controle = 0;
        for (String key : linhas.keySet()) {
            if (i == 0) { // Primeira linha do bloco
                if (key.equals("vazio")) { // Caso o bloco esteja vazio
                    System.out.println("    " + getDirty_bit() + "    |  " + key + "  |  " + linhas.get(key) + "    |");
                } else {
                    System.out.println("    " + getDirty_bit() + "    |  " + key + "   |  " + linhas.get(key) + "   |");
                }
            } else { // Segunda linha do bloco
                if (key.equals("vazio ")) { // caso o bloco
                    System.out.println("|       |         |         |  " + key + " |  " + linhas.get(key) + "    |");
                } else {
                    System.out.println("|       |         |         |  " + key + "   |  " + linhas.get(key) + "   |");
                }
            }
            i++;
        }
    }
    public void alterar_Valor_Bloco(String endereco, int valor) {
        linhas.put(endereco, valor);
        setDirty_bit(1);
    }
    public void atualiza_Memoria_Principal(MemoriaPrincipal mp) {
        for(String key : linhas.keySet()){ // Para cada key, de cada linha do bloco retirado da cache por conta do LRU
            for(LinhaDeMemoria endereco : mp.getMemoriaPrincipal()){ // Irá comparar com cada endereco de cada linha da memória principal
                if( (key == endereco.getEndereco()) && (linhas.get(key) != endereco.getDado()) ){ // Se a key da linha do bloco retirado for igual a um endereço de uma linha de memória, é realizada também a verificação se os dois possuem o mesmo dado
                    endereco.setDado(linhas.get(key)); // Caso tenham dados diferentes, o dado na memória principal é alterado pelo dado armazenado na linha correspondente do bloco retirado
                }
            }
        }
    }
    public int getDirty_bit() {
        return dirty_bit;
    }

    private void setDirty_bit(int valor) {
        this.dirty_bit = valor;
    }
}
