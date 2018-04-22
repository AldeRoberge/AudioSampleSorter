package constants.icons.iconChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.icons.Icons;
import constants.icons.IconsLibrary;
import constants.icons.UserIcon;
import ui.MiddleOfTheScreen;
import ui.WrapLayout;

public class IconChooser extends JFrame {

	static Logger log = LoggerFactory.getLogger(IconChooser.class);

	private JPanel contentPane;
	private JPanel iconsPanel;

	private final Font font = new Font("Segoe UI Light", Font.PLAIN, 13);

	private String searchForValue;

	/**
	 * Create the frame.
	 */
	public IconChooser(final GetIcon waitingForAnswer) {

		searchForValue = "";

		setTitle("Icon Chooser");
		setIconImage(Icons.ICON_CHOOSER.getImage());

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		setBounds(100, 100, 450, 300);

		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel content = new JPanel();
		contentPane.add(content);
		content.setLayout(new BorderLayout(0, 0));

		JScrollPane iconScrollPane = new JScrollPane();
		content.add(iconScrollPane, BorderLayout.CENTER);
		iconScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		iconsPanel = new JPanel();
		iconsPanel.setBackground(SystemColor.textHighlightText);
		iconScrollPane.setViewportView(iconsPanel);
		iconsPanel.setLayout(new WrapLayout());

		JPanel importPanel = new JPanel();
		importPanel.setBackground(SystemColor.control);
		iconScrollPane.setColumnHeaderView(importPanel);

		JButton btnImport = new JButton("Refresh list");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repopulate(waitingForAnswer, searchForValue);
			}
		});
		btnImport.setFont(font.deriveFont(Font.BOLD));
		importPanel.add(btnImport);

		JButton btnOpenContainingFolder = new JButton("Open containing folder");
		btnOpenContainingFolder.setFont(font.deriveFont(Font.BOLD));
		btnOpenContainingFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane,
						"Opening the icon folder. \nIcons are loaded as 16x16 images that support transparency.\nIcons are stored in "
								+ IconsLibrary.LOCATION_OF_ICONS
								+ ".\nYou might have to reload the program to see your icon.");

				try {
					Desktop.getDesktop().open(new File(IconsLibrary.LOCATION_OF_ICONS));
				} catch (IOException e1) {
					log.error("Could not open folder containing icon images!");
					e1.printStackTrace();
				}

			}
		});
		btnOpenContainingFolder.setBackground(Color.ORANGE);
		btnOpenContainingFolder.setForeground(Color.BLACK);
		btnOpenContainingFolder.setIcon(Icons.ABOUT.getImageIcon());
		importPanel.add(btnOpenContainingFolder);

		JPanel panel = new JPanel();
		content.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JTextField searchFor = new JTextField();

		searchFor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateValue();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateValue();
			}

			public void updateValue() {
				searchForValue = searchFor.getText();
				repopulate(waitingForAnswer, searchForValue);
			}

		});

		panel.add(searchFor);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);

		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut);

		JLabel lblSearchForLabel = new JLabel("Search for :");
		panel_1.add(lblSearchForLabel);

		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut_1);

		//

		log.info("Repopulating...");

		iconsPanel.removeAll();
		iconsPanel.revalidate();
		iconsPanel.repaint();

	}

	private void repopulate(GetIcon waitingForAnswer, String searchForValue) {
		for (UserIcon i : IconsLibrary.userIcons) {

			if (i.containsString(searchForValue, true)) {
				JPanel icon = new JPanel();
				icon.setLayout(new FlowLayout());
				iconsPanel.add(icon);

				IconChooserButton selectThisIcon = new IconChooserButton(i);
				selectThisIcon.setFont(font);
				selectThisIcon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//selectThisIcon.getIcon(), 

						waitingForAnswer.getResponse(selectThisIcon.getStaticIcon());
						setVisible(false);
					}
				});
				icon.add(selectThisIcon);
			}

		}

	}

}

class IconChooserButton extends JButton {

	private UserIcon staticIcon;

	public IconChooserButton(UserIcon i) {
		super();

		staticIcon = i;

		setIcon(staticIcon.getImageIcon());
		setToolTipText(staticIcon.getImagePath());
	}

	public UserIcon getStaticIcon() {
		return staticIcon;
	}

}
