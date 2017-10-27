package samplerSorter.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import samplerSorter.Constants;
import samplerSorter.SamplerSorter;
import samplerSorter.properties.Properties;
import samplerSorter.util.FileSizeToString;

public class SoundPanel extends JPanel {

	public static final Color SELECTED = Constants.SICK_PURPLE;
	public static final Color UNSELECTED = Color.LIGHT_GRAY;

	private static final long serialVersionUID = 1L;

	private Sorter sorter;

	private Sound sound;

	private JLabel lblFilename;
	private JLabel lblFileSize;

	boolean isPlaying = true;
	boolean canResume = false;

	private SoundPanel me = this;

	public SoundPanel(Sound sound, Sorter s) {
		this.sorter = s;
		this.sound = sound;

		setBackground(UNSELECTED);

		setPreferredSize(new Dimension(579, 48));
		setLayout(null);

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(Color.WHITE); //always white, the other panel changes
		containerPanel.setBounds(1, 1, 576, 44);
		add(containerPanel);
		containerPanel.setLayout(null);

		lblFilename = new JLabel();
		lblFilename.setBounds(12, 5, 552, 16);
		containerPanel.add(lblFilename);
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFilename.setForeground(Constants.SICK_PURPLE);

		lblFileSize = new JLabel("File Size");
		lblFileSize.setBounds(12, 22, 552, 16);
		containerPanel.add(lblFileSize);
		lblFileSize.setForeground(Constants.SICK_PURPLE);
		updateFilenameLabel();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				sorter.soundPanelIsClicked(me);

			}
		});

	}

	public void updateFilenameLabel() {
		lblFilename.setText(sound.getName(Properties.DISPLAY_SOUND_SUFFIXES.isTrue()));
		lblFileSize.setText(FileSizeToString.getFileSizeAsString(sound.file));
	}

	public void setSelected(boolean isSelected) {
		if (isSelected) {

			setBackground(SELECTED);

			if (Properties.PLAY_ON_CLICK.isTrue()) {
				play();
			}

		} else {
			setBackground(UNSELECTED);
		}
	}

	//Should only be used by Actions, use setSelected(true) instead for selection
	public void play() {
		sorter.audioPlayer.playNewSoundOrResume(sound);
	}
	
	public void stop() {
		sorter.audioPlayer.pause();
	}

	@Override
	public String toString() {
		return "SoundPanel [sound=" + sound + ", lblFilename=" + lblFilename + ", lblFileSize=" + lblFileSize + "]";
	}

	

}
