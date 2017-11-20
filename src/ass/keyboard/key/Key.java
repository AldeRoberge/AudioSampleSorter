package ass.keyboard.key;

import java.awt.event.KeyEvent;
import java.io.Serializable;

//A simple KeyEvent
public class Key implements Serializable {

	private int keyCode;
	public String keyName;

	public Key(int keyCode) {
		this.keyCode = keyCode;
		this.keyName = KeyEvent.getKeyText(keyCode);
	}

	//Override equals so we can use .equals() appropriately
	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof Key)) {
			return false;
		}

		Key c = (Key) o;

		return (keyCode == c.keyCode) && (keyName.equals(c.keyName));
	}

	@Override
	public String toString() {
		return "Key [keyCode=" + keyCode + ", keyName=" + keyName + "]";
	}

}
