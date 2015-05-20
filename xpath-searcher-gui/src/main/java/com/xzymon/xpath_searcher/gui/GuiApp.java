package com.xzymon.xpath_searcher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.dom.SlicedNode;
import com.xzymon.xpath_searcher.core.parsing.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parsing.SliceRepresentation;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalettesManager;

public class GuiApp extends JFrame{
	private static final long serialVersionUID = 3219548713775085362L;
	
	private static final Logger logger = LoggerFactory.getLogger("com.xzymon.xpath_searcher.gui.GuiApp");
	private static final String PROPS_PATH = "/xpath-searcher.properties";
	
	private JMenuBar menuBar;
	private JMenu mFile;
	private JPanel mainPanel, topPanel, centralPanel;
	private JTextPaneWrapper analysePane;

	//private SelectAction selectAction;
	//private SearchAction searchAction;
	//private NextResultAction nextResultAction;
	//private ResetAction resetAction;
	//private ClearAction clearAction;
	
	private JButton selectButton;
	private JButton searchButton;
	private JButton nextResultButton;
	private JButton resetButton;
	private JButton clearButton;

	private JTextField searchField;
	
	private final JFileChooser fileChooser = new JFileChooser();
	private final XmlStylePalettesManager palettesManager = new XmlStylePalettesManager();
	
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
		//mFile.add(new OpenFileAction());
		mFile.add(new OpenFileWithJSoupAction());
		mFile.addSeparator();
		mFile.add(new CloseAction());
		menuBar.add(mFile);
		this.setJMenuBar(menuBar);
		
		mainPanel = new JPanel(new BorderLayout());
		topPanel = new JPanel(new GridBagLayout());
		centralPanel = new JPanel(new GridLayout(1,1));
		
		
		JLabel searchLabel = new JLabel("Phrase to search");
		searchField = new JTextField(50);
		searchButton = new JButton(new SearchAction());
		nextResultButton = new JButton(new NextResultAction());
		resetButton = new JButton(new  ResetAction());
		clearButton = new JButton(new ClearAction());
		
		selectButton = new JButton(new SelectAction());
		
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
		gbc.gridy = 1;
		gbc.gridx = 0;
		topPanel.add(nextResultButton, gbc);
		gbc.gridx = 1;
		topPanel.add(resetButton, gbc);
		gbc.gridx = 2;
		topPanel.add(clearButton, gbc);
		gbc.gridy = 2;
		gbc.gridx = 0;
		topPanel.add(selectButton, gbc);
		
		analysePane = new JTextPaneWrapper();
		analysePane.setPalette(palettesManager.getCurrentPalette());
		centralPanel.add(new JScrollPane(analysePane));
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centralPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		
		pack();
	}

	private Properties loadProperties(){
		Properties props = new Properties();
		String strKey, strValue;
		boolean managerAdded = false;
		
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
				managerAdded = false;
				strKey = key.toString();
				strValue = props.get(key).toString();
				logger.info(String.format("Loaded property: %1$s=%2$s", strKey, strValue));
				palettesManager.overrideStyleByParameter(strKey, strValue);
				logger.debug("Manager : " + managerAdded);
			}
		}
		
		return props;
	}
	
	class CloseAction extends AbstractAction{
		private static final long serialVersionUID = -2458760999790533135L;

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
		private static final long serialVersionUID = 1638709187169923458L;

		public SearchAction(){
			putValue(Action.NAME, "Search");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			//if(engine!=null){
			if(!analysePane.isEmpty()){
				try {
					//NodeList list = engine.findNodes(phrase);
					analysePane.newSearch(phrase);
					analysePane.selectAllFoundNodes();
				} catch (XPathExpressionException e1) {
					e1.printStackTrace();
				}
			}
		}		
	}
	
	
	class NextResultAction extends AbstractAction{
		private static final long serialVersionUID = 5431085850101225464L;

		public NextResultAction(){
			putValue(Action.NAME, "Next Result");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			analysePane.nextNode();
		}		
	}
	
	class ResetAction extends AbstractAction{
		private static final long serialVersionUID = 2735784609760788713L;

		public ResetAction(){
			putValue(Action.NAME, "Reset");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			analysePane.reset();
		}		
	}
	
	class ClearAction extends AbstractAction {
		private static final long serialVersionUID = -8592549398602437626L;

		public ClearAction(){
			putValue(Action.NAME, "Clear");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			searchField.setText("");
			stainAgain();
			analysePane.clear();
		}
	}
	
	class OpenFileAction extends AbstractAction{
		private static final long serialVersionUID = -7305718383597147777L;

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
						try{
							is = new FileInputStream(file);
							analysePane.loadStream(is);
							//engine = new XPathProcessor(new String(analysePane.getSlicer().getSavedChars()));
							//searchAction.setEngine(engine);
							//bindElementsToText();
							//bindAttributesToText();
						} catch (FileNotFoundException ex) {
							logger.error(String.format("FileNotFoundException during: new FileInputStream(\"%1$s\")", file.getAbsolutePath()));
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
	
	class OpenFileWithJSoupAction extends AbstractAction{
		private static final long serialVersionUID = -7305718383597147777L;

		public OpenFileWithJSoupAction(){
			putValue(Action.NAME, "Open File with JSoup");
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
						InputStream preparedIs = null;
						List<String> cssElementsToRemove = new ArrayList<String>();
						cssElementsToRemove.add("head");
						cssElementsToRemove.add("img");
						cssElementsToRemove.add("input");
						cssElementsToRemove.add("br");
						cssElementsToRemove.add("area");
						cssElementsToRemove.add("button");
						cssElementsToRemove.add("script");
						
						cssElementsToRemove.add("div.footer1");
						cssElementsToRemove.add("div.footer2");
						cssElementsToRemove.add("div.footer3");
						cssElementsToRemove.add("div.footer4");
						//cssElementsToRemove.add("div.center");
						cssElementsToRemove.add("div.askCookies");
						cssElementsToRemove.add("div.hidden");
						
						try{
							is = new FileInputStream(file);
							preparedIs = prepareHtmlWithJsoup(is, cssElementsToRemove);
							analysePane.loadStream(preparedIs);
						} catch (FileNotFoundException ex) {
							logger.error(String.format("FileNotFoundException during: new FileInputStream(\"%1$s\")", file.getAbsolutePath()));
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							if(is!=null){
								try{
									is.close();
								} catch(IOException ex){
									ex.printStackTrace();
								}
							}
							if(preparedIs!=null){
								try{
									preparedIs.close();
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
	
	private InputStream prepareHtmlWithJsoup(InputStream srcStream, List<String> elementTypesToRemove) throws IOException{
		InputStream resultStream = null;
		int avail = srcStream.available();
		if(avail>0){
			byte[] bytes = new byte[avail];
			srcStream.read(bytes);
			String strSource = new String(bytes);
			logger.info(String.format("parsing by Jsoup..."));
			org.jsoup.nodes.Document parsedDoc = Jsoup.parse(strSource);
			for(String typeName : elementTypesToRemove){
				Elements els = parsedDoc.select(typeName);
				for(Element el: els){
					el.remove();
				}
			}
			String fromHtml = parsedDoc.html();
			String corrected = fromHtml.replaceAll("&nbsp;", "&#160;");
			logger.info(String.format("html retrieved from Jsoup parsed document"));
			resultStream = new ByteArrayInputStream(corrected.getBytes());
		}
		return resultStream;
	}
	
	class SelectAction extends AbstractAction {
		private static final long serialVersionUID = -4031390293576294826L;

		public SelectAction(){
			putValue(Action.NAME, "Select");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectText();
		}
	}
	
	public void selectText(){
		analysePane.selectText();
	}
	
	public void stainAgain(){
		analysePane.stainAgain();
	}
}
