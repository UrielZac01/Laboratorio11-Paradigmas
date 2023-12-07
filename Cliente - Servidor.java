//Servidor en Java
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 5408;

        try (ServerSocket servidorSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor esperando conexiones en el puerto " + puerto + "...");
            while (true) {
                try (Socket clienteSocket = servidorSocket.accept();
                     PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()))) {

                    String mensajeCliente = in.readLine();
                    if ("requesting_home".equals(mensajeCliente)) {
                        String respuesta = "<html><head><title>Saludo</title></head><body><h1>Bienvenido a la pagina de inicio</h1></body></html>";
                        out.println(respuesta);

                        //Guardamos la respuesta en un archivo llamado home.html
                        try (PrintWriter archivo = new PrintWriter("home.html")) {
                            archivo.println(respuesta);
                            System.out.println("Respuesta guardada en home.html");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//Cliente en Java
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5408;

        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar mensaje al servidor
            out.println("requesting_home");

            // Recibir respuesta del servidor
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor:\n" + respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
