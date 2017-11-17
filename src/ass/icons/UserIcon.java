package ass.icons;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * An icon stored on the user's disk
 */
public class UserIcon implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imagePath;
    private ImageIcon imageIcon;

    //Default dimensions
    public static final int DEFAULT_HEIGHT = 16;
    public static final int DEFAULT_WIDTH = 16;

    public UserIcon(String pathOfImage) {
        this(pathOfImage,DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    public UserIcon(String pathOfImage, int height, int width) {
        this.imageIcon = Icons.createImageIcon(pathOfImage, height, width);
        this.imagePath = pathOfImage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image getImage() {
        return imageIcon.getImage();
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

}