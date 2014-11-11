package org.jmope.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.jmope.core.transport.JmopeMessage.Request;

public class JmopeRequestMessageEncoder extends MessageToByteEncoder<Request> {

	/*@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		Request request = (Request) msg;
		if (request.validate()) {
			//Buffer size(bytes) = header length + body length + 4 for header length value + 4 for body length value 
			int buffer = (int)(request.header.getHeaderLength() + request.header.getBodyLength() + 4 * 2);
			ByteBuf encoded = ctx.alloc().buffer(buffer);
			encoded.writeLong(request.header.getHeaderLength());
			encoded.writeLong(request.header.getBodyLength());
			encoded.writeBytes(request.header.toString().getBytes());
			encoded.writeBytes(request.body.toString().getBytes());
			ctx.write(encoded, promise);
		}
	}*/

	@Override
	protected void encode(ChannelHandlerContext ctx, Request request, ByteBuf out) throws Exception {
		if (request.validate()) {
			//Buffer size(bytes) = header length + body length + 4 for header length value + 4 for body length value 
			int buffer = (int)(request.header.getHeaderLength() + request.header.getBodyLength() + 4 * 2);
			out = ctx.alloc().buffer(buffer);
			out.writeLong(request.header.getHeaderLength());
//			resetWriteCursor(out);
			out.writeLong(request.header.getBodyLength());
//			resetWriteCursor(out);
			out.writeBytes(request.header.toString().getBytes());
//			resetWriteCursor(out);
			out.writeBytes(request.body.toString().getBytes());
		}
	}

	private void resetWriteCursor(ByteBuf out) {
		out.markWriterIndex();
		out.resetWriterIndex();
	}
	
}
