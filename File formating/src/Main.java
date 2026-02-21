// 251RDB013 Dans Grūbe 15

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("input file name:");
            String fileName = sc.nextLine().trim();

            String command = sc.nextLine().trim();

            if (command.equals("print")) {
                print(fileName);
                continue;
            }
            if (command.equals("format")) {
                format(fileName);
                continue;
            }
            if (command.equals("exit")) {
                break;
            }

            try {

                Scanner fileScanner = new Scanner(new FileReader(fileName));
                PrintWriter out = new PrintWriter(new FileWriter("temp.txt"));

                while (fileScanner.hasNextLine()) {
                    String s = fileScanner.nextLine().toUpperCase();
                    out.println(s);
                }
                fileScanner.close();
                out.close();

                if (!new File(fileName).delete()) {}
                if (!new File("temp.txt").renameTo(new File(fileName))) {}


            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

        sc.close();

    }

    public static void format(String filename) {

        try {
            Scanner fileScanner = new Scanner(new FileReader(filename));
            List<String> lines = new ArrayList<>();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                line = Character.toUpperCase(line.charAt(0)) + line.substring(1);

                lines.add(line);
            }

            fileScanner.close();

            int maxLen = 0;
            for (String line : lines) {
                if (line.length() > maxLen) maxLen = line.length();
            }

            PrintWriter out2 = new PrintWriter(new FileWriter("temp2.txt"));

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                int diff = maxLen - line.length();
                int left = diff / 2;
                int right = diff - left;

                String centered = " ".repeat(left) + line + " ".repeat(right);

                out2.println(centered);
                System.out.println(centered);

                if (i % 2 == 1) {
                    out2.println();
                    System.out.println();
                }
            }
            out2.close();

            if (!new File(filename).delete());
            if (!new File("temp2.txt").renameTo(new File(filename)));

        } catch (Exception e) {
            System.out.println("Error formatting file: " + filename);
        }
    }

    public static void print(String filename) {
        try {
            Scanner fileScanner = new Scanner(new FileReader(filename));
            List<String> lines = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
            fileScanner.close();

            lines.removeIf(String::isEmpty);

            for (int i = 0; i < lines.size(); i +=2) {
                System.out.println(lines.get(i));

                if (i + 1 < lines.size()) {
                    System.out.println(lines.get(i + 1));

                }

                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println("Couldn 't find a file: " + filename);
        }

    }


}
