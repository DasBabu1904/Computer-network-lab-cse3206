import java.util.Random;
import java.util.Scanner;
class Sender{
    static Receiver reveiver;
    int windowSize;
    int framenumber;
    boolean t;
    boolean ak[];
    int c=0;
    Sender(int ws, int fn){
        windowSize=ws;
        framenumber=fn;
        ak=new boolean[ws+1];
        t=true;
    }
    static void setReceiver(Receiver r){
        reveiver=r;
    }
    void send(){
        for(int i=1;i<=framenumber;i++){
            reveiver.receive(i);
            if((!t) && i%windowSize==0){
                for(int j=i-windowSize+1;j<=i && j<=framenumber ;j++){
//                    System.out.print("error starts at "+i+ "  errors:"+j+"  \n");
                    if(ak[j%windowSize]==false){
                        reveiver.receive(j);
                    }
                }
                for(int j=0;j<=windowSize;j++)
                    ak[j%windowSize]=true;
                t=true;
            }
        }
    }
    void akReceive(boolean a,int indx){
//        System.out.println("er at :"+indx );
//        System.out.print(a);
        ak[indx]=a;
        t=t & a;
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
        if(c%windowSize==0 ){
            for(int j=1;j<=windowSize;j++) sender.akReceive(aksend(),(j));
        }
    }
    boolean aksend(){
        return random.nextDouble() < 0.7;
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