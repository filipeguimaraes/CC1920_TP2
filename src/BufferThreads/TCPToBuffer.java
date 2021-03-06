package BufferThreads;

import DataManagement.HeaderData;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;

public class TCPToBuffer implements Runnable{
    private PriorityBlockingQueue<HeaderData> buff;
    private int indice;
    private DataInputStream tcp;
    private byte[] addr;
    private String type;
    private String uid;


    public TCPToBuffer (Socket s, PriorityBlockingQueue<HeaderData> data, String t, String id, byte[] a) throws IOException {
        tcp = new DataInputStream(s.getInputStream());
        addr = a;
        buff = data;
        indice = 0;
        type = t;
        uid = id;
    }

    public void storeData (byte[] buff_byte, int size) {
        HeaderData hd = new HeaderData(type,uid,addr,indice,size,buff_byte.clone());
        indice++;
        buff.put(hd);


        System.out.println("TCPToBuffer: " + hd.getUid() + hd.getType());
        System.out.println("TCPToBuffer: " + new String(buff_byte));
    }

    @Override
    public void run() {
        int k = 1;
        int tam = 0;
        int off_set = 0;
        byte[] buff_byte = new byte[k];

        try {
            while((tam = tcp.read(buff_byte,off_set,k)) > 0)
                storeData(buff_byte,tam);
        }
        catch (IOException ignored) {}

    }
}
