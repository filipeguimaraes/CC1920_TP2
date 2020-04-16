import java.io.*;
import java.net.ServerSocket;
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
            while (true) {
                try {

                    ServerSocket anon = new ServerSocket(local_TCP_port);
                    System.out.println("Anon iniciado na porta " + local_TCP_port);

                    Thread server = new ServerConection(anon);
                    server.start();
                    Thread client = new ClientConection(anon);
                    client.start();

                    System.out.println("Teste");
                /*
                Request request = new Request(1, anon, target_server, local_TCP_port);

                OutputStream output = request.getServerOutputStrem();
                PrintWriter writer = new PrintWriter(output, true);

                Scanner entrada = new Scanner(request.getClientInputStream());
                while (entrada.hasNextLine()) {
                    System.out.println("1");
                    writer.println(entrada.nextLine());
                    System.out.println("2");
                }
                request.getClient().close();

                InputStream input = request.getServerInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }



                entrada.close();
                request.close();
                */
                    anon.close();

                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } else System.out.println("Not enough arguments!");
    }
}