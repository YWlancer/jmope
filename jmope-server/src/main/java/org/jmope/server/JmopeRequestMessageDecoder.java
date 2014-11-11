package org.jmope.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.jmope.core.transport.BodyPart;
import org.jmope.core.transport.HeaderPart;
import org.jmope.core.transport.JmopeMessage;
import org.jmope.core.transport.JmopeMessage.Request;

public class JmopeRequestMessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
			return;
		}
		//Read header length from the input buffer.
		in.markReaderIndex();
		long headerLength = in.readLong();
		
		if (in.readableBytes() < 4) {
			in.resetReaderIndex();
			return;
		}
		
		//Read body length from the input buffer.
		in.markReaderIndex();
		long bodyLength = in.readLong();
		
		if (in.readableBytes() < headerLength) {
			in.resetReaderIndex();
			return;
		}
		
		//Read actual header content
		in.markReaderIndex();
		ByteBuf header = in.readBytes((int)headerLength);
		
		if (in.readableBytes() < bodyLength) {
			in.resetReaderIndex();
			return;
		}
		
		//Read actual body content
		in.markReaderIndex();
		ByteBuf body = in.readBytes((int)bodyLength);
		
		out.add(this.createMessageFromBytes(headerLength, bodyLength, header, body));
	}
	
	private JmopeMessage createMessageFromBytes(long headerLength, long bodyLength, ByteBuf header, ByteBuf body) {
		HeaderPart h = new HeaderPart();
		h.setHeaderLength(headerLength);
		h.setBodyLength(bodyLength);
		h.decode(new String(header.array()));
		
		BodyPart b = new BodyPart(h);
		b.decode(new String(body.array()));
		
		Request request = new Request(h, b);
		return request;
	}

}