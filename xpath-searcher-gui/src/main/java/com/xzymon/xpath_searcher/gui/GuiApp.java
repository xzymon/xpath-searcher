package com.xzymon.xpath_searcher.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiApp extends JFrame {
	private static final long serialVersionUID = 3219548713775085362L;

	private static final Logger logger = LoggerFactory.getLogger(GuiApp.class.getName());
	private static final String DEFAULT_PROPS_PATH = "/xpath-searcher.properties";

	private Properties appProperties;

	private JMenuBar menuBar;
	private JMenu mFile;
	private XPathSearchingPanel searchingP;

	private final JFileChooser fileChooser = new JFileChooser();

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if (("Metal").equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
				// */
				/*
				 * if(("Nimbus").equals(info.getName())){
				 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); }
				 */
				/*
				 * if(("CDE/Motif").equals(info.getName())){
				 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); }
				 */
				/*
				 * if(("GTK+").equals(info.getName())){
				 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); }
				 */
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} finally {

		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GuiApp gui = new GuiApp();
				gui.setVisible(true);
			}
		});
	}

	public GuiApp() {
		appProperties = loadProperties();

		initComponents();
	}

	private void initComponents() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		mFile = new JMenu("File");
		mFile.add(new OpenFileAction());
		mFile.add(new OpenHtmlFileFilteringWithJSoupAction());
		mFile.addSeparator();
		mFile.add(new CloseAction());
		menuBar.add(mFile);
		this.setJMenuBar(menuBar);

		searchingP = new XPathSearchingPanel(appProperties);
		this.add(searchingP);

		pack();
	}

	private Properties loadProperties() {
		Properties props = new Properties();

		InputStream is = this.getClass().getResourceAsStream(DEFAULT_PROPS_PATH);
		if (is != null) {
			try {
				props.load(is);
			} catch (IOException | IllegalArgumentException ex) {
				ex.printStackTrace();
			}
		}

		return props;
	}

	class CloseAction extends AbstractAction {
		private static final long serialVersionUID = -2458760999790533135L;

		public CloseAction() {
			putValue(Action.NAME, "Close");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Bye!");
			System.exit(0);
		}

	}

	class OpenFileAction extends AbstractAction {
		private static final long serialVersionUID = -7305718383597147777L;

		public OpenFileAction() {
			putValue(Action.NAME, "Open XML File");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int returnValue = fileChooser.showOpenDialog(GuiApp.this);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				logger.info(String.format("Opening file: %1$s", file.getAbsolutePath()));
				if (file.exists()) {
					if (file.canRead()) {
						InputStream is = null;
						try {
							is = new FileInputStream(file);
							searchingP.loadXML(is);
						} catch (FileNotFoundException ex) {
							logger.error(String.format("FileNotFoundException during: new FileInputStream(\"%1$s\")",
									file.getAbsolutePath()));
						} finally {
							if (is != null) {
								try {
									is.close();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}
						}
					} else {
						logger.error(String.format("File %1$s could not be opened!", file.getAbsolutePath()));
					}
				} else {
					logger.error(String.format("File %1$s does not exist!", file.getAbsolutePath()));
				}
			}
		}

	}

	class OpenHtmlFileFilteringWithJSoupAction extends AbstractAction {
		private static final long serialVersionUID = -7305718383597147777L;

		public OpenHtmlFileFilteringWithJSoupAction() {
			putValue(Action.NAME, "Open Html File with JSoup");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int returnValue = fileChooser.showOpenDialog(GuiApp.this);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				logger.info(String.format("Opening file: %1$s", file.getAbsolutePath()));
				if (file.exists()) {
					if (file.canRead()) {
						InputStream is = null;
						/*
						 * cssElementsToRemove.add("div.footer1");
						 * cssElementsToRemove.add("div.footer2");
						 * cssElementsToRemove.add("div.footer3");
						 * cssElementsToRemove.add("div.footer4");
						 * //cssElementsToRemove.add("div.center");
						 * cssElementsToRemove.add("div.askCookies");
						 * cssElementsToRemove.add("div.hidden");
						 */

						try {
							is = new FileInputStream(file);
							searchingP.loadHTML(is);
						} catch (FileNotFoundException ex) {
							logger.error(String.format("FileNotFoundException during: new FileInputStream(\"%1$s\")",
									file.getAbsolutePath()));
						} finally {
							if (is != null) {
								try {
									is.close();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}
						}
					} else {
						logger.error(String.format("File %1$s could not be opened!", file.getAbsolutePath()));
					}
				} else {
					logger.error(String.format("File %1$s does not exist!", file.getAbsolutePath()));
				}
			}
		}

	}

	class SelectAction extends AbstractAction {
		private static final long serialVersionUID = -4031390293576294826L;

		public SelectAction() {
			putValue(Action.NAME, "Select");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectText();
		}
	}

	public void selectText() {
		searchingP.selectText();
	}

	public void stainAgain() {
		searchingP.stainAgain();
	}
}
