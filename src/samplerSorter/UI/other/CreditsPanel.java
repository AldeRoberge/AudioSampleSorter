package samplerSorter.UI.other;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.FlowLayout;

public class CreditsPanel extends JPanel {

	public String credits;
	
	/**
	 * Create the panel.
	 */
	public CreditsPanel() {
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		credits = samplerSorter.util.FileReader.readFile("res//credits.html");

		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		HTMLDocument doc = (HTMLDocument) textPane.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) textPane.getEditorKit();
		String text = credits;
		try {
			editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
		} catch (BadLocationException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scrollPane.setViewportView(textPane);
	}

}
