package ass.macro.ui.edit;

import javax.swing.JButton;

import ass.icons.UserIcon;

public class ToolBarButton extends JButton {

	private UserIcon staticIcon;

	public ToolBarButton(UserIcon i) {
		super();

		staticIcon = i;

		setIcon(staticIcon.getImageIcon());
		setToolTipText(staticIcon.getImagePath());

	}

	public UserIcon getStaticIcon() {
		return staticIcon;
	}

}
