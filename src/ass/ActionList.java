package ass;

import javax.swing.JPanel;

import ass.keyboard.action.interfaces.Action;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class ActionList extends JPanel {

	/**
	 * Create the panel.
	 */
	public ActionList() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
	}
	
	
	public void addAction(Action a) {
		
	}
	
	

}