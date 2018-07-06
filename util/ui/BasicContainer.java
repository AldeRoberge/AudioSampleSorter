package ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.util.window.UtilityJFrame;
import alde.commons.util.window.UtilityJPanel;

public class BasicContainer {

	static Logger log = LoggerFactory.getLogger(BasicContainer.class);

	String title;
	Image iconImage;
	JPanel content;

	public BasicContainer(String title, Image iconImage, JPanel content) {
		this.title = title;
		this.iconImage = iconImage;
		this.content = content;
	}

	public BasicContainer(String title, Image iconImage, JComponent component) {
		this(title, iconImage, generatePanel(component));
	}

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

	public JPanel getContent() {
		return content;
	}

}
