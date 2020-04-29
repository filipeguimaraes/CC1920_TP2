import java.io.*;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonGw {


    public static void main(String[] args) {
        int argc = args.length;
        int local_TCP_port = 80;
        int UDP_port = 6666;
        List<String> overlay_peers;
        String target_server = "10.3.3.1";

        if (argc >= 4) {
            target_server = args[1];
            local_TCP_port = Integer.parseInt(args[3]);
            overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));
        } else System.out.println("A usar valores por defeito");

        try {
            ServerSocket anon = new ServerSocket(local_TCP_port);


//            ArrayList<String> pergunta = new ArrayList<>();
//            ArrayList<String> resposta = new ArrayList<>();

            Request request = new Request(1, anon, target_server, local_TCP_port);
/*
            PrintWriter writerClient = new PrintWriter(request.getClientOutputStrem(), true);
            BufferedReader readerCliente = new BufferedReader(
                    new InputStreamReader(request.clientInputStream));


            String pergunta_line = " ";
             while (!(pergunta_line.equals(""))) {
                pergunta_line = readerCliente.readLine();
                pergunta.add(pergunta_line);
                System.out.println(pergunta_line);
            }
            System.out.println(pergunta);



            PrintWriter writerServer = new PrintWriter(request.getServerOutputStrem(), true);
            BufferedReader readerServidor = new BufferedReader(
                    new InputStreamReader(request.getServerInputStream()));

            //Enviar a pergunta ao servidor
            pergunta.forEach(writerServer::println);
*/

            Transfer transferRequest = new Transfer(
                    request.getClientInputStream(),
                    request.getServerOutputStrem());

            transferRequest.receiveResponse(request.getServerInputStream());
            transferRequest.sendResponse();

            //Resposta do servidor
            Transfer transferResponse = new Transfer(
                                request.getServerInputStream(),
                                request.getClientOutputStrem());

            transferResponse.receiveResponse(request.getClientInputStream());
            transferResponse.sendResponse();

            request.close();
            anon.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}