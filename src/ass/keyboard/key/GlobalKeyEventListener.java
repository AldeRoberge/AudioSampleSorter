package ass.keyboard.key;

import java.util.List;

public abstract class GlobalKeyEventListener {

	public abstract void keyPressedChanged(List<Key> pressedKeys);

	public abstract void keyPressed(Key releasedKey);

	public abstract void keyReleased(Key pressedKey);

}
