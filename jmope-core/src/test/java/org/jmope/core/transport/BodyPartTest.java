package org.jmope.core.transport;

import java.util.UUID;

import junit.framework.Assert;

import org.jmope.core.transport.JmopeMessage.NodeRef;
import org.jmope.core.transport.JmopeMessage.OpCode;
import org.jmope.core.transport.exceptions.JmopeBodyValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BodyPartTest {
	
	private HeaderPart headerPart;
	private BodyPart bodyPart;
	private String uuid;
	private String recordRef;
	
	@Before
	public void setUp() throws Exception {
		headerPart = new HeaderPart(OpCode.INSERT_REQ);
		
		uuid = UUID.randomUUID().toString();
		recordRef = UUID.randomUUID().toString();
		
		bodyPart = new BodyPart(headerPart);
		bodyPart.setDataColumnRef("column1");
		bodyPart.setEncValue("38siseur233encpeted");
		bodyPart.setNodeRef(NodeRef.ROOT);
		bodyPart.setUuid(uuid);
		bodyPart.setRecordRef(recordRef);
	}

	@After
	public void tearDown() throws Exception {
		headerPart = null;
		uuid = null;
		recordRef = null;
	}

	@Test
	public void testToString() throws Exception {
		String expected = NodeRef.ROOT.toString() + "|" + recordRef + "|column1|" + uuid + "|38siseur233encpeted";
		String actual = bodyPart.toString();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidate() throws Exception {
		boolean expected = true;
		boolean actual = bodyPart.validate();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateNullNodeRef() throws Exception {
		bodyPart.setNodeRef(null);
		bodyPart.validate();
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateNullDataColumnRef() throws Exception {
		bodyPart.setDataColumnRef(null);
		bodyPart.validate();
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateEmptyDataColumnRef() throws Exception {
		bodyPart.setDataColumnRef("");
		bodyPart.validate();
	}
	
	@Test
	public void testValidateNonInsertNullEncValue() throws Exception {
		boolean expected = true;
		bodyPart.setEncValue(null);
		boolean actual = bodyPart.validate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertNullEncValue() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_LEFT);
		bodyPart.setEncValue(null);
		bodyPart.validate();
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertEmptyEncValue() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_RIGHT);
		bodyPart.setEncValue("");
		bodyPart.validate();
	}
	
	@Test
	public void testValidateNonInsertNullUUID() throws Exception {
		boolean expected = true;
		bodyPart.setUuid(null);
		boolean actual = bodyPart.validate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertNullUUID() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_LEFT);
		bodyPart.setUuid(null);
		bodyPart.validate();
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertEmptyUUID() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_RIGHT);
		bodyPart.setUuid("");
		bodyPart.validate();
	}
	
	@Test
	public void testValidateNonInsertNullRecordRef() throws Exception {
		boolean expected = true;
		bodyPart.setRecordRef(null);
		boolean actual = bodyPart.validate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertNullRecordRef() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_LEFT);
		bodyPart.setRecordRef(null);
		bodyPart.validate();
	}
	
	@Test(expected = JmopeBodyValidationException.class)
	public void testValidateInsertEmptyRecordRef() throws Exception {
		bodyPart.setNodeRef(NodeRef.INSERT_RIGHT);
		bodyPart.setEncValue("");
		bodyPart.validate();
	}

}
