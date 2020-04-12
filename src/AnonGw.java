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
                /*
                Socket server_socket = new Socket(target_server, local_TCP_port);
                System.out.println("Servidor" + target_server +
                        " conectado na porta " + local_TCP_port);

                Socket client_socket = anon.accept();
                System.out.println("Cliente conectado do IP " +
                        client_socket.getInetAddress().getHostAddress());
*/
                Request request = new Request(1,anon,target_server,local_TCP_port);

                OutputStream output = request.getServerOutputStrem();
                PrintWriter writer = new PrintWriter(output, true);

                Scanner entrada = new Scanner(request.getClientInputStream());
                anon.close();
                while (entrada.hasNextLine()) {
                    System.out.println("1");
                    writer.println(entrada.nextLine());
                    System.out.println("2");
                }

                InputStream input = request.getServerInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

/*
                OutputStream output_client = client_socket.getOutputStream();
                PrintWriter writer_client = new PrintWriter(output_client, true);
*/


                entrada.close();
                request.close();
                anon.close();

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else System.out.println("Not enough arguments!");
    }
}