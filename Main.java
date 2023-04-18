import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CalculadoraTLC {
    private static final String URL_TLC = "https://www.larepublica.co/indicadores-economicos/mercado-cambiario/dolar";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el precio en dólares del producto: ");
            double precioDolares = scanner.nextDouble();

            // Obtener el contenido HTML de la página web del TLC
            Document document = Jsoup.connect(URL_TLC).get();
            Element elemento = document.selectFirst("#tipoCambio");
            String tipoCambioTexto = elemento.text();

            // Analizar el tipo de cambio para obtener el valor actual
            double tipoCambio = analizarTipoCambio(tipoCambioTexto);

            // Calcular el precio en pesos y determinar si es mejor importar
            double precioPesos = precioDolares * tipoCambio;
            if (precioPesos > 1000) {
                System.out.println("Es mejor importar el producto.");
            } else {
                System.out.println("Es mejor comprar el producto en el país.");
            }
        }
    }
    import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class CalculadoraComercio {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el enlace de la página con la información de comercio: ");
        String enlace = scanner.nextLine();

        JSONObject datosComercio = obtenerDatosComercio(enlace);
        double momentoOptimo = calcularMomentoOptimo(datosComercio);

        System.out.println("El momento óptimo para importar es " + momentoOptimo);
    }

    private static JSONObject obtenerDatosComercio(String enlace) throws IOException {
        URL url = new URL(enlace);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder respuesta = new StringBuilder();
            while (scanner.hasNextLine()) {
                respuesta.append(scanner.nextLine());
            }
            scanner.close();
            connection.disconnect();
            return new JSONObject(respuesta.toString());
        } else {
            throw new IOException("Error al obtener los datos de la página: " + responseCode);
        }
    }

    private static double calcularMomentoOptimo(JSONObject datosComercio) {
        double max = datosComercio.getDouble("max");
        double min = datosComercio.getDouble("min");

        return (max - min) / max;
    }
}


    private static double analizarTipoCambio(String tipoCambioTexto) {
        // Analizar el tipo de cambio para obtener el valor actual
        // En este ejemplo, simplemente se toma el primer número que aparece
        String[] partes = tipoCambioTexto.split(" ");
        String numeroTexto = partes[0];
        return Double.parseDouble(numeroTexto);
    }
}
