package samplerSorter.actions;

/**
 * This class is a duplicate of an action that is to be performed. Action themselves are immutable, 
 * we clone them to preserve for future use
 * 
 * @see GlobalMacroKeyListenner -> checkForBinds*/

public class HistoryAction {

	private Action action;

	public HistoryAction(Action a) {
		this.action = a;
	}

	public void begin() {
		// TODO Auto-generated method stub
		
	}

}
