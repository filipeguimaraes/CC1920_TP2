import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AnonGw {

    private static int local_TCP_port = 80;
    private static int UDP_port = 6666;

    public static void main(String[] args) {
        int argc = args.length;
        if (argc >= 4) {
            String target_server = args[1];
            int port = Integer.parseInt(args[3]);
            //List<String> overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));
            //System.out.println(overlay_peers.toString());

            try {
                ServerSocket welcomeSocket = new ServerSocket(local_TCP_port);
                welcomeSocket.accept();

                Socket socketSaida = new Socket(target_server, 80);
            } catch (IOException e) {
                System.out.println("Connection TCP error: " + e.getMessage());
            }

*/
        } else System.out.println("Not enough arguments.");


    }
}
