package org.ope.utils;

import junit.framework.Assert;

import org.junit.Test;

public class JMOPEConfigTest {

	@Test
	public void testGetInstance() throws Exception {
		JMOPEConfig instance = JMOPEConfig.getInstance();
		Assert.assertNotNull(instance);
	}

	@Test
	public void testGetConfiguration() throws Exception {
		JMOPEConfig instance = JMOPEConfig.getInstance();
		String result = instance.getConfiguration("jmope.tree");
		Assert.assertEquals("jmope-tree", result);
	}

}
