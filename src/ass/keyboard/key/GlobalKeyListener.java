package ass.keyboard.key;

import java.awt.event.KeyEvent;
import java.io.Serializable;
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

//Call GlobalScreen.unregisterNativeHook(); to remove (unneeded here)

public class GlobalKeyListener implements NativeKeyListener {

	static Logger log = LoggerFactory.getLogger(GlobalKeyListener.class);

	private static GlobalKeyListener globalKeyListener; //Singleton

	private static List<GlobalKeyEventListener> gs = new ArrayList<>();

	/**
	 * List of pressed keys
	 * Converted from NativeKey
	 */
	private List<Key> pressedKeys = new ArrayList<>();

	public static void addListener(GlobalKeyEventListener g) {
		gs.add(g);
	}

	/**
	 * @return self, creates if not already instantiated
	 */
	public static GlobalKeyListener get() {
		if (globalKeyListener == null) {
			globalKeyListener = new GlobalKeyListener();
		}
		return globalKeyListener;
	}

	/**
	 * NativeKeyListener pressed keys
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent ke) {

		Key k = GetNativeKeyAsJavaKey.getAsJavaKey(ke);

		if (!pressedKeys.contains(k)) {
			pressedKeys.add(k);

			warnListenersOfUpdate();
		}

		for (GlobalKeyEventListener g : gs) {
			g.keyPressed(k);
		}

		warnListenersOfUpdate();
	}

	/**
	 * NativeKeyListener released keys
	 */
	@Override
	public void nativeKeyReleased(NativeKeyEvent ke) {

		Key k = GetNativeKeyAsJavaKey.getAsJavaKey(ke);

		pressedKeys.remove(k);

		for (GlobalKeyEventListener g : gs) {
			g.keyReleased(k);
		}

		warnListenersOfUpdate();
	}

	private void warnListenersOfUpdate() {
		for (GlobalKeyEventListener g : gs) {
			g.keyPressedChanged(pressedKeys);
		}
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
	 * @param Key pressedKey
	 */
	public boolean keyIsPressed(Key pressedKey) {
		return pressedKeys.contains(pressedKey);
	}

	/**
	 * @param int code of pressedKey
	 */
	boolean keyIsPressed(int keyCodeOfPressedKey) {
		return pressedKeys.contains(new Key(keyCodeOfPressedKey));
	}

	/**
	 * Returns true only if all keys are pressed
	 */
	private boolean keysArePressed(ArrayList<Key> keys) {
		for (Key key : keys) {
			if (!pressedKeys.contains(key)) {
				return false;
			}
		}
		return true;
	}

	public boolean keyCombinaisonIsPressed(ArrayList<Key> requiredKeys) {
		final int required = requiredKeys.size();
		int current = 0;

		for (Key kR : requiredKeys) {
			for (Key k : pressedKeys) {
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
