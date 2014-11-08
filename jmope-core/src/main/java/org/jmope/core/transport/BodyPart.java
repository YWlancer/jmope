package org.jmope.core.transport;

import java.util.Observable;
import java.util.Observer;

import org.jmope.core.transport.exceptions.JmopeBodyValidationException;
import org.jmope.transport.JmopeMessage.NodeRef;

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
		this.notifyObservers();
	}
	
	public String getEncValue() {
		return encValue;
	}
	
	public void setEncValue(String encValue) {
		this.encValue = encValue;
		this.notifyObservers();
	}
	
	public NodeRef getNodeRef() {
		return nodeRef;
	}
	
	public void setNodeRef(NodeRef nodeRef) {
		this.nodeRef = nodeRef;
		this.notifyObservers();
	}

	public String getDataColumnRef() {
		return dataColumnRef;
	}
	
	public void setDataColumnRef(String dataColumnRef) {
		this.dataColumnRef = dataColumnRef;
		this.notifyObservers();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s = s.append(this.nodeRef.toString());
		s = s.append("|").append(this.recordRef);
		s = s.append("|").append(this.dataColumnRef);
		s = s.append("|").append(this.uuid);
		s = s.append("|").append(this.encValue);

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
				&& (this.recordRef == null || !(this.recordRef.length() > 0))) {
			throw new JmopeBodyValidationException("recordRef connot be null or empty when value insert happens.");
		}
		return true;
	}
	
}