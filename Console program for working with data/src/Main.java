// 251RDB013 Dans Grūbe 15. grupa
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "db.csv";
        Scanner sc = new Scanner(System.in);
        while (true) {

            String command = sc.nextLine().trim();

            if (command.startsWith("add ")) {
                add(command);
                continue;
            }
            if (command.startsWith("del ")) {
                del(command);
                continue;
            }
            if (command.startsWith("edit ")) {
                edit(command);
                continue;
            }
            if (command.equals("print")) {
                print(command);
                continue;
            }
            if (command.equals("sort")) {
                sort(command);
                continue;
            }
            if (command.startsWith("find ")) {
                find(command);
                continue;
            }
            if (command.equals("avg")) {
                avg(command);
                continue;
            }
            if (command.equals("exit")) {
                break;
            }

            System.out.println("wrong command");
            }

        sc.close();
    }

public static void add(String command) {
    String filePath = "db.csv";
    String[] ievade = command.split(" ", 2);

    if (ievade.length < 2) {
        System.out.println("wrong field count");
        return;
    }

    String data = ievade[1];
    String[] fields = data.split(";");

    if (fields.length != 6) {
        System.out.println("wrong field count");
        return;
    }
    String idStr = fields[0].trim();
    String city = fields[1].trim();
    String date = fields[2].trim();
    String daysStr = fields[3].trim();
    String priceStr = fields[4].trim();
    String vehicle = fields[5].trim();

    int id;
    try {
        id = Integer.parseInt(idStr);
    } catch (NumberFormatException e) {
        System.out.println("wrong id");
        return;
    }

    if (id > 999 || id < 100) {
        System.out.println("wrong id");
        return;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(";");
            if (parts.length > 0) {
                String existingId = parts[0].trim();
                if (existingId.equals(idStr)) {
                    System.out.println("wrong id");
                    return;
                }
            }
        }
    } catch (IOException e) {
        System.out.println("wrong id");
        return;
    }
        if (!city.isEmpty()) {
            city = city.toLowerCase().trim();
            String[] nosauk = city.split("\\s+");
            String newCity = "";

            for (String s : nosauk) {
                if (!s.isEmpty()) {
                    newCity += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
                }
            }

            city = newCity.trim();
        }
    String regexformats = "^\\d{2}/\\d{2}/\\d{4}$";
    if (!date.matches(regexformats)) {
        System.out.println("wrong date");
        return;
    }
    String[] komponenti = date.split("/");
    int diena;
    int menesis;
    int gads;

    try {
        diena = Integer.parseInt(komponenti[0]);
        menesis = Integer.parseInt(komponenti[1]);
        gads = Integer.parseInt(komponenti[2]);

    } catch (NumberFormatException e) {
        System.out.println("wrong date");
        return;
    }
    if (diena < 1 || diena > 31) {
        System.out.println("wrong date");
        return;
    }
    if (menesis < 1 || menesis > 12) {
        System.out.println("wrong date");
        return;
    }
    if (gads < 2020) {
        System.out.println("wrong date");
        return;
    }

    try {
        int days = Integer.parseInt(daysStr);
        if (days <= 0) {
            System.out.println("wrong day count");
            return;
        }

    } catch (NumberFormatException e) {
        System.out.println("wrong day count");
        return;
    }

    double price;
    try {
        price = Double.parseDouble(priceStr);
    } catch (NumberFormatException e) {
        System.out.println("wrong price");
        return;
    }
    if (price <= 0) {
        System.out.println("wrong price");
        return;
    }

        vehicle = vehicle.toUpperCase();

    if (!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
        System.out.println("wrong vehicle");
        return;
    }

    try (FileWriter fw = new FileWriter("db.csv", true)) {
        fw.write(idStr + ";" + city + ";" + date + ";" + daysStr + ";" + priceStr + ";" + vehicle + "\n");

    } catch (IOException e) {
        return;
    }
    System.out.println("added");
}

public static void del(String command) {
    String filePath = "db.csv";
    String[] fields = command.split(" ");

    if (fields.length != 2) {
        System.out.println("wrong id");
        return;
    }
    String idStr = fields[1].trim();

    int id;
    try {
        id = Integer.parseInt(idStr);
    } catch (NumberFormatException e) {
        System.out.println("wrong id");
        return;
    }

    if (id > 999 || id < 100) {
        System.out.println("wrong id");
        return;
    }
    boolean atrasts = false;

    List<String> rindas = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(";");
            if (parts.length < 1) {
                continue;
            }
                String existingId = parts[0].trim();

                if (existingId.equals(idStr)) {
                    atrasts = true;
                    continue;
                }
                rindas.add(line);

        }
    } catch (IOException e){
        System.out.println("wrong id");
        return;
    }
    if (!atrasts) {
        System.out.println("wrong id");
        return;
    }

    try (FileWriter fw = new FileWriter(filePath)) {
        for (String l : rindas) {
            fw.write(l + "\n");
        }
    } catch (IOException e) {
        return;
    }
    System.out.println("deleted");
}
    public static void edit(String command) {
        String filePath = "db.csv";
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("wrong field count");
            return;
        }

        String values = parts[1];
        String[] fields = values.split(";", -1);

        if (fields.length != 6) {
            System.out.println("wrong field count");
            return;
        }

        String idStr = fields[0].trim();
        String city = fields[1].trim();
        String date = fields[2].trim();
        String daysStr = fields[3].trim();
        String priceStr = fields[4].trim();
        String vehicle = fields[5].trim();

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("wrong id");
            return;
        }

        if (id > 999 || id < 100) {
            System.out.println("wrong id");
            return;
        }

        List<String> visasrindas = new ArrayList<>();
        String rinda = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                visasrindas.add(line);
                String[] dati = line.split(";");

                if (dati[0].trim().equals(idStr)) {
                    rinda = line;
                }
            }
        } catch(IOException e) {
            System.out.println("wrong id");
            return;
        }
        if (rinda == null) {
            System.out.println("wrong id");
            return;
        }

        if (!city.isEmpty()) {
            city = city.toLowerCase().trim();
            String[] nosauk = city.split("\\s+");
            String newCity = "";

            for (String s : nosauk) {
                if (!s.isEmpty()) {
                    newCity += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
                }
            }

            city = newCity.trim();
        }
        if (!date.isEmpty()) {
            if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("wrong date");
                return;
            }
            String[] komponenti = date.split("/");
            int diena;
            int menesis;
            int gads;

            try {
                diena = Integer.parseInt(komponenti[0]);
                menesis = Integer.parseInt(komponenti[1]);
                gads = Integer.parseInt(komponenti[2]);

            } catch (NumberFormatException e) {
                System.out.println("wrong date");
                return;
            }
            if (diena < 1 || diena > 31) {
                System.out.println("wrong date");
                return;
            }
            if (menesis < 1 || menesis > 12) {
                System.out.println("wrong date");
                return;
            }
            if (gads < 2020) {
                System.out.println("wrong date");
                return;
            }
        }
        if (!daysStr.isEmpty()) {
            try {
                int days = Integer.parseInt(daysStr);
                if (days <= 0) {
                    System.out.println("wrong day count");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("wrong day count");
                return;
            }
        }

        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    System.out.println("wrong price");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("wrong price");
                return;
            }
        }
        if (!vehicle.isEmpty()) {
            if (!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
                System.out.println("wrong vehicle");
                return;
            }
        }

        String[] veciedati = rinda.split(";");
        if (!city.isEmpty()) veciedati[1] = city;
        if (!date.isEmpty()) veciedati[2] = date;
        if (!daysStr.isEmpty()) veciedati[3] = daysStr;
        if (!priceStr.isEmpty()) veciedati[4] = priceStr;
        if (!vehicle.isEmpty()) veciedati[5] = vehicle;

        String jauniedati = (veciedati[0] + ";" + veciedati[1] + ";"+ veciedati[2] + ";"+ veciedati[3] + ";"+ veciedati[4] + ";"+ veciedati[5]);

        try (FileWriter fw = new FileWriter(filePath)) {
            for (String l : visasrindas) {
                if (l.equals(rinda)) {
                    fw.write(jauniedati + "\n");
                } else {
                    fw.write(l + "\n");
                }
            }
        } catch (IOException e) {
            return;
        }
        System.out.println("changed");
    }
    public static void print(String command) {
        String filePath = "db.csv";
        String format = "%-4s%-21s%-11s%6s%10s %-8s%n";

        System.out.println("-".repeat(60));

        System.out.printf(format, "ID", "City", "Date", "Days", "Price", "Vehicle");
        System.out.println("-".repeat(60));

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");

                System.out.printf(format, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            }
        } catch (IOException e) {
            return;
        }
        System.out.println("-".repeat(60));
    }
    public static void sort(String command) {
        String filePath = "db.csv";
        List<String> rindas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rindas.add(line);
                }

            }

        } catch (IOException e) {
            return;
        }

        rindas.sort((a, b) -> {
            String[] pa = a.split(";");
            String[] pb = b.split(";");

            String[] da = pa[2].split("/");
            String[] db = pb[2].split("/");

            int dienaA = Integer.parseInt(da[0]);
            int menesisA = Integer.parseInt(da[1]);
            int gadsA = Integer.parseInt(da[2]);

            int dienaB = Integer.parseInt(db[0]);
            int menesisB = Integer.parseInt(db[1]);
            int gadsB = Integer.parseInt(db[2]);

            if (gadsA != gadsB) return gadsA - gadsB;
            if (menesisA != menesisB) return menesisA - menesisB;
            return dienaA - dienaB;
        });

        try (FileWriter fw = new FileWriter("db.csv")) {
            for (String l : rindas) {
                fw.write(l + "\n");
            }

        } catch (IOException e) {
            return;
        }
        System.out.println("sorted");
    }
    public static void find(String command) {
        String filePath = "db.csv";
        String format = "%-4s%-21s%-11s%6s%10s %-8s%n";
        String[] parts = command.split(" ");
        if(parts.length != 2) {
            System.out.println("wrong price");
            return;
        }
        String priceStr = parts[1];
        double maxcena;
        try {
            maxcena = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            System.out.println("wrong price");
            return;
        }
        System.out.println("-".repeat(60));
        System.out.printf(format, "ID", "City", "Date", "Days", "Price", "Vehicle");
        System.out.println("-".repeat(60));

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(";");
                double cena = Double.parseDouble(fields[4]);

                if (cena <= maxcena) {
                    System.out.printf(format, fields[0], fields[1],fields[2], fields[3], fields[4], fields[5]);
                }
            }
        } catch (IOException e) {
            return;
        }
        System.out.println("-".repeat(60));
    }
    public static void avg(String command) {
    String filePath = "db.csv";
        double kopejasumma = 0.0;
        int skaits = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");

                double cena = Double.parseDouble(parts[4]);

                kopejasumma += cena;
                skaits++;
            }
        } catch (IOException e) {
            return;
        }

        if (skaits ==0) return;
        double vid =kopejasumma / skaits;

        System.out.printf("average=%.2f%n", vid);
    }

}