package DataManagement;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HeaderData implements Comparable, KeyUniqueId {

    private static final byte[] TOKEN = ".%.".getBytes();

    private byte[] address;
    private String uid;
    private String type;
    private int length;
    private int offset;
    private byte[] message;


    public HeaderData (String t, String id, byte[] add, int off_set, int l, byte[] m) {
        type = t;
        uid = id;
        address = add.clone();
        offset = off_set;
        length = l;
        message = m.clone();
    }

    public byte[] getMessage () {
        return message.clone();
    }

    public int getOffset() {
        return offset;
    }

    public byte[] getAddress() {
        return address.clone();
    }

    public String getUid() {
        return uid;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public void setAddress(byte[] address) {
        this.address = address.clone();
    }

    public HeaderData (byte[] data) {
        List<byte[]> lb = HeaderData.split(TOKEN,data);
        address = lb.get(0).clone();
        uid = new String(lb.get(1));
        type = new String(lb.get(2));
        length = Integer.parseInt(new String(lb.get(3)));
        offset = Integer.parseInt(new String(lb.get(4)));
        for (int i = 0; i < 5 ; i++)
            lb.remove(0);


        int k = 0;

        for (byte[] b : lb)
            k += b.length;

        message = new byte[k];
        int i = 0;

        for (byte[] br : lb)
            for (byte b : br)
                message[i++] = b;

    }

    public static byte[] encodeArrayByte (byte [] array_of_bytes) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);

        byte[] encVal = c.doFinal(array_of_bytes);
        return encVal;
    }

    public static byte[] decodeArrayByte (byte [] array_of_bytes) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);

        byte[] decValue = c.doFinal(array_of_bytes);
        return decValue;
    }

    private static Key generateKey() {
        byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
                'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
        Key key = new SecretKeySpec(keyValue, "AES");
        return key;
    }


    public byte[] toArrayByte () {
        List<byte[]> l = new ArrayList<>();
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
        List<byte[]> l = new ArrayList<>();
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

    @Override
    public String getKeyUid() {
        return uid;
    }
}
