import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("破防了");
        Scanner rnm=new Scanner(System.in);
        String Fuck=rnm.nextLine();
        Fuck=new String(Fuck.getBytes(),"GBK");
        System.out.println("system:"+Fuck);
        rnm.close();
    }
}
