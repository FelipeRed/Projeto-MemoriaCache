package Classes;
import java.util.ArrayList;
import java.util.HashMap;

public class Bloco {
    private HashMap<String, Integer> bloco;
    private boolean dirty_bit = false; //atributo que irá dizer se foi modificado algum valor dentro do bloco ou não

    public Bloco() {
        bloco = new HashMap<>();
        bloco.put("nul0", -1);
        bloco.put("nul1", -1);
    }
    public Bloco(String ender_alvo, MemoriaPrincipal mp) {
        /*
          - Este método recebrá em seu parâmetro qual o endereço de memória que se deseja trazer para a cache
          - Em seguida irá varrer a memória principal buscando as duas linhas que tenham os três primeiros bits iguais
            aos do endereço desejado
          - Então irá alocá-las dentro do atributo bloco
          - Substring(0,3) captura os 3 primeiros caracteres da String
        */
        bloco = new HashMap<>();
        ArrayList<LinhaDeMemoria> memoriaPrincipal = mp.getMemoriaPrincipal();
        ender_alvo = ender_alvo.substring(0,3);
        for (LinhaDeMemoria linha : memoriaPrincipal) {
            String primeiros3Bits = linha.getEndereco().substring(0, 3);
            if (ender_alvo.equals(primeiros3Bits)) {
                bloco.put(linha.getEndereco(), linha.getDado());
            }
        }
    }
    public HashMap<String, Integer> getBloco() {
        return bloco;
    }
    public void alterarValor(String endereco, int valor) {
        bloco.put(endereco, valor);
        dirty_bit = true;
    }
    public boolean isDirty_bit() {
        return dirty_bit;
    }
}
