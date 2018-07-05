package constants.icons.iconChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

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

import alde.commons.util.window.UtilityJFrame;
import ui.WrapLayout;

public class IconChooser extends UtilityJFrame {

	static Logger log = LoggerFactory.getLogger(IconChooser.class);

	private JPanel contentPane;
	private JPanel iconsPanel;

	private final Font font = new Font("Segoe UI Light", Font.PLAIN, 13);

	private String searchForValue;

	/**
	 * Create the frame.
	 */
	public IconChooser(final Consumer<UserIcon> waitingForAnswer) {

		searchForValue = "";

		setTitle("Icon Chooser");
		setIconImage(Icons.ICON_CHOOSER.getImage());

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel content = new JPanel();
		contentPane.add(content);
		content.setLayout(new BorderLayout(0, 0));

		JScrollPane iconsScrollPane = new JScrollPane();
		content.add(iconsScrollPane, BorderLayout.CENTER);
		iconsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		iconsPanel = new JPanel();
		iconsPanel.setBackground(SystemColor.textHighlightText);
		iconsScrollPane.setViewportView(iconsPanel);
		iconsPanel.setLayout(new WrapLayout());

		JPanel actionPanel = new JPanel();
		actionPanel.setBackground(SystemColor.control);
		iconsScrollPane.setColumnHeaderView(actionPanel);

		JButton btnImport = new JButton("Refresh list");
		btnImport.addActionListener(e -> repopulate(waitingForAnswer, searchForValue));
		btnImport.setFont(font.deriveFont(Font.BOLD));
		actionPanel.add(btnImport);

		JButton btnShowInExplorer = new JButton("Show in explorer");
		btnShowInExplorer.setFont(font.deriveFont(Font.BOLD));
		btnShowInExplorer.addActionListener(e -> {
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

		});
		btnShowInExplorer.setBackground(Color.ORANGE);
		btnShowInExplorer.setForeground(Color.BLACK);
		btnShowInExplorer.setIcon(Icons.ABOUT.getImageIcon());
		actionPanel.add(btnShowInExplorer);

		JPanel searchPanel = new JPanel();
		content.add(searchPanel, BorderLayout.SOUTH);
		searchPanel.setLayout(new BorderLayout(0, 0));

		JTextField searchInput = new JTextField();

		searchInput.getDocument().addDocumentListener(new DocumentListener() {

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
				searchForValue = searchInput.getText();
				repopulate(waitingForAnswer, searchForValue);
			}

		});

		searchPanel.add(searchInput);

		JPanel searchLabelPanel = new JPanel();
		searchPanel.add(searchLabelPanel, BorderLayout.WEST);

		Component searchLabelRightStrut = Box.createHorizontalStrut(5);
		searchLabelPanel.add(searchLabelRightStrut);

		JLabel searchLabel = new JLabel("Search for :");
		searchLabelPanel.add(searchLabel);

		Component searchLabelLeftStrut = Box.createHorizontalStrut(5);
		searchLabelPanel.add(searchLabelLeftStrut);

		//

		repopulate(waitingForAnswer, searchForValue);

		setVisible(true);

	}

	private void repopulate(Consumer<UserIcon> waitingForAnswer, String searchForValue) {

		log.info("Repopulating with " + IconsLibrary.userIcons.size() + " icons.");

		for (UserIcon i : IconsLibrary.userIcons) {

			if (i.containsString(searchForValue, true)) {
				JPanel icon = new JPanel();
				icon.setLayout(new FlowLayout());
				iconsPanel.add(icon);

				IconChooserButton selectThisIcon = new IconChooserButton(i);
				selectThisIcon.setFont(font);
				selectThisIcon.addActionListener(e -> {
					waitingForAnswer.accept(selectThisIcon.getStaticIcon());
					setVisible(false);
				});
				icon.add(selectThisIcon);
			}

		}

		log.info("Repopulating...");

		iconsPanel.revalidate();
		iconsPanel.repaint();

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
