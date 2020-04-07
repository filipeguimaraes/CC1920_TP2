import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AnonGw {


    public static void main(String[] args) {
        int argc = args.length;
        int local_TCP_port;
        int UDP_port = 6666;
        List<String> overlay_peers;

        if (argc >= 4) {
            String target_server = args[1];
            local_TCP_port = Integer.parseInt(args[3]);
            overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));

            try {


                ServerSocket anon = new ServerSocket(local_TCP_port);
                System.out.println("Anon iniciado na porta " + local_TCP_port);


                Socket client_socket = anon.accept();
                System.out.println("Cliente conectado do IP " +
                        client_socket.getInetAddress().getHostAddress());

                Socket server_socket = new Socket(target_server, local_TCP_port);
                System.out.println("Servidor" + target_server +
                            " conectado na porta " + local_TCP_port);

                OutputStream output = server_socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                Scanner entrada = new Scanner(client_socket.getInputStream());
                while (entrada.hasNextLine()) {
                    writer.println(entrada.nextLine());
                }
        /*
                InputStream input = server_socket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

         */


                OutputStream client_output = client_socket.getOutputStream();
                PrintWriter writer_cli = new PrintWriter(client_output, true);

                Scanner entrada_server = new Scanner(server_socket.getInputStream());
                while (entrada_server.hasNextLine()) {
                    writer_cli.println(entrada_server.nextLine());
                }

                client_socket.close();
                entrada.close();
                server_socket.close();
                anon.close();

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else System.out.println("Not enough arguments!");
    }
}