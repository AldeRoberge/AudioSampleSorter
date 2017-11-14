package global.icons;

import java.awt.Image;

import javax.swing.ImageIcon;

public class StaticIcon {
	private String imageName;
	private ImageIcon imageIcon;

	public StaticIcon(String imageName) {
		this.imageIcon = Icons.createImageIcon(Icons.LOCATION_OF_ICONS + imageName);
		this.imageName = imageName;
	}

	public String getPath() {
		return Icons.LOCATION_OF_ICONS + imageName;
	}

	public Image getImage() {
		return imageIcon.getImage();
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

}