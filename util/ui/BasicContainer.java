package ui;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicContainer {

	static Logger log = LoggerFactory.getLogger(BasicContainer.class);

	String title;
	Image iconImage;
	JComponent content;

	public BasicContainer(String title, Image iconImage, JComponent content) {
		this.title = title;
		this.iconImage = iconImage;
		this.content = content;
	}

	/**public BasicContainer(String title, Image iconImage, JComponent component) {
		this(title, iconImage, generatePanel(component));
	}*/

	private static JPanel generatePanel(JComponent component) {
		JScrollPane jscp = new JScrollPane();
		jscp.setViewportView(component);

		JPanel p = new JPanel(new BorderLayout());
		p.add(jscp, BorderLayout.CENTER);
		return p;
	}

	public String getTitle() {
		return title;
	}

	public Image getIconImage() {
		return iconImage;
	}

	public JComponent getContent() {
		return content;
	}

}
