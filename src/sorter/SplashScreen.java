package sorter;

import global.icons.Icons;
import global.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class SplashScreen {

	private static final String IMAGE_LOCATION = new File(".").getAbsolutePath() + "/res/splashScreen/";

	public static String subText = "(Alpha) Ultimate 1.0";
	public static Font subTextFont = new Font("Calibri Light", Font.PLAIN, 20);

	public static TimerTask close;

	public static void main(String[] args) {
		new SplashScreen();
	}

	public SplashScreen() {

		SorterUI sorterUI = new SorterUI();

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				JFrame frame = new JFrame("SplashScreen");
				frame.setIconImage(Icons.SOFTWARE.getImage());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new SplashScreenPane());
				frame.setUndecorated(true);
				frame.setBackground(new Color(0, 0, 0, 0));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setOpacity(0f);
				frame.setVisible(true);

				close = new TimerTask() {
					@Override
					public void run() {
						sorterUI.setVisible(true);

						frame.dispose();
						close.cancel();
					}
				};

				java.util.Timer timer = new java.util.Timer();
				timer.schedule(close, 10000);

				new Thread("threadedListener") {

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

	public static class SplashScreenPane extends JPanel {

		private String TAG = "SplashScreenPane";

		public static final long RUNNING_TIME = 800;

		private BufferedImage inImage;
		private BufferedImage outImage;

		private BufferedImage textImage;

		private float alpha = 0f;
		private long startTime = -1;

		private boolean mouseOver = false;
		private boolean transitioned = false;

		public SplashScreenPane() {

			setLayout(new GridBagLayout());

			try {
				inImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG_BLURRY.png"));
				outImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG.png"));

				textImage = ImageIO.read(new File(IMAGE_LOCATION + "/TITLE.png"));
			} catch (IOException exp) {
				exp.printStackTrace();
			}

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

				public void go() {
					alpha = 0f;
					BufferedImage tmp = inImage;
					inImage = outImage;
					outImage = tmp;
					timer.start();
				}

			});

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
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
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

		public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
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