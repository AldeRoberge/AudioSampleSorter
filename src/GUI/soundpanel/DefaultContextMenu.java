package GUI.soundpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//From https://stackoverflow.com/questions/766956/how-do-i-create-a-right-click-context-menu-in-java-swing

class DefaultContextMenu extends JPopupMenu {

	private JMenuItem play;
	private JMenuItem remove;
	private JMenuItem openFileLocation;
	private JMenuItem delete;

	private SoundPanel soundPanel;

	private DefaultContextMenu() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		addPopupMenuItems();
	}

	private void addPopupMenuItems() {
		play = new JMenuItem("Play");
		play.setEnabled(true);
		play.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		play.addActionListener(event -> soundPanel.sound.playOrPause());
		add(play);

		remove = new JMenuItem("Remove");
		remove.setEnabled(true);
		remove.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		remove.addActionListener(event -> soundPanel.sorter.removeSoundPanel(soundPanel));
		add(remove);

		add(new JSeparator());

		openFileLocation = new JMenuItem("Open file location");
		openFileLocation.setEnabled(false);
		openFileLocation.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFileLocation.addActionListener(event -> soundPanel.openFileLocation());
		add(openFileLocation);

		delete = new JMenuItem("Delete");
		delete.setEnabled(false);
		delete.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		//delete.addActionListener(event -> textComponent.replaceSelection(""));
		add(delete);

		add(new JSeparator());

	}

	private void addTo(SoundPanel soundPanelComponent) {
		soundPanelComponent.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent pressedEvent) {
				if ((pressedEvent.getKeyCode() == KeyEvent.VK_Z) && ((pressedEvent.getModifiersEx()
						& Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0)) {
					//undo
				}

				if ((pressedEvent.getKeyCode() == KeyEvent.VK_Y) && ((pressedEvent.getModifiersEx()
						& Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0)) {

				}
			}
		});

		soundPanelComponent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent releasedEvent) {
				handleContextMenu(releasedEvent);
			}

			@Override
			public void mouseReleased(MouseEvent releasedEvent) {
				handleContextMenu(releasedEvent);
			}
		});

	}

	private void handleContextMenu(MouseEvent releasedEvent) {
		if (releasedEvent.getButton() == MouseEvent.BUTTON3) {
			processClick(releasedEvent);
		}
	}

	private void processClick(MouseEvent event) {
		soundPanel = (SoundPanel) event.getSource();
		soundPanel.requestFocus();

		boolean enableUndo = true;
		boolean enableRedo = true;
		boolean openFileLocatio2n = true;
		boolean enableDelete = false;
		boolean enableSelectAll = false;

		play.setEnabled(enableUndo);
		remove.setEnabled(enableRedo);
		openFileLocation.setEnabled(openFileLocatio2n);
		delete.setEnabled(enableDelete);

		// Shows the popup menu
		show(soundPanel, event.getX(), event.getY());
	}

	public static void addDefaultContextMenu(SoundPanel component) {
		DefaultContextMenu defaultContextMenu = new DefaultContextMenu();
		defaultContextMenu.addTo(component);
	}
}