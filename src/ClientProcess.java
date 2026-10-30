import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {

    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {

            pw.println("Bienvenue ! Vous etes le client numero " + numero
                    + " (tapez 'quit' pour quitter)");

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().equalsIgnoreCase("quit")) {
                    pw.println("Au revoir client numero " + numero);
                    break;
                }
                System.out.println("[Client n°" + numero + "] " + ligne);
                pw.println("Serveur> message recu (" + ligne.length() + " caracteres) : " + ligne);
            }
        } catch (IOException e) {
            System.err.println("Client n°" + numero + " : erreur de communication (" + e.getMessage() + ")");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client n°" + numero + " deconnecte.");
        }
    }
}
