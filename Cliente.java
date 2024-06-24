package escom.clienteservidor;

import java.awt.Desktop;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String hostName = "localhost"; // Dirección del servidor, localhost significa que está en la misma máquina
        int port = 5000; // El puerto debe coincidir con el que el servidor está escuchando

        try (Socket socket = new Socket(hostName, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar una solicitud HTTP simple al servidor
            out.println("GET / HTTP/1.1");
            out.println("Host: " + hostName);
            out.println("Connection: Close");
            out.println();

            // Leer la respuesta del servidor
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            // Guardar el contenido HTML en un archivo
            String filePath = "output.html";
            try (PrintWriter fileOut = new PrintWriter(filePath)) {
                fileOut.println(response.toString());
            }

            System.out.println("Contenido HTML recibido y guardado en " + filePath);

            // Abrir el archivo HTML en el navegador web predeterminado
            File htmlFile = new File(filePath);
            if (htmlFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } else {
                    System.out.println("La apertura automática del navegador no es compatible en este sistema.");
                }
            } else {
                System.out.println("El archivo HTML no fue creado correctamente.");
            }
        } catch (UnknownHostException e) {
            System.out.println("No se pudo detectar el servidor en " + hostName);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("No se pudo obtener I/O para la conexión con: " + hostName);
            System.out.println(e.getMessage());
        }
    }
}
