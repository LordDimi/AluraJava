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
        System.out.println(ExchangeValue(apiKey, "USD", "AED"));
    }


    public static String ExchangeRateAPI(String APIKey, String BaseCoin){
        String exchangeRateJSON = null;
        try {
            exchangeRateJSON = APIClass.APIResponse(APIKey,BaseCoin);
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }
        return exchangeRateJSON;
    }

    public static Double ExchangeValue(String APIKey, String BaseCoin, String ExchCoin){
        Gson gson = new Gson();
        String exchangeRateJSON = ExchangeRateAPI(APIKey, BaseCoin);
        JsonObject jsonObject = gson.fromJson(exchangeRateJSON, JsonObject.class);
        String values = jsonObject.get("conversion_rates").toString();
        Map<String,Double> result = new Gson().fromJson(values, Map.class);
        return result.get(ExchCoin);
    }
}
