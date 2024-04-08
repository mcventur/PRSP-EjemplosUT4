import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import java.io.IOException;


public class ClienteSMTP {
    public static void main(String[] args) {
        SMTPClient client = new SMTPClient();
        try{
            int respuesta;
            //CONECTAMOS CON EL SERVIDOR SMTP
            //Sin segundo argumento, se conecta en el puerto 25
            client.connect("localhost");
            System.out.println(client.getReplyString());
            respuesta = client.getReplyCode();
            if(!SMTPReply.isPositiveCompletion(respuesta)){
                client.disconnect();
                System.err.println("Conexion Rechazada");
                System.exit(1);
            }
            //ENVIAMOS MENSAJE
            client.login();
            String sender = "origen@prsp.com";
            String recipient = "destino@prsp.com";
            String mensaje =
                    "subject: Prueba desde Apache\n\n" + //Importante dos saltos de línea tras el subject, antes del cuerpo
                    "Esto se envía con la librería de Apache. \n" +
                    "Un saludo.";
            client.sendSimpleMessage(sender, recipient, mensaje);
            System.out.println(client.getReplyString());
            client.logout();
            System.out.println(client.getReplyString());
        } catch (IOException e) {
            System.err.println("Error al conectar");
            e.printStackTrace();
        }

        //DESCONECTAMOS
        try{
            client.disconnect();
        }catch (IOException e){
            System.out.println("Error al desconectar");
            e.printStackTrace();
        }
    }
}