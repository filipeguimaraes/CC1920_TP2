package BufferThreads;

import DataManagement.HeaderData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BufferToTCP implements Runnable{
    private PriorityBlockingQueue<HeaderData> buff;
    private int indice;
    private DataOutputStream tcp;


    public BufferToTCP(Socket s, PriorityBlockingQueue<HeaderData> data) throws IOException {
        tcp = new DataOutputStream(s.getOutputStream());
        buff = data;
        indice = 0;
    }

    public void sendData () throws IOException, InterruptedException {
        HeaderData hd =  buff.poll(1, TimeUnit.MINUTES);
        if(hd != null && hd.getOffset() == indice){
            byte[] b = hd.getMessage();
            tcp.write(b,0,hd.getLength());
            tcp.flush();
            indice++;

            System.out.println("BufferToTCP: " + hd.getLength() + " " + new String(hd.getMessage()));
        } else{
            if(hd.getOffset()>indice) buff.add(hd);
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
