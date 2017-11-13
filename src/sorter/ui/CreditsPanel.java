package sorter.ui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import file.FileReader;

public class CreditsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public CreditsPanel() {
		setBounds(100, 100, 550, 600);
		setLayout(new BorderLayout(0, 0));

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		add(contentPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		String credits = FileReader.readFile("res//credits.html");

		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		HTMLDocument doc = (HTMLDocument) textPane.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) textPane.getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(), credits, 0, 0, null);
		} catch (BadLocationException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scrollPane.setViewportView(textPane);

		
		
		/**JFXPanel jfxPanel = new JFXPanel(); // Scrollable JCompenent
		Platform.runLater(() -> { // FX components need to be managed by JavaFX
			WebView webView = new WebView();
			webView.getEngine().loadContent("<html> Hello World!");
			webView.getEngine().load("http://www.stackoverflow.com/");
			jfxPanel.setScene(new Scene(webView));
		});*/

	}

}
