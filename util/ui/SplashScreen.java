package ui;

import logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class SplashScreen {

	private static String TAG = "SplashScreen";

	private final Font subTextFont = new Font("Calibri Light", Font.PLAIN, 20);
	private TimerTask close;

	public SplashScreen(Image icon, BufferedImage inImage, BufferedImage outImage, BufferedImage titleImage, String subText, JFrame component) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame frame = new JFrame("Loading screen");
				frame.setIconImage(icon);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //will stop if user closes the program
				frame.add(new SplashScreenPane(inImage, outImage, titleImage, subText));
				frame.setUndecorated(true); //no title, no border
				frame.setBackground(new Color(0, 0, 0, 0)); //transparent
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setOpacity(0f);
				frame.setVisible(true);

				close = new TimerTask() {
					@Override
					public void run() {
						component.setVisible(true); //Show the program

						frame.dispose();
						close.cancel();
					}
				};

				/**java.util.Timer timer = new java.util.Timer();
				timer.schedule(close, 10000);*/

				new Thread("slowly appear") {

					@Override
					public void run() {
						float previousOpacity = 0;
						float increments = 0.2f;
						boolean go = true;

						while (go) { //gradually show software
							if ((previousOpacity + increments) >= 1) {
								go = false;
								frame.setOpacity(1f);
							} else {
								frame.setOpacity(frame.getOpacity() + increments);
								previousOpacity = frame.getOpacity() + increments;
							}
						}
					}
				}.start();

			}
		});
	}

	public class SplashScreenPane extends JPanel {

		private String TAG = "SplashScreenPane";

		public static final long RUNNING_TIME = 800;

		private BufferedImage inImage;
		private BufferedImage outImage;

		private BufferedImage textImage;

		private String subText;

		private float alpha = 0f;
		private long startTime = -1;

		private boolean mouseOver = false;

		public SplashScreenPane(BufferedImage inImage, BufferedImage outImage, BufferedImage textImage, String subText) {

			this.inImage = inImage;
			this.outImage = outImage;
			this.textImage = textImage;

			this.subText = subText;

			//

			setLayout(new GridBagLayout());

			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {

					if (!mouseOver) {
						go();
						mouseOver = true;
					}

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					Logger.logInfo(TAG, "Launching...");
					close.run();
				}

			});

		}

		public void go() {
			alpha = 0f;
			BufferedImage tmp = inImage;
			inImage = outImage;
			outImage = tmp;

			final Timer timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (startTime < 0) {
						startTime = System.currentTimeMillis();
					} else {

						long time = System.currentTimeMillis();
						long duration = time - startTime;
						if (duration >= RUNNING_TIME) {
							startTime = -1;
							((Timer) e.getSource()).stop();
							alpha = 0f;

							close.run();
						} else {
							alpha = 1f - ((float) duration / (float) RUNNING_TIME);
						}
						repaint();
					}
				}
			});

			timer.start();

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1080 / 2, 720 / 2);
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			g2d.setComposite(AlphaComposite.SrcAtop.derive(alpha));
			g2d.drawImage(inImage, 0, 0, getWidth(), getHeight(), this);

			g2d.setComposite(AlphaComposite.SrcAtop.derive(1f - alpha));
			g2d.drawImage(outImage, 0, 0, getWidth(), getHeight(), this);

			g2d.setComposite(AlphaComposite.SrcAtop.derive(1f));
			g2d.drawImage(textImage, 0, 0, getWidth(), getHeight(), this);

			//String

			g.setColor(Color.WHITE);
			g.setFont(subTextFont);

			Rectangle bottom = new Rectangle(0, getPreferredSize().height - 50, getPreferredSize().width, 20);

			drawCenteredString(g, subText, bottom, subTextFont);

			g2d.dispose();

		}

		private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
			// Get the FontMetrics
			FontMetrics metrics = g.getFontMetrics(font);
			// Determine the X coordinate for the text
			int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
			// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
			int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
			// Set the font
			g.setFont(font);
			// Draw the String
			g.drawString(text, x, y);
		}

	}

}