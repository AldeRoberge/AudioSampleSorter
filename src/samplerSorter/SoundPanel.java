package samplerSorter;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import samplerSorter.properties.Properties;
import samplerSorter.util.FileSizeToString;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;

public class SoundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Sound sound;

	private JLabel lblFilename;
	private JLabel lblFileSize;

	boolean isPlaying = true;
	boolean canResume = false;

	private SoundPanel me = this;

	public SoundPanel(Sound sound) {
		this.sound = sound;

		setBackground(Color.BLACK);

		setPreferredSize(new Dimension(579, 58));
		setLayout(null);

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(48, 0, 89));
		containerPanel.setBounds(1, 1, 576, 56);
		add(containerPanel);
		containerPanel.setLayout(null);

		lblFilename = new JLabel();
		lblFilename.setBounds(12, 5, 552, 16);
		containerPanel.add(lblFilename);
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFilename.setForeground(Color.WHITE);

		lblFileSize = new JLabel("File Size");
		lblFileSize.setBounds(12, 34, 552, 16);
		containerPanel.add(lblFileSize);
		lblFileSize.setForeground(Color.WHITE);
		updateFilenameLabel();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				SSUI.soundPanelIsClicked(me);

			}
		});

	}

	public void updateFilenameLabel() {
		lblFilename.setText(sound.getName(Properties.DISPLAY_SOUND_SUFFIXES.isTrue()));
		lblFileSize.setText(FileSizeToString.getFileSizeAsString(sound.file));
	}

	public void setSelected(boolean isSelected) {
		if (isSelected) {

			setBackground(Color.WHITE);

			if (Properties.PLAY_ON_CLICK.isTrue()) {
				SSUI.audioPlayer.playNewSoundOrResume(sound);
			}

		} else {
			setBackground(Color.BLACK);
		}
	}
}
