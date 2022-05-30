import java.util.Arrays;

public class Cromosoma {

    int[] individuo;
    Integer aptitud;

    public Cromosoma(int[] individuo1, int aptitud1) {
        this.individuo = individuo1;
        this.aptitud = aptitud1;
    }

    public void print1() {
        for (int i = 0; i < individuo.length; i++) {
            if (individuo[i] == 1)
                System.out.print("R ");
            else
                System.out.print(". ");
            if ((i + 1) % 8 == 0)
                System.out.println();
        }
        System.out.println("apt: " + aptitud + "\n");
    }

    private int contarReinas(){
        int contador = 0;
        for (int i = 0; i < individuo.length; i++) {
            if (individuo[i] == 1)
                contador++;
        }
        return contador;
    }


    
    public void print() {
        int n = 0;
        for (int i = 0; i <= 7; i++) {
            n = i;
            for (int j = 0; j <= 7; j++) {
                if (individuo[n] == 1)
                    System.out.print("R ");
                else
                    System.out.print(". ");
                n += 8;
            }
            System.out.println();
        }
        System.out.println("apt: " + aptitud);
        System.out.println("reinas: " + contarReinas() + "\n");

    }
}
