package ass.keyboard.key;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class that implements NativeKeyListener and dispatches events to listeners
 */
public class GlobalKeyListener implements NativeKeyListener {

	private static Logger log = LoggerFactory.getLogger(GlobalKeyListener.class);

	private static GlobalKeyListener instance;

	private static List<GlobalKeyEventListener> listeners = new ArrayList<>();

	/**
	 * List of currently pressed keys
	 * converted from NativeKey
	 */
	private List<Key> keysPressed = new ArrayList<>();

	public static void addListener(GlobalKeyEventListener g) {
		listeners.add(g);
	}

	static {
		try {
			GlobalScreen.registerNativeHook();

			// Get the logger for "org.jnativehook" and set the level to off.
			java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);

			// Change the level for all handlers attached to the default logger.
			Handler[] handlers = java.util.logging.Logger.getLogger("").getHandlers();
			for (Handler handler : handlers) {
				handler.setLevel(Level.OFF);
			}

			//Add 'this' to the nativeKeyListenners
			GlobalScreen.addNativeKeyListener(get());
		} catch (NativeHookException ex) {
			log.error("There was a problem registering the native hook.", ex);
		}
	}

	/**
	 * Singleton GlobalKeyListener
	 * Returns instance of this (creates if null)
	 */
	public static GlobalKeyListener get() {
		if (instance == null) {
			instance = new GlobalKeyListener();
		}
		return instance;
	}

	/**
	 * Receive 'pressed keys' and send them to listeners
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent ke) {

		Key k = Key.getAsJavaKey(ke);

		if (!keysPressed.contains(k)) {
			keysPressed.add(k);
		}

		for (GlobalKeyEventListener g : listeners) {
			g.keyPressed(k);
		}

		warnListenersOfUpdate();
	}

	/**
	 * Receive 'released keys' and send them to listeners
	 */
	@Override
	public void nativeKeyReleased(NativeKeyEvent ke) {

		Key k = Key.getAsJavaKey(ke);

		if (keysPressed.contains(k)) {
			keysPressed.remove(k);
		}

		for (GlobalKeyEventListener g : listeners) {
			g.keyReleased(k);
		}

		warnListenersOfUpdate();
	}

	private void warnListenersOfUpdate() {
		for (GlobalKeyEventListener g : listeners) {
			g.keysPressedChanged(keysPressed);
		}
	}

	/**
	 * @param Key pressedKey
	 */
	public boolean keyIsPressed(Key pressedKey) {
		return keysPressed.contains(pressedKey);
	}

	/**
	 * @param int code of pressedKey
	 */
	boolean keyIsPressed(int keyCodeOfPressedKey) {
		return keysPressed.contains(new Key(keyCodeOfPressedKey));
	}

	/**
	 * Returns true only if all keys are pressed
	 */
	private boolean keysArePressed(ArrayList<Key> keys) {
		for (Key key : keys) {
			if (!keysPressed.contains(key)) {
				return false;
			}
		}
		return true;
	}

	public boolean keyCombinaisonIsPressed(ArrayList<Key> requiredKeys) {
		final int required = requiredKeys.size();
		int current = 0;

		for (Key kR : requiredKeys) {
			for (Key k : keysPressed) {
				if (k.equals(kR)) {
					current++;
				}
			}
		}

		return current == required;

	}

	public boolean shiftIsPressed() {
		return keyIsPressed(KeyEvent.VK_SHIFT);
	}

	public boolean ctrlIsPressed() {
		return keyIsPressed(KeyEvent.VK_CONTROL);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) { // left intentionally blank
	}

}
