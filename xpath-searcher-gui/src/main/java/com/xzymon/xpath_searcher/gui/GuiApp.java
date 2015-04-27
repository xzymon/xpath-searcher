package com.xzymon.xpath_searcher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiApp extends JFrame{
	private static final long serialVersionUID = 3219548713775085362L;
	
	private static final Logger logger = LoggerFactory.getLogger("com.xzymon.xpath_searcher.gui.GuiApp");
	private static final String PROPS_PATH = "/xpath-searcher.properties";
	
	private JMenuBar menuBar;
	private JMenu mFile;
	private JPanel mainPanel, topPanel, centralPanel;
	private JTextPaneWrapper analysePane;

	private JButton searchButton;

	private JTextField searchField;
	
	private final JFileChooser fileChooser = new JFileChooser();

	public static void main(String[] args) {
		try{
			for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
				//System.out.println(info.getName());
				///*
				if(("Metal").equals(info.getName())){
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
				//*/
				/*
				if(("Nimbus").equals(info.getName())){
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
				*/
				/*
				if(("CDE/Motif").equals(info.getName())){
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
				*/
				/*
				if(("GTK+").equals(info.getName())){
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
				*/
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
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
	
	public GuiApp(){
		Properties props = loadProperties();
		
		initComponents();
	}

	private void initComponents() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		mFile = new JMenu("File");
		mFile.add(new OpenFileAction());
		mFile.add(new StainTextAction());
		mFile.addSeparator();
		mFile.add(new CloseAction());
		menuBar.add(mFile);
		this.setJMenuBar(menuBar);
		
		mainPanel = new JPanel(new BorderLayout());
		topPanel = new JPanel(new GridBagLayout());
		centralPanel = new JPanel(new GridLayout(1,1));
		
		
		JLabel searchLabel = new JLabel("Phrase to search");
		searchField = new JTextField(30);
		searchButton = new JButton(new SearchAction());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		topPanel.add(searchLabel, gbc);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 1;
		topPanel.add(searchField, gbc);
		gbc.gridx = 2;
		topPanel.add(searchButton, gbc);
		
		analysePane = new JTextPaneWrapper(new DefaultStyledDocument());
		analysePane.setLineWrap(false);
		analysePane.setAutoscrolls(true);
		centralPanel.add(new JScrollPane(analysePane));
		
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centralPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		
		pack();
	}

	private Properties loadProperties(){
		Properties props = new Properties();
		
		InputStream is = this.getClass().getResourceAsStream(PROPS_PATH);
		if(is!=null){
			try{
				props.load(is);
			} catch (IOException | IllegalArgumentException ex){
				ex.printStackTrace();
			}
		}
		
		if(props.entrySet().isEmpty()){
			logger.info("There are not any loaded properties.");
		}
		else {
			logger.info("Loaded properties: ");
			for(Object key: props.keySet()){
				logger.info(String.format("Loaded property: %1$s=%2$s", key, props.get(key)));
			}
		}
		
		return props;
	}
	
	class CloseAction extends AbstractAction{

		public CloseAction(){
			putValue(Action.NAME, "Close");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Bye!");
			System.exit(0);
		}
		
	}
	
	class SearchAction extends AbstractAction{

		public SearchAction(){
			putValue(Action.NAME, "Search");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info(String.format("Invoked for search phrase: %1$s", searchField.getText()));
		}
		
	}
	
	class OpenFileAction extends AbstractAction{

		public OpenFileAction(){
			putValue(Action.NAME, "Open File");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int returnValue = fileChooser.showOpenDialog(GuiApp.this);
			
			if(returnValue == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				logger.info(String.format("Opening file: %1$s", file.getAbsolutePath()));
				if(file.exists()){
					if(file.canRead()){
						InputStream is = null;
						int avail = 0;
						byte[] bytes = null;
						String str = null;
						try{
							is = new FileInputStream(file);
							avail = is.available();
							if(avail>0){
								bytes = new byte[avail];
								is.read(bytes);
								str = new String(bytes);
								analysePane.setText(str);
							}
						} catch (FileNotFoundException ex) {
							logger.error(String.format("FileNotFoundException during: new FileInputStream(\"%1$s\")", file.getAbsolutePath()));
						} catch (IOException ex) {
							//logger.error(String.format("IOException during: new FileInputStream(\"%1$s\")", file.getAbsolutePath()));
							ex.printStackTrace();
						} finally {
							if(is!=null){
								try{
									is.close();
								} catch(IOException ex){
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
	
	class StainTextAction extends AbstractAction {

		public StainTextAction() {
			putValue(Action.NAME, "Stain Text");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			StyledDocument sdoc = analysePane.getStyledDocument();
			
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setForeground(sas, Color.decode("0xFF0000"));
			StyleConstants.setBold(sas, true);
			
			int length = analysePane.getDocument().getLength();
			try {
				sdoc.insertString(length, "Mayahee", sas);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
			//sdoc.setCharacterAttributes(0, 10, sas, false);
		}
		
	}
}
