package wip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ASSFile {

	private File file;
	private FilePanel filePanel;

	public ASSFile(File file) {
		this.file = file;
	}

	public JPanel getFilePanel() {
		if (filePanel == null) {
			filePanel = new FilePanel(file);
		}
		return filePanel;
	}

}

class FilePanel extends JPanel {

	public boolean isSelected;
	
	public File file;

	public FilePanel(File file) {
		this.file = file;

		setLayout(new BorderLayout(0, 0));

		JPanel namePanel = new JPanel();
		add(namePanel, BorderLayout.SOUTH);

		JLabel nameLabel = new JLabel(file.getName());
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		namePanel.add(nameLabel);

		JPanel imagePanel = new JPanel();
		add(imagePanel, BorderLayout.CENTER);
		imagePanel.setLayout(new GridLayout(0, 1, 0, 0));

		Label imageLabel = new Label("Icon");
		imageLabel.setAlignment(Label.CENTER);
		imagePanel.add(imageLabel);
		
		
		addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                background = getBackground();
                setBackground(Color.RED);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
            }
        });
		
		
	}

}
