import java.io.*;
import java.net.InetAddress;
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

                //Pedido
                ServerSocket anon = new ServerSocket(local_TCP_port);
                System.out.println("Anon iniciado na porta " + local_TCP_port);


                Socket client_socket = anon.accept();
                System.out.println("Cliente conectado do IP " +
                        client_socket.getInetAddress().getHostAddress());

                String clienteIP = client_socket.getInetAddress().getHostAddress();

                Socket server_socket = new Socket(target_server, local_TCP_port);
                System.out.println("Servidor" + target_server +
                            " conectado na porta " + local_TCP_port);

                OutputStream output = server_socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                Scanner entrada = new Scanner(client_socket.getInputStream());
                while (entrada.hasNextLine()) {
                    writer.println(entrada.nextLine());
                }

                InputStream input = server_socket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }




                //Resposta


                Socket server_socket_response = anon.accept();
                System.out.println("Servidor conectado"+
                        client_socket.getInetAddress().getHostAddress());

                Socket client_socket_response = new Socket(clienteIP, local_TCP_port);
                System.out.println("cliente a escutar");

                OutputStream output_resp = client_socket_response.getOutputStream();
                PrintWriter writer_resp = new PrintWriter(output_resp, true);

                Scanner entrada_resp = new Scanner(server_socket_response.getInputStream());
                while (entrada_resp.hasNextLine()) {
                    writer_resp.println(entrada_resp.nextLine());
                }

                InputStream input_resp = client_socket_response.getInputStream();

                BufferedReader reader_resp = new BufferedReader(new InputStreamReader(input_resp));

                String line_resp;

                while ((line_resp = reader_resp.readLine()) != null) {
                    System.out.println(line_resp);
                }


                //Fechar conexoes
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