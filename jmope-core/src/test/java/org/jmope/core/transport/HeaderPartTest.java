package org.jmope.core.transport;

import java.util.UUID;

import junit.framework.Assert;

import org.jmope.core.transport.JmopeMessage.NodeRef;
import org.jmope.core.transport.exceptions.JmopeHeaderValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeaderPartTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() throws Exception {
		String expected = "0|REQUEST";
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.INIT_REQ);
		h.setHeaderLength(15);
		h.setBodyLength(60);
		String actual = h.toString();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidate() throws Exception {
		boolean expected = true;
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.INIT_REQ);
		h.setHeaderLength(15);
		h.setBodyLength(60);
		boolean actual = h.validate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeHeaderValidationException.class)
	public void testValidateEmptyHeaderLength() throws Exception {
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.INIT_REQ);
		h.setBodyLength(60);
		h.validate();
	}
	
	public void testValidateEmptyBodyLengthValid() throws Exception {
		boolean expected = true;
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.INIT_REQ);
		h.setHeaderLength(15);
		boolean actual = h.validate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeHeaderValidationException.class)
	public void testValidateEmptyBodyLengthInvalid() throws Exception {
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.QUERY_REQ);
		h.setHeaderLength(15);
		h.validate();
	}
	
	@Test
	public void testUpdate() {
		HeaderPart h = new HeaderPart(JmopeMessage.OpCode.INIT_REQ);
		String uuid = UUID.randomUUID().toString();
		String recordRef = UUID.randomUUID().toString();
		
		BodyPart bodyPart = new BodyPart(h);
		bodyPart.setDataColumnRef("column1");
		bodyPart.setEncValue("38siseur233encpeted");
		bodyPart.setNodeRef(NodeRef.ROOT);
		bodyPart.setUuid(uuid);
		bodyPart.setRecordRef(recordRef);
		
		int expected = uuid.getBytes().length + recordRef.getBytes().length + bodyPart.getDataColumnRef().getBytes().length
				+ bodyPart.getEncValue().getBytes().length + bodyPart.getNodeRef().toString().getBytes().length + "|".getBytes().length * 4;
		
		Assert.assertEquals(expected, h.getBodyLength());
	}

}
