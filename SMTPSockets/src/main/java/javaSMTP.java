import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Dado que el dominio de los correos es inventado (no existe o no somos realmente sus dueños) y usamos autenticación insegura
 * se debe ejecutar este ejemplo desde un servidor smtp local
 */

class javaSMTP {

    public static void main(String[] args) {
        String servidor = "localhost";
        String dominio = "prsp.com";
        String usuario = "marce@prsp.com";
        String destino = "destino@prsp.com";
        int puerto = 25;  // puerto SMTP

        try(Socket socket = new Socket(servidor, puerto)) {
            // Abrimos un socket conectado al servidor y al
            // puerto del protocolo SMTP


            //Canal de entrada
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //Canal de salida
            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true);

            // Comenzamos la conversación con el servidor de
            // correo electrónico
            salida.println("helo " + dominio);
            System.out.println(entrada.readLine());
            salida.println("mail from: " + usuario);
            System.out.println(entrada.readLine());
            salida.println("rcpt to: " + destino);
            System.out.println(entrada.readLine());

            //Contenido del mensaje
            salida.println("data");
            System.out.println(entrada.readLine());
            //Preparamos un timestamp para crear mensajes distintos
            String timeStamp = (new Date()).toString();
            //Asunto
            salida.println("subject: mensaje de prueba " + timeStamp);
            //Debe haber una línea en blanco tras el subject
            salida.println();
            //Cuerpo
            salida.println("Hola mundo. Adios.");
            salida.println(".");
            System.out.println("quit");
            System.out.println(entrada.readLine());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Debes estar conectado para que esto funcione bien.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
