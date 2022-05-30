import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        FileOutputStream  os = new FileOutputStream("iteraciones.txt");
        PrintStream ps = new PrintStream(os);


        Algoritmo a = new Algoritmo();
        List<int[]> poblacionInicial = a.poblacionInicial();
        List<Cromosoma> cromosomas = new ArrayList<>();
        List<Cromosoma> seleccionados = new ArrayList<>();
        List<Cromosoma> nuevos = new ArrayList<>();

        for (int[] individuo : poblacionInicial) {
            cromosomas.add(new Cromosoma(individuo, a.funcionDeAptitud(individuo, 8)));
        }
        

        
        do {
            seleccionados = a.seleccion(cromosomas);
            nuevos = a.cruzamiento(seleccionados);
            cromosomas = a.mutacion(nuevos);
        } while (!a.terminacion(cromosomas));

        // for (int index = 0; index < 20000; index++) {
        //     seleccionados = a.seleccion(cromosomas);
        //     nuevos = a.cruzamiento(seleccionados);
        //     cromosomas = a.mutacion(nuevos);
        // }


        for (Cromosoma cromosoma : cromosomas) {
            cromosoma.print();
            if(cromosoma.aptitud==0){
                System.out.println("Solucion encontrada");
                break;
            }
        }

        
    }
}
