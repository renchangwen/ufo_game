import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable,MouseListener {
    private Image theUfo1=null;
    private Image theUfo2=null;
    private Image theUfo3=null;
    private Image theCity=null;
    private Image theCityBurn=null;
    private Image theboom=null;
    private Image theBackGround=null;
    private Thread th;
    private city[] citys;
    private Ufo[] ufoZ;
    private boolean[] setUfo;
    private boolean[] cityBurnX;
    private int[] hitUfos;
    private ArrayList<Boom> boomAry;
    private int cnt=0; 
    private Random ran;
    private int cityLeft;
    private int score;
    public GamePanel(){
        addMouseListener(this);
        init();
        th=new Thread(this);
        th.start();
    }
    public void init(){
        theUfo1=Toolkit.getDefaultToolkit().getImage("..//picture//Ufo1.gif");
        theUfo2=Toolkit.getDefaultToolkit().getImage("..//picture//Ufo2.gif");
        theUfo3=Toolkit.getDefaultToolkit().getImage("..//picture//Ufo3.gif");
        theCity=Toolkit.getDefaultToolkit().getImage("..//picture//city.gif");
        theCityBurn =Toolkit.getDefaultToolkit().getImage("..//picture//CityBurn.gif");
        theboom=Toolkit.getDefaultToolkit().getImage("..//picture//boom.png");
        theBackGround=Toolkit.getDefaultToolkit().getImage("..//picture//backgroud.jpeg");

        citys=new city[4];
        cityBurnX=new boolean[4];   
        ufoZ=new Ufo[10];
        hitUfos=new int[10];         
        setUfo=new boolean[10];
        boomAry=new ArrayList<>();
        ran=new Random();
        Random random=new Random();;
        int c1=random.nextInt(0,150);
        int c2=random.nextInt(160,270);
        int c3=random.nextInt(300,420);
        int c4=random.nextInt(450,480);
        for(int i=0;i<ufoZ.length;i+=1){
            ufoZ[i]=new Ufo(); 
        }

        citys[0]=new city(c1, 620);
        citys[1]=new city(c2, 620);
        citys[2]=new city(c3, 620);
        citys[3]=new city(c4, 620);
        score=0;
        cityLeft=citys.length;
    }

    public void paint(Graphics g){

        super.paint(g);
        g.drawImage(theBackGround, 0, 0,540,700,this);
        if(!gameOver()){
        for(int i=0;i<citys.length;i+=1){
            if(!cityBurnX[i]){
                g.drawImage(theCity, citys[i].retX(), citys[i].retY(), this);
            }
            else{
            g.drawImage(theCityBurn, citys[i].retX(), citys[i].retY(), this);
            }
        }

        for(int i=0;i<ufoZ.length;i+=1){
            if(setUfo[i]==true)
        {g.drawImage(theUfo1, ufoZ[i].retX(), ufoZ[i].retY(), this);}
        }
        for(int i=0;i<boomAry.size();i+=1){
             g.drawImage(theboom, boomAry.get(i).retX(), boomAry.get(i).retY(),20,30, this);
        }
        for(int i=0;i<hitUfos.length;i+=1){
            if(hitUfos[i]!=0){
            if(hitUfos[i]>=13){
                g.drawImage(theUfo2, ufoZ[i].retX(), ufoZ[i].retY(), this);
                hitUfos[i]--;
            }
            else{
                g.drawImage(theUfo3, ufoZ[i].retX(), ufoZ[i].retY(), this);
                hitUfos[i]--;
            }
        }
     }   
    }
    else{
        g.setColor(Color.RED);
        g.drawString("gameover!",230,300);
    }
     g.setColor(Color.GREEN);
     g.drawString("score:"+score,80,40);
     g.drawString("cityLeft:"+cityLeft,320,40);
    }
    
    public void draw(){
        repaint();
        updateUfo();
        updateBoom();
        updateCityLeft();
    }
    public void run(){
        while(true){
            draw();
            if(cnt<=1800){
                if(cnt%200==0){
                    setUfo[cnt/200]=true;
                }
                cnt++;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        int ux=0,uy=0;
        for(int j=0;j<ufoZ.length;j+=1){
            ux=ufoZ[j].retX();
            uy=ufoZ[j].retY();
        if(isHit(x, y,ux,uy)&&setUfo[j]==true){
            score+=10;
            HitUfo(j,ux,uy);
            hitUfos[j]=24;
        }
     }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    
    }
    @Override
    public void mouseExited(MouseEvent e) {
    
    }
    public boolean isSollision(Boom b,int t){
        int boomX=b.retX();
        int boomY=b.retY();
        int height=theCity.getHeight(this);
        int weith=theCity.getWidth(this);
        if(boomX-t<weith&&boomX-t>=0&&
        boomY-620<height&&boomY-620>=0){
            return true;
        }
        return false;
    }
    public boolean isHit(int x,int y,int ux,int uy){
        int height=theUfo1.getHeight(this);
        int weith=theUfo1.getWidth(this);
        if(x-ux<weith&&x-ux>=0
        &&y-uy<height&&y-uy>=0){
            return true;
        }
         return false;
    }
    public void updateUfo(){
                for(int i=0;i<ufoZ.length;i+=1){
            if(setUfo[i]==true){
            ufoZ[i].move();
            if(ran.nextInt(1000)>=995){
                boomAry.add(ufoZ[i].release());
            }
         }
        }
    }
    public void updateBoom(){
        for(int i=0;i<boomAry.size();i+=1){
            boomAry.get(i).fall();
            for(int j=0;j<citys.length;j+=1){
            if(isSollision(boomAry.get(i),citys[j].retX())){
                cityBurnX[j]=true;
                boomAry.remove(i);
                i--;
                break;
              }
              else if(boomAry.get(i).retY()>=700){
                boomAry.remove(i);
                i--;
                break;
            }
            // int count=0;
            // for(int ii=0;ii<cityBurnX.length;ii++){
            //     if(cityBurnX[ii]){
            //         count++;
            //     }
            // }
            // cityLeft=cityBurnX.length-count;
            }
        }
    }

    public void HitUfo(int index,int ux,int uy){
        setUfo[index]=false;
        ufoZ[index]=null;
        ufoZ[index]=new Ufo(ux,uy);
    }
    public void updateCityLeft(){
        int count=0;
            for(int ii=0;ii<cityBurnX.length;ii++){
                if(cityBurnX[ii]){
                    count++;
                }
            }
            cityLeft=cityBurnX.length-count;
    }
    public boolean gameOver(){
        if(cityLeft==0){
            return true;
        }
        return false;
    }
}

