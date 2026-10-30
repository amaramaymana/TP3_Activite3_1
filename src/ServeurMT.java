import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class ServeurMT extends Thread {

    public static final int PORT = 1234;


    private final AtomicInteger numeroClient = new AtomicInteger(0);

    @Override
    public void run() {

        try (ServerSocket socketServeur = new ServerSocket(PORT)) {
            System.out.println("Le serveur attend des clients sur le port " + PORT + "...");

            while (true) {

                Socket socket = socketServeur.accept();

                int numero = numeroClient.incrementAndGet();

                System.out.println("Client n°" + numero + " connecté depuis "
                        + socket.getRemoteSocketAddress());

                new ClientProcess(socket, numero).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ServeurMT().start();
    }
}
