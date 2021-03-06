package org.jmope.core.transport;

import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import org.jmope.core.transport.JmopeMessage.OpCode;
import org.jmope.core.transport.exceptions.JmopeHeaderValidationException;

public class HeaderPart implements Observer, JmopeMessagePart {

	private static final long serialVersionUID = 4939900928465278280L;

	private OpCode opCode;
	private long headerLength = 0;
	private long bodyLength = 0;
	
	public HeaderPart(OpCode opCode) {
		this.opCode = opCode;
		this.headerLength = this.toString().getBytes().length;
	}
	
	public HeaderPart() {
	}

	public void update(Observable o, Object arg) {
		BodyPart body = (BodyPart) o;
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
	
	public OpCode getOpCode() {
		return opCode;
	}

	public void setOpCode(OpCode opCode) {
		this.opCode = opCode;
	}

	public void decode(String header) {
		StringTokenizer tkz = new StringTokenizer(header, "|");
		String [] tokens = new String[tkz.countTokens()];
		for (int i = 0; tkz.hasMoreElements(); i++) {
			tokens[i] = tkz.nextToken();
		}
		this.opCode = OpCode.opCodeIdx[Integer.parseInt(tokens[0])];
		this.headerLength = this.toString().getBytes().length;
	}
	
	@Override
	public String toString() {
		StringBuilder header = new StringBuilder(Integer.toString(this.opCode.index));
		header = header.append("|");
		header = header.append(opCode.direction.toString());
		return header.toString();
	}

	public boolean validate() throws JmopeHeaderValidationException {
		if (this.opCode == null) {
			throw new JmopeHeaderValidationException("OpCode should be valid. Accepted values : "
					+ "[INIT_REQ, INIT_RES, INSERT_REQ, INSERT_RES, REMOVE_REQ, REMOVE_RES, QUERY_REQ, QUERY_RES, ORDER_REQ, ORDER_RES, TRAVERSAL_REQ, TRAVERSAL_RES]");
		}
		if (this.headerLength == 0) {
			throw new JmopeHeaderValidationException("Header length cannot be zero");
		}
		if (!OpCode.INIT_REQ.equals(this.opCode) && this.bodyLength == 0) {
			throw new JmopeHeaderValidationException("Body length cannot be zero");
		}
		return true;
	}
	
}