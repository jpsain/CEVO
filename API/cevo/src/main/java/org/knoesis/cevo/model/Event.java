package org.knoesis.cevo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
	private String verb;
	private String lemma;
	
	private List<VerbOffset> verbOffsets = new ArrayList<>();
	private List<CEVOEvent> cevoEvents = new ArrayList<>();

	public String getVerb() {
		return verb;
	}

	public List<VerbOffset> getVerbOffsets() {
		return verbOffsets;
	}

	public void setVerbOffsets(List<VerbOffset> verbOffsets) {
		this.verbOffsets = verbOffsets;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	
	public List<CEVOEvent> getCEVOEvents() {
		return cevoEvents;
	}

	public void setCEVOEvents(List<CEVOEvent> events) {
		this.cevoEvents = events;
	}
	
}
