package org.jmope.core.transport;

import java.io.Serializable;

import org.jmope.core.transport.exceptions.JmopeValidationException;

public interface JmopeMessagePart extends Serializable {
	
	boolean validate() throws JmopeValidationException;

}
