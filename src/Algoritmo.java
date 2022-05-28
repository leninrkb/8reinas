import java.util.ArrayList;
import java.util.List;

public class Algoritmo {
    public List<int[]> poblacionInicial() {
        List<int[]> poblacion = new ArrayList();
        int individuo1 [] = {   0,0,0,0,0,0,0,0,
                                0,0,1,0,0,0,0,0,
                                0,0,0,0,0,0,0,0,
                                0,0,0,1,0,0,0,0,
                                0,0,0,0,0,1,0,0,
                                1,0,0,0,0,0,0,0,
                                0,1,0,0,1,0,0,0,
                                0,0,0,0,0,0,1,1,};

        int individuo2 [] = {   0,0,0,0,1,0,0,0,
                                0,0,1,0,0,0,0,0,
                                0,0,0,0,0,0,0,0,
                                0,0,0,0,0,1,1,0,
                                0,1,0,1,0,0,0,0,
                                0,0,0,0,0,0,0,0,
                                1,0,0,0,0,0,0,1,
                                0,0,0,0,0,0,0,0,};
        poblacion.add(individuo1);
        poblacion.add(individuo2);

        return poblacion;
    }

    public void funcionDeAptitud(int[] individuo, int dimensionTablero) {
        int [] posiciones = determinarPosicionesReinas(individuo);
        for (int i : posiciones) {
            System.out.println(i);
        }
    }

    public void seleccion() {

    }

    public void cruzamiento() {

    }

    public void mutacion() {

    }

    public void terminacion() {

    }


    /*-----------------------------------------------------*/

    private int[] determinarPosicionesReinas(int[] individuo){
        int[] posiciones = new int[8];
        int cont = 0;
        for (int i = 0; i < individuo.length; i++) {
            if (individuo[i] == 1) {
                posiciones[cont] = i;
                cont++;
            }          
        }
        return posiciones;
    }
    
    private List<int[]> obtenerSegmentos(int[] individuo, int dimensionTablero) {
        List<int[]> listaSegmentos = new ArrayList();
        int segmentoInicio = 0, segmentoFin = 0;
        for (int i = 0; i <= individuo.length; i++) {
            if ((i != 0) &&  (i % dimensionTablero == 0)) {
                int[] segmentos = new int[2];
                segmentoFin = i-1;
                segmentoInicio = segmentoFin-(dimensionTablero-1);
                segmentos[0] = segmentoInicio;
                segmentos[1] = segmentoFin;
                listaSegmentos.add(segmentos);
                // System.out.print(segmentoInicio + " " + segmentoFin + "\n");
                // for (int j = segmentoInicio; j <= segmentoFin; j++) {
                //     System.out.print(j);

                // }
                // System.out.println();
            }
            
        }
        return listaSegmentos;
    }
}
