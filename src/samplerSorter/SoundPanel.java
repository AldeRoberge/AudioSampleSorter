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

	public SoundPanel(Sound sound) {
		this.sound = sound;

		setBackground(new Color(48, 0, 89));

		setPreferredSize(new Dimension(589, 75));
		setLayout(null);

		lblFilename = new JLabel();
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFilename.setForeground(Color.WHITE);
		lblFilename.setBounds(12, 13, 484, 16);
		add(lblFilename);

		lblFileSize = new JLabel("File Size");
		lblFileSize.setForeground(Color.WHITE);
		lblFileSize.setBounds(12, 42, 134, 16);
		add(lblFileSize);

		//\u25BA PLAY
		JButton btnNewButton = new JButton("\u25BA");
		btnNewButton.setBounds(521, 13, 45, 45);
		add(btnNewButton);
		updateFilenameLabel();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				RunSS.audioPlayer.playNewSoundOrResume(sound);
				
				


			}
		});

	}

	public void updateFilenameLabel() {
		lblFilename.setText(sound.getName(Properties.DISPLAY_SOUND_SUFFIXES.isTrue()));
		lblFileSize.setText(FileSizeToString.getFileSizeAsString(sound.file));
	}
}
