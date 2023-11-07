
package Ultil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class BackgroundHelp extends JPanel{
   
    public BackgroundHelp(){
        setOpaque(false);
        setBackground(new Color(233,211,168));
    }
    
    // Cho kích cỡ bo viền
    private int round = 30;
    
    //Khởi tạo get and set cho border

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    @Override
    public void paint(Graphics g) { //vẽ lại giao diện
        Graphics2D g2 = (Graphics2D) g.create(); // khởi tạo ra Graphics2D, giúp việc căn chỉnh lại giao diện
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255,255,255)); // cho màu background là màu trắng
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
        g2.dispose();
        super.paint(g);
    }
}
