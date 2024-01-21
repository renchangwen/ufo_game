

public class Boom {
    private int xb,yb;
    public Boom(int x,int y){
        xb=x;
        yb=y;
    }
    public int retX(){
        return xb;
    }
    public int retY(){
        return yb;
    }
    public void fall(){
        yb+=5;
    }
}
