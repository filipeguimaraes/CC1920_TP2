import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConection extends Thread {

    ServerSocket anon;
    Socket client;
    InputStream clientInputStream;
    OutputStream clientOutputStrem;

    public ClientConection(ServerSocket anon) {
        this.anon = anon;
    }

    public Socket getClient() {
        return client;
    }

    public InputStream getClientInputStream() {
        return clientInputStream;
    }

    public OutputStream getClientOutputStrem() {
        return clientOutputStrem;
    }

    public void run() {
        try {
            this.client = anon.accept();
            System.out.println("Cliente conectado do IP " + client.getInetAddress().getHostAddress());
            this.clientInputStream = client.getInputStream();
            this.clientOutputStrem = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
