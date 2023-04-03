import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CalculadoraTLC {
    private static final String URL_TLC = "https://www.example.com/tlc";

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

    private static double analizarTipoCambio(String tipoCambioTexto) {
        // Analizar el tipo de cambio para obtener el valor actual
        // En este ejemplo, simplemente se toma el primer número que aparece
        String[] partes = tipoCambioTexto.split(" ");
        String numeroTexto = partes[0];
        return Double.parseDouble(numeroTexto);
    }
}
