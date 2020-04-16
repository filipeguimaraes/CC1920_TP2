import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConection extends Thread{

        ServerSocket anon;
        Socket server;
        InputStream serverInputStream;
        OutputStream serverOutputStrem;

        public ServerConection(ServerSocket anon) {
            this.anon = anon;
        }

        public Socket getserver() {
            return server;
        }

        public InputStream getserverInputStream() {
            return serverInputStream;
        }

        public OutputStream getserverOutputStrem() {
            return serverOutputStrem;
        }

        public void run() {
            try {
                this.server = anon.accept();
                System.out.println("servere conectado do IP " + server.getInetAddress().getHostAddress());
                this.serverInputStream = server.getInputStream();
                this.serverOutputStrem = server.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
