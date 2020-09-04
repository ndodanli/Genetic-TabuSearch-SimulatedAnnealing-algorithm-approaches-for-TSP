package ObjectModels;

import java.util.Random;
/**
 *
 * @author emcs
 */
public class City {

    private String name;
    private int x, y;

    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static City getRandomCity(Random random) {
        String name = getRandomName(random);
        int x = random.nextInt(500);
        int y = random.nextInt(500);
        return new City(name, x, y);
    }

    private static String getRandomName(Random random) {

        int[] name = new int[random.nextInt(5) + 3];
        for (int i = 0; i < name.length; i++) {
            name[i] = random.nextInt(26) + 65;
        }

        StringBuilder sb = new StringBuilder();
        for (int i : name) {
            sb.append((char) i);
        }

        return new String(sb);
    }

    public static double distance(City city1, City city2) {

        int x1 = city1.getX();
        int y1 = city1.getY();

        int x2 = city2.getX();
        int y2 = city2.getY();

        int xDiff = x2 - x1;
        int yDiff = y2 - y1;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        City city = (City) o;

        if (x != city.x) {
            return false;
        }
        if (y != city.y) {
            return false;
        }
        return name.equals(city.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    public String getCityName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
}
