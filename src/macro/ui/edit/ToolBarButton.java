package macro.ui.edit;

import javax.swing.JButton;

import icons.StaticIcon;

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
