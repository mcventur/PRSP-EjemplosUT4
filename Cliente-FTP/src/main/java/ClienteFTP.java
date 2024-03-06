import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class ClienteFTP {

    private static final String SERVIDOR_FTP = "ftp.rediris.es";

    public static void main(String[] args) throws IOException {
        FTPClient cliente = new FTPClient();
        System.out.println("Vamos a conectar a " + SERVIDOR_FTP);
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
            System.out.println("Listado de ficheros: ");
            for (FTPFile ftpFile : cliente.listFiles()) {
                System.out.println(ftpFile);
            }
        }

        cliente.disconnect();
        System.out.println("Conexión finalizada");
    }
}
