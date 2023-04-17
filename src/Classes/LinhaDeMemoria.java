package Classes;
import java.util.Random;

public class LinhaDeMemoria {
    private final String endereco; // Nome da linnha de memória ex: "0000"
    private int dado; // Dado armazenado na linha de memória ex: 45

    public LinhaDeMemoria(int n_linha) {
        // Lista com as Strings que serão utilizadas como endereço da MemóriaPrincial
        String[] linhas = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010"};

        Random rdn = new Random(); // Classe utilizada para capturar um valor rancômico
        this.endereco = linhas[n_linha]; // Atributo endereço receberá o array linhas na posição do parâmetro n_linhas
        this.dado = rdn.nextInt(100, 200); // Atributo valor receberá um número aleatório de 100 a 200
    }
    public String getEndereco() {
        return endereco;
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }
    public int getEnderecoDecimal() {
        // Retornará endereço binário transformado em decimal (exemplo: "0011" -> return 3)
        return Integer.parseInt(endereco, 2);
    }
}
