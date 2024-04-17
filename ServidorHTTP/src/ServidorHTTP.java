import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorHTTP {
    private static final int PUERTO = 8000;

    public ServidorHTTP() {
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Esperando clientes...");
            while (true) {
                //Esperar peticiones
                Socket cliente = servidor.accept();
                System.out.println("Atendiendo a cliente");
                ProcesadorHTTP procesador = new ProcesadorHTTP(cliente);
                procesador.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
