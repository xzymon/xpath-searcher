package com.xzymon.xpath_searcher.core.dom;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Stack;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.XmlPreprocessingMode;
import com.xzymon.xpath_searcher.core.exception.ParserException;
import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;
import com.xzymon.xpath_searcher.core.parser.HalfElementsParser;

public class DocumentTreeRepresentation {
	private static final Logger logger = LoggerFactory.getLogger(DocumentTreeRepresentation.class);

	private DocumentNodeRepresentation rootNode;
	// private HalfElementsParser heParser;

	private char[] savedSource;
	
	/**
	 * 
	 * @param is
	 * @param mode - na razie nie ma żadnego wpływu na przetwarzanie (być może zostanie usunięte)
	 * @param orphanedPolicies
	 */
	public DocumentTreeRepresentation(InputStream is, XmlPreprocessingMode mode, OrphanedPolicies orphanedPolicies) {
		DocumentTreeRepresentation doc = null;

		HalfElementsParser heParser = null;

		OrphanedElementsPolicy policy;
		
		char[] chars = null;

		List<HalfElementRepresentation> parserProducedTagList = null;
		
		Integer logLength;
		
		try {
			heParser = new HalfElementsParser(is);
			chars = heParser.getSavedChars();
			savedSource = chars;
			parserProducedTagList = heParser.getHalfElementsList();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(parserProducedTagList!=null && !parserProducedTagList.isEmpty()){
			Map<String, TagLocations> tagLocationsMap = new HashMap<String, TagLocations>();
			TagLocations locs = null;
			HalfElementRepresentation tag = null;
			String tagName = null;
			// przygotowanie najważniejszych elementów algorytmu - tagList i
			// tagStateList
			
			//parser produkuje LinkedList - ale tu wydajniej jest posługiwać się ArrayList
			// - bo daje swobodny dostęp do elementów asymptotycznie w czasie O(1)
			List<HalfElementRepresentation> herList = new ArrayList<HalfElementRepresentation>(parserProducedTagList);
			List<HalfElementRepresentation> tagList = Collections.unmodifiableList(herList);

			int tagsCount = tagList.size();
			List<TagState> tagStateList = new ArrayList<TagState>(tagsCount);
			for (int createLoop = 0; createLoop < tagsCount; createLoop++) {
				tagStateList.add(new TagState());
				tag = tagList.get(createLoop);
				if (!tag.isOther() && !tag.isRaw()) {
					tagName = tag.getTagName(heParser.getSavedChars());
					locs = tagLocationsMap.get(tagName);
					if (locs == null) {
						locs = new TagLocations(tagName);
						tagLocationsMap.put(tagName, locs);
					}
					if (tag.isOpening()) {
						locs.addOpening(createLoop);
					}
					if (tag.isSelfClosing()) {
						locs.addOpening(createLoop);
					}
					if (tag.isClosing()) {
						locs.addClosing(createLoop);
					}
				}
			}

			// pokazanie statystyk
			for (Entry<String, TagLocations> ent : tagLocationsMap.entrySet()) {
				tagName = ent.getKey();
				locs = ent.getValue();
				logger.info("tag <" + tagName + "> : opening=" + locs.getOpening().size() + ", closing="
						+ locs.getSelfClosing().size() + ", self-closing=" + locs.getClosing());
			}
			// algorytm bugujący strukturę obiektową
			if (tagsCount > 0) {
				Integer revId;
				String curTagName, revName, treeNodeName;
				HalfElementRepresentation curTag, revTag;
				TagState curTagState, revTagState;
				DocumentNodeRepresentation curNode = null, parentNode = null, treeNode, revNode;
				//TODO: Deque'i mają gromadzić węzły DocumentNodeRepresentation a nie tagi
				Stack<DocumentNodeRepresentation> treeNodeStack = new Stack<DocumentNodeRepresentation>();
				Stack<DocumentNodeRepresentation> revNodeStack = new Stack<DocumentNodeRepresentation>();
				// openTags - rejestruje wszystkie otwarte tagi, które o ile
				// zostaną
				// prawidłowo zamknięte to utworzą prawidłowe węzły
				// - zakładam że nie wszystkie muszą zostać prawidłowo zamknięte
				//TODO: może zmienić nazwę z openTags na openNodes ?
				CountingSet<String> openTags = new CountingSet<String>();
				// znajdź najpierw korzeń drzewa - najpewniej jest to pierszy
				// otwierający tag
				boolean knownRoot = false;
				int pLoop = 0;
				while (pLoop < tagsCount && !knownRoot) {
					curTag = tagList.get(pLoop);
					curTagState = tagStateList.get(pLoop);
					if (curTag.isOpening()) {
						rootNode = DocumentNodeRepresentation.createOpeningNode(curTag, null, curTag.getTagName(chars),
								pLoop);
						tagStateList.get(pLoop).setNodeBindingThisTag(rootNode);
						treeNodeStack.push(rootNode);
						openTags.put(rootNode.getName());
						knownRoot = true;
					}
					pLoop++;
				}
				if(rootNode!=null){
					// w założeniu - kolejne węzły są dziećmi
					parentNode = rootNode;
					// korzeń jest znany - teraz normalne przetwarzanie - od
					// kolejnego węzła
					for (; pLoop < tagsCount; pLoop++) {
						logger.info("ploop = " + pLoop);
						curTag = tagList.get(pLoop);
						curTagState = tagStateList.get(pLoop);
						if (curTag.isOther() || curTag.isRaw()) {
							curNode = DocumentNodeRepresentation.createNamelessNode(curTag, parentNode, null, pLoop);
							logLength = curTag.getEndPosition() - curTag.getStartPosition() + 1;
							logger.info("adding nameless node to treeNodeStack - length=" + logLength );
							treeNodeStack.add(curNode);
							curTagState.setNodeBindingThisTag(curNode);
							curTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.BUILT_IN);
						} else {
							if (curTag.isOpening()) {
								curNode = DocumentNodeRepresentation.createOpeningNode(curTag, parentNode,
										curTag.getTagName(chars), pLoop);
								treeNodeStack.add(curNode);
								parentNode = curNode;
								curTagState.setNodeBindingThisTag(curNode);
								curTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.BUILT_IN);
								// jeżeli tag sam siebie zamyka to nie jest śledzony
								// przez openTags
								// ponieważ nie ma wpływu na strukturę drzewa
								if (!curTag.isSelfClosing()) {
									openTags.put(curNode.getName());
								}
							}
							if (curTag.isClosing()) {
								curTagName = curTag.getTagName(chars);
								logger.info("poszukiwanie tagu zamykającego dla węzła [" + curTagName + "]");
								if(openTags.hasValue(curTagName)){
									//cofaj się w stosie, przenosząc przetrawione elementy na stos revNodeDeq
									// dopóki nie znajdziesz poszukiwanego otwartego węzła o tej samej nazwie
									while(!treeNodeStack.empty()){
										treeNode = treeNodeStack.pop();
										treeNodeName = treeNode.getName();
										//poniższy warunek powinno dać się skrócić wyrzucając pierwszy test
										//jeżeli znaleziono nazwany, nie zamknięty węzeł o tej samej nazwie co tag zamykający
										if(treeNodeName!=null && curTagName.equals(treeNodeName) && !treeNode.isClosed()){
											logger.info("zamykanie węzła [" + treeNodeName  + "] tagiem o id=" + pLoop);
											//zamknij znaleziony węzeł rozpatrywanym tagiem
											if(treeNode.setClosingTag(curTag, pLoop)){
												logger.info("zamknięto węzeł [" + treeNodeName  + "] tagiem o id=" + pLoop);
											} else {
												logger.info("nie zamknięto węzła!");
											}
											//ustaw powiązanie tagu zamykającego z węzłem
											tagStateList.get(pLoop).setNodeBindingThisTag(treeNode);
											//włóż do tego węzła wszystkie węzły z revNodeDeq
											//while-WKŁADACZ
											logger.info("przenoszenie węzłów");
											while(!revNodeStack.empty()){
												logger.info("przenoszenie węzła");
												revNode = revNodeStack.pop();
												if(!revNode.isRaw() && !revNode.isClosed()){
													logger.info("węzeł jest błędny - został zbudowany w oparciu o tag[" + revNode.getName() + "] otwierający który sprawia problemy. Węzeł zostanie zniszczony, a tag zostanie przeznaczony do naprawy");
													
													revId = revNode.getOpeningTagListId();
													revTagState = tagStateList.get(revId);
													revTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.SKIPPED);
													revTagState.setNodeBindingThisTag(null);
													tagLocationsMap.get(revNode.getName()).addProblematic(revId);
													revNode.setParent(null);
													//TODO: czy poniższa linia jest potrzebna ?
													revNode = null;
													
													
												} else {
													//węzeł jest prawidłowy - więc utrwal obustronne powiązanie rodzic-dziecko
													//logger.info("podpinanie prawidłowego węzła do parenta");
													//logger.info("revNode = [" +revNode.stringifyAsStringBuffer(savedSource) + "]");
													//logger.info("nazwa parentNode[=treeNode] = " + treeNodeName);
													revNode.setParent(treeNode);
													treeNode.addChild(revNode);
													//logger.info("nazwa ustawionego rodzica: " + revNode.getParent().getName());
													//logger.info("ilość dzieci ustawionego rodzica = " + revNode.getParent().getChildren().size());
													
												}
												
											}
											logger.info("zakończono przenoszenie węzłów");
											//wstaw właśnie zamknięty węzeł ponownie do stosu i zbioru
											treeNodeStack.add(treeNode);
											openTags.put(treeNodeName);
											//przełam pętlę cofającą się - dalej przetwarzaj węzły
											break;
										} 
										// jeżeli albo węzeł nie jest nazwany, albo jest zamknięty,
										// albo jest nazwany ale ma inną nazwę niż poszukiwana
										else {
											//jeżeli węzeł jest nazwany i otwarty-nie zamykający się sam
											if(treeNodeName!=null && !treeNode.isSelfClosing()){
												//ponieważ ten węzeł zostanie zamknięty wewnątrz węzła którego początek
												//właśnie jest poszukiwany więc nie będzie on miał wpływu na przyszłe
												//wyszukiwania - zatem trzeba go usunąć z listy otwartych tagów
												//by nie wprowadzał przyszłych wyszukiwań w błąd
												//Ponadto, ten węzeł na pewno nie zostanie prawidłowo zamknięty,
												//ale oznaczenie go jako błędny zostanie obsłużone w pętli while-WKŁADACZ
												openTags.take(treeNodeName);
											}
											revNodeStack.add(treeNode);
										}
									}
								} else {
									// nie istnieje odpowiedni tag otwierający - więc curTag jest na pewno błędny
									//TODO: trzeba od razu zamknąć lub usunąć taki węzeł - zgodnie z obowiązującą 
									//polityką
									logger.info("nie istnieje odpowiedni tag otwierający dla [" + curTagName + "] - trzeba będzie go otworzyć lub usunąć");
									tagStateList.get(pLoop).setTagTreeBuildingStatus(TagTreeBuildingStatus.SKIPPED);
									tagLocationsMap.get(curTagName).addProblematic(pLoop);
								}
							}
						}
					}
					// struktura jest prawidłowa (pomijając tagi SKIPPED) jeżeli rootNode 
					// został prawidłowo zamknięty
					if(rootNode.isClosed()){
						if(rootNode.getOpeningTag()!=null){
							tagStateList.get(rootNode.getOpeningTagListId()).setTagTreeBuildingStatus(TagTreeBuildingStatus.BUILT_IN);
						} else {
							logger.info("rootNode.getOpeningTag() == null !");
						}
						if(rootNode.getClosingTag()!=null){
							tagStateList.get(rootNode.getClosingTagListId()).setTagTreeBuildingStatus(TagTreeBuildingStatus.BUILT_IN);
						} else {
							logger.info("rootNode.getClosingTag() == null !");
						}
						logger.info("struktura jest prawidłowa - tj. rootNode jest zamknięty");
						//logger.info("test referencji tagu zamykającego węzła - czy nie jest null? :" + ((tagStateList.get(rootNode.getClosingTagListId()).getNodeBindingThisTag()!=null)?"nie jest":"jest"));
	/* UWAGA: zmiana */ openTags.clear(); //chyba nic to nie wnosi
						// trzeba najpierw zebrać wszystkie problematyczne tagi
						NavigableSet<Integer> problematicTags = new TreeSet<Integer>();
						for(TagLocations tl : tagLocationsMap.values()){
							for(Integer itl : tl.getProblematic()){
								problematicTags.add(itl);
							}
						}
						
						Integer skippedTagId, skippedPredId, pcClosingId, 
						openingTagId, closingTagId, excludingTagId, includingTagId,
						//childOpeningTagId, childClosingTagId
						chOpenTagId, chCloseTagId,
						rootOpeningTagId=rootNode.getOpeningTagListId();
						
						// pc = skrótowo: parentCandidate, ld = lastDescendant
						HalfElementRepresentation pcTag, openingTag, closingTag, includedTag;
						TagState pcTagState, openingTagState, closingTagState, excludingTagState, includingTagState;
						DocumentNodeRepresentation pcNode, ldNode;
						// dopuki z problematicTags da się wyciągać id tagów
						while((skippedTagId = problematicTags.pollFirst())!=null){
							logger.info("przetwarzanie kolejnego problematycznego tagu o id=" + skippedTagId);
							//najpierw trzeba ponownie włożyć to id - jego usunięciem na dobre zajmie się dalszy kod
							problematicTags.add(skippedTagId);
							//znajdź węzeł obudowujący - czyli taki który może się stać rodzicem
							// dla naprawianego tagu - nie możesz wyjść dalej niż poza rootNode
							skippedPredId = skippedTagId;
							while((--skippedPredId)>=rootOpeningTagId && tagStateList.get(skippedTagId).isStatusSkipped()){
								logger.info("szukanie węzła obudowującego tag o id=" + skippedTagId + ": rozpatrywany kandydat - skippedPredId=" + skippedPredId);
								pcTag = tagList.get(skippedPredId);
								if(pcTag.isOpening() && !pcTag.isSelfClosing()){
									logger.info("tag otwierający i nie samozamykający się");
									openingTagId = skippedPredId;
									pcTagState = tagStateList.get(skippedPredId);
									pcNode = pcTagState.getNodeBindingThisTag();
									logger.info("czy powiązany węzeł jest null? :" + ((pcNode==null)?"tak":"nie"));
									if(pcNode!=null && pcNode.isClosed() && !pcNode.isFixedBySelfClosing()){
										pcClosingId = pcNode.getClosingTagListId();
										logger.info("węzeł jest zamknięty - to obiecujące... id zamykającego tagu to pcClosingId=" + pcClosingId);
										if(pcClosingId > skippedTagId){
											// znaleziono węzeł obudowujący
											parentNode = pcNode;
											closingTagId = parentNode.getClosingTagListId();
											//oznacz granice obszaru do przeszukania - granicę wyznaczają tagi parenta
											openingTagState = tagStateList.get(openingTagId);
											closingTagState = tagStateList.get(closingTagId);
											openingTagState.setParsingParticipationState(ParsingParticipationState.PARENT);
											closingTagState.setParsingParticipationState(ParsingParticipationState.PARENT);
											//przejdź przez wnętrze obszaru między openinigTag i closingTag
											//i włącz przetwarzanie wszystkich SKIPPED
											includingTagId = openingTagId + 1;
											logger.info("granice naprawianego obszaru : <" + openingTagId + ";" + closingTagId + ">");
											while(includingTagId<closingTagId){
												logger.info("analiza naprawianego obszaru - czy aktywować przetwarzanie includingTagId=" + includingTagId + " ?");
												includingTagState = tagStateList.get(includingTagId);
												if(includingTagState.isStatusSkipped()){
													includingTagState.setParsingParticipationState(ParsingParticipationState.INCLUDED);
													logger.info("aktywacja przetwarzania dla includingTagId=" + includingTagId);
												}
												includingTagId++;
											}
											// teraz wyłącz z przetwarzania wszystkie tagi które
											// - są SKIPPED ale są wewnątrz potomka parentNode
											// - są prawidłowe, ale są wewnątrz potomka parentNode
											// - są prawidłowe, ale zamykają potomka parentNode
											// natomiast włącz do przetwarzania tagi otwierające
											// oraz FIXED tagi zamykające 
											if(parentNode.getChildren()!=null){
												for(DocumentNodeRepresentation childNode : parentNode.getChildren()){
													if(childNode.isRaw()){
														tagStateList.get(childNode.getNamelessTagListId()).setParsingParticipationState(ParsingParticipationState.INCLUDED);
														continue;
													}
													if(childNode.isClosed()){
														chOpenTagId = childNode.getOpeningTagListId();
														//może być null gdy node jest FIXED isClosed
														if(chOpenTagId!=null){
															chCloseTagId = childNode.getClosingTagListId();
															tagStateList.get(chOpenTagId).setParsingParticipationState(ParsingParticipationState.INCLUDED);
															logger.info("włączanie przetwarzania dla chOpenTagId=" + chOpenTagId);
															if(!childNode.isSelfClosing()){
																excludingTagId = new Integer(chOpenTagId);
																while((++excludingTagId) < chCloseTagId){
																	tagStateList.get(excludingTagId).setParsingParticipationState(ParsingParticipationState.EXCLUDED);
																	logger.info("wyłączanie z przetwarzania dla excludingTagId=" + excludingTagId);
																}
																tagStateList.get(chCloseTagId).setParsingParticipationState(ParsingParticipationState.EXCLUDED);
																logger.info("wyłączanie z przetwarzania dla chCloseTagId=" + chCloseTagId);
															}
														} else {
															//node musi być zbudowany w oparciu o FIXED isClosed tag
															chCloseTagId = childNode.getClosingTagListId();
															tagStateList.get(chCloseTagId).setParsingParticipationState(ParsingParticipationState.INCLUDED);
														}
														continue;
													}
													if(childNode.isSelfClosing()){
														tagStateList.get(childNode.getOpeningTagListId()).setParsingParticipationState(ParsingParticipationState.INCLUDED);
														continue;
													}
												}
											}
											//teraz wszystkie węzły które mają stan INCLUDED to bracia tagu SKIPPED ( tego o id = skippedId)
											Deque<Integer> fixingTagDeq = new LinkedList<Integer>();
											Integer dequedId, speciesId;
											HalfElementRepresentation dequedTag, speciesTag;
											TagState dequedTagState, speciesTagState;
											String dequedTagName, speciesTagName;
											DocumentNodeRepresentation fixedNode, moveNode;
											//zakolejkuj wszystkie tagi INCLUDED do przetworzenia
											for(int incLoop = openingTagId; incLoop <= closingTagId; incLoop++){
												includingTagState = tagStateList.get(incLoop);
												if(includingTagState.getParsingParticipationState().equals(ParsingParticipationState.INCLUDED)){
													fixingTagDeq.add(incLoop);
													logger.info("kolejkowanie tagu o id=" + incLoop + " do fixingTagDeq - ponieważ jest INCLUDED");
												}
											}
											//przetwórz wyciągając z kolejki
											while(fixingTagDeq.peek()!=null){
												dequedId = fixingTagDeq.poll();
												logger.info("wyciąganie z kolejki fixingTagDeq: wyciągnięto (i włożono ponownie) dequedId=" + dequedId );
												dequedTag = tagList.get(dequedId);
												dequedTagState = tagStateList.get(dequedId);
												logger.info("status wyciągniętego tagu to:" + dequedTagState.getTagTreeBuildingStatus());
												// przez domniemanie tag jest nazwany, gdyż gdyby nie był nazwany
												// to już wcześniej zostałby prawidłowo wbudowany w drzewo
												if(dequedTagState.isStatusSkipped()){
													logger.info("tag jest SKIPPED; jeżeli jest otwierający - to trzeba go zamknąć; jeżeli jest zamykający - to trzeba go otworzyć lub usunąć");
													dequedTagName = dequedTag.getTagName(chars);
													policy = orphanedPolicies.getPolicyForTag(dequedTagName);
													if(dequedTag.isOpening()){
														logger.info("tag [" + dequedId + "] jest otwierający - trzeba go zamknąć");
														//tag wymaga zamknięcia
														switch(policy.getOpeningMode()){
														case DEFAULT:
															//stwórz i wstaw do drzewa węzeł zbudowany na otwierającym tagu - zamknij go wirtualnym tagiem
															logger.info("zamknięcie DEFAULT dla tagu " + dequedTagName);
															fixedNode = DocumentNodeRepresentation.createOpeningNode(dequedTag, parentNode, dequedTagName, dequedId);
															fixedNode.fixByClosingAtTagId(dequedId);
															dequedTagState.setNodeBindingThisTag(fixedNode);
															parentNode.addChild(fixedNode);
															dequedTagState.setParsingParticipationState(ParsingParticipationState.EXCLUDED);
															dequedTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.FIXED);
															problematicTags.remove(dequedId);
															break;
														case SELF_CLOSED:
															//stwórz i wstaw do drzewa węzeł zbudowany na otwierającym tagu - zamknij go wewnętrznym samozamknięciem
															logger.info("zamknięcie SELF_CLOSED dla tagu " + dequedTagName);
															fixedNode = DocumentNodeRepresentation.createOpeningNode(dequedTag, parentNode, dequedTagName, dequedId);
															fixedNode.fixBySelfClosing();
															dequedTagState.setNodeBindingThisTag(fixedNode);
															parentNode.addChild(fixedNode);
															dequedTagState.setParsingParticipationState(ParsingParticipationState.EXCLUDED);
															dequedTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.FIXED);
															problematicTags.remove(dequedId);
															break;
														case CLOSE_ON_NEXT_SAME_SPECIES:
															//trzeba znaleźć następny tag otwierający tego samego gatunku albo dowieść że taki nie istnieje w obrębie tego parentNode
															logger.info("zamknięcie CLOSE_ON_NEXT_SAME_SPECIES dla tagu " + dequedTagName);
															fixedNode = DocumentNodeRepresentation.createOpeningNode(dequedTag, parentNode, dequedTagName, dequedId);
															while((speciesId = fixingTagDeq.peek())!=null){
																speciesTag = tagList.get(speciesId);
																speciesTagState = tagStateList.get(speciesId);
																speciesTagName = speciesTag.getTagName(chars);
																logger.info("poszukiwanie kolejnego tagu otwierającego: testowanie tagu " + speciesTagName + " o speciesId=" + speciesId);
																if(speciesTagName!=null && speciesTagName.equals(dequedTagName)){
																	logger.info("znaleziono następny tag tego samego gatunku [gatunek=" + dequedTagName + "] - można zamykać");
																	//znaleziony tag tego samego gatunku należy pozostawić w kolejce
																	// - bo być może jest istotny dla dalszego przetwarzania
																	fixedNode.fixByClosingAtTagId(speciesId);
																	break;
																} else {
																	logger.info("testowany tag nie jest tego samego gatunku [poszukiwany gatunek=" + dequedTagName + "] zatem:");
																	if((moveNode = speciesTagState.getNodeBindingThisTag())!=null){
																		logger.info(" - w oparciu o ten tag istnieje węzeł - wykonywanie przepinania pod " + dequedTagName);
																		moveNode.getParent().removeChild(moveNode);
																		moveNode.setParent(fixedNode);
																		fixedNode.addChild(moveNode);
																	}
																	logger.info(" - wywalanie z kolejki!");
																	fixingTagDeq.poll();
																}
															}
															if(speciesId==null){
																logger.info("kolejka została wyczerpana a nie znaleziono tagu tego samego gatunku [gatunek=" + dequedTagName + "] - trzeba zamknąć tagiem zamykającym parentNode");
																// - zatem tag zamykający to tag zamykający z parentNode
																fixedNode.fixByClosingAtTagId(closingTagId);
															}
															dequedTagState.setNodeBindingThisTag(fixedNode);
															parentNode.addChild(fixedNode);
															dequedTagState.setParsingParticipationState(ParsingParticipationState.EXCLUDED);
															dequedTagState.setTagTreeBuildingStatus(TagTreeBuildingStatus.FIXED);
															problematicTags.remove(dequedId);
															break;
														}
													}
													if(dequedTag.isClosing()){
														logger.info("tag [" + dequedId + "] jest zamykający - trzeba go otworzyć albo usunąć");
														tagStateList.get(dequedId).setTagTreeBuildingStatus(TagTreeBuildingStatus.SKIPPED);
														
														policy = orphanedPolicies.getPolicyForTag(dequedTagName);
														if(policy.isClosingDefault()){
															logger.info("dostarczona polityka narzuca otwarcie tagu");
															logger.info("tworzenie nowego węzła [" + dequedTagName + ", id=" + dequedId + "] z parentNode=" + parentNode.getName());
															curNode = DocumentNodeRepresentation.createClosingNode(dequedTag, parentNode, dequedTagName, dequedId);
															parentNode.addChild(curNode);
															curNode.fixByOpeningAtClosingTagId();
															dequedTagState.setParsingParticipationState(ParsingParticipationState.EXCLUDED);
															tagStateList.get(dequedId).setTagTreeBuildingStatus(TagTreeBuildingStatus.FIXED);
															problematicTags.remove(dequedId);
															logger.info("nowy węzeł [" + dequedTagName + " utworzony i wstawiony do drzewa z parentNode=" + curNode.getParent().getName());
														}
														if(policy.isClosingRemove()){
															logger.info("dostarczona polityka narzuca usunięcie tagu");
															dequedTagState.setParsingParticipationState(ParsingParticipationState.EXCLUDED);
															tagStateList.get(dequedId).setTagTreeBuildingStatus(TagTreeBuildingStatus.REMOVED);
															problematicTags.remove(dequedId);
															logger.info("tag [" + dequedTagName + ", id=" + dequedId + "] został usunięty");
														}
													}
												}
											}
											//teraz trzeba posprzątać cały obszar który podlegał naprawianiu
											//czyli dla wszystkich tagów z tego obszaru oraz z okalającego
											//parenta ustawić parsingParticipationState na INCLUDED
											Integer tagToCleanId = parentNode.getOpeningTagListId();
											if(tagToCleanId<tagsCount){
												TagState tagToCleanState = tagStateList.get(tagToCleanId);
												while(!tagToCleanState.isStateOuter()){
													logger.info("czyszczenie tagu o id=" + tagToCleanId + " (ustawianie na OUTER)");
													tagToCleanState.setParsingParticipationState(ParsingParticipationState.OUTER);
													tagToCleanId++;
													if(tagToCleanId<tagsCount){
														tagToCleanState = tagStateList.get(tagToCleanId);
													}
												}
											}
											//obszar posprzątany - można rozpocząć nową procedurę naprawiania
										}
									}
								}
							}
						}
					}
				} else {
					logger.info("There is no root node!");
				}
			}
		}// tu if(heParser!=null) się kończy
	}

	public DocumentNodeRepresentation getRootNode() {
		return this.rootNode;
	}

	public DocumentNodeIterator iterator() {
		return new DocumentNodeIterator(this.rootNode);
	}
	
	public String stringifyTree(){
		if(rootNode!=null){
			StringBuffer stringified = rootNode.stringifyAsStringBuffer(this.savedSource);
			return stringified.toString();
		}
		return null;
	}

	public class DocumentNodeIterator implements Iterator<DocumentNodeRepresentation> {
		private DocumentNodeRepresentation nextNode = null;
		private LinkedList<Iterator<DocumentNodeRepresentation>> itStack = new LinkedList<Iterator<DocumentNodeRepresentation>>();

		public DocumentNodeIterator(DocumentNodeRepresentation creator) {
			this.nextNode = creator;
		}

		public boolean hasNext() {
			return nextNode != null;
		}

		public DocumentNodeRepresentation next() {
			DocumentNodeRepresentation result = nextNode;
			Iterator<DocumentNodeRepresentation> listIt = null;
			// ustalanie następcy węzła do zwrócenia
			// poszukiwanie wśród węzłów potomnych
			if (nextNode.hasChildren()) {
				itStack.add(nextNode.getChildren().iterator());
				listIt = itStack.getLast();
				if (listIt.hasNext()) {
					nextNode = listIt.next();
				} else {
					// nieosiągalne
				}
			} else {
				listIt = itStack.getLast();
				if (listIt.hasNext()) {
					nextNode = listIt.next();
				} else {
					// wyjdź poziom wyżej
					do {
						itStack.removeLast();
						if (itStack.size() > 0) {
							listIt = itStack.getLast();
						} else {
							nextNode = null;
							break;
						}
					} while (!listIt.hasNext());
					if (listIt.hasNext()) {
						nextNode = listIt.next();
					} else {
						nextNode = null;
					}
				}
			}
			return result;
		}

	}
}
