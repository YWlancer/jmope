package org.jmope.server;

import java.nio.ByteBuffer;

import org.jmope.core.transport.JmopeMessage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println(JmopeMessage.OpCode.opCodeIdx[5].toString());
    	byte [] b = ByteBuffer.allocate(8).putInt(5000).array();
    	String t = ""+null;
        System.out.println(t);
    }
}
