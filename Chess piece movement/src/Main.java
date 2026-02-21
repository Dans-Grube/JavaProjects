// Dans Grūbe
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        while (true) {
            String s = sc.next();
            if (s.equalsIgnoreCase("done")) break;

            boolean b = Pattern.matches("[KDLZT]?[a-h][1-8]-[a-h][1-8]", s);
            if (b) {
                System.out.println(s);
                System.out.println(check(s));
            } else {
                System.out.println("Error");
            }

        }
        sc.close();

    }

    static public boolean check(String s) {
        int x1, y1, x2, y2;
        x1 = getCoord(s, 1);
        y1 = getCoord(s, 2);
        x2 = getCoord(s, 3);
        y2 = getCoord(s, 4);
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);

        switch (Character.toUpperCase(s.charAt(0))) {
            case 'T':
                return tornis(x1, y1, x2, y2);
            case 'K':
                return karalis(x1, y1, x2, y2);
            case 'Z':
                return zirgs(x1, y1, x2, y2);
            case 'L':
                return laidnis(x1, y1, x2, y2);
            case 'D':
                return dama(x1, y1, x2, y2);
        }
        return false;

    }

    public static int getCoord(String s, int i) {
        int result = 0;
        s = s.replace("-", "");
        String tikaikoordinates;
        if (s.length() == 5) {
            tikaikoordinates = s.substring(1);
        }
        else {
            tikaikoordinates = s;
        }
        char c = tikaikoordinates.charAt(i);
        if (Character.isAlphabetic(c)) {
            result = c - 'a' + 1;
        }
        else {
            result = c - '0';
        }
        return result;
    }

    public static boolean tornis(int x1, int y1, int x2, int y2) {
        if (x1 == x2 || y1 == y2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean karalis(int x1, int y1, int x2, int y2) {
        if (Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1) {
            return true;
        }
        else {
            return false;
        }

    }
    public static boolean zirgs(int x1, int y1, int x2, int y2) {
        int sx = Math.abs(x1 - x2);
        int sy = Math.abs(y1 - y2);

        if ((sx == 2 && sy == 1) || (sx == 1 && sy == 2)) {
            return true;
        }
        else {
            return false;
        }

    }
    public static boolean laidnis(int x1, int y1, int x2, int y2) {
        int sx = Math.abs(x1 - x2);
        int sy = Math.abs(y1 - y2);

        if (sx == sy && sx !=0) {
            return true;
        }
        else {
            return false;
            }
    }
    public static boolean dama(int x1, int y1, int x2, int y2) {
        int sx = Math.abs(x1 - x2);
        int sy = Math.abs(y1 - y2);

        if (x1 == x2 || y1 == y2) {
            return true;
        }
        else if (sx == sy && sx !=0) {
            return true;
        }
        else {
            return false;
        }
    }

}

