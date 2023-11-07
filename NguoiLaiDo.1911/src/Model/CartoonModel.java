
package Model;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.Icon;

public class CartoonModel {
    
    private Icon image;
    private Dimension imageSize; // Kích thước ban đầu
    private Point imageLocation; // Vị trí ban đầu

    public CartoonModel() {
    }

    public CartoonModel(Icon image, Dimension imageSize, Point imageLocation) {
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
}
