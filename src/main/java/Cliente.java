import java.io.*;
import java.net.*;

public class Cliente {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new ClienteRunnable()).start();
        }
    }

    private static class ClienteRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Socket servidor = new Socket(InetAddress.getLocalHost().getHostName(), 9876);
                System.out.println("THREAD " + Thread.currentThread().getId() + ": Conexão estabelecida com o servidor");

                BufferedReader in = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
                PrintWriter out = new PrintWriter(servidor.getOutputStream(), true);

                // Envia as matrizes para o servidor
                int[][] matriz1 = {{1, 2}, {3, 4}};
                int[][] matriz2 = {{5, 6}, {7, 8}};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        out.println(matriz1[i][j]);
                    }
                }
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        out.println(matriz2[i][j]);
                    }
                }

                // Lê o resultado da multiplicação das matrizes
                int[][] resultado = new int[2][2];
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        resultado[i][j] = Integer.parseInt(in.readLine());
                    }
                }

                synchronized (System.out) {
                    System.out.println("Resultado da thread " + Thread.currentThread().getId() + ":");
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            System.out.print(resultado[i][j] + " ");
                        }
                        System.out.println();
                    }
                }

                servidor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
