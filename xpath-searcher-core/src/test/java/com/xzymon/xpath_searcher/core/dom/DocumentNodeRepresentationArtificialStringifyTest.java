package com.xzymon.xpath_searcher.core.dom;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.XmlPreprocessingMode;

public class DocumentNodeRepresentationArtificialStringifyTest {
	private static Logger logger = LoggerFactory.getLogger(DocumentNodeRepresentationArtificialStringifyTest.class);

	DocumentTreeRepresentation tree;
	OrphanedPolicies defaultOrphanedPolicies, defaultRemovedOrphanedPolicies, defaultSelfOrphanedPolicies,
			selfRemovedOrphanedPolicies, defaultNextSameOrphanedPolicies, nextSameRemovedOrphanedPolicies;
	OrphanedElementsPolicy defaultPolicy;

	byte[] savedStream;
	String input, expected, actual;

	public DocumentNodeRepresentationArtificialStringifyTest() {
		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.DEFAULT, OrphanedClosingTagMode.DEFAULT);
		defaultOrphanedPolicies = new OrphanedPolicies(defaultPolicy);
		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.DEFAULT, OrphanedClosingTagMode.REMOVE);
		defaultRemovedOrphanedPolicies = new OrphanedPolicies(defaultPolicy);

		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.SELF_CLOSED, OrphanedClosingTagMode.DEFAULT);
		defaultSelfOrphanedPolicies = new OrphanedPolicies(defaultPolicy);
		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.SELF_CLOSED, OrphanedClosingTagMode.REMOVE);
		selfRemovedOrphanedPolicies = new OrphanedPolicies(defaultPolicy);

		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.CLOSE_ON_NEXT_SAME_SPECIES,
				OrphanedClosingTagMode.DEFAULT);
		defaultNextSameOrphanedPolicies = new OrphanedPolicies(defaultPolicy);
		defaultPolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.CLOSE_ON_NEXT_SAME_SPECIES,
				OrphanedClosingTagMode.REMOVE);
		nextSameRemovedOrphanedPolicies = new OrphanedPolicies(defaultPolicy);
	}

	@Test
	public void noTree() {
		input = "";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "          ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html>";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <!DOCTYPE html>";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <!DOCTYPE html> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <!DOCTYPE html> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <!DOCTYPE html>      ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <!DOCTYPE html>      ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>      ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <?xml version=\"1.0\" encoding=\"UTF-8\"?>      ";
		expected = null;
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void oneNodeEmptyTree() {
		input = "<root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void oneNodeEmptyTreeWithRawTags() {
		/*
		 * samozamykający się rootNode
		 */
		input = " <root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root/> ";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <root/> ";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <root/> ";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <root/>      ";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <root/>      ";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * zamknięty pusty rootNode
		 */

		input = " <root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root></root> ";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <root></root> ";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <root></root> ";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = " <root></root>      ";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "      <root></root>      ";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

	}

	@Test
	public void oneNodeEmptyTreeWithComments() {
		input = "<!----><root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><!----><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><!----><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * zamknięty pusty rootNode
		 */

		input = "<!----><root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><!----><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!----><!----><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

	}

	@Test
	public void oneNodeEmptyHtmlLikeTreeWithComments() {
		input = "<!DOCTYPE html><root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><!----><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><!----><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * zamknięty pusty rootNode
		 */

		input = "<!DOCTYPE html><root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><!----><root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><!----><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<!DOCTYPE html><!----><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void oneNodeEmptyXmlLikeTreeWithComments() {
		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root/>";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root/><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root/><!----><!---->";
		expected = "<root/>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * zamknięty pusty rootNode
		 */

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root></root>";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root></root><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!----><root></root><!----><!---->";
		expected = "<root></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void oneNodeTreeWithNamelessTags() {
		/*
		 * raw tags
		 */
		input = "<root> </root>";
		expected = "<root> </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root>  </root>";
		expected = "<root>  </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root>   </root>";
		expected = "<root>   </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * comment tags
		 */

		input = "<root><!----></root>";
		expected = "<root><!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><!----><!----></root>";
		expected = "<root><!----><!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><!----><!----><!----></root>";
		expected = "<root><!----><!----><!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * mixed raw & comment tags - two tags
		 */

		input = "<root> <!----></root>";
		expected = "<root> <!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><!----> </root>";
		expected = "<root><!----> </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * mixed raw & comment tags - three tags
		 */

		input = "<root> <!----> </root>";
		expected = "<root> <!----> </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root> <!----><!----></root>";
		expected = "<root> <!----><!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><!---->  </root>";
		expected = "<root><!---->  </root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><!----> <!----></root>";
		expected = "<root><!----> <!----></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void treeWithEmptyNamedTags() {
		/*
		 * same samozamykające się tagi
		 */
		input = "<root><node1/></root>";
		expected = "<root><node1/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1/><node2/></root>";
		expected = "<root><node1/><node2/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1/><node2/><node3/></root>";
		expected = "<root><node1/><node2/><node3/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * same puste zamykane tagi
		 */

		input = "<root><node1></node1></root>";
		expected = "<root><node1></node1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1></node1><node2></node2></root>";
		expected = "<root><node1></node1><node2></node2></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1></node1><node2></node2><node3></node3></root>";
		expected = "<root><node1></node1><node2></node2><node3></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * mieszane - dwa
		 */
		input = "<root><node1/><node2></node2></root>";
		expected = "<root><node1/><node2></node2></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1></node1><node2/></root>";
		expected = "<root><node1></node1><node2/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * mieszane - trzy
		 */
		input = "<root><node1/><node2></node2><node3/></root>";
		expected = "<root><node1/><node2></node2><node3/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1></node1><node2/><node3></node3></root>";
		expected = "<root><node1></node1><node2/><node3></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1/><node2></node2><node3/></root>";
		expected = "<root><node1/><node2></node2><node3/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1></node1><node2/><node3></node3></root>";
		expected = "<root><node1></node1><node2/><node3></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	 @Test
	public void treeWithNestedNamedTags() {
		/*
		 * mieszane - dwa
		 */
		input = "<root><node1/><node2><node2a/></node2></root>";
		expected = "<root><node1/><node2><node2a/></node2></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1/><node2><node2a></node2a></node2></root>";
		expected = "<root><node1/><node2><node2a></node2a></node2></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a/></node1><node2/></root>";
		expected = "<root><node1><node1a/></node1><node2/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a></node1a></node1><node2/></root>";
		expected = "<root><node1><node1a></node1a></node1><node2/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * mieszane - trzy
		 */
		input = "<root><node1/><node2><node2a/></node2><node3/></root>";
		expected = "<root><node1/><node2><node2a/></node2><node3/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1/><node2><node2a></node2a></node2><node3/></root>";
		expected = "<root><node1/><node2><node2a></node2a></node2><node3/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a/></node1><node2/><node3><node3a/></node3></root>";
		expected = "<root><node1><node1a/></node1><node2/><node3><node3a/></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a></node1a></node1><node2/><node3><node3a/></node3></root>";
		expected = "<root><node1><node1a></node1a></node1><node2/><node3><node3a/></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a/></node1><node2/><node3><node3a></node3a></node3></root>";
		expected = "<root><node1><node1a/></node1><node2/><node3><node3a></node3a></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><node1><node1a></node1a></node1><node2/><node3><node3a></node3a></node3></root>";
		expected = "<root><node1><node1a></node1a></node1><node2/><node3><node3a></node3a></node3></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

	}

	 @Test
	public void fixingOneFirstLevelSkippedOpeningTagDefaultPolicy() {
		input = "<root><ignored></root>";
		expected = "<root><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok/></root>";
		expected = "<root><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok></ok></root>";
		expected = "<root><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored></root>";
		expected = "<root><ok/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored></root>";
		expected = "<root><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * 2+ braci
		 */
		input = "<root><ok/><ignored><ok/></root>";
		expected = "<root><ok/><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored><ok></ok></root>";
		expected = "<root><ok/><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok/></root>";
		expected = "<root><ok></ok><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok></ok></root>";
		expected = "<root><ok></ok><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	 @Test
	public void fixingOneFirstLevelSkippedOpeningTagSelfPolicy() {
		input = "<root><ignored></root>";
		expected = "<root><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok/></root>";
		expected = "<root><ignored/><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok></ok></root>";
		expected = "<root><ignored/><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored></root>";
		expected = "<root><ok/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored></root>";
		expected = "<root><ok></ok><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * 2+ braci
		 */
		input = "<root><ok/><ignored><ok/></root>";
		expected = "<root><ok/><ignored/><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored><ok></ok></root>";
		expected = "<root><ok/><ignored/><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok/></root>";
		expected = "<root><ok></ok><ignored/><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok></ok></root>";
		expected = "<root><ok></ok><ignored/><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingOneFirstLevelSkippedOpeningTagNextSamePolicy() {
		input = "<root><ignored></root>";
		expected = "<root><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok/></root>";
		expected = "<root><ignored><ok/></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ok></ok></root>";
		expected = "<root><ignored><ok></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored></root>";
		expected = "<root><ok/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored></root>";
		expected = "<root><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * 2+ braci
		 */
		input = "<root><ok/><ignored><ok/></root>";
		expected = "<root><ok/><ignored><ok/></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok/><ignored><ok></ok></root>";
		expected = "<root><ok/><ignored><ok></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok/></root>";
		expected = "<root><ok></ok><ignored><ok/></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok></ok><ignored><ok></ok></root>";
		expected = "<root><ok></ok><ignored><ok></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyFirstLevelSkippedOpeningTagsDefaultPolicy() {
		input = "<root><ignored><ignored></root>";
		expected = "<root><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ignored><ignored></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root><ok/><ignored><ignored></root>";
		expected = "<root><ok/><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok/><ignored><ignored></ignored></root>";
		expected = "<root><ok/><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></root>";
		expected = "<root><ignored></ignored><ok/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok/></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok/></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root><ok></ok><ignored><ignored></root>";
		expected = "<root><ok></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ok><ignored><ignored></ignored></root>";
		expected = "<root><ok></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></root>";
		expected = "<root><ignored></ignored><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyFirstLevelSkippedOpeningTagsSelfPolicy() {
		input = "<root><ignored><ignored></root>";
		expected = "<root><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ignored><ignored></root>";
		expected = "<root><ignored/><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * 
		 */
		input = "<root><ok/><ignored><ignored></root>";
		expected = "<root><ok/><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok/><ignored><ignored></ignored></root>";
		expected = "<root><ok/><ignored/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></root>";
		expected = "<root><ignored/><ok/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></ignored></root>";
		expected = "<root><ignored/><ok/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok/></root>";
		expected = "<root><ignored/><ignored/><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok/></root>";
		expected = "<root><ignored/><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root><ok></ok><ignored><ignored></root>";
		expected = "<root><ok></ok><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ok><ignored><ignored></ignored></root>";
		expected = "<root><ok></ok><ignored/><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></root>";
		expected = "<root><ignored/><ok></ok><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></ignored></root>";
		expected = "<root><ignored/><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok></ok></root>";
		expected = "<root><ignored/><ignored/><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok></ok></root>";
		expected = "<root><ignored/><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyFirstLevelSkippedOpeningTagsNextSamePolicy() {
		input = "<root><ignored><ignored></root>";
		expected = "<root><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ignored><ignored><ignored></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		/*
		 * 
		 */
		input = "<root><ok/><ignored><ignored></root>";
		expected = "<root><ok/><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok/><ignored><ignored></ignored></root>";
		expected = "<root><ok/><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></root>";
		expected = "<root><ignored><ok/></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok/><ignored></ignored></root>";
		expected = "<root><ignored><ok/></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok/></root>";
		expected = "<root><ignored></ignored><ignored><ok/></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok/></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root><ok></ok><ignored><ignored></root>";
		expected = "<root><ok></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ok><ignored><ignored></ignored></root>";
		expected = "<root><ok></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></root>";
		expected = "<root><ignored><ok></ok></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok></ok><ignored></ignored></root>";
		expected = "<root><ignored><ok></ok></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok></ok></root>";
		expected = "<root><ignored></ignored><ignored><ok></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyFirstLevelOneNestedSkippedOpeningTagsDefaultPolicy() {
		input = "<root><ok><ignored></ok><ignored><ignored></root>";
		expected = "<root><ok><ignored></ignored></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ignored></ok></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok><ignored></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}
	
	@Test
	public void fixingManyFirstLevelOneNestedSkippedOpeningTagsSelfPolicy() {
		input = "<root><ok><ignored></ok><ignored><ignored></root>";
		expected = "<root><ok><ignored/></ok><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored/><ok><ignored/></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ignored></ok></root>";
		expected = "<root><ignored/><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored/><ok><ignored/></ok><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored/><ok><ignored/></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored/></ok><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored/></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored/><ignored/><ok><ignored/></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok><ignored></ok></root>";
		expected = "<root><ignored/><ignored></ignored><ok><ignored/></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}
	
	@Test
	public void fixingManyFirstLevelOneNestedSkippedOpeningTagsNextSamePolicy() {
		input = "<root><ok><ignored></ok><ignored><ignored></root>";
		expected = "<root><ok><ignored></ignored></ok><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored><ok><ignored></ignored></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ignored></ok></root>";
		expected = "<root><ignored><ok><ignored></ignored></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored><ok><ignored></ignored></ok></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored><ok><ignored></ignored></ok></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored></ignored><ok><ignored></ok><ignored></ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored><ok><ignored></ok></root>";
		expected = "<root><ignored></ignored><ignored><ok><ignored></ignored></ok></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ignored></ignored><ok><ignored></ok></root>";
		expected = "<root><ignored></ignored><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyNestedSkippedOpeningTagsDefaultPolicy() {
		input = "<root><ok1><ok2></ok2><ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2></ok2><ignored/></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2><ignored></ignored><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored/></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2><ignored></ignored><ignored/></ok2><ignored/></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ignored></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2><ignored></ignored><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyNestedSkippedOpeningTagsSelfPolicy() {
		input = "<root><ok1><ok2></ok2><ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored/><ignored/></ok1><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored/><ignored></ignored></ok1><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2></ok2><ignored/></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2></ok2><ignored/></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2></ok2><ignored></ignored></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2><ignored/><ignored/></ok2><ignored/></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored/></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2><ignored/><ignored/></ok2><ignored/></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ignored></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored/><ok2><ignored/><ignored></ignored></ok2><ignored></ignored></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingManyNestedSkippedOpeningTagsNextSamePolicy() {
		input = "<root><ok1><ok2></ok2><ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2><ignored><ignored></ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2></ok2></ignored><ignored></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2></ok2></ignored><ignored/></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2></ok2></ignored><ignored></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2><ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored><ignored></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored/></ok2><ignored/></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2><ignored></ignored><ignored/></ok2></ignored><ignored/></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ignored></ok2><ignored></ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored><ignored></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}
	
	@Test
	public void fixingSkippedClosingTagsDefaultPolicy() {
		input = "<root><ok></ok></ignored></root>";
		expected = "<root><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ignored></ok></root>";
		expected = "<root><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ignored></ok></ignored></root>";
		expected = "<root><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ok></ignored></root>";
		expected = "<root><ignored></ignored><ok></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ignored></ok></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ignored></ok></ignored></root>";
		expected = "<root><ignored></ignored><ok><ignored></ignored></ok><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ok1><ok2></ok2></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2></ignored></ok1></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ignored></ok2></ok1></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1></ignored><ok2></ok2></ok1></root>";
		expected = "<root><ok1><ignored></ignored><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1></ignored><ok2></ignored></ok2></ok1></root>";
		expected = "<root><ok1><ignored></ignored><ok2><ignored></ignored></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok1></ignored><ok2></ignored></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ok1><ignored></ignored><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok1></ignored><ok2></ignored></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored></ignored><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}
	
	@Test
	public void fixingSkippedClosingTagsRemovedPolicy() {
		input = "<root><ok></ok></ignored></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ignored></ok></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok></ignored></ok></ignored></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ok></ignored></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ignored></ok></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok></ignored></ok></ignored></root>";
		expected = "<root><ok></ok></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ok1><ok2></ok2></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2></ignored></ok1></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2></ignored></ok2></ok1></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1></ignored><ok2></ok2></ok1></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1></ignored><ok2></ignored></ok2></ok1></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);

		input = "<root><ok1></ignored><ok2></ignored></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ok1></ignored><ok2></ignored></ok2></ignored></ok1></ignored></root>";
		expected = "<root><ok1><ok2></ok2></ok1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsDefaultPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored></ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored></ignored><ignored></ignored><ok2><ignored></ignored></ok2></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored></ignored><ignored></ignored><ok2><ignored></ignored></ok2><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsSelfDefaultPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored/></ok2><ignored></ignored><ignored/></ok1><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored/></ok2><ignored></ignored><ignored/></ok1><ignored></ignored><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored><ok2><ignored/><ignored/></ok2></ignored></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored/><ignored/><ok2><ignored/></ok2></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored/><ignored/><ok2><ignored/></ok2><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultSelfOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsNextSameDefaultPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored></ignored><ignored><ok2><ignored></ignored></ok2></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored></ignored><ignored><ok1><ignored></ignored><ignored><ok2><ignored></ignored></ok2></ignored><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultNextSameOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsDefaultRemovedPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored></ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored></ignored><ignored></ignored><ok2><ignored></ignored></ok2></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored></ignored><ignored></ignored><ok2><ignored></ignored></ok2><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsSelfRemovedPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored/></ok2><ignored/></ok1><ignored/><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				selfRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored/></ok2><ignored/></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				selfRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored/><ok1><ignored><ok2><ignored/><ignored/></ok2></ignored></ok1><ignored/></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				selfRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored/><ignored/><ok2><ignored/></ok2></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				selfRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored/><ignored/><ok2><ignored/></ok2><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				selfRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingWrongParalellyOpenedAndClosedTagsNextSameRemovedPolicy(){
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		expected = "<root><ok1><ok2><ignored></ignored></ok2><ignored></ignored></ok1><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		/*
		 * 
		 */
		input = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		expected = "<root><ignored><ok1><ignored><ok2><ignored></ignored><ignored></ignored></ok2></ignored></ok1></ignored><ignored></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		/*
		 * 
		 */
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored></ignored><ignored><ok2><ignored></ignored></ok2></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";
		expected = "<root><ignored><ok1><ignored></ignored><ignored><ok2><ignored></ignored></ok2></ignored><ignored></ignored></ok1></ignored></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	@Test
	public void fixingOpeningTagsToBeNestedNextSameRemovedPolicy() {
		input = "<root><ignored1><ignored2></root>";
		expected = "<root><ignored1><ignored2></ignored2></ignored1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored1><ignored2><ok></ok></root>";
		expected = "<root><ignored1><ignored2><ok></ok></ignored2></ignored1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored1><ignored2><ignored3></root>";
		expected = "<root><ignored1><ignored2><ignored3></ignored3></ignored2></ignored1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<root><ignored1><ignored2><ignored3><ok></ok></root>";
		expected = "<root><ignored1><ignored2><ignored3><ok></ok></ignored3></ignored2></ignored1></root>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				nextSameRemovedOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	public void checkTest() {
		input = "";
		expected = "";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	public void hibernatedCode() {
		//błędne tagi zamykające
		
		
		String i10 = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1><ignored><ignored></root>";
		String i11 = "<root><ok1><ok2><ignored></ok2></ignored><ignored></ok1></ignored><ignored></root>";
		
		String i20 = "<root><ignored><ok1><ignored><ok2><ignored><ignored></ok2></ignored></ok1><ignored></root>";
		String i21 = "<root><ignored><ok1></ignored><ignored><ok2></ignored><ignored></ok2></ignored></ok1><ignored></root>";
		
		String i30 = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2></ok1></ignored></root>";
		String i31 = "<root></ignored><ignored><ok1><ignored><ignored><ok2><ignored></ok2><ignored></ignored></ok1></ignored></root>";

		String j10 = "<root><ignored1><ignored2></root>";
		String j11 = "<root><ignored1><ignored2><ok></ok></root>";
		String j20 = "<root><ignored1><ignored2><ignored3></root>";
		String j21 = "<root><ignored1><ignored2><ignored3><ok></ok></root>";
		
		input = "";
		expected = "";

		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				defaultOrphanedPolicies);
		actual = tree.stringifyTree();

		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

}
