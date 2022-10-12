import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Listener 
{
    public static InetAddress address;
    public static int port;
    public static void lisTen() throws IOException
    {
        DatagramSocket socket=new DatagramSocket(5130);
        byte [] data=new byte[1024];
        DatagramPacket packet=new DatagramPacket(data, data.length);
        System.out.println("Server Start Successfully!");
        
        socket.receive(packet);
        address=packet.getAddress();
        port=packet.getPort();
        String info=new String(data,0,packet.getLength());
        System.out.println("Message Get:"+info);

        socket.close();
    }
    public static void resPonse() throws IOException
    {
        try {Thread.sleep(30000);}catch (InterruptedException e){e.printStackTrace();}
        byte[] data="This is response from MRS\n".getBytes();
        DatagramPacket packet=new DatagramPacket(data,data.length,address,port);
        DatagramSocket socket=new DatagramSocket(5130);
        socket.send(packet);
        socket.close();
        System.out.println("Response sent!");
    }
    public static void main(String args[]) throws IOException
    {
        Listener.lisTen();
        Listener.resPonse();
    }
}
//socket：倾听/发送端口,DatagramSocket库
//packet: 数据包
//socket能在没有链接的时候撑20s左右