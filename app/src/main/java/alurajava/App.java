package alurajava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args){
        String apiKey = getApiKey();
        System.out.println(ExchangeValue(apiKey, "USD", "AED"));
    }

    private static String getApiKey() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("apiKey");
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
