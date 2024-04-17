import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProcesadorHTTP extends Thread {
    private Socket cliente;
    private BufferedReader flujoEntrada;
    private PrintWriter flujoSalida;

    public ProcesadorHTTP(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        procesarSolicitud();
        System.out.println("Solicitud atendida");
        try {
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera la primera línea de la solicitud y la atiende
     */
    private void procesarSolicitud() {
        try {
            //Inicializamos los flujos
            flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream(), "UTF-8"));
            flujoSalida = new PrintWriter(cliente.getOutputStream(), true);

            //Leemos la primera línea de la petición del cliente, que contiene el método y el recurso solicitado
            String peticion = flujoEntrada.readLine();
            System.out.println("Petición recibida: " + peticion);
            //Rompemos la línea en trozos, separados por uno o más espacios
            //Formato esperado: MétodoHttp /recurso Protocolo/Version
            //Ejemplo: GET /wiki/Java HTTP/2
            String[] datos = peticion.split("\s+");
            //Confirmamos que sea una solicitud HTTP
            if (datos[2].contains("HTTP")) {
                switch (datos[0]) {
                    case "GET":
                        procesarGet(datos[1]);
                        break;
                    default: //Solo implementamos respuesta a solicitudes GET
                        System.out.println("Sólo se atienden peticiones GET");
                }
            } else {
                //Qué código de respuesta sería adecuado devolver aquí?
                System.out.println("Sólo se responde a solicitudes HTTP");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void procesarGet(String recurso) {
        String respuestaHtml;
        String cabeceraRespuesta;

        //La respuesta depende del recurso (la página) solicitada
        switch (recurso) {
            case "/":
                //En caso de devolver el contenido de archivos, devolveríamos el index.html
                respuestaHtml = "<html><head><title>Página principal</title></head>" +
                        "<body><h1>Página principal</h1><p>Servidor funciona correctamente</p></body></html>";
                cabeceraRespuesta = "HTTP/1.1 200 OK\n" +
                        "Content-Type:text/html;charset=UTF-8\n" +
                        "Content-Length: " + (respuestaHtml.length() + 2) + "\n";
                flujoSalida.println(cabeceraRespuesta);
                flujoSalida.println(respuestaHtml);
                break;
            default://Si no tenemos el recurso solicitado, error 404
                respuestaHtml = "<html><head><title>Error 404</title></head>" +
                        "<body><h1>Error</h1><p>Página no encontrada</p></body></html>";
                cabeceraRespuesta = "HTTP/1.1 404 Not Found\n" +
                        "Content-Type:text/html;charset=UTF-8\n" +
                        "Content-Length: " + (respuestaHtml.length() + 1) + "\n";
                flujoSalida.println(cabeceraRespuesta);
                flujoSalida.println(respuestaHtml);
                break;
        }
    }
}
