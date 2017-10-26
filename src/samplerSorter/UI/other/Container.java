package samplerSorter.UI.other;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import samplerSorter.properties.RelativeLocationManager;

public class Container extends JFrame {

	public JPanel contentPane;

	/**
	 * Panels contained in the Container need to be static.
	 * The Container setBounds(); to the content's bounds 
	 */
	public Container(String title, Image iconImage, JPanel content, boolean isResizeable) {
		contentPane = content;

		
		setIconImage(iconImage);
		setResizable(isResizeable);
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(content.getBounds());
		setContentPane(contentPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		//Logger.init(contentPane);

	}

}
