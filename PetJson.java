package cs1302.omega;

import com.google.gson.*;
import java.net.URL;
import java.io.InputStreamReader;
import javafx.scene.image.Image;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 *This class will be utlized to parse the json of the cat and dog api.
 */
public class PetJson {

    URL url;
    InputStreamReader reader;
    String animalUrl;
    Image image;
    JsonArray jsonResults;

    /**
     * This method takes in an api url and parses the response into the proper image format.
     * @param petQuery of type String
     * @return an Image object.
     */
    public Image petParser (String petQuery) {


        try {

            url = new URL(petQuery);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK means the connection has been made sucessfully.
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();
                JsonParser parse = new JsonParser();
                JsonArray dataObject = (JsonArray) parse.parse(String.valueOf(informationString));

                //Get the first JSON object in the JSON array
                JsonObject dogData = (JsonObject) dataObject.get(0);
                JsonElement imageHolder = dogData.get("url");

                String data = imageHolder.getAsString();

                image = new Image(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return image;
    } //petparser


} // PetJson
