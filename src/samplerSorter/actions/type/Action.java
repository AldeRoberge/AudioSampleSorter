package samplerSorter.actions.type;

import java.io.Serializable;

public interface Action extends Serializable {

	public static final String TAG = "Action";

	public abstract void perform();

	public abstract void undo();
	
	public abstract String toString();

}
