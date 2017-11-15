package macro.action.editable;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TEST extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TEST frame = new TEST();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TEST() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		EditableProperty<String> newName = new EditableProperty<String>("NewFileName", "New name");

		EditablePropertyPanel panel = new EditablePropertyPanel(newName);
		contentPane.add(panel, BorderLayout.CENTER);

		EditablePropertyPanel panel1 = new EditablePropertyPanel(newName);
		contentPane.add(panel1, BorderLayout.NORTH);

		EditablePropertyPanel panel2 = new EditablePropertyPanel(newName);
		contentPane.add(panel2, BorderLayout.SOUTH);

	}

}
