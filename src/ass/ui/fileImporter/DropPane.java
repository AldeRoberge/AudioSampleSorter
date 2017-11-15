package ass.ui.fileImporter;

import javax.imageio.ImageIO;
import javax.swing.*;

import icons.Icons;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * Thanks to https://stackoverflow.com/questions/13597233/how-to-drag-and-drop-files-from-a-directory-in-java
 *
 */

public class DropPane extends JPanel {

	private DropTarget dropTarget;
	private DropTargetHandler dropTargetHandler;
	private Point dragPoint;

	private boolean dragOver = false;
	private BufferedImage target;

	private JLabel message;

	private FileImporter fileImporter;

	public DropPane(FileImporter f) {
		this.fileImporter = f;

		try {
			target = ImageIO.read(new File(Icons.CROSS.getImagePath()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		setLayout(new GridBagLayout());
		message = new JLabel();
		message.setFont(message.getFont().deriveFont(Font.BOLD, 24));
		add(message);

		updateMessage();

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	protected DropTarget getMyDropTarget() {
		if (dropTarget == null) {
			dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
		}
		return dropTarget;
	}

	protected DropTargetHandler getDropTargetHandler() {
		if (dropTargetHandler == null) {
			dropTargetHandler = new DropTargetHandler();
		}
		return dropTargetHandler;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		try {
			getMyDropTarget().addDropTargetListener(getDropTargetHandler());
		} catch (TooManyListenersException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		getMyDropTarget().removeDropTargetListener(getDropTargetHandler());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (dragOver) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(new Color(0, 255, 0, 64));
			g2d.fill(new Rectangle(getWidth(), getHeight()));
			if (dragPoint != null && target != null) {
				int x = dragPoint.x - 12;
				int y = dragPoint.y - 12;
				g2d.drawImage(target, x, y, this);
			}
			g2d.dispose();
		}
	}

	protected class DropTargetHandler implements DropTargetListener {

		protected void processDrag(DropTargetDragEvent dtde) {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrag(DnDConstants.ACTION_COPY);
			} else {
				dtde.rejectDrag();
			}

			SwingUtilities.invokeLater(new DragUpdate(true, dtde.getLocation()));
			repaint();
		}

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			processDrag(dtde);
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			processDrag(dtde);
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			SwingUtilities.invokeLater(new DragUpdate(false, null));
			repaint();
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {

			SwingUtilities.invokeLater(new DragUpdate(false, null));

			Transferable transferable = dtde.getTransferable();
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(dtde.getDropAction());
				try {

					List<File> transferData = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
					if (transferData != null && transferData.size() > 0) {
						fileImporter.importAll(transferData);
						dtde.dropComplete(true);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				dtde.rejectDrop();
			}
		}
	}

	public class DragUpdate implements Runnable {

		private boolean dragOver;
		private Point dragPoint;

		public DragUpdate(boolean dragOver, Point dragPoint) {
			this.dragOver = dragOver;
			this.dragPoint = dragPoint;
		}

		@Override
		public void run() {
			DropPane.this.dragOver = dragOver;
			DropPane.this.dragPoint = dragPoint;
			DropPane.this.repaint();
		}
	}

	public void updateMessage() {
		int nbFiles = fileImporter.getTotalFilesToImport();

		if (nbFiles == 0) {
			message.setText("Drag and drop files here");
		} else if (nbFiles == 1) {
			message.setText("Importing " + nbFiles + " file");
		} else {
			message.setText("Importing " + nbFiles + " files");
		}

	}
}