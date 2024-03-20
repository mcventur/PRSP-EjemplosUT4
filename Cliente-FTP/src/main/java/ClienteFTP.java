import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Scanner;

public class ClienteFTP {

    private static final String SERVIDOR_FTP = "ftp.rediris.es";
    private static final String FICHERO_DESCARGA = "README";

    public static void main(String[] args) throws IOException {
        FTPClient cliente = new FTPClient();
        System.out.println("Vamos a conectar a " + SERVIDOR_FTP);
        //Indicamos el juego de caracteres para usar en la conexión de control
        cliente.setControlEncoding("UTF-8");
        cliente.connect(SERVIDOR_FTP);
        //Mostramos la respuesta String
        System.out.println(cliente.getReplyString());
        //Recogemos la respuesta numérica (código de respuesta)
        int codRespuesta = cliente.getReplyCode();
        //Comprobamos si el código de respuesta no es de conexión aceptada
        if(!FTPReply.isPositiveCompletion(codRespuesta)){
            cliente.disconnect();
            System.out.println("Conexión rechazada con código " + codRespuesta);
            //Salimos del programa con un código de error
            System.exit(0);
        }

        boolean logOk = cliente.login("","");
        System.out.println(cliente.getReplyString());
        if(logOk){
            listarDirectorioActual(cliente);

            //Entro en el directorio debian
            System.out.println("Entrando en el directorio debian");
            cliente.changeWorkingDirectory("debian");
            listarDirectorioActual(cliente);

            System.out.println("Descargando fichero ");
            descargarFichero(FICHERO_DESCARGA, cliente);

        }
        cliente.disconnect();
        System.out.println("Conexión finalizada");
    }

    private static void descargarFichero(String ficheroRemoto, FTPClient cliente) {
        //Creamos el directorio "descargas" en la raiz del proyecto, si no existe
        File descargas = new File("descargas");
        if(!descargas.isDirectory()){
            descargas.mkdir();
        }


        //Creamos un OutputStream para crear un fichero local en el directorio descargas
        try(BufferedOutputStream flujoFicheroLocal = new BufferedOutputStream(new FileOutputStream("descargas/" + ficheroRemoto))){
            boolean descargaOk = cliente.retrieveFile(ficheroRemoto, flujoFicheroLocal);
            if(descargaOk){
                System.out.println("Archivo descargado corrrectamente");
            }
        } catch (IOException e){
            System.out.println("Error al descargar el fichero " + ficheroRemoto + ": " + e.getMessage());
        }
    }

    private static void esperarEnter() throws IOException {
        System.out.println("Pulsa enter para continuar: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        input.readLine();
    }

    private static void listarDirectorioActual(FTPClient cliente) throws IOException {
        //Mostramos la ruta actual
        System.out.println("\n\nDirectorio actual: " + cliente.printWorkingDirectory());
        //Mostramos el contenido
        for (FTPFile ftpFile : cliente.listFiles()) {
            System.out.println(ftpFile);
        }
    }
}
