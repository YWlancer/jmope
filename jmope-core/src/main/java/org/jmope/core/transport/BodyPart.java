package org.jmope.core.transport;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import org.jmope.core.transport.JmopeMessage.NodeRef;
import org.jmope.core.transport.exceptions.JmopeBodyValidationException;

public class BodyPart extends Observable implements JmopeMessagePart {

	private static final long serialVersionUID = -2916539741495501473L;

	public BodyPart(Observer o) {
		addObserver(o);
	}
	
	private String uuid;
	private String encValue;
	private NodeRef nodeRef;
	private String recordRef;
	private String dataColumnRef;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getEncValue() {
		return encValue;
	}
	
	public void setEncValue(String encValue) {
		this.encValue = encValue;
		this.setChanged();
		this.notifyObservers();
	}
	
	public NodeRef getNodeRef() {
		return nodeRef;
	}
	
	public void setNodeRef(NodeRef nodeRef) {
		this.nodeRef = nodeRef;
		this.setChanged();
		this.notifyObservers();
	}

	public String getDataColumnRef() {
		return dataColumnRef;
	}
	
	public void setDataColumnRef(String dataColumnRef) {
		this.dataColumnRef = dataColumnRef;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getRecordRef() {
		return recordRef;
	}

	public void setRecordRef(String recordRef) {
		this.recordRef = recordRef;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void decode(String body) {
		StringTokenizer tkz = new StringTokenizer(body, "|");
		String [] tokens = new String[tkz.countTokens()];
		
		for (int i = 0; tkz.hasMoreElements(); i++) {
			tokens[i] = tkz.nextToken();
		}
		
		this.setNodeRef(JmopeMessage.NodeRef.valueOf(tokens[0]));
		this.setRecordRef(tokens[1]);
		this.setDataColumnRef(tokens[2]);
		this.setUuid(tokens[3]);
		this.setEncValue(tokens[4]);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s = (this.nodeRef == null) ? s.append("") : s.append(this.nodeRef.toString());
		s = (this.recordRef == null) ? s.append("|").append("") : s.append("|").append(this.recordRef);
		s = (this.dataColumnRef == null) ? s.append("|").append("") : s.append("|").append(this.dataColumnRef);
		s = (this.uuid == null) ? s.append("|").append("") : s.append("|").append(this.uuid);
		s = (this.encValue == null) ? s.append("|").append("") : s.append("|").append(this.encValue);

		return s.toString();
	}

	public boolean validate() throws JmopeBodyValidationException {
		if (this.nodeRef == null) {
			throw new JmopeBodyValidationException("nodeRef cannot be null.");
		}
		if (this.dataColumnRef == null || !(this.dataColumnRef.length() > 0)) {
			throw new JmopeBodyValidationException("dataColumnRef cannot be null or empty.");
		}
		if ((NodeRef.INSERT_LEFT.equals(this.nodeRef) || NodeRef.INSERT_RIGHT.equals(this.nodeRef))
				&& (this.encValue == null || !(this.encValue.length() > 0))) {
			throw new JmopeBodyValidationException("encValue connot be null or empty when value insert happens.");
		}
		if ((NodeRef.INSERT_LEFT.equals(this.nodeRef) || NodeRef.INSERT_RIGHT.equals(this.nodeRef))
				&& (this.uuid == null || !(this.uuid.length() > 0))) {
			throw new JmopeBodyValidationException("uuid connot be null or empty when value insert happens.");
		}
		if ((NodeRef.INSERT_LEFT.equals(this.nodeRef) || NodeRef.INSERT_RIGHT.equals(this.nodeRef))
				&& (this.getRecordRef() == null || !(this.getRecordRef().length() > 0))) {
			throw new JmopeBodyValidationException("recordRef connot be null or empty when value insert happens.");
		}
		return true;
	}
	
}