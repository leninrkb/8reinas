import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Algoritmo {
    FileOutputStream os;
    PrintStream ps;

    public List<int[]> poblacionInicial() {
        List<int[]> poblacion = new ArrayList();

        // int individuo1[] = { 0, 0, 0, 0, 0, 0, 0, 0,
        //                     0, 0, 1, 0, 0, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 0, 0, 0,
        //                     0, 0, 0, 1, 0, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 1, 0, 0,
        //                     1, 0, 0, 0, 0, 0, 0, 0,
        //                     0, 1, 0, 0, 1, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 0, 1, 1, };

        // int individuo2[] = { 0, 0, 0, 0, 1, 0, 0, 0,
        //                     0, 0, 1, 0, 0, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 1, 1, 0,
        //                     0, 1, 0, 1, 0, 0, 0, 0,
        //                     0, 0, 0, 0, 0, 0, 0, 0,
        //                     1, 0, 0, 0, 0, 0, 0, 1,
        //                     0, 0, 0, 0, 0, 0, 0, 0, };
        
        int individuo1[] = { 0, 0, 0, 1, 0, 0, 0, 0,
                            0, 0, 1, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1, 0, 0,
                            0, 0, 0, 1, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1, 0, 0,
                            1, 0, 0, 0, 0, 0, 0, 0,
                            0, 1, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 1, };

        int individuo2[] = { 0, 0, 0, 0, 1, 0, 0, 0,
                            0, 0, 1, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 1, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 1, 0,
                            0, 0, 0, 1, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1, 0, 0,
                            1, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 1, 0, 0, 0, };

        poblacion.add(individuo1);
        poblacion.add(individuo2);

        return poblacion;
    }

    public int funcionDeAptitud(int[] individuo, int dimensionTablero) {
        List<int[]> segmentos = obtenerSegmentos(individuo, dimensionTablero);
        int[] posiciones = determinarPosicionesReinas(individuo);
        int ataques = 0;
        for (int posicionReina : posiciones) {
            ataques += determinarNumeroAtaquesDeReina(posicionReina, individuo, segmentos);
        }
        return ataques;
    }

    public List<Cromosoma> seleccion(List<Cromosoma> cromosomas) throws FileNotFoundException {
        if (cromosomas.size() <= 2) {
            return cromosomas;
        } else {
            
            List<Cromosoma> ordenados = cromosomas;
            for (int i = 0; i < ordenados.size(); i++) {
                if ((i + 1) >= ordenados.size())
                    break;
                Cromosoma actual=ordenados.get(i), siguiente=ordenados.get(i + 1);
                if ( actual.aptitud > siguiente.aptitud) {
                    ordenados.set(i, siguiente);
                    ordenados.set(i + 1, actual);
                    i--;
                }

            }

            int aptitudGlobal = 0;
            for (Cromosoma cromosoma : ordenados) {
                aptitudGlobal += cromosoma.aptitud;
            }

            List<Cromosoma> probables = new ArrayList<>();
            HashMap<Integer, Cromosoma> probabilidades = new HashMap<>();
            int cant = (ordenados.size()/2) + 1;
            for (int i = 0; i < cant; i++) {
                if(!probabilidades.containsKey(ordenados.get(i).aptitud)){
                    probabilidades.put(ordenados.get(i).aptitud, ordenados.get(i));
                    probables.add(ordenados.get(i));
                } 
                
            }

            os = new FileOutputStream("seleccion.txt");
            ps = new PrintStream(os);

            ps.println("aptitud global: " + aptitudGlobal);
            ps.println("total: " + ordenados.size());
            String general="";
            for (Cromosoma cromosoma : ordenados) {
                general += ("sus aptitudes: " + cromosoma.aptitud + "\n");
            }
            ps.println(general);
            
            general ="";
            ps.println("probables selecionados: " + probables.size());
            for (Cromosoma cromosoma : probables) {
                general +=("sus aptitudes: " + cromosoma.aptitud + "\n");
            }
            ps.println(general);
            ps.println("/--------------/");
            
            return probables;
        }
    }

    

    public List<Cromosoma> cruzamiento(List<Cromosoma> parent1) throws FileNotFoundException {
        List<Cromosoma> parent2 = parent1;
        List<Cromosoma> nuevos = new ArrayList<>();
        for (int i = 0; i < parent1.size(); i++) {
            for (int j = 0; j < parent1.size(); j++) {
                if(!(i==j)){
                    int[] _parent1 = parent1.get(j).individuo;
                    int[] _parent2 = parent2.get(i).individuo;
                    int[] _nuevo = new int[_parent1.length];
                    int corte = generarCorte();
                    for (int k = 0; k < corte; k++) {
                        _nuevo[k] = _parent1[k];
                    }
                    for (int k = corte; k < _parent1.length; k++) {
                        _nuevo[k] = _parent2[k];
                    }
                    nuevos.add(new Cromosoma(_nuevo, funcionDeAptitud(_nuevo, 8)));
                }
            }   
        }

        for (Cromosoma cromosoma : parent1) {
            nuevos.add(cromosoma);
        }

        return nuevos;
    }

    public List<Cromosoma> mutacion(List<Cromosoma> originales) throws FileNotFoundException {   
        List<Cromosoma> nuevos = new ArrayList<>();
        int mutar = (originales.size()/2) + 1;
        int n=0;
        System.out.println("original");
        for (Cromosoma cromosoma : originales) {
            cromosoma.print();
        }


        System.out.println("mutado");
        for (Cromosoma cromosoma : originales) {
            int[] individuo_original = cromosoma.individuo;
            int[] segmento = generarSegmento();
            int posicion = generarRandom();
            int m = segmento[0] + posicion;
            for (int i = segmento[0]; i <= segmento[1]; i++) {
                if(individuo_original[i] == 1){
                    individuo_original[i] = 0;
                }
            }
            individuo_original[m] = 1;
            cromosoma.individuo = individuo_original;
            nuevos.add(cromosoma);
            if(n == mutar) break;
            n++;
        }
       return nuevos;
    }

    public Boolean terminacion(List<Cromosoma> cromosomas) {

        for (Cromosoma cromosoma : cromosomas) {
            if (cromosoma.aptitud == 0 || cromosoma.aptitud == 2) {
                System.out.println("/****************************************/");
                cromosoma.print();
                System.out.println("solucion encontrada: " + cromosoma.aptitud);
                return true;
            }
        }
        return false;
    }

    /*-----------------------------------------------------*/

    private int generarCorte() throws FileNotFoundException{
        Random random = new Random();
        Integer[] lista = {7,15,23,31,39,47,55};
        int pos = random.nextInt(lista.length);
        int n = lista[pos];
        return n;
    }

    private int generarRandom() {
        Random random = new Random();
        Integer[] lista = {0,1,2,3,4,5,6};
        int pos = random.nextInt(lista.length);
        int n = lista[pos];
        return n;
    }

    private int[] generarSegmento(){
        Random random = new Random();
        int[] lista = {0,7};
        int[] lista2 = {8,15};
        int[] lista3 = {16,23};
        int[] lista4 = {24,31};
        int[] lista5 = {32,39};
        int[] lista6 = {40,47};
        int[] lista7 = {48,55};
        int[] lista8 = {56,63};
        List<int[]> todo = new ArrayList<>();
        todo.add(lista);
        todo.add(lista2);
        todo.add(lista3);
        todo.add(lista4);
        todo.add(lista5);
        todo.add(lista6);
        todo.add(lista7);
        todo.add(lista8);


        int pos = generarRandom();
        return todo.get(pos);
    }

    private int[] determinarPosicionesReinas(int[] individuo) {
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
            if ((i != 0) && (i % dimensionTablero == 0)) {
                int[] segmentos = new int[2];
                segmentoFin = i - 1;
                segmentoInicio = segmentoFin - (dimensionTablero - 1);
                segmentos[0] = segmentoInicio;
                segmentos[1] = segmentoFin;
                listaSegmentos.add(segmentos);
                // System.out.print(segmentoInicio + " " + segmentoFin + "\n");
                // for (int j = segmentoInicio; j <= segmentoFin; j++) {
                // System.out.print(j);

                // }
                // System.out.println();
            }

        }
        return listaSegmentos;
    }

    private int[] determinarSegmentoPertenece(List<int[]> segmentos, int posicionReina) {
        for (int[] segmento : segmentos) {
            if ((posicionReina >= segmento[0]) && (posicionReina <= segmento[1])) {
                return segmento;
            }
        }
        return null;
    }

    private int indentificarFinEsquinaInferiorIzquierda(List<int[]> segmentos, int posicionReina, int[] individuo) {
        int actual = posicionReina, despues = 0;
        while ((actual + 7) <= 63) {
            despues = actual + 7;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s1[1] + 1) != s2[0])) {
                    break;
                }
            }
            actual += 7;
        }
        return actual;
    }

    private int indentificarFinEsquinaInferiorDerecha(List<int[]> segmentos, int posicionReina, int[] individuo) {
        int actual = posicionReina, despues = 0;
        while ((actual + 9) <= 63) {
            despues = actual + 9;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if ((s1 == s2) || ((s1[1] + 1) != s2[0])) {
                    break;
                }
            }
            actual += 9;
        }
        return actual;
    }

    private int indentificarFinEsquinaSuperiorDerecha(List<int[]> segmentos, int posicionReina, int[] individuo) {
        int actual = posicionReina, despues = 0;
        while ((actual - 7) >= 0) {
            despues = actual - 7;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s2[1] + 1) != s1[0])) {
                    break;
                }
            }
            actual -= 7;
        }
        return actual;
    }

    private int indentificarFinEsquinaSuperiorIzquierda(List<int[]> segmentos, int posicionReina, int[] individuo) {
        int actual = posicionReina, despues = 0;
        while ((actual - 9) >= 0) {
            despues = actual - 9;
            int[] s1 = determinarSegmentoPertenece(segmentos, actual);
            int[] s2 = determinarSegmentoPertenece(segmentos, despues);

            if (s1 != null && s2 != null) {
                if (s1 == s2 || ((s2[1] + 1) != s1[0])) {
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
        // horizontal
        for (int i = segmentosPertenece[0]; i < segmentosPertenece[1]; i++) {
            if ((i != posicionReina) && (individuo[i] == 1)) {
                numeroAtaques++;
            }
        }

        // vertical arriba
        int aux = posicionReina;
        while ((aux - 8) >= 0) {
            if (individuo[(aux - 8)] == 1) {
                numeroAtaques++;
            }
            aux -= 8;
        }
        // vertical abajo
        aux = posicionReina;
        while ((aux + 8) <= 63) {
            if (individuo[(aux + 8)] == 1) {
                numeroAtaques++;
            }
            aux += 8;
        }

        // diagonal1 abajo
        aux = posicionReina;
        while ((aux + 7) <= indentificarFinEsquinaInferiorIzquierda(segmentos, aux, individuo)) {
            if (individuo[(aux + 7)] == 1) {
                numeroAtaques++;
            }
            aux += 7;

        }
        // diagonal1 arriba
        aux = posicionReina;
        while ((aux - 7) >= indentificarFinEsquinaSuperiorDerecha(segmentos, aux, individuo)) {
            if (individuo[(aux - 7)] == 1) {
                numeroAtaques++;
            }
            aux -= 7;

        }

        // diagonal2 arriba
        aux = posicionReina;
        while ((aux - 9) >= indentificarFinEsquinaSuperiorIzquierda(segmentos, aux, individuo)) {
            if (individuo[(aux - 9)] == 1) {
                numeroAtaques++;
            }
            aux -= 9;

        }

        // diagonal2 abajo
        aux = posicionReina;
        while ((aux + 9) <= indentificarFinEsquinaInferiorDerecha(segmentos, aux, individuo)) {
            if (individuo[(aux + 9)] == 1) {
                numeroAtaques++;
            }
            aux += 9;
        }

        return numeroAtaques;
    }
}
