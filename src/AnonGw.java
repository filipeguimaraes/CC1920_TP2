import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AnonGw {

    private static int local_TCP_port = 80;
    private static int UDP_port = 6666;
    private static List<String> overlay_peers;

    public static void main(String[] args) {
        int argc = args.length;
        if (argc >= 4) {
            String target_server = args[1];
            int port = Integer.parseInt(args[3]);
            overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));
            //System.out.println(overlay_peers.toString());
        }

        try {
            ServerSocket server = new ServerSocket(local_TCP_port);
            System.out.println("Servidor iniciado na porta "+local_TCP_port);

            Socket cliente = server.accept();
            System.out.println("Cliente conectado do IP " + cliente.getInetAddress().
                    getHostAddress());
            Scanner entrada = new Scanner(cliente.getInputStream());
            while (entrada.hasNextLine()) {
                System.out.println(entrada.nextLine());
            }

            entrada.close();
            server.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


}