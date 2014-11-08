package org.jmope.core.transport;


public abstract class JmopeMessage {
	
	public final HeaderPart header;
	public final JmopeMessagePart body;
	
	protected JmopeMessage(OpCode opcode) {
		this.header = new HeaderPart(opcode);
		this.body = new BodyPart(this.header);
	}
	
	public enum OpCode {
				
		INIT_REQ		(0, Direction.REQUEST),
		INIT_RES		(1, Direction.RESPONSE),
		INSERT_REQ		(2, Direction.REQUEST),
		INSERT_RES		(3, Direction.RESPONSE),
		REMOVE_REQ		(4, Direction.REQUEST),
		REMOVE_RES		(5, Direction.RESPONSE),
		QUERY_REQ		(6, Direction.REQUEST),
		QUERY_RES		(7, Direction.RESPONSE),
		ORDER_REQ		(8, Direction.REQUEST),
		ORDER_RES		(9, Direction.RESPONSE),
		TRAVERSAL_REQ	(10, Direction.REQUEST),
		TRAVERSAL_RES	(11, Direction.RESPONSE);
		
		public final int index;
		public final Direction direction;
		public static final OpCode [] opCodeIdx;
		
		static {
			int maxOpCode = -1;
			for (OpCode opCode : OpCode.values()) {
				maxOpCode = Math.max(maxOpCode, opCode.index);
			}
			opCodeIdx = new OpCode[maxOpCode + 1];
			for (OpCode opCode : OpCode.values()) {
				if (opCodeIdx[opCode.index] != null)
                    throw new IllegalStateException("Duplicate opcode");
                opCodeIdx[opCode.index] = opCode;
			}
		}
		
		private OpCode(int index, Direction direction) {
			this.index = index;
			this.direction = direction;
		}
	}
	
	public enum Direction {
		REQUEST, RESPONSE
	}
	
	public enum NodeRef {
		ROOT, GET_LEFT, GET_RIGHT, INSERT_LEFT, INSERT_RIGHT
	}

	public static abstract class Request extends JmopeMessage {

		protected Request(OpCode opcode) {
			super(opcode);
			if (opcode.direction != Direction.REQUEST) {
				throw new IllegalArgumentException("Invalid message direction. Direction of the Request message should be REQUEST");
			}
		}
		
	}
	
	public static abstract class Response extends JmopeMessage {
		
		protected Response(OpCode opcode) {
			super(opcode);
			if (opcode.direction != Direction.RESPONSE) {
				throw new IllegalArgumentException("Invalid message direction. Direction of the Response message should be RESPONSE");
			}
		}
	}
}
