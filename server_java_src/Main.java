import java.util.Scanner;
public class Main {
    public static void main(String args[])
    {
        tcpListener spy1=new tcpListener();
        Thread t1=new Thread(spy1);
        t1.start();
        System.out.println("Press Any Key To Stop:");
        Scanner in=new Scanner(System.in);
        in.nextLine();
        in.close();
        tcpListener.modify();
        return;
    }
}
