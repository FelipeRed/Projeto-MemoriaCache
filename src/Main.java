import Ex5.Exercicio5;
import Ex6.Exercicio6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("5- Exercício 5\n6- Exercício 6");
        System.out.println("Qual exercício gostaria de executar: ");
        int escolha = scanner.nextInt();

        if (escolha == 5) {
            Exercicio5.main(args);
        } else {
            Exercicio6.main(args);
        }
    }
}
