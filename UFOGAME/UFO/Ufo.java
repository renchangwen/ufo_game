import java.util.Random;

public class Ufo {
     private int x,y;
     private double vf;
     private int v;
     private int flagx,flagy;
     
     public Ufo(){
        set();
        flagx=1;
        flagy=1;
        vf=speedFactor();
        v=5;

     }
     public Ufo(int xt,int yt){
        x=xt;
        y=yt;
        flagx=0;
        flagy=0;
        vf=1;
        v=4;
     }
     public int retX(){
        return x;
     }
     public int retY(){
        return y;
     }
     public void set(){
        Random ran=new Random();
        if(ran.nextInt(600)<300){
            x=100;
        }
        else{
            x=400;
        }
        y=ran.nextInt(300);
    }
    public double speedFactor(){
        double temp=0;
        while(temp<=0.3) temp=Math.random();
        return temp;

    }

    public void move(){
        x=x+(int)(v*flagx*vf);
        if(x>503||x<18){
            flagx=-flagx;
        }
        y=y+(int)(v*flagy*vf);
        if(y>400||y<=0){
            flagy=-flagy;
        }
    }
    public Boom release(){
        Boom oneBoom=new Boom(retX(), retY());
        return oneBoom;
    }
}
