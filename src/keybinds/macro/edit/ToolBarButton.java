package keybinds.macro.edit;

import javax.swing.JButton;

import global.icons.StaticIcon;

public class ToolBarButton extends JButton {

	private StaticIcon staticIcon;

	public ToolBarButton(StaticIcon i) {
		super();
		
		staticIcon = i;

		setIcon(staticIcon.getImageIcon());
		setText(staticIcon.getImagePath());

	}

	public StaticIcon getStaticIcon() {
		return staticIcon;
	}

}
