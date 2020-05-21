import BufferThreads.UDPToBuffer;
import DataManagement.RoutingData;
import Requests.RequestClient;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AnonGw {

    public static int UDP_PORT = 6666;

    public static void main(String[] args) {
        int argc = args.length;
        int local_TCP_port = 80;
        List<String> overlay_peers = new ArrayList<>();
        String target_server = "10.3.3.1";

        if (argc >= 4) {
            target_server = args[1];
            local_TCP_port = Integer.parseInt(args[3]);
            overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));
        } else System.out.println("A usar valores por defeito");

        try {
            DatagramSocket ds = new DatagramSocket(UDP_PORT);

            RoutingData routerData = new RoutingData(target_server,local_TCP_port,UDP_PORT,ds);

            Thread threadUDP = new Thread(new UDPToBuffer(routerData,ds));
            threadUDP.start();

            ServerSocket anon = new ServerSocket(local_TCP_port);

            while (true) {
                Socket client = anon.accept();
                RequestClient r;
                r = new RequestClient(client, ds,
                        InetAddress.getByName(overlay_peers.get(new Random().nextInt(overlay_peers.size()))).getAddress().clone());

                routerData.addRequestClient(r);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}