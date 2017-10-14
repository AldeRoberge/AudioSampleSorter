package samplerSorter.globalkeylistenner;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import samplerSorter.macro.Key;
import samplerSorter.util.NativeKeyEventToKey;

public class GlobalKeyListener implements NativeKeyListener {

	private static final Key SHIFT_KEY = new Key(KeyEvent.VK_SHIFT);

	public ArrayList<Key> pressed = new ArrayList<Key>();

	boolean isListenningForInputs = true;

	public boolean shiftIsPressed() {
		update();

		return pressed.contains(SHIFT_KEY);
	}

	private void update() {
		System.out.println(pressed.toString() + " " + pressed.size());
	}

	public void setIsListenningForInput(boolean isListenning) {
		isListenningForInputs = isListenning;
	}

	public void nativeKeyPressed(NativeKeyEvent ke) {

		
		
		if (isListenningForInputs) {
			Key e = NativeKeyEventToKey.getJavaKeyEvent(ke);

			if (!pressed.contains(e)) {
				pressed.add(e);

				checkForKeyCombos();
				
				update();
			}

		}
		
		

	}

	private void checkForKeyCombos() {
		//TODO
	}

	public void nativeKeyReleased(NativeKeyEvent ke) {

		Key e = NativeKeyEventToKey.getJavaKeyEvent(ke);

		if (pressed.contains(e)) {
			//pressed.remove(e);
		}

	}

	public void init() { //GlobalScreen.unregisterNativeHook();
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// Change the level for all handlers attached to the default logger.
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.OFF);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// left intentionally blank
	}
}