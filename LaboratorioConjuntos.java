import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

public class LaboratorioConjuntos {

    public static void main(String[] args) {
        // Pedir los 3 conjuntos al usuario
        Set<Integer> c1 = leerConjunto("Ingrese los números del CONJUNTO 1 (separados por espacio o coma):");
        Set<Integer> c2 = leerConjunto("Ingrese los números del CONJUNTO 2 (separados por espacio o coma):");
        Set<Integer> c3 = leerConjunto("Ingrese los números del CONJUNTO 3 (separados por espacio o coma):");

        // 1. Unión de los 3 conjuntos sin duplicados
        Set<Integer> union = new HashSet<>();
        union.addAll(c1);
        union.addAll(c2);
        union.addAll(c3);

        // Listas para almacenar los resultados
        List<Integer> impares = new ArrayList<>();
        List<Integer> pares = new ArrayList<>();
        List<Integer> primos = new ArrayList<>();
        List<Integer> ordenados = new ArrayList<>(union);

        // 2. Filtrar números pares, impares y primos
        for (int num : union) {
            if (num % 2 == 0) {
                pares.add(num);
            } else {
                impares.add(num);
            }

            if (esPrimo(num)) {
                primos.add(num);
            }
        }

        // 3. Ordenar conjuntos de menor a mayor
        Collections.sort(impares);
        Collections.sort(pares);
        Collections.sort(primos);
        Collections.sort(ordenados);

        // 4. Construir la salida para JOptionPane
        StringBuilder resultado = new StringBuilder();
        resultado.append("--- RESULTADOS DEL LABORATORIO ---\n\n");
        resultado.append("Conjunto 1: ").append(c1).append("\n");
        resultado.append("Conjunto 2: ").append(c2).append("\n");
        resultado.append("Conjunto 3: ").append(c3).append("\n\n");
        resultado.append("1. Números Impares: ").append(impares).append("\n");
        resultado.append("2. Números Pares: ").append(pares).append("\n");
        resultado.append("3. Números Primos: ").append(primos).append("\n");
        resultado.append("4. Todos Ordenados (Menor a Mayor): ").append(ordenados).append("\n");

        // Mostrar resultados
        JOptionPane.showMessageDialog(null, resultado.toString(), "Resultado de Conjuntos", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método auxiliar para leer e interpretar la entrada del usuario
    private static Set<Integer> leerConjunto(String mensaje) {
        Set<Integer> conjunto = new HashSet<>();
        String entrada = JOptionPane.showInputDialog(null, mensaje, "Entrada de Datos", JOptionPane.QUESTION_MESSAGE);

        if (entrada != null && !entrada.trim().isEmpty()) {
            // Separa por comas o espacios
            String[] tokens = entrada.trim().split("[,\\s]+");
            for (String token : tokens) {
                try {
                    conjunto.add(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    // Ignora elementos que no sean numeros enteros
                }
            }
        }
        return conjunto;
    }

    // Algoritmo generico para determinar si un numero es primo
    private static boolean esPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}