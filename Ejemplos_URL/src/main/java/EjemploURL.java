import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class EjemploURL {
    public static final String URL = "https://www.cifpaviles.net/ciclo-formativo-de-grado-superior/informatica-y-comunicaciones-gs";

    public static void main(String[] args) {
        URL aURL = null;
        try {
            aURL = new URL(URL);
            mostrarDatos(aURL);
            mostrarContenido(aURL);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        }
    }

    private static void mostrarDatos(URL aURL) {
        System.out.println("protocolo = " + aURL.getProtocol());
        System.out.println("host = " + aURL.getHost());
        System.out.println("filename = " + aURL.getFile());
        System.out.println("port = " + aURL.getPort());
        System.out.println("default port = " + aURL.getDefaultPort());
        System.out.println("ref = " + aURL.getRef());
    }

    private static void mostrarContenido(URL aURL) {
        try {
            //Conectamos con la url
            URLConnection urlConnection = aURL.openConnection();

            //Flujo para leer el contenido de la url
            BufferedReader lectura = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String leido;
            while((leido = lectura.readLine()) != null){
                System.out.println(leido);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

