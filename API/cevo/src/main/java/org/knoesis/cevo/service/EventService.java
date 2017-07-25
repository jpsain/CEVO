package org.knoesis.cevo.service;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;

import org.knoesis.cevo.model.Event;
import org.knoesis.cevo.process.VerbAnnotator;

public class EventService {
	
	public List<Event> getAllEvents(String inputText){
		
		LinkedHashMap<String, Event> events = new LinkedHashMap<>();
		VerbAnnotator lobjVerbAnnotator = new VerbAnnotator();
		lobjVerbAnnotator.AnnotateVerbs(inputText,events);
		return new ArrayList<Event>(events.values());
	}

}
