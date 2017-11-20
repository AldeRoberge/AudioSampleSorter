package ass.keyboard.macro;

import java.util.ArrayList;

public interface ListenForMacroChanges {

	public void macroChanged(ArrayList<MacroAction> newMacros);

}
