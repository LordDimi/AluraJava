package alurajava;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args){
        String apiKey = "d16ede8e558218804d62e775";
        coinConversion(apiKey);
    }

    public static void coinConversion(String apiKey){
        Map<Integer, String> coins = Map.of(1,"MXN", 2,"ARS", 3,"BOB", 4,"BRL", 5,"CLP", 6,"COP", 7,"USD", 8,"EUR", 9,"PEN");
        String response;
        do{    
        System.out.print("""
                            \t\t CoinConverter

                            1 - MXN - Peso mexicano

                            2 - ARS - Peso argentino

                            3 - BOB - Boliviano boliviano

                            4 - BRL - Real brasileño

                            5 - CLP - Peso chileno

                            6 - COP - Peso colombiano

                            7 - USD - Dólar estadounidense

                            8 - EUR - Euro

                            9 - PEN - Sol peruano

                             """);
            System.out.println("Ingrese el número de la moneda base: ");
            
            String baseCoin = coins.get(Integer.valueOf(System.console().readLine()));
            System.out.println("Ingrese el número de la moneda a la que desea convertir: ");
            String exchCoin = coins.get(Integer.valueOf(System.console().readLine()));
            Double moneyAmount = Double.valueOf(System.console().readLine("Ingrese la cantidad de " + baseCoin + " que desea convertir: "));
            Double exchangeValue = ExchangeValue(apiKey, baseCoin, exchCoin);
            System.out.println("El valor de " + moneyAmount + " " + baseCoin + " en " + exchCoin + " es: " + moneyAmount * exchangeValue + " " + exchCoin);
            System.out.println("¿Desea realizar otra conversión? (s/n)");
            response = System.console().readLine();
            System.console().flush();
        }while(response.equals("s"));
    }
    

    public static String ExchangeRateAPI(String APIKey, String BaseCoin){
        String exchangeRateJSON = null;
        try {
            exchangeRateJSON = APIClass.APIResponse(APIKey,BaseCoin);
        } catch (IOException | InterruptedException ex) {
        }
        return exchangeRateJSON;
    }

    public static Double ExchangeValue(String APIKey, String BaseCoin, String ExchCoin){
        Gson gson = new Gson();
        String exchangeRateJSON = ExchangeRateAPI(APIKey, BaseCoin);
        JsonObject jsonObject = gson.fromJson(exchangeRateJSON, JsonObject.class);
        String values = jsonObject.get("conversion_rates").toString();
        Map<String, Double> result = new Gson().fromJson(values, new com.google.gson.reflect.TypeToken<Map<String, Double>>(){}.getType());
        return result.get(ExchCoin);
    }
}
