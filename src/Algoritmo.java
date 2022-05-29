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
        // int individuo2 [] = {   0,0,0,0,0,1,0,0,
        //                         0,0,1,0,0,0,0,0,
        //                         0,0,0,0,0,0,0,0,
        //                         0,0,0,0,1,1,1,0,
        //                         0,1,0,0,0,0,0,0,
        //                         0,0,0,0,0,0,0,0,
        //                         1,0,0,0,0,1,0,0,
        //                         0,0,0,0,0,0,0,0,};
        poblacion.add(individuo1);
        poblacion.add(individuo2);

        return poblacion;
    }

    public void funcionDeAptitud(int[] individuo, int dimensionTablero) {
        List<int[]> segmentos = obtenerSegmentos(individuo, dimensionTablero);
        int[] posiciones = determinarPosicionesReinas(individuo);
        // for (int posicionReina : posiciones) {
        //     determinarNumeroAtaquesDeReina(posicionReina, individuo, determinarSegmentoPertenece(segmentos, posicionReina));
        // }
        System.out.println("numero ataques:" + determinarNumeroAtaquesDeReina(0, individuo, segmentos));
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

    private int[] determinarSegmentoPertenece(List<int[]> segmentos, int posicionReina){
        for (int[] segmento : segmentos) {
            if ((posicionReina >= segmento[0]) && (posicionReina <= segmento[1])) {
                return segmento;
            }
        }
        return null;
    }

    private int indentificarFinEsquinaInferiorIzquierda(List<int[]> segmentos, int posicionReina, int[] individuo){
        int actual=posicionReina, despues=0;
        while ((actual + 7) <= 63) {
            despues = actual + 7;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s1[1]+1) != s2[0])) {
                    break;
                }
            }
            actual += 7;
        }
        return actual;
    }

    private int indentificarFinEsquinaInferiorDerecha(List<int[]> segmentos, int posicionReina, int[] individuo){
        int actual=posicionReina, despues=0;
        while ((actual + 9) <= 63) {
            despues = actual + 9;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if ( (s1 == s2) || ((s1[1]+1) != s2[0]) ) {
                    break;
                }
            }
            actual += 9;
        }
        return actual;
    }

    private int indentificarFinEsquinaSuperiorDerecha(List<int[]> segmentos, int posicionReina, int[] individuo){
        int actual=posicionReina, despues=0;
        while ((actual - 7) >= 0) {
            despues = actual - 7;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s2[1]+1) != s1[0])) {
                    break;
                }
            }
            actual -= 7;
        }
        return actual;
    }

    private int indentificarFinEsquinaSuperiorIzquierda(List<int[]> segmentos, int posicionReina, int[] individuo){
        int actual=posicionReina, despues=0;
        while ((actual - 9) >= 0) {
            despues = actual - 9;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s2[1]+1) != s1[0])) {
                    break;
                }
            }
            actual -= 9;
        }
        return actual;
    }

    private int determinarNumeroAtaquesDeReina(int posicionReina, int[] individuo, List<int[]> segmentos) {
        int numeroAtaques = 0;
        int[] segmentosPertenece = determinarSegmentoPertenece(segmentos, posicionReina);
        //horizontal
        for (int i = segmentosPertenece[0]; i < segmentosPertenece[1]; i++) {
            if ((i != posicionReina) && (individuo[i] == 1)) {
                numeroAtaques++;
            }
        }

        //vertical arriba
        int aux = posicionReina;
        while ((aux - 8) >= 0) {
            if (individuo[(aux - 8)] == 1) {
                numeroAtaques++;
            }
            aux -= 8;
        }
        //vertical abajo
        aux = posicionReina;
        while ((aux + 8) <= 63) {
            if (individuo[(aux + 8)] == 1) {
                numeroAtaques++;
            }
            aux += 8;
        }

        //diagonal1 abajo
        aux = posicionReina;
        while ((aux + 7) <= indentificarFinEsquinaInferiorIzquierda(segmentos, aux, individuo)) {
            if (individuo[(aux + 7)] == 1) {
                numeroAtaques++;
            }
            aux += 7;  
            System.out.println(aux);

        }
        //diagonal1 arriba
        aux = posicionReina;
        while ((aux - 7) >= indentificarFinEsquinaSuperiorDerecha(segmentos, aux, individuo)) {
            if (individuo[(aux - 7)] == 1) {
                numeroAtaques++;
            }
            aux -= 7;
            System.out.println(aux);

        }

        //diagonal2 arriba
        aux = posicionReina;
        while ((aux - 9) >= indentificarFinEsquinaSuperiorIzquierda(segmentos, aux, individuo)) {
            if (individuo[(aux - 9)] == 1) {
                numeroAtaques++;
            }
            aux -= 9;
            System.out.println(aux);

        }

        //diagonal2 abajo
        aux = posicionReina;
        while ((aux + 9) <= indentificarFinEsquinaInferiorDerecha(segmentos, aux, individuo)) {
            if (individuo[(aux + 9)] == 1) {
                numeroAtaques++;
            }
            aux += 9;
            System.out.println(aux);
        }

        return numeroAtaques;
    }
}
