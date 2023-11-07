
package Ultil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class JPanelMain extends JPanel {
    JPanelMain m;
    private Icon image;
    private Dimension imageSize; // Kích thước ban đầu
    private Point imageLocation; // Vị trí ban đầu
    
    private Animator animator;
    private Point animatePoint;
    
    public JPanelMain(){
        setOpaque(false);
        setBackground(new Color(255, 255, 255)); 
         m = new JPanelMain(new ImageIcon(getClass().getResource("/Image/wolf.png")), new Dimension(130, 110), new Point(250, 370));
    }

    public JPanelMain(Icon image, Dimension imageSize, Point imageLocation) {
        this.image = image;
        this.imageSize = imageSize;
        this.imageLocation = imageLocation;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }
    
    public Dimension getImageSize() {
        return imageSize;
    }

    public void setImageSize(Dimension imageSize) {
        this.imageSize = imageSize;
    }

    public Point getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(Point imageLocation) {
        this.imageLocation = imageLocation;
    }
    // Vẽ lại các con vật khi thay đổi vị trí
    
    
    private void ViewAnimation(int i, Point newPoint){
        // Nếu nhưư đến đích thì dừng lại
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }   
        //System.out.println(arrayCartoon.get(i).getImageLocation());
        animator = PropertySetter.createAnimator(1000, m, "imageLocation", m.getImageLocation(), newPoint);

        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                
            }

            @Override
            public void end() {
                // Cập nhật lại mảng
//                arrayCartoon.get(i).setImageLocation(newPoint);
                repaint();
                System.out.println("May lan");
            }
        });
         animator.setResolution(5);
        animator.start();
    }  
    private void teActionPerformed(java.awt.event.ActionEvent evt) {                                      
        animatePoint = new Point(710, 280);
            ViewAnimation(1, animatePoint);
    }  
}