package org.knoesis.cevo.process;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import org.knoesis.cevo.model.CEVOEvent;
import org.knoesis.cevo.model.Event;
import org.knoesis.cevo.model.VerbOffset;

public class VerbAnnotator {
	
	public void AnnotateVerbs(String inputText, LinkedHashMap<String, Event> events){
		Event lobjEvent;
		VerbOffset lobjVerbOffset;
		Properties props = new Properties();
	    props.put("annotators", "tokenize, ssplit, pos, lemma");
	    
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    
	    // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
		Annotation annotation = new Annotation(inputText);
	    
	    // Run all the selected Annotators on the input text
	    pipeline.annotate(annotation);
	    
	    List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
	    
	    // Instantiate EventTagger class, available in this project
	    EventTagger lobjEventTagger = new EventTagger();
	    List<CEVOEvent> lstCEVOEvents = new ArrayList<>();
	    List<VerbOffset> lstVerbOffsets;
	    // Loop through each sentence in the input 
	    for (CoreMap sentence: sentences) 
	    {
	      // traversing the words in the current sentence
	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
	      {
	    	// If the current word is a verb then enter the If block
	    	String lstrTokenLemma = token.get(LemmaAnnotation.class);  
	    	if (events.containsKey(lstrTokenLemma))
	    	{
	    		lstVerbOffsets = events.get(lstrTokenLemma).getVerbOffsets();
	    		lobjVerbOffset = new VerbOffset();
	    		lobjVerbOffset.setBeginOffset(token.get(CharacterOffsetBeginAnnotation.class));
			   	lobjVerbOffset.setEndOffset(token.get(CharacterOffsetEndAnnotation.class)); 
			   	lstVerbOffsets.add(lobjVerbOffset);
	    		lobjVerbOffset=null;
			   	lstVerbOffsets=null;
	    		
	    	}		    	
	    	else if (token.get(PartOfSpeechAnnotation.class).contains("VB"))
	    	{
	    		lobjEvent = new Event();
	    		
	    		lstCEVOEvents = lobjEventTagger.getCEVOEvents(lstrTokenLemma);
	    		
	    		// Loop through all the entries in the list
	    			    			
	    		if (!lstCEVOEvents.isEmpty()) 
	      		 {
	    									
					   	lobjEvent.setVerb(token.get(TextAnnotation.class));
					   	lobjEvent.setLemma(lstrTokenLemma);
					   	
					   	lstVerbOffsets = new ArrayList<>();
					   	lobjVerbOffset = new VerbOffset();
					   	lobjVerbOffset.setBeginOffset(token.get(CharacterOffsetBeginAnnotation.class));
					   	lobjVerbOffset.setEndOffset(token.get(CharacterOffsetEndAnnotation.class)); 
					   	lstVerbOffsets.add(lobjVerbOffset);
					   	lobjEvent.setVerbOffsets(lstVerbOffsets);
					   	lobjEvent.setCEVOEvents(lstCEVOEvents);
					   	events.put(lstrTokenLemma, lobjEvent);
					   	
					    lobjEvent=null;
					   	lobjVerbOffset=null;
					   	lstVerbOffsets=null;
					   	
	      	    	
	      	     } // End If 
	    			    			    			    		
	    	 } // End If ((token.get...contains("VB"))
	       		
	       } // End for (CoreLabel...)

	     } // End for (CoreMap...)
	    
	}

} // End Class
