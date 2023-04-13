import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(9876);
        System.out.println("Servidor iniciado na porta 9876");

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

            // Inicia uma nova thread para lidar com a conexão do cliente
            Thread t = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);

                    // Lê as matrizes enviadas pelo cliente
                    int[][] matriz1 = new int[2][2];
                    int[][] matriz2 = new int[2][2];
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            matriz1[i][j] = Integer.parseInt(in.readLine());
                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            matriz2[i][j] = Integer.parseInt(in.readLine());
                        }
                    }

                    // Realiza a multiplicação das matrizes
                    int[][] resultado = new int[2][2];
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 2; k++) {
                                resultado[i][j] += matriz1[i][k] * matriz2[k][j];
                            }
                        }
                    }

                    // Envia o resultado da multiplicação para o cliente
                    out.println(resultado[0][0]);
                    out.println(resultado[0][1]);
                    out.println(resultado[1][0]);
                    out.println(resultado[1][1]);

                    cliente.close();
                } catch (IOException e) {
                    System.out.println("Erro ao comunicar com o cliente: " + e.getMessage());
                }
            });
            t.start();
        }
    }
}
