package ass.macro.event;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import ass.macro.action.Action;

public class EventFrame extends JFrame {

	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// EventFrame frame = new EventFrame(new ActionManager());
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param actionManager
	 */
	public EventFrame(ass.macro.event.EventManager actionManager) {

		setTitle("History");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(EventFrame.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnUndo = new JButton("Undo");
		panel.add(btnUndo);

		JButton btnRedo = new JButton("Redo");
		btnRedo.setIcon(null);
		panel.add(btnRedo);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		TableModel model = new FootballClubTableModel(new ArrayList<Action>());
		JTable table = new JTable(model);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

}

class FootballClubTableModel extends AbstractTableModel {
	private List<Action> clubs;
	private String[] columns;

	public FootballClubTableModel(List<Action> aClubList) {
		super();
		clubs = aClubList;
		columns = new String[] { "Pos", "Team", "P", "W", "L", "D", "MP", "GF", "GA", "GD" };
	}

	// Number of column of your table
	public int getColumnCount() {
		return columns.length;
	}

	// Number of row of your table
	public int getRowsCount() {
		return clubs.size();
	}

	// The object to render in a cell
	public Object getValueAt(int row, int col) {
		Action club = clubs.get(row);
		switch (col) {
		case 0:
			return club;
		// to complete here...
		default:
			return null;
		}
	}

	// Optional, the name of your column
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public int getRowCount() {
		return clubs.size();
	}

}