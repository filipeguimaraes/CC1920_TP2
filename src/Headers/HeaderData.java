package Headers;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HeaderData implements Comparable {

    private static final byte[] TOKEN = "%".getBytes();

    private byte[] address;
    private String uid;
    private String type;
    private int length;
    private int offset;
    private byte[] message;


    public HeaderData (String t, String id, byte[] add, int off_set, int l, byte[] m) {
        type = t;
        uid = id;
        address = add;
        offset = off_set;
        length = l;
        message = m;
    }

    public byte[] getMessage () {
        return message.clone();
    }

    public int getOffset() {
        return offset;
    }

    public HeaderData (byte[] data) {
        List<byte[]> lb = HeaderData.split(TOKEN,data);
        address = lb.get(0).clone();
        uid = new String(lb.get(1));
        type = new String(lb.get(2));
        length = Integer.parseInt(new String(lb.get(3)));
        offset = Integer.parseInt(new String(lb.get(4)));

        for (int i = 0; i < 5; i++)
            lb.remove(i);


        int k = 0;

        for (byte[] b : lb)
            k += b.length;

        message = new byte[k];
        int i = 0;

        for (byte[] br : lb)
            for (byte b : br)
                message[i++] = b;
    }

    public byte[] toArrayByte () {
        List<byte[]> l = new LinkedList<>();
        l.add(address);
        l.add(TOKEN);
        l.add(uid.getBytes());
        l.add(TOKEN);
        l.add(type.getBytes());
        l.add(TOKEN);
        l.add(String.valueOf(length).getBytes());
        l.add(TOKEN);
        l.add(String.valueOf(offset).getBytes());
        l.add(TOKEN);
        l.add(message);

        int k = 0;
        for (byte[] b : l) k += b.length;
        byte[] r = new byte[k];
        int i = 0;

        for (byte[] br : l)
            for (byte b : br)
                r[i++] = b;

        return r;
    }

    public static byte[] insertHeader (String t, String u, byte[] a, String o, byte[] m) {
        List<byte[]> l = new LinkedList<>();
        l.add(a);
        l.add(TOKEN);
        l.add(u.getBytes());
        l.add(TOKEN);
        l.add(t.getBytes());
        l.add(TOKEN);
        l.add(o.getBytes());
        l.add(TOKEN);
        l.add(o.getBytes());
        l.add(TOKEN);
        l.add(m);

        int k = 0;
        for (byte[] b : l) k += b.length;
        byte[] r = new byte[k];
        int i = 0;

        for (byte[] br : l)
            for (byte b : br)
                r[i++] = b;

        return r;
    }

    public static boolean isMatch (byte[] pattern, byte[] input, int pos) {
        for(int i=0; i< pattern.length; i++) {
            if(pattern[i] != input[pos+i]) {
                return false;
            }
        }
        return true;
    }

    public static List<byte[]> split (byte[] pattern, byte[] input) {
        List<byte[]> l = new LinkedList<>();
        int blockStart = 0;
        for(int i=0; i<input.length; i++) {
            if(isMatch(pattern,input,i)) {
                l.add(Arrays.copyOfRange(input, blockStart, i));
                blockStart = i+pattern.length;
                i = blockStart;
            }
        }
        l.add(Arrays.copyOfRange(input, blockStart, input.length ));
        return l;
    }


    @Override
    public int compareTo (Object o) {
        HeaderData hd = (HeaderData) o;
        return Integer.compare(this.offset, hd.offset);
    }
}