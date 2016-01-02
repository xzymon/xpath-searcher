package com.xzymon.xpath_searcher.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.gui.stain.XmlStylePalettesManager;

/**
 * Komponent dostarczający pełnej funkcjonalności dotyczącej wyszukiwania
 * wyrażeń XPath w załadowanych strumieniach. Wyświetla załadowany strumień oraz
 * wizualizuje wyszukiwanie, umożliwiając wprowadzanie wyrażeń do wyszukania.
 * @author Szymon Ignaciuk
 * @since 0.1.1.Final
 */
public class XPathSearchingPanel extends JPanel{
	private static final long serialVersionUID = -7003767119391060481L;

	private static final Logger logger = LoggerFactory.getLogger(XPathSearchingPanel.class.getName());

	private JPanel topPanel, centralPanel;
	private XPathSearcherDisplay searchPane;

	private JButton selectButton;
	private JButton searchButton;
	private JButton nextResultButton;
	private JButton resetButton;
	private JButton clearButton;

	private JTextField searchField;

	private final XmlStylePalettesManager palettesManager = new XmlStylePalettesManager();
	
	public XPathSearchingPanel() {
		initComponents();
	}

	public XPathSearchingPanel(Properties props) {
		loadStylesFromProperties(props);
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());

		topPanel = new JPanel(new GridLayout(2, 1));
		JLabel searchLabel = new JLabel("Phrase to search");
		searchField = new JTextField(50);
		searchButton = new JButton(new SearchAction());
		nextResultButton = new JButton(new NextResultAction());
		resetButton = new JButton(new ResetAction());
		clearButton = new JButton(new ClearAction());

		selectButton = new JButton(new SelectAction());

		JPanel topPanel1 = new JPanel(new GridBagLayout());
		JPanel topPanel2 = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		topPanel1.add(searchLabel, gbc);
		topPanel2.add(nextResultButton, gbc);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 1;
		topPanel1.add(searchField, gbc);
		topPanel2.add(resetButton, gbc);
		gbc.gridx = 2;
		topPanel1.add(searchButton, gbc);
		topPanel2.add(clearButton, gbc);
		gbc.gridx = 3;
		topPanel2.add(selectButton, gbc);

		topPanel.add(topPanel1);
		topPanel.add(topPanel2);

		centralPanel = new JPanel(new GridLayout(1, 1));
		searchPane = new XPathSearcherDisplay();
		searchPane.setPalette(palettesManager.getCurrentPalette());
		centralPanel.add(new JScrollPane(searchPane));

		this.add(topPanel, BorderLayout.NORTH);
		this.add(centralPanel, BorderLayout.CENTER);

	}

	public void loadXML(InputStream is) {
		searchPane.loadXMLStream(is);
	}

	public void loadHTML(InputStream is) {
		searchPane.loadHTMLStreamWithJSoup(is, null);
	}

	public void loadHTML(InputStream is, List<String> cssClassesToRemoveFromDoc) {
		searchPane.loadHTMLStreamWithJSoup(is, cssClassesToRemoveFromDoc);
	}

	public void loadStylesFromProperties(Properties props) {
		String strKey, strValue;
		boolean managerAdded = false;

		if (props.entrySet().isEmpty()) {
			logger.info("There are not any loaded properties.");
		} else {
			logger.info("Loaded properties: ");
			for (Object key : props.keySet()) {
				managerAdded = false;
				strKey = key.toString();
				strValue = props.get(key).toString();
				logger.info(String.format("Loaded property: %1$s=%2$s", strKey, strValue));
				palettesManager.overrideStyleByParameter(strKey, strValue);
				logger.debug("Manager : " + managerAdded);
			}
		}
	}

	class SearchAction extends AbstractAction {
		private static final long serialVersionUID = 1638709187169923458L;

		public SearchAction() {
			putValue(Action.NAME, "Search");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			if (!searchPane.isEmpty()) {
				try {
					searchPane.newSearch(phrase);
					searchPane.selectAllFoundNodes();
				} catch (XPathExpressionException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class NextResultAction extends AbstractAction {
		private static final long serialVersionUID = 5431085850101225464L;

		public NextResultAction() {
			putValue(Action.NAME, "Next Result");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			searchPane.nextNode();
		}
	}

	class ResetAction extends AbstractAction {
		private static final long serialVersionUID = 2735784609760788713L;

		public ResetAction() {
			putValue(Action.NAME, "Reset");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String phrase = searchField.getText();
			logger.info(String.format("Invoked for search phrase: %1$s", phrase));
			stainAgain();
			searchPane.reset();
		}
	}

	class ClearAction extends AbstractAction {
		private static final long serialVersionUID = -8592549398602437626L;

		public ClearAction() {
			putValue(Action.NAME, "Clear");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			searchField.setText("");
			stainAgain();
			searchPane.clear();
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
		searchPane.selectText();
	}

	public void stainAgain() {
		searchPane.stainAgain();
	}
}
