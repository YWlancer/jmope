package org.jmope.core.transport;

import java.util.Observable;
import java.util.Observer;

import org.jmope.core.transport.exceptions.JmopeHeaderValidationException;
import org.jmope.transport.JmopeMessage.OpCode;

public class HeaderPart implements Observer, JmopeMessagePart {

	private static final long serialVersionUID = 4939900928465278280L;

	public final OpCode opCode;
	
	private long headerLength;
	private long bodyLength;
	
	public HeaderPart(OpCode opCode) {
		this.opCode = opCode;
		this.init();
	}
	
	private void init() {
		this.setHeaderLength(0);
		StringBuilder header = new StringBuilder(Integer.toString(this.opCode.index));
		header = header.append("|");
		header = header.append(opCode.direction.toString());
		header = header.append("|");
	}

	public void update(Observable o, Object arg) {
		JmopeMessagePart body = (JmopeMessagePart) o;
		this.setBodyLength(body.toString().getBytes().length);
	}

	public long getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(long headerLength) {
		this.headerLength = headerLength;
	}

	public long getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(long bodyLength) {
		this.bodyLength = bodyLength;
	}

	public boolean validate() throws JmopeHeaderValidationException {
		// TODO Auto-generated method stub
		return false;
	}
	
}