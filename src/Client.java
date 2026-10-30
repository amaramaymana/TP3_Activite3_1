import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String hote = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;

        try {
            InetAddress adresse = InetAddress.getByName(hote);
            InetSocketAddress adresseServeur = new InetSocketAddress(adresse, port);

            Socket socket = new Socket();
            socket.connect(adresseServeur, 5000);
            System.out.println("Connecte au serveur " + adresseServeur);

            try (BufferedReader reseau = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println(reseau.readLine());

                String saisie;
                System.out.print("> ");
                while ((saisie = clavier.readLine()) != null) {
                    pw.println(saisie);
                    String reponse = reseau.readLine();
                    if (reponse == null) break;
                    System.out.println(reponse);
                    if (saisie.trim().equalsIgnoreCase("quit")) break;
                    System.out.print("> ");
                }
            } finally {
                socket.close();
            }
            System.out.println("Deconnecte.");
        } catch (IOException e) {
            System.err.println("Impossible de communiquer avec le serveur " + hote + ":" + port
                    + " (" + e.getMessage() + ")");
        }
    }
}
