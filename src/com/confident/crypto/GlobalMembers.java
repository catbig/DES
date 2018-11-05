package com.confident.crypto;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;

/**
 * DES/DES3 in Javacard 2.2.2
 * <p>Limitation Javacard 2.2.2:
 * <br>1. Static array initialization not allowed.
 * <br>2. Multidimension array
 * <br>3. String, int, char, double, float
 * <br>4. Division & modulo
 * @author catur.nugroho@myconfident.com
 * @since 20160228
 *
 */
public class GlobalMembers extends Applet
{
	private static byte[] X = new byte[4];
	private static byte[] Y = new byte[4];
	private static byte[] T = new byte[4];
	private static byte[] SK = null;
	private static byte[] oddParityTable = null;
	private static byte[] weakKey0 = null;
	private static byte[] weakKey1 = null;
	private static byte[] weakKey2 = null;
	private static byte[] weakKey3 = null;
	private static byte[] weakKey4 = null;
	private static byte[] weakKey5 = null;
	private static byte[] weakKey6 = null;
	private static byte[] weakKey7 = null;
	private static byte[] weakKey8 = null;
	private static byte[] weakKey9 = null;
	private static byte[] weakKey10 = null;
	private static byte[] weakKey11 = null;
	private static byte[] weakKey12 = null;
	private static byte[] weakKey13 = null;
	private static byte[] weakKey14 = null;
	private static byte[] weakKey15 = null;

	public static final class EParity {
		public static final byte
		ptNone = 0,
		ptOdd = 1,
		ptEven = 2;
	};
	
	private static final byte bytes = 4;

	// WeakKeyTable
	//---------------------------------------------------------------
	private static byte[] getWeakKey0() {
		byte[] key0 = { 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 };
		return key0;
	}
	
	private static byte[] getWeakKey1() {
		byte[] key1 = { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE };
		return key1;
	}
	
	private static byte[] getWeakKey2() {
		byte[] key2 = { (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x0E, (byte) 0x0E, (byte) 0x0E, (byte) 0x0E };
		return key2;
	}
	
	private static byte[] getWeakKey3() {
		byte[] key3 = { (byte) 0xE0, (byte) 0xE0, (byte) 0xE0, (byte) 0xE0, (byte) 0xF1, (byte) 0xF1, (byte) 0xF1, (byte) 0xF1 };
		return key3;
	}
	
	private static byte[] getWeakKey4() {
		byte[] key4 = { (byte) 0x01, (byte) 0x1F, (byte) 0x01, (byte) 0x1F, (byte) 0x01, (byte) 0x0E, (byte) 0x01, (byte) 0x0E };
		return key4;
	}
	
	private static byte[] getWeakKey5() {
		byte[] key5 = { (byte) 0x1F, (byte) 0x01, (byte) 0x1F, (byte) 0x01, (byte) 0x0E, (byte) 0x01, (byte) 0x0E, (byte) 0x01 };
		return key5;
	}
	
	private static byte[] getWeakKey6() {
		byte[] key6 = { (byte) 0x01, (byte) 0xE0, (byte) 0x01, (byte) 0xE0, (byte) 0x01, (byte) 0xF1, (byte) 0x01, (byte) 0xF1 };
		return key6;
	}
	
	private static byte[] getWeakKey7() {
		byte[] key7 = { (byte) 0xE0, (byte) 0x01, (byte) 0xE0, (byte) 0x01, (byte) 0xF1, (byte) 0x01, (byte) 0xF1, (byte) 0x01 };
		return key7;
	}
	
	private static byte[] getWeakKey8() {
		byte[] key8 = { (byte) 0x01, (byte) 0xFE, (byte) 0x01, (byte) 0xFE, (byte) 0x01, (byte) 0xFE, (byte) 0x01, (byte) 0xFE };
		return key8;
	}
	
	private static byte[] getWeakKey9() {
		byte[] key9 = { (byte) 0xFE, (byte) 0x01, (byte) 0xFE, (byte) 0x01, (byte) 0xFE, (byte) 0x01, (byte) 0xFE, (byte) 0x01 };
		return key9;
	}
	
	private static byte[] getWeakKey10() {
		byte[] key10 = { (byte) 0x1F, (byte) 0xE0, (byte) 0x1F, (byte) 0xE0, (byte) 0x0E, (byte) 0xF1, (byte) 0x0E, (byte) 0xF1 };
		return key10;
	}
	
	private static byte[] getWeakKey11() {
		byte[] key11 = { (byte) 0xE0, (byte) 0x1F, (byte) 0xE0, (byte) 0x1F, (byte) 0xF1, (byte) 0x0E, (byte) 0xF1, (byte) 0x0E };
		return key11;
	}
	
	private static byte[] getWeakKey12() {
		byte[] key12 = { (byte) 0x1F, (byte) 0xFE, (byte) 0x1F, (byte) 0xFE, (byte) 0x0E, (byte) 0xFE, (byte) 0x0E, (byte) 0xFE };
		return key12;
	}
	
	private static byte[] getWeakKey13() {
		byte[] key13 = { (byte) 0xFE, (byte) 0x1F, (byte) 0xFE, (byte) 0x1F, (byte) 0xFE, (byte) 0x0E, (byte) 0xFE, (byte) 0x0E };
		return key13;
	}
	
	private static byte[] getWeakKey14() {
		byte[] key14 = { (byte) 0xE0, (byte) 0xFE, (byte) 0xE0, (byte) 0xFE, (byte) 0xF1, (byte) 0xFE, (byte) 0xF1, (byte) 0xFE };
		return key14;
	}
	
	private static byte[] getWeakKey15() {
		byte[] key15 = { (byte) 0xFE, (byte) 0xE0, (byte) 0xFE, (byte) 0xE0, (byte) 0xFE, (byte) 0xF1, (byte) 0xFE, (byte) 0xF1 };
		return key15;
	}
	//---------------------------------------------------------------
	

	public static final byte MBEDTLS_DES_ENCRYPT = 1;
	public static final byte MBEDTLS_DES_DECRYPT = 0;
	public static final byte MBEDTLS_DES_KEY_SIZE = 16;
	public static final byte WEAK_KEY_COUNT = 16;
	
	public static void main()
	{
	
		// these are input data
		// they can be for sure byte arrays for javacard (no problem)
		byte[] MstKey = {0x41,0x23,0x45,0x67,(byte)0x89,(byte)0xAB,(byte)0xCD,(byte)0xEF,0x01,0x23,0x45,0x67,(byte)0x89,(byte)0xAB,(byte)0xCF,(byte)0xEF}; // 16 bytes
//		byte[] MstKey = {0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF};
		
		byte[] IniVec = new byte[32];

		byte height = 8;
		byte atc = 1;
		byte branchFactor = 4;
		byte pParity = EParity.ptEven;
		byte cryptoVersion = 1; // from 0 to 2 (CV10,CV14 and CV17)

		// AAC test	
		byte KeyOut[] = new byte[32];
		byte Output[] = new byte[64];

		//int data[] = {   // CV17
	//	0x00, 0x00, 0x00, 0x00, 0x10, 0x00,  // 9F02
	//	0x30, 0x90, 0x1B, 0x6A,              // 9F37
	//	0x00, 0x01,                          // 9F36
	//	0xA4                                 // 9F10 (CVR Only) // Byte2 only
	//};
		
		

		byte data[] = { 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			0x07, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0x10, 0x13, 0x02, 0x05, 
			0x00, 0x30, (byte) 0x90, 0x1B, 0x6A, 0x3C, 0x00, 0x00, 0x01, 0x03, (byte) 0xA4, (byte) 0xA0, (byte) 0x82 }; // CV10/CV14

		initialize();
		Function1(KeyOut, MstKey, IniVec, height, atc, branchFactor, pParity);
		Function2(data, (byte) 13, KeyOut, Output, cryptoVersion);

		clearSK();
	}

	public static void Function1(byte[] KeyOut, byte[] MasterKey,
			byte[] InitialVector, byte height, byte atc,
			byte branchFactor, byte parity) {
		byte[] parent = (new byte[32]);
		byte[] grandParent = (new byte[32]);
		byte[] keyOut = (new byte[64]);
		byte[] kcv = (new byte[32]);

		BitOperation.arrayCopy(MasterKey, (short) 0, parent, (short) 0, (short) 16);
		BitOperation.arrayCopy(MasterKey, (short) 0, parent, (short) 16, (short) 8);
		BitOperation.arrayCopy(InitialVector, (short) 0, grandParent, (short) 0, (short) 16);

		byte[] tempRef_parent = new byte[parent.length];
		byte[] tempRef_grandParent = new byte[grandParent.length];
		byte[] tempRef_keyOut = new byte[keyOut.length];
		BitOperation.arrayCopy(parent, (short) 0, tempRef_parent, (short) 0, (short) parent.length);
		BitOperation.arrayCopy(grandParent, (short) 0, tempRef_grandParent, (short) 0, (short) grandParent.length);
		BitOperation.arrayCopy(keyOut, (short) 0, tempRef_keyOut, (short) 0, (short) keyOut.length);
		subFun1(tempRef_parent, tempRef_grandParent, height, atc, branchFactor, tempRef_keyOut);
		BitOperation.arrayCopy(tempRef_parent, (short) 0, parent, (short) 0, (short) parent.length);
		BitOperation.arrayCopy(tempRef_grandParent, (short) 0, grandParent, (short) 0, (short) grandParent.length);
		BitOperation.arrayCopy(tempRef_keyOut, (short) 0, keyOut, (short) 0, (short) keyOut.length);
		if (parity == EParity.ptOdd) {
			byte[] tempRef_keyOut2 = new byte[keyOut.length];
			BitOperation.arrayCopy(keyOut, tempRef_keyOut2);
			ChkAndFixParityOdd(tempRef_keyOut2, (short) 16);
			BitOperation.arrayCopy(tempRef_keyOut2, keyOut);
		} else if (parity == EParity.ptEven) {
			byte[] tempRef_keyOut3 = new byte[keyOut.length];
			BitOperation.arrayCopy(keyOut, tempRef_keyOut3);
			ChkAndFixParityEven(tempRef_keyOut3, (short) 16);
			BitOperation.arrayCopy(tempRef_keyOut3, keyOut);
		}

		GetKcv(keyOut, kcv, (short) 1);
		BitOperation.arrayCopy(keyOut, (short) 0, KeyOut, (short) 0, (short) 16);
		//return ((int) kcv[0] << 16) | ((int) kcv[1] << 8) | (int) kcv[2];
	}

	public static void Function2(byte[] dataIn, short dataLen, byte[] sessionKey, byte[] output, byte cv) {

		byte[] data = (new byte[512]);
		byte[] h_block = (new byte[16]);
		short blocks_n;

		if (dataLen > 0x200 || dataLen <= 7) // Input data size exceeds data
												// buffer.
		{
			return;
		}
		for (short i = 0; i < dataLen; i++) {
			data[i] = (byte) dataIn[i];
		}
		// BitOperation.arrayCopy(dataIn, (short) 0, data, (short) 0, dataLen);
		if (cv != 0 && cv != 2) {
			if (cv == 1) {
				if ((dataLen & 7) != 0) {
					data[dataLen] = (byte) 0x80; // -128
					dataLen += 8 - (((((dataLen >> 31) >> 29) + (byte) dataLen) & 7) - ((dataLen >> 31) >> 29));
				}
			}
			// else // Unsupported cryptogram version
		} else if ((dataLen & 7) != 0)
			dataLen += 8 - (((((dataLen >> 31) >> 29) + (byte) dataLen) & 7) - ((dataLen >> 31) >> 29));

		short[] results = new short[2];
		BitOperation.divMod(dataLen, (short) 8, results);
		blocks_n = results[0];
		BitOperation.arrayCopy(data, (short) 0, h_block, (short) 0, (short) 8);
		byte[] tempRef_h_block = new byte[h_block.length];
		byte[] tempRef_h_block2 = new byte[h_block.length];
		byte[] tempRef_sessionKey = new byte[sessionKey.length];
		BitOperation.arrayCopy(h_block, tempRef_h_block);
		BitOperation.arrayCopy(h_block, tempRef_h_block2);
		BitOperation.arrayCopy(sessionKey, tempRef_sessionKey);
		EncryptDes(tempRef_h_block, tempRef_sessionKey, tempRef_h_block2);
		BitOperation.arrayCopy(tempRef_h_block2, (short) 0, h_block, (short) 0, (short) tempRef_h_block2.length);
		BitOperation.arrayCopy(tempRef_sessionKey, (short) 0, sessionKey, (short) 0, (short) tempRef_sessionKey.length);

		for (byte j = 1; j < blocks_n; ++j) {
			for (byte i = 0; i <= 7; ++i) {
				h_block[i] ^= (data[(8 * j) + i]);
			}
			byte[] tempRef_h_block3 = new byte[h_block.length];
			byte[] tempRef_h_block4 = new byte[h_block.length];
			byte[] tempRef_sessionKey2 = new byte[sessionKey.length];
			BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block3, (short) 0, (short) h_block.length);
			BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block4, (short) 0, (short) h_block.length);
			BitOperation.arrayCopy(sessionKey, (short) 0, tempRef_sessionKey2, (short) 0, (short) sessionKey.length);
			EncryptDes(tempRef_h_block3, tempRef_sessionKey2, tempRef_h_block4);
			BitOperation.arrayCopy(tempRef_sessionKey2, (short) 0, sessionKey, (short) 0, (short) tempRef_sessionKey2.length);
			BitOperation.arrayCopy(tempRef_h_block4, (short) 0, h_block, (short) 0, (short) tempRef_h_block4.length);
		}
		byte[] tempRef_h_block5 = new byte[h_block.length];
		byte[] tempRef_h_block6 = new byte[h_block.length];
		BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block5, (short) 0, (short) h_block.length);
		BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block6, (short) 0, (short) h_block.length);
		byte subkey[] = new byte[(short)sessionKey.length - 8];
		BitOperation.arrayCopy(sessionKey, (short) 8, subkey, (short) 0, (short) subkey.length);
		DecodeDes(tempRef_h_block5, subkey, tempRef_h_block6);
		BitOperation.arrayCopy(tempRef_h_block6, (short) 0, h_block, (short) 0, (short) tempRef_h_block6.length);

		byte[] tempRef_h_block7 = new byte[h_block.length];
		byte[] tempRef_h_block8 = new byte[h_block.length];
		byte[] tempRef_sessionKey3 = new byte[sessionKey.length];
		BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block7, (short) 0, (short) h_block.length);
		BitOperation.arrayCopy(h_block, (short) 0, tempRef_h_block8, (short) 0, (short) h_block.length);
		BitOperation.arrayCopy(sessionKey, (short) 0, tempRef_sessionKey3, (short) 0, (short) sessionKey.length);
		EncryptDes(tempRef_h_block7, tempRef_sessionKey3, tempRef_h_block8);
		BitOperation.arrayCopy(tempRef_sessionKey3, (short) 0, sessionKey, (short) 0, (short) tempRef_sessionKey3.length);
		BitOperation.arrayCopy(tempRef_h_block8, (short) 0, h_block, (short) 0, (short) tempRef_h_block8.length);

		BitOperation.arrayCopy(h_block, (short) 0, output, (short) 0, (short) 8);

	}

	public static void XorComponents(byte[] comp1, byte[] comp2, short keyLen) {
		for (short i = 0; i <= keyLen; ++i) {
			comp1[(short) (8 * i)] ^= comp2[(short) (8 * i)];
			comp1[(short) (8 * i + 1)] ^= comp2[(short) (8 * i + 1)];
			comp1[(short) (8 * i + 2)] ^= comp2[(short) (8 * i + 2)];
			comp1[(short) (8 * i + 3)] ^= comp2[(short) (8 * i + 3)];
			comp1[(short) (8 * i + 4)] ^= comp2[(short) (8 * i + 4)];
			comp1[(short) (8 * i + 5)] ^= comp2[(short) (8 * i + 5)];
			comp1[(short) (8 * i + 6)] ^= comp2[(short) (8 * i + 6)];
			comp1[(short) (8 * i + 7)] ^= comp2[(short) (8 * i + 7)];
		}
	}

	// ---------------------------------------------------------------------------
	public static void DecodeDes(byte[] data, byte[] key, byte[] output) {
		// long[] ctx = new long[32];
		byte[] ctx = new byte[32*bytes];

		byte[] data_block2 = (new byte[16]);
		byte[] key_block2 = (new byte[16]);

		BitOperation.arrayCopy(key, (short) 0, key_block2, (short) 0, (short) 8);
		BitOperation.arrayCopy(data, (short) 0, data_block2, (short) 0, (short) 8);

		byte[] tempRef_key_block2 = new byte[key_block2.length];
		BitOperation.arrayCopy(key_block2, (short) 0, tempRef_key_block2, (short) 0, (short) key_block2.length);
		mbedtls_des_setkey_dec(ctx, tempRef_key_block2);
		BitOperation.arrayCopy(tempRef_key_block2, (short) 0, key_block2, (short) 0, (short) key_block2.length);
		byte[] tempRef_data_block2 = new byte[data_block2.length];
		byte[] tempRef_output = new byte[output.length];
		BitOperation.arrayCopy(data_block2, (short) 0, tempRef_data_block2, (short) 0, (short) data_block2.length);
		BitOperation.arrayCopy(output, (short) 0, tempRef_output, (short) 0, (short) output.length);
		mbedtls_des_crypt_ecb(ctx, tempRef_data_block2, tempRef_output);
		BitOperation.arrayCopy(tempRef_data_block2, (short) 0, data_block2, (short) 0, (short) data_block2.length);
		BitOperation.arrayCopy(tempRef_output, (short) 0, output, (short) 0, (short) tempRef_output.length);
		mbedtls_des_free(ctx);
	}
	
	

	// ---------------------------------------------------------------------------
	public static void EncryptDes(byte[] data, byte[] key, byte[] output) {
		byte[] ctx = new byte[96*bytes];

		byte[] data_block2 = (new byte[16]);
		byte[] key_block2 = (new byte[16]);

		BitOperation.arrayCopy(key, (short) 0, key_block2, (short) 0, (short) 8);
		BitOperation.arrayCopy(data, (short) 0, data_block2, (short) 0, (short) 8);

		byte[] tempRef_key_block2 = new byte[key_block2.length];
		BitOperation.arrayCopy(key_block2, (short) 0, tempRef_key_block2, (short) 0, (short) key_block2.length);
		mbedtls_des_setkey_enc(ctx, tempRef_key_block2);
		BitOperation.arrayCopy(tempRef_key_block2, (short) 0, key_block2, (short) 0, (short) key_block2.length);
		byte[] tempRef_data_block2 = new byte[data_block2.length];
		byte[] tempRef_output = new byte[output.length];
		BitOperation.arrayCopy(data_block2, (short) 0, tempRef_data_block2, (short) 0, (short) data_block2.length);
		BitOperation.arrayCopy(output, (short) 0, tempRef_output, (short) 0, (short) output.length);
		mbedtls_des_crypt_ecb(ctx, tempRef_data_block2, tempRef_output);
		BitOperation.arrayCopy(tempRef_output, (short) 0, output, (short) 0, (short) tempRef_output.length);
		mbedtls_des_free(ctx);
	}

	// ---------------------------------------------------------------------------
	public static void EncryptDes3(byte[] data, byte[] key, byte[] output) {
		byte[] ctx = new byte[96*bytes];

		byte[] key_block2 = (new byte[64]);
		BitOperation.arrayCopy(key, (short) 0, key_block2, (short) 0, (short) 24);

		byte[] data_block2 = (new byte[16]);
		BitOperation.arrayCopy(data, (short) 0, data_block2, (short) 0, (short) 8);
		
		byte[] tempRef_key_block2 = (new byte[key_block2.length]);
		BitOperation.arrayCopy(key_block2, tempRef_key_block2);
		
		mbedtls_des3_set3key_enc(ctx, tempRef_key_block2);
		
		BitOperation.arrayCopy(tempRef_key_block2, key_block2);
		
		byte[] tempRef_data_block2 = (new byte[data_block2.length]);
		BitOperation.arrayCopy(data_block2, tempRef_data_block2);

		byte[] tempRef_output = (new byte[output.length]);
		BitOperation.arrayCopy(output, tempRef_output);
		
		mbedtls_des3_crypt_ecb(ctx, tempRef_data_block2, tempRef_output);
		BitOperation.arrayCopy(tempRef_data_block2, data_block2);
		BitOperation.arrayCopy(tempRef_output, output);
	}

	// ---------------------------------------------------------------------------
	public static void DeriveIK(byte[] parent, byte[] grandParent, short derivator, byte[] outputKey) {
		byte[] keyOut = (new byte[24]);
		byte[] innerKey = (new byte[24]);
		BitOperation.arrayCopy(grandParent, (short) 0, innerKey, (short) 0, (short) innerKey.length);
		innerKey[7] ^= derivator;
		innerKey[15] ^= derivator ^ 0xF0;
		byte[] tempRef_innerKey = (new byte[innerKey.length]);
		byte[] tempRef_keyOut = (new byte[keyOut.length]);
		byte[] tempRef_parent = (new byte[parent.length]);
		BitOperation.arrayCopy(innerKey, (short) 0, tempRef_innerKey, (short) 0, (short) innerKey.length);
		BitOperation.arrayCopy(keyOut, (short) 0, tempRef_keyOut, (short) 0, (short) keyOut.length);
		BitOperation.arrayCopy(parent, (short) 0, tempRef_parent, (short) 0, (short) parent.length);
		
		EncryptDes3(tempRef_innerKey, tempRef_parent, tempRef_keyOut);
		
		BitOperation.arrayCopy(tempRef_innerKey, (short) 0, innerKey, (short) 0, (short) innerKey.length);
		BitOperation.arrayCopy(tempRef_keyOut, (short) 0, keyOut, (short) 0, (short) keyOut.length);
		BitOperation.arrayCopy(tempRef_parent, (short) 0, parent, (short) 0, (short) parent.length);
		byte[] tempRef_parent2 = (new byte[parent.length]);
		BitOperation.arrayCopy(parent, (short) 0, tempRef_parent2, (short) 0, (short) parent.length);

		byte subInnerkey[] = new byte[(short) (innerKey.length - 8)];
		BitOperation.arrayCopy(innerKey, (short) 8, subInnerkey, (short) 0, (short) subInnerkey.length);

		byte subkeyOut[] = new byte[(short) (keyOut.length - 8)];
		BitOperation.arrayCopy(keyOut, (short) 8, subkeyOut, (short) 0, (short) subkeyOut.length);

		EncryptDes3(subInnerkey, tempRef_parent2, subkeyOut);
		BitOperation.arrayCopy(tempRef_parent2, parent);
		BitOperation.arrayCopy(subkeyOut, (short) 0, keyOut, (short) 8, (short) 8);

		BitOperation.arrayCopy(keyOut, (short) 0, keyOut, (short) 16, (short) 8);
		BitOperation.arrayCopy(keyOut, (short) 0, outputKey, (short) 0, (short) keyOut.length);
	}

	// ---------------------------------------------------------------------------
	public static void subFun1(byte[] parent, byte[] grandParent, short height, short atc, short branchFactor,
			byte[] outputKey) {
		byte[] tempGradpa = new byte[24];
		byte[] tempKey = new byte[24];

		for (short heighta = (short) (height - 1);  (~heighta >> 31) != 0; --heighta) {
			short derivator = (short) ((short) (atc / BitOperation.powerN((short) branchFactor, (short) heighta)) % branchFactor);
			BitOperation.arrayCopy(parent, (short) 0, tempKey, (short) 0, (short) tempKey.length);
			BitOperation.arrayCopy(grandParent, (short) 0, tempGradpa, (short) 0, (short) tempGradpa.length);
			byte[] tempRef_parent = (new byte[parent.length]);
			byte[] tempRef_grandParent = (new byte[grandParent.length]);
			byte[] tempRef_parent2 = (new byte[parent.length]);
			BitOperation.arrayCopy(parent, (short) 0, tempRef_parent, (short) 0, (short) parent.length);
			BitOperation.arrayCopy(grandParent, (short) 0, tempRef_grandParent, (short) 0, (short) parent.length);
			BitOperation.arrayCopy(parent, (short) 0, tempRef_parent2, (short) 0, (short) parent.length);
			DeriveIK(tempRef_parent, tempRef_grandParent, derivator, tempRef_parent2);
			BitOperation.arrayCopy(tempRef_grandParent, grandParent);
			BitOperation.arrayCopy(tempRef_parent2, parent);
			BitOperation.arrayCopy(tempKey, grandParent);
		}
		byte[] tempRef_tempGradpa = (new byte[tempGradpa.length]);
		byte[] tempRef_parent3 = (new byte[parent.length]);
		BitOperation.arrayCopy(tempGradpa, tempRef_tempGradpa);
		BitOperation.arrayCopy(parent, tempRef_parent3);
		XorComponents(tempRef_parent3, tempRef_tempGradpa, (short) 2);
		BitOperation.arrayCopy(tempRef_tempGradpa, tempGradpa);
		BitOperation.arrayCopy(tempRef_parent3, outputKey, (short) 24);
	}

	// ---------------------------------------------------------------------------
	public static void ChkAndFixParityOdd(byte[] keyIn, short keyLen) {
		for (short i = 0; i < keyLen; ++i) {
			switch (((keyIn[i] >> 4) ^ keyIn[i]) & 0xF) {
			default:
				keyIn[i] ^= 1;
				break;
			case 1:
			case 2:
			case 4:
			case 7:
			case 8:
			case 11:
			case 13:
			case 14:
				continue;
			}
		}
	}

	// ---------------------------------------------------------------------------
	public static void ChkAndFixParityEven(byte[] keyIn, short keyLen) {
		for (short i = 0; i < keyLen; ++i) {
			switch (((keyIn[i] >> 4) ^ keyIn[i]) & 0xF) {
			default:
				keyIn[i] ^= 1;
				break;
			case 0:
			case 3:
			case 5:
			case 6:
			case 9:
			case 0xA:
			case 0xC:
			case 0xF:
				continue;
			}
		}
	}

	// ---------------------------------------------------------------------------
	public static void GetKcv(byte[] keyIn, byte[] output, short keyLength) {
		byte[] zeroes = (new byte[32]);
		byte[] localKey = (new byte[32]);

		if (keyLength == 1) {
			BitOperation.arrayCopy(keyIn, (short) 0, localKey, (short) 0, (short) 16);
			BitOperation.arrayCopy(keyIn, (short) 0, localKey, (short) 16, (short) 8);

			byte[] tempRef_zeroes = new byte[zeroes.length];
			byte[] tempRef_localKey = new byte[localKey.length];
			byte[] tempRef_output = new byte[output.length];
			BitOperation.arrayCopy(zeroes, (short) 0, tempRef_zeroes, (short) 0, (short) zeroes.length);
			BitOperation.arrayCopy(localKey, (short) 0, tempRef_localKey, (short) 0, (short) localKey.length);
			BitOperation.arrayCopy(output, (short) 0, tempRef_output, (short) 0, (short) output.length);
			EncryptDes3(tempRef_zeroes, tempRef_localKey, tempRef_output);
			BitOperation.arrayCopy(tempRef_zeroes, (short) 0, zeroes, (short) 0, (short) tempRef_zeroes.length);
			BitOperation.arrayCopy(tempRef_localKey, (short) 0, localKey, (short) 0, (short) tempRef_localKey.length);
			BitOperation.arrayCopy(tempRef_output, (short) 0, output, (short) 0, (short) tempRef_output.length);
		} else if (keyLength == 2) {
			byte[] tempRef_zeroes2 = new byte[zeroes.length];
			byte[] tempRef_keyIn = new byte[keyIn.length];
			byte[] tempRef_output2 = new byte[output.length];
			BitOperation.arrayCopy(zeroes, (short) 0, tempRef_zeroes2, (short) 0, (short) zeroes.length);
			BitOperation.arrayCopy(keyIn, (short) 0, tempRef_keyIn, (short) 0, (short) keyIn.length);
			BitOperation.arrayCopy(output, (short) 0, tempRef_output2, (short) 0, (short) output.length);
			EncryptDes3(tempRef_zeroes2, tempRef_keyIn, tempRef_output2);
			BitOperation.arrayCopy(tempRef_zeroes2, (short) 0, zeroes, (short) 0, (short) zeroes.length);
			BitOperation.arrayCopy(tempRef_keyIn, (short) 0, keyIn, (short) 0, (short) keyIn.length);
			BitOperation.arrayCopy(tempRef_output2, (short) 0, output, (short) 0, (short) tempRef_output2.length);
		} else if (keyLength != 0) {
			// Unsupported key length provided.
		} else {
			byte[] tempRef_zeroes3 = new byte[zeroes.length];
			byte[] tempRef_keyIn2 = new byte[keyIn.length];
			byte[] tempRef_output3 = new byte[output.length];
			BitOperation.arrayCopy(zeroes, (short) 0, tempRef_zeroes3, (short) 0, (short) zeroes.length);
			BitOperation.arrayCopy(keyIn, (short) 0, tempRef_keyIn2, (short) 0, (short) keyIn.length);
			BitOperation.arrayCopy(output, (short) 0, tempRef_output3, (short) 0, (short) output.length);
			EncryptDes(tempRef_zeroes3, tempRef_keyIn2, tempRef_output3);
			BitOperation.arrayCopy(tempRef_zeroes3, (short) 0, zeroes, (short) 0, (short) zeroes.length);
			BitOperation.arrayCopy(tempRef_keyIn2, (short) 0, keyIn, (short) 0, (short) keyIn.length);
			BitOperation.arrayCopy(tempRef_output3, (short) 0, output, (short) 0, (short) tempRef_output3.length);
		}
	}
	
	public static void mbedtls_des_init(byte[] ctx) {
		BitOperation.fill(ctx, (byte) 0, (short) ctx.length);
	}

	public static void mbedtls_des_free(byte[] ctx) {
		if (ctx == null)
			return;
		BitOperation.fill(ctx, (byte) 0, (short) ctx.length);
	}

	public static void mbedtls_des3_init(byte[] ctx) {
		BitOperation.fill(ctx, (byte) 0, (short) ctx.length);
	}

	public static void mbedtls_des_key_set_parity(byte[] key) {
		byte i;

		for (i = 0; i < MBEDTLS_DES_KEY_SIZE; i++)
			key[i] = oddParityTable[(short) (key[i] / 2)];
	}

	public static short mbedtls_des_key_check_key_parity(byte[] key) {
		byte i;

		for (i = 0; i < MBEDTLS_DES_KEY_SIZE; i++)
			if (key[i] != oddParityTable[(short) (key[i] / 2)])
				return (1);

		return (0);
	}

	public static byte mbedtls_des_key_check_weak(byte[] key) {
		if (BitOperation.equals(weakKey0, key)) return (1);
		if (BitOperation.equals(weakKey1, key)) return (1);
		if (BitOperation.equals(weakKey2, key)) return (1);
		if (BitOperation.equals(weakKey3, key)) return (1);
		if (BitOperation.equals(weakKey4, key)) return (1);
		if (BitOperation.equals(weakKey5, key)) return (1);
		if (BitOperation.equals(weakKey6, key)) return (1);
		if (BitOperation.equals(weakKey7, key)) return (1);
		if (BitOperation.equals(weakKey8, key)) return (1);
		if (BitOperation.equals(weakKey9, key)) return (1);
		if (BitOperation.equals(weakKey10, key)) return (1);
		if (BitOperation.equals(weakKey11, key)) return (1);
		if (BitOperation.equals(weakKey12, key)) return (1);
		if (BitOperation.equals(weakKey13, key)) return (1);
		if (BitOperation.equals(weakKey14, key)) return (1);
		if (BitOperation.equals(weakKey15, key)) return (1);
		return (0);
	}

	public static short mbedtls_des_setkey_enc(byte[] ctx, byte[] key) {
		mbedtls_des_setkey(ctx, key);
		return (0);
	}

	public static void mbedtls_des_setkey_dec(byte[] ctx, byte[] key) {
		byte[] tempRef_key = new byte[key.length];
		BitOperation.arrayCopy(key, (short) 0, tempRef_key, (short) 0, (short) key.length);
		mbedtls_des_setkey(ctx, tempRef_key);
		BitOperation.arrayCopy(tempRef_key, (short) 0, key, (short) 0, (short) key.length);

		short p1 = bytes;
		short p30 = (short) (30*bytes);
		short p31 = (short) (p30 + bytes);
		byte[] temp1 = new byte[4];
		byte[] temp2 = new byte[4];
		for (byte i = 0; i < 16; i += 2) {
			short p = (short) (i*bytes);
			BitOperation.getValue(ctx, temp1, i);
			BitOperation.getValue(ctx, temp2, (short) (30 - i));
			BitOperation.arrayCopy(temp1, (short) 0, ctx, (short) (p30 - p), (short) 4);
			BitOperation.arrayCopy(temp2, (short) 0, ctx, (short) p, (short) 4);

			BitOperation.getValue(ctx, temp1, (short) (i + 1));
			BitOperation.getValue(ctx, temp2, (short) (31 - i));
			BitOperation.arrayCopy(temp1, (short) 0, ctx, (short) (p31 - p), (short) 4);
			BitOperation.arrayCopy(temp2, (short) 0, ctx, (short) (p + p1), (short) 4);
		}
	}

	public static short mbedtls_des3_set2key_enc(byte[] ctx, byte[] key) {
		updateSK((short) (96*bytes));
		byte[] tempRef_key = new byte[key.length];
		BitOperation.arrayCopy(key, (short) 0, tempRef_key, (short) 0, (short) key.length);
		des3_set2key(ctx, SK, tempRef_key);
		BitOperation.arrayCopy(tempRef_key, (short) 0, key, (short) 0, (short) tempRef_key.length);
		BitOperation.fill(SK, (byte) 0);
		return (0);
	}

	public static short mbedtls_des3_set2key_dec(byte[] ctx, byte[] key) {
		updateSK((short) (96*bytes));
		byte[] tempRef_key = new byte[key.length];
		BitOperation.arrayCopy(key, tempRef_key);
		des3_set2key(SK, ctx, tempRef_key);
		BitOperation.arrayCopy(tempRef_key, (short) 0, key, (short) 0, (short) tempRef_key.length);
		BitOperation.fill(SK, (byte) 0);

		return (0);
	}

	public static short mbedtls_des3_set3key_enc(byte[] ctx, byte[] key) {
		updateSK((short) (96*bytes));
		byte[] tempRef_key = new byte[key.length];
		BitOperation.arrayCopy(key, tempRef_key);
		des3_set3key(ctx, SK, tempRef_key);
		BitOperation.arrayCopy(tempRef_key, (short) 0, key, (short) 0, (short) tempRef_key.length);
		return (0);
	}

	public static short mbedtls_des3_set3key_dec(byte[] ctx, byte[] key) {
		updateSK((short) (96*bytes));
		byte[] tempRef_key = new byte[key.length];
		BitOperation.arrayCopy(key, tempRef_key);
		des3_set3key(SK, ctx, tempRef_key);
		BitOperation.arrayCopy(tempRef_key, (short) 0, key, (short) 0, (short) tempRef_key.length);
		return (0);
	}
	
	public static void mbedtls_des_crypt_ecb(byte[] ctx, byte[] input, byte[] output) {
		updateSK((short) ctx.length);
		byte p = 0;
		
		BitOperation.arrayCopy(ctx, SK);
		
		DesOperation.getXY(input, X, bytes, (byte) 0);
		DesOperation.getXY(input, Y, bytes, (byte) 4);

		DesOperation.initialPermutation(X, Y, T, bytes);

		for(byte i = 0; i < 8; i++ ) {
	    	p = DesOperation.round(Y, X, T, SK, p, bytes);
	    	p = DesOperation.round(X, Y, T, SK, p, bytes);
	    }
		
		DesOperation.finalPermutation(Y, X, T, bytes);
		
		BitOperation.arrayCopy(Y, (short) 0, output, (short) 0, (short) 4);
		BitOperation.arrayCopy(X, (short) 0, output, (short) 4, (short) 4);
	}

	/**
	 * 3DES-ECB block encryption/decryption
	 * 
	 * @param ctx
	 * @param input
	 * @param output
	 * @return
	 */
	public static void mbedtls_des3_crypt_ecb(byte[] ctx, byte[] input, byte[] output) {
		updateSK((short) ctx.length);
		byte p = 0;
		
		BitOperation.arrayCopy(ctx, SK);
		
		DesOperation.getXY(input, X, bytes, (byte) 0);
		DesOperation.getXY(input, Y, bytes, (byte) 4);

		DesOperation.initialPermutation(X, Y, T, bytes);

		for(byte i = 0; i < 8; i++ ) {
	    	p = DesOperation.round(Y, X, T, SK, p, bytes);
	    	p = DesOperation.round(X, Y, T, SK, p, bytes);
	    }
		for(byte i = 0; i < 8; i++ ) {
	    	p = DesOperation.round(X, Y, T, SK, p, bytes);
	    	p = DesOperation.round(Y, X, T, SK, p, bytes);
	    }
		for(byte i = 0; i < 8; i++ ) {
	    	p = DesOperation.round(Y, X, T, SK, p, bytes);
	    	p = DesOperation.round(X, Y, T, SK, p, bytes);
	    }

		DesOperation.finalPermutation(Y, X, T, bytes);
		
		BitOperation.arrayCopy(Y, (short) 0, output, (short) 0, (short) 4);
		BitOperation.arrayCopy(X, (short) 0, output, (short) 4, (short) 4);
	}
	
	public static void mbedtls_des_setkey(byte[] SK, byte[] key) {
		DesOperation.initialize();
		DesOperation.getXY(key, X, bytes, (byte) 0);
		DesOperation.getXY(key, Y, bytes, (byte) 4);
		
		DesOperation.permutedChoice1(X, Y, T, bytes);
		DesOperation.calculateSubkeys(X, Y, SK, bytes);
	}

	private static void initialize() {
		if (oddParityTable != null) {
			return;
		}
		oddParityTable = getOddParityTable();

		//uncomment if we use weakKeyChecker
//		if (weakKey0 != null) {
		if (weakKey0 == null) {
			return;
		}
        weakKey0 = getWeakKey0();
        weakKey1 = getWeakKey1();
        weakKey2 = getWeakKey2();
        weakKey3 = getWeakKey3();
        weakKey4 = getWeakKey4();
        weakKey5 = getWeakKey5();
        weakKey6 = getWeakKey6();
        weakKey7 = getWeakKey7();
        weakKey8 = getWeakKey8();
        weakKey9 = getWeakKey9();
        weakKey10 = getWeakKey10();
        weakKey11 = getWeakKey11();
        weakKey12 = getWeakKey12();
        weakKey13 = getWeakKey13();
        weakKey14 = getWeakKey14();
        weakKey15 = getWeakKey15();
	}
	public static byte[] getOddParityTable() {
		byte[] oddParityTable = { 1, 2, 4, 7, 8, 11, 13, 14, 16, 19, 21, 22, 25, 26, 28, 31, 32, 35,
				37, 38, 41, 42, 44, 47, 49, 50, 52, 55, 56, 59, 61, 62, 64, 67, 69, 70, 73, 74, 76, 79, 81, 82, 84, 87, 88,
				91, 93, 94, 97, 98, 100, 103, 104, 107, 109, 110, 112, 115, 117, 118, 121, 122, 124, 127, -128, -127, -126, 
				-125, -124, -123, -122, -121, -120, -119, -118, -117, -116, -115, -114, -113, -112, -111, -110, -109, -108, 
				-107, -106, -105, -104, -103, -102, -101, -100, -99, -98, -97, -96, -95, -94, -93, -92, -91, -90, -89, -88, 
				-87, -86, -85, -84, -83, -82, -81, -80, -79, -78, -77, -76, -75, -74, -73, -72, -71, -70, -69, -68, -67, 
				-66, -65, -64, -63, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, 
				-45, -44, -43, -42, -41, -40, -39, -38, -37, -36, -35, -34, -33, -32, -31, -30, -29, -28, -27, -26, -25, 
				-24, -23, -22, -21, -20, -19, -18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2 };
		return oddParityTable;
	}

	public static void des3_set2key(byte[] esk, byte[] dsk, byte[] key) {
	    mbedtls_des_setkey( esk, key );

	    byte[] tempDsk = new byte[dsk.length - 128];
		BitOperation.arrayCopy(dsk, (short) 128, tempDsk , (short) 0, (short) tempDsk.length);
	    byte[] tempKey = new byte[key.length - 8];
		BitOperation.arrayCopy(key, (short) 8, tempKey , (short) 0, (short) tempKey.length);
	    mbedtls_des_setkey( tempDsk, tempKey );
		BitOperation.arrayCopy(tempDsk, (short) 0, dsk , (short) 128, (short) tempDsk.length);
		BitOperation.arrayCopy(tempKey, (short) 0, key , (short) 8, (short) tempKey.length);
		
		short p62 = (short) (62*bytes);
		short p63 = (short) (p62 + bytes);
		short p64 = (short) (p63 + bytes);
		short p65 = (short) (p64 + bytes);
		short p30 = (short) (30*bytes);
		short p31 = (short) (p30 + bytes);
		short p32 = (short) (p31 + bytes);
		short p33 = (short) (p32 + bytes);
		short p1 = bytes;
		for(byte i = 0; i < 32; i += 2 )
	    {
			short p = (short) (i*bytes);
			for (byte j = 0; j < bytes; j++) {
		        dsk[p + j      ]  = esk[p30 - p + j];
		        dsk[p + j + p1 ]  = esk[p31 - p + j];

		        esk[p + j + p32] = dsk[p62 - p + j];
		        esk[p + j + p33] = dsk[p63 - p + j];

		        esk[p + j + p64] = dsk[      p + j];
		        esk[p + j + p65] = dsk[p1  + p + j];

		        dsk[p + j + p64] = esk[      p + j];
		        dsk[p + j + p65] = esk[p1  + p + j];
			}
	    }
	}

	public static void des3_set3key(byte[] esk, byte[] dsk, byte[] key) {
	    mbedtls_des_setkey( esk, key );
	    byte[] tempDsk = new byte[dsk.length - 128];
		BitOperation.arrayCopy(dsk, (short) 128, tempDsk , (short) 0, (short) tempDsk.length);
	    byte[] tempKey = new byte[key.length - 8];
		BitOperation.arrayCopy(key, (short) 8, tempKey , (short) 0, (short) tempKey.length);
	    mbedtls_des_setkey( tempDsk, tempKey );
		BitOperation.arrayCopy(tempDsk, (short) 0, dsk , (short) 128, (short) tempDsk.length);
		BitOperation.arrayCopy(tempKey, (short) 0, key , (short) 8, (short) tempKey.length);
		
	    byte[] tempEsk = new byte[esk.length - 256];
		BitOperation.arrayCopy(esk, (short) 256, tempEsk , (short) 0, (short) tempEsk.length);
	    tempKey = new byte[key.length - 16];
		BitOperation.arrayCopy(key, (short) 16, tempKey , (short) 0, (short) tempKey.length);
	    mbedtls_des_setkey( tempEsk, tempKey );
		BitOperation.arrayCopy(tempEsk, (short) 0, esk , (short) 256, (short) tempEsk.length);
		BitOperation.arrayCopy(tempKey, (short) 0, key , (short) 16, (short) tempKey.length);

		short p94 = (short) (94*bytes);
		short p95 = (short) (p94 + bytes);
		short p62 = (short) (62*bytes);
		short p63 = (short) (p62 + bytes);
		short p64 = (short) (p63 + bytes);
		short p65 = (short) (p64 + bytes);
		short p30 = (short) (30*bytes);
		short p31 = (short) (p30 + bytes);
		short p32 = (short) (p31 + bytes);
		short p33 = (short) (p32 + bytes);
		short p1 = bytes;
		for(byte i = 0; i < 32; i += 2 )
	    {
			short p = (short) (i*bytes);
			for (byte j = 0; j < bytes; j++) {
		        dsk[p + j      ]  = esk[p94 - p + j];
		        dsk[p + j + p1 ]  = esk[p95 - p + j];

		        esk[p + j + p32] = dsk[p62 - p + j];
		        esk[p + j + p33] = dsk[p63 - p + j];

		        dsk[p + j + p64] = esk[p30 - p + j];
		        dsk[p + j + p65] = esk[p31 - p + j];
			}
	    }
	}
	public void process(APDU apdu) throws ISOException {
		if (selectingApplet())
		{
			return;
		}
	
		byte[] buf = apdu.getBuffer();
		main();
		switch (buf[ISO7816.OFFSET_INS])
		{
		case (byte)0x00:
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new GlobalMembers().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
	}
	
	/**
	* The following method updates the buffer size by removing
	* the old buffer object from the memory by requesting
	* object deletion and creates a new one with the
	* required size.
	* 
	* @param requiredSize
	*/
	private static void updateSK(short requiredSize){
	     try{
	         if(SK != null && SK.length == requiredSize){
	             //we already have a buffer of required size
	             return;
	         }
	         JCSystem.beginTransaction();
	         byte[] oldBuffer = SK;
	         SK = new byte[requiredSize];
	         if (oldBuffer != null)
	             JCSystem.requestObjectDeletion();
	         JCSystem.commitTransaction();
	     }catch(Exception e){
	         JCSystem.abortTransaction();
	     }
	}
	
	private static void clearSK() {
		updateSK((short) 0);
		DesOperation.clear();
		SK = null;
	}
}