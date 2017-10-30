package GUI.other;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ui.MiddleOfTheScreen;

public class Container extends JFrame {

	/**
	 * Panels contained in the Container need to be static.
	 * The Container setBounds(); to the content's bounds 
	 */
	public Container(String title, Image iconImage, JPanel content, boolean isResizeable) {

		setIconImage(iconImage);
		setResizable(isResizeable);
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		if (content != null) {
			setBounds(content.getBounds());
			setContentPane(content);
		}

		setLocation(MiddleOfTheScreen.getLocationFor(this));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		//Logger.init(contentPane);

	}

}
