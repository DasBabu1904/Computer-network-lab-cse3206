import java.util.Random;
import java.util.Scanner;
class Sender{
    static Receiver reveiver;
    int windowSize;
    int framenumber;
    boolean t;
    Sender(int ws, int fn){
        windowSize=ws;
        framenumber=fn;
    }
    static void setReceiver(Receiver r){
        reveiver=r;
    }
    void send(){
        for(int i=1;i<=framenumber;i++){
            reveiver.receive(i);
            if((!t) && i%windowSize==0){
                i=i-windowSize+1;
                t=true;
            }
        }
    }
    void akReceive(boolean a){
        t=t&a;
    }
}

class Receiver{
    Sender sender;
    int windowSize;
    int framenumber;
    int c=1;
    Random random = new Random();
    Receiver(Sender s,int ws,int fn){
        sender=s;
        windowSize=ws;
        framenumber=fn;
    }

    void receive(int i){
        System.out.print(i+" ");
        if(i<c)c=i;
        else c++;
        if(c%framenumber==0){
            for(int j=0;j<framenumber;j++) sender.akReceive(aksend());
        }
    }
    boolean aksend(){
        return random.nextDouble() < 0.01;
    }
}

public class Main {
    static void initializeEnvironmetn(int f, int ws){
        Sender s=new Sender(ws,f);
        Receiver r= new Receiver(s,ws,f);
        Sender.setReceiver(r);
        s.send();

    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int f,ws;
        System.out.println("Give me the Frame number and window size: ");
        f=scan.nextInt();
        ws=scan.nextInt();
        initializeEnvironmetn(f,ws);
    }
}