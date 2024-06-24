package escom.clienteservidor;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int port = 5000; // Puerto en el que el servidor escuchará
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado, escuchando en el puerto: " + port);

            // El servidor necesita correr indefinidamente
            while (true) {
                try {
                    try (Socket clientSocket = serverSocket.accept() // Aceptar una conexión de un cliente
                    ) {
                        System.out.println("Cliente conectado");
                        // Abrir streams para enviar y recibir datos del cliente
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        // Leer la solicitud del cliente (sin hacer nada con ella en este caso)
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            if (inputLine.isEmpty()) {
                                break;
                            }
                        }   // Contenido HTML para enviar al cliente
                        String htmlContent = "<html><body><h1>Hola, cliente!</h1><p>Este es un mensaje HTML desde el servidor.</p></body></html>";
                        // Enviar una respuesta HTTP al cliente
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: text/html; charset=UTF-8");
                        out.println("Content-Length: " + htmlContent.length());
                        out.println();
                        out.println(htmlContent);
                        // Cerrar la conexión con el cliente después de enviar el mensaje
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }
}
