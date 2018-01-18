package ui;

import javax.swing.*;

import logger.Logger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BasicContainer extends JFrame {

	/**
	 * Panels contained in the Container need to be static. (Irrelevant to any other components)
	 * The Container setBounds(); to the content's bounds 
	 */
	public BasicContainer(String title, Image iconImage, JPanel content, boolean isResizeable) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception weTried) {
			Logger.logError("BasicContainer (" + title + ")", "Error with look and feel", weTried);
		}

		setIconImage(iconImage);
		setResizable(isResizeable);
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		if (content != null) {
			setBounds(content.getBounds());
			setContentPane(content);
		}

		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

	}

}
