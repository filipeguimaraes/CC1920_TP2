package BufferThreads;

import Headers.HeaderData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Time;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BufferToTCP implements Runnable{
    private PriorityBlockingQueue<HeaderData> buff;
    private int indice;
    private DataOutputStream tcp;
    private byte[] addr;
    private String type;
    private String uid;


    //IMPORTA VERIFICAR A ORDEM
    public BufferToTCP (Socket s, PriorityBlockingQueue<HeaderData> data, String t, String id) throws IOException {
        tcp = new DataOutputStream(s.getOutputStream());
        addr = s.getInetAddress().getAddress();
        buff = data;
        indice = 0;
        type = t;
        uid = id;
    }

    public void sendData () throws IOException, InterruptedException {
        HeaderData hd =  buff.poll(1, TimeUnit.MINUTES);
        if(hd != null && hd.getOffset() == indice){
            byte[] b = hd.getMessage();
            tcp.write(b,0,b.length);
            indice++;
        }
        else {
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            while(true)
                sendData();
        }
        catch (InterruptedException | IOException ignored) { }
    }
}
