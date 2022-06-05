import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {


        Algoritmo a = new Algoritmo();
        List<int[]> poblacionInicial = a.poblacionInicial();
        List<Cromosoma> cromosomas = new ArrayList<>();
        List<Cromosoma> seleccionados = new ArrayList<>();
        List<Cromosoma> nuevos = new ArrayList<>();

        //cromosomas padres
        for (int[] individuo : poblacionInicial) {
            cromosomas.add(new Cromosoma(individuo, a.funcionDeAptitud(individuo, 8)));
        }


        //Algoritmo genetico
        // do {
        //     seleccionados = a.seleccion(cromosomas);
        //     nuevos = a.cruzamiento(seleccionados);
        //     cromosomas = a.mutacion(nuevos);
        // } while (!a.terminacion(cromosomas));

        for (int i = 0; i < 10; i++) {
            seleccionados = a.seleccion(cromosomas);
            nuevos = a.cruzamiento(seleccionados);
            cromosomas = a.mutacion(nuevos);
        }


        //System.out.println("cromosomas "+cromosomas.size());
        //seleccionados = a.seleccion(cromosomas);    
        for (Cromosoma cromosoma : cromosomas) {
            cromosoma.print();
            if(cromosoma.aptitud==24){
                System.out.println("Solucion encontrada");
                break;
            }
        }

        
    }
}
