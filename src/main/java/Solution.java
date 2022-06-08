import graphClasses.Graph;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Solution{
    public static void getResult(String path, String race) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = null;

        switch (race){ //Берём данные для выбранной расы
            case "Human" -> reader = new FileReader("src/main/java/Json/Human.json");
            case "Swamper" -> reader = new FileReader("src/main/java/Json/Swamper.json");
            case "Woodman" -> reader = new FileReader("src/main/java/Json/Woodman.json");
            default -> System.out.println("The race is unavailable!");
        }

        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        int S = Integer.parseInt(jsonObject.get("S").toString());
        int W = Integer.parseInt(jsonObject.get("W").toString());
        int T = Integer.parseInt(jsonObject.get("T").toString());
        int P = Integer.parseInt(jsonObject.get("P").toString());


        assert reader != null;
        reader.close();

        Graph graph = new Graph();
        for (int i = 65; i <= 80 ; i++) {
            char letter = (char) i;
            graph.addVertex(String.valueOf(letter));
        }

        String[] cells = path.split("");

        //Заполнение графа
        //////////////////////////////////////////////
        for(int i = 0; i < 4; i++){
            switch (cells[i + 1]){
                case "S" -> graph.addEdge(i, i + 1, S);
                case "W" -> graph.addEdge(i, i + 1, W);
                case "T" -> graph.addEdge(i, i + 1, T);
                case "P" -> graph.addEdge(i, i + 1, P);
            }
        }

        for(int i = 4; i < 8; i++){
            switch (cells[i + 1]){
                case "S" -> graph.addEdge(i, i + 1, S);
                case "W" -> graph.addEdge(i, i + 1, W);
                case "T" -> graph.addEdge(i, i + 1, T);
                case "P" -> graph.addEdge(i, i + 1, P);
            }
        }

        for(int i = 8; i < 12; i++){
            switch (cells[i + 1]){
                case "S" -> graph.addEdge(i, i + 1, S);
                case "W" -> graph.addEdge(i, i + 1, W);
                case "T" -> graph.addEdge(i, i + 1, T);
                case "P" -> graph.addEdge(i, i + 1, P);
            }
        }

        for(int i = 12; i < 15; i++){
            switch (cells[i + 1]){
                case "S" -> graph.addEdge(i, i + 1, S);
                case "W" -> graph.addEdge(i, i + 1, W);
                case "T" -> graph.addEdge(i, i + 1, T);
                case "P" -> graph.addEdge(i, i + 1, P);
            }
        }

        for(int i = 0; i < 12; i+= 4){
            switch (cells[i + 4]){
                case "S" -> graph.addEdge(i, i + 4, S);
                case "W" -> graph.addEdge(i, i + 4, W);
                case "T" -> graph.addEdge(i, i + 4, T);
                case "P" -> graph.addEdge(i, i + 4, P);
            }
        }

        for(int i = 1; i < 13; i+= 4){
            switch (cells[i + 4]){
                case "S" -> graph.addEdge(i, i + 4, S);
                case "W" -> graph.addEdge(i, i + 4, W);
                case "T" -> graph.addEdge(i, i + 4, T);
                case "P" -> graph.addEdge(i, i + 4, P);
            }
        }

        for(int i = 2; i < 14; i+= 4){
            switch (cells[i + 4]){
                case "S" -> graph.addEdge(i, i + 4, S);
                case "W" -> graph.addEdge(i, i + 4, W);
                case "T" -> graph.addEdge(i, i + 4, T);
                case "P" -> graph.addEdge(i, i + 4, P);
            }
        }

        for(int i = 3; i < 15; i+= 4){
            switch (cells[i + 4]){
                case "S" -> graph.addEdge(i, i + 4, S);
                case "W" -> graph.addEdge(i, i + 4, W);
                case "T" -> graph.addEdge(i, i + 4, T);
                case "P" -> graph.addEdge(i, i + 4, P);
            }
        }
        //////////////////////////////////////////////

        //Вызов алгоритма Дейкстры
        graph.path();
    }
}
