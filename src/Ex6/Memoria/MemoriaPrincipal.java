package Ex6.Memoria;

import java.util.ArrayList;

/* Serão criados 15 objetos do tipo LinhaDeMemoria (que irão possuir seu endereço e seu dado armazenado)
E todos esses 15 objetos serão adicionados a uma lista, formando assim a nossa MemoriaPrincipal

Exemplo de duas LinhaDeMemoria: "0000" = 120  //  "0001" = 45
Exemplo de MemoriaPrincipal: ArrayList = {{"0000", 120}, {"0001", 45}}  */

public class MemoriaPrincipal {
    private ArrayList<LinhaDeMemoria> memoriaPrincipal = new ArrayList<>();
    public MemoriaPrincipal() {  //Lógica explicada em cima do nome da classe
        for (int i = 0; i < 16; i++) {
            LinhaDeMemoria linha = new LinhaDeMemoria(i);
            memoriaPrincipal.add(linha);
        }
    }
    public void print() { //função que irá imprimir a memória principal
        System.out.println();
        System.out.println(" MEMÓRIAL PRINCIPAL");
        System.out.println("| Endereço | Valor |");
        //Para cada objeto linha_de_memoria guardado na memoriaPrincipal, será printado seu endereço e dado armazenado
        for (LinhaDeMemoria linha_de_memoria : memoriaPrincipal){
            System.out.printf("|   %s   |  %d  |%n", linha_de_memoria.getEndereco(), linha_de_memoria.getDado());
        }
        System.out.println("--------------------");
    }
    public ArrayList<LinhaDeMemoria> getMemoriaPrincipal() {
        return memoriaPrincipal;
    }
}
