package ass.keyboard.key;

import java.util.List;

/**
 * To be implemented by clients interested in global keyboard events.
 */
public interface GlobalKeyEventListener {

	void keysPressedChanged(List<Key> pressedKeys);

	void keyPressed(Key releasedKey);

	void keyReleased(Key pressedKey);

}
