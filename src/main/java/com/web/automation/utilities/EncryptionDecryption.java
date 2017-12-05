package com.web.automation.utilities;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;
import com.web.automation.accelerators.ActionsLibrary;


public class EncryptionDecryption extends ActionsLibrary
{
	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

	/**
	 * @author Cigniti
	 * @param Data
	 * @return
	 * @throws Exception
	 */
	
	/**
	 * @author Cigniti
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey() throws Exception
	{
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
}