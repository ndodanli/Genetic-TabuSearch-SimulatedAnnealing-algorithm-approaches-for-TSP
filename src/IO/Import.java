package IO;

import ObjectModels.City;

import java.io.*;
/**
 *
 * @author emcs
 */
public class Import {

    public static City[] getCities() {

        String dataSetName;
        int startingLine;
        dataSetName = "cities10.tsp";
        startingLine = 3;

        String[] lines = read(dataSetName).split("\n");
        String[] words = lines[1].split(" ");
        int numOfCities = Integer.parseInt(words[words.length - 1]);
        City[] cities = new City[numOfCities];

        for (int i = startingLine; i < startingLine + numOfCities; i++) {
            String[] line = removeWhiteSpace(lines[i]).trim().split(" ");
            int x = (int) Double.parseDouble(line[1].trim());
            int y = (int) Double.parseDouble(line[2].trim());
            City city = new City(line[0], x, y);
            cities[i - startingLine] = city;
        }

        return cities;
    }

    private static String removeWhiteSpace(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ' ' && s.charAt(i - 1) == ' ') {
                if (i != s.length()) {
                    s = s.substring(0, i) + s.substring(i + 1, s.length());
                    i--;
                } else {
                    s = s.substring(0, i);
                    i--;
                }
            }
        }
        return s;
    }

    private static String read(String fileName) {
        InputStream stream = Import.class.getResourceAsStream(fileName);
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
