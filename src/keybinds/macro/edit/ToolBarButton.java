package keybinds.macro.edit;

import javax.swing.JButton;

import global.icons.StaticIcon;

public class ToolBarButton extends JButton {

	private StaticIcon staticIcon;

	public ToolBarButton(StaticIcon i) {
		staticIcon = i;

		setIcon(i.getImageIcon());
		setText(i.getPath());
	}

	public StaticIcon getStaticIcon() {
		return staticIcon;
	}

}
