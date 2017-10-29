package samplerSorter.util.icon;

public class ImageDimension {
	private int height;
	private int width;

	public ImageDimension(int h, int w) {
		this.height = h;
		this.width = w;
	}

	//get

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	//set

	public void setWidth(int w) {
		this.width = w;
	}

	public void setHeight(int h) {
		this.height = h;
	}

}