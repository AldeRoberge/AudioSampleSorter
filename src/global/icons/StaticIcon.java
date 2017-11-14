package global.icons;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class StaticIcon implements Serializable {
	private static final long serialVersionUID = 1L;

	private String imagePath;
	private ImageIcon imageIcon;

	public StaticIcon(String pathOfImage) {

		this.imageIcon = Icons.createImageIcon(pathOfImage);
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