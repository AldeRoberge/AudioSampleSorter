package file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class WriteToFile {

	private String file = "null";

	public WriteToFile(String file) {
		this.file = file;
	}

	private boolean isAlreadyRunning;
	private ArrayList<String> bufferedText = new ArrayList<String>();

	public void write(String text) {
		if (!isAlreadyRunning) {

			isAlreadyRunning = true;

			BufferedWriter bw = null;
			try {
				// APPEND MODE SET HERE
				bw = new BufferedWriter(new FileWriter(file, true));
				bw.write(text);
				bw.newLine();

				Iterator<String> it = bufferedText.iterator();
				while (it.hasNext()) {
					String pair = it.next();
					bw.write(pair);
					it.remove();
				}

					try {
						bw.close();
					} catch (IOException ioe2) {
						// just ignore it
					}


			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			isAlreadyRunning = false;

		} else {
			bufferedText.add(text);
		}
	}

}