import java.awt.Container;
import javax.swing.JFrame;

public class UfoFrame extends JFrame{
    GamePanel panel;
    public UfoFrame(){
        setTitle("UfoGame");
        panel=new GamePanel();
        Container contentPane=getContentPane();
        contentPane.add(panel);
        pack();
    }
    
    public static void main(String[] args){
          
            UfoFrame e1=new UfoFrame();
            e1.setSize(540,700);
            e1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            e1.setVisible(true);
        }

}
