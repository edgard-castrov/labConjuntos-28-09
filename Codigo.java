import javax.swing.*;
import java.awt.*;

public class SimuladorQuickSort extends JFrame {

    // Arreglo inicial basado en la imagen proporcionada
    private int[] arreglo = {4, 3, 1, 5, 2, 6, 8};
    
    // Variables de estado para la interfaz gráfica
    private int indicePivote = -1;
    private int indiceI = -1;
    private int indiceJ = -1;
    private int valorPivoteActual = -1;
    private boolean ordenado = false;

    public SimuladorQuickSort() {
        setTitle("Simulación Quick Sort - 100% Recursivo (Sin Ciclos)");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PanelOrdenamiento());
    }

    // Método para iniciar la animación en un hilo separado
    public void iniciarSimulacion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pausa(1000); // Pausa inicial
                quickSortRecursivo(0, arreglo.length - 1);
                
                // Limpiar punteros al finalizar
                indicePivote = -1;
                indiceI = -1;
                indiceJ = -1;
                ordenado = true;
                actualizarUI();
            }
        }).start();
    }

    // 1. RECURSIVIDAD PRINCIPAL DE QUICK SORT
    private void quickSortRecursivo(int low, int high) {
        if (low < high) {
            int pi = particionRecursiva(low, high);
            quickSortRecursivo(low, pi - 1);
            quickSortRecursivo(pi + 1, high);
        }
    }

    // 2. PARTICIÓN RECURSIVA (Reemplaza al ciclo externo)
    private int particionRecursiva(int low, int high) {
        int pivote = arreglo[low];
        valorPivoteActual = pivote;
        indicePivote = low;

        // Inicia el cruce de punteros de forma recursiva
        return bucleParticion(low, high, low, high, pivote);
    }

    // Función que simula el bucle principal de partición
    private int bucleParticion(int low, int high, int i, int j, int pivote) {
        // Mover 'i' recursivamente hacia la derecha
        i = moverI(i, high, pivote, j);
        
        // Mover 'j' recursivamente hacia la izquierda
        j = moverJ(j, low, pivote, i);

        if (i < j) {
            intercambiar(i, j);
            actualizarUIConEstado(low, i, j);
            // Llamada recursiva simulando la continuación del bucle
            return bucleParticion(low, high, i + 1, j - 1, pivote);
        } else {
            // Cuando se cruzan, intercambiamos pivote con j
            intercambiar(low, j);
            actualizarUIConEstado(j, -1, -1);
            return j;
        }
    }

    // 3. REEMPLAZO DE BUCLE WHILE PARA 'i'
    private int moverI(int i, int high, int pivote, int jActual) {
        actualizarUIConEstado(indicePivote, i, jActual);
        if (i <= high && arreglo[i] <= pivote) {
            return moverI(i + 1, high, pivote, jActual); // Llamada recursiva
        }
        return i;
    }

    // 4. REEMPLAZO DE BUCLE WHILE PARA 'j'
    private int moverJ(int j, int low, int pivote, int iActual) {
        actualizarUIConEstado(indicePivote, iActual, j);
        if (j >= low && arreglo[j] > pivote) {
            return moverJ(j - 1, low, pivote, iActual); // Llamada recursiva
        }
        return j;
    }

    private void intercambiar(int a, int b) {
        int temp = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = temp;
        pausa(800); // Pausa para ver el intercambio
    }

    // Actualiza la interfaz gráfica
    private void actualizarUIConEstado(int piv, int i, int j) {
        indicePivote = piv;
        indiceI = i;
        indiceJ = j;
        actualizarUI();
        pausa(700); // Velocidad de la animación
    }

    private void actualizarUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    private void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // CLASE INTERNA PARA DIBUJAR LA INTERFAZ
    class PanelOrdenamiento extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            
            if (ordenado) {
                g.setColor(new Color(0, 150, 50));
                g.drawString("¡Arreglo Ordenado!", 50, 40);
            } else if (valorPivoteActual != -1 && indicePivote != -1) {
                g.setColor(Color.BLACK);
                g.drawString("Pivote = " + valorPivoteActual, 50, 40);
            }

            // 5. DIBUJADO 100% RECURSIVO (Sin ciclo for)
            dibujarArregloRecursivo(g, 0, 50, 100);
        }

        private void dibujarArregloRecursivo(Graphics g, int index, int x, int y) {
            // Caso base de la recursividad visual
            if (index >= arreglo.length) {
                return;
            }

            // Dibujar el cuadro (Azul suave como en la imagen)
            g.setColor(new Color(100, 150, 220));
            g.fillRect(x, y, 50, 50);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, 50, 50);

            // Dibujar el número
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(arreglo[index]), x + 20, y + 32);

            // Dibujar flecha 'i' (Verde hacia arriba)
            if (index == indiceI) {
                g.setColor(new Color(0, 150, 50)); // Verde
                g.fillPolygon(new int[]{x+15, x+35, x+25}, new int[]{y+80, y+80, y+60}, 3);
                g.drawString("i", x+22, y+100);
            }

            // Dibujar flecha 'j' (Roja hacia arriba)
            if (index == indiceJ) {
                g.setColor(Color.RED);
                // Si i y j están en la misma posición, desplazar 'j' un poco para que se vean ambas
                int offset = (indiceI == indiceJ) ? 25 : 0; 
                g.fillPolygon(new int[]{x+15+offset, x+35+offset, x+25+offset}, new int[]{y+80, y+80, y+60}, 3);
                g.drawString("j", x+22+offset, y+100);
            }

            // Llamada recursiva para dibujar el siguiente elemento
            dibujarArregloRecursivo(g, index + 1, x + 50, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimuladorQuickSort ventana = new SimuladorQuickSort();
                ventana.setVisible(true);
                ventana.iniciarSimulacion();
            }
        });
    }
  }
  
