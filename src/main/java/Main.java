import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static Double calculate(String fromCode, String toCode, Double amount) throws IOException {
        String url_str = "https://api.exchangerate.host/latest?base=" + fromCode + "&symbols=" + toCode;

        Double exchangeRate;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        int response = request.getResponseCode();

        if (response == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputline;
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputline = in.readLine()) != null){
                stringBuilder.append(inputline);
            }in.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

           exchangeRate = jsonObject.getJSONObject("rates").getDouble(toCode);

           return Double.parseDouble(decimalFormat.format((amount * exchangeRate)));

        } else{
            System.out.println("ERROR");
            System.exit(0);
            return 0.0;
        }

    }

    public static void main(String[] args) throws IOException {

        String original = "USD";
        String output = "EUR";

        System.out.print("Please enter the amount of " + original +" you wish to convert to " + output +": ");
        Scanner scanner = new Scanner(System.in);

        Double amount = scanner.nextDouble();
        scanner.close();

        System.out.println(amount + " " + original + " is equal to " + calculate(original, output, amount) + " " + output);

    }
}
