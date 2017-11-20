package icons.iconChooser;

import javax.swing.JButton;

import icons.UserIcon;

class IconChooserButton extends JButton {

	private UserIcon staticIcon;

	public IconChooserButton(UserIcon i) {
		super();

		staticIcon = i;

		setIcon(staticIcon.getImageIcon());
		setToolTipText(staticIcon.getImagePath());

	}

	public UserIcon getStaticIcon() {
		return staticIcon;
	}

}
