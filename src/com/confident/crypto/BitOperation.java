package com.confident.crypto;

import javacard.framework.Util;

/**
 * This class provide bit operation for 8 digit hex use 4 arrays of byte
 * 
 * @author catur.nugroho@myconfident.com
 *
 * @version 2016.02.27
 * @since 1.7
 */

public class BitOperation {
	private static final byte bytes = 4;
	private static byte[] b3 = new byte[bytes];
	private static byte[] b4 = new byte[bytes];
	private static byte[] bShift = new byte[bytes];
	private static byte[] bCarryBits = new byte[bytes];
	private static byte[] bWithCarry = new byte[bytes];
	private static short[] results = new short[2];
	/**
	 * iterate shift right
	 * 
	 * @param b1 input
	 * @param b2 output
	 * @param x shift value
	 * @param bytes
	 */
	public static void shrs(byte[] b1, byte[] b2, byte x,  byte bytes) {
		divMod(x, (short) 8, results);
		byte itr = (byte) results[0];
		reset(b3);

		// copy input to b3
		arrayCopy(b1, (short) 0, b3, (short) (b3.length - b1.length), (short) b1.length);

		for (byte i = 0; i < itr; i++) {
			shr(b3, b4, (byte) 8, bytes);

			for (byte j = 0; j < bytes; j++) {
				// copy b4 to b3
				b3[j] = b4[j];
				// reset b4
				b4[j] = 0x00;
			}
		}

		shr(b3, b4, (byte) results[1], bytes);
		// copy b4 to output
		arrayCopy(b4, b2);
	}

	/**
	 * Shift right by x, x <= 8, b2 = b1 >> x
	 * @param b1 input
	 * @param b2 output
	 * @param x shift value
	 * @param bytes
	 */
	private static void shr(byte[] b1, byte[] b2, byte x, byte bytes) {
		reset(bShift, bCarryBits, bWithCarry);
		
		for (byte i = 0; i < bytes; i++) {
			if (b1[i] < 0) {
				// if negative convert to unsigned byte before shift right
				bShift[i] = (byte) ((b1[i] & 0xFF) >>> x);
			} else {
				bShift[i] = (byte) (b1[i] >> x);
			}
			bCarryBits[i] = (byte) (b1[i] << (8 - x));
		}
		for (byte i = 0; i < bytes - 1; i++) {
			bWithCarry[i] = (byte) (bShift[i + 1] | bCarryBits[i]);
		}

		// final result
		b2[0] = bShift[0];
		for (byte i = 1; i < bytes; i++) {
			b2[i] = (byte) (bCarryBits[i - 1] | bWithCarry[i - 1]);
		}
	}

	/**
	 * iterate shift left
	 * 
	 * @param b1 input
	 * @param b2 output
	 * @param x shift value
	 * @param bytes
	 */
	public static void shls(byte[] b1, byte[] b2, byte x, byte bytes) {
		divMod(x, (short) 8, results);
		byte itr = (byte) results[0];
		reset(b3);
	
		// copy input to b3
		arrayCopy(b1, (short) 0, b3, (short) (b3.length - b1.length), (short) b1.length);
	
		for (byte i = 0; i < itr; i++) {
			shl(b3, b4, (byte) 8, bytes);
	
			for (byte j = 0; j < bytes; j++) {
				// copy b4 to b3
				b3[j] = b4[j];
				// reset b4
				b4[j] = 0x00;
			}
		}
	
		shl(b3, b4, (byte) results[1], bytes);
		// copy b4 to output
		arrayCopy(b4, b2);
	}

	/**
	 * Shift left by x, x <= 8, b2 = b1 << x
	 * @param b1 input
	 * @param b2 output
	 * @param x shift value
	 * @param bytes
	 */
	private static void shl(byte[] b1, byte[] b2, byte x, byte bytes) {
		reset(bShift, bCarryBits, bWithCarry);
	
		for (byte i = 0; i < bytes; i++) {
			bShift[i] = (byte) (b1[i] << x);
			if (b1[i] < 0) {
				// if negative convert to unsigned byte before shift right
				bCarryBits[i] = (byte) ((b1[i] & 0xFF) >>> (8 - x)); 
			} else {
				bCarryBits[i] = (byte) (b1[i] >> (8 - x));
			}
		}
		for (byte i = 1; i < bytes; i++) {
			bWithCarry[i] = (byte) (bShift[i - 1] | bCarryBits[i]);
		}
	
		// final result
		b2[bytes - 1] = bShift[bytes - 1];
		for (byte i = 0; i < bytes - 1; i++) {
			b2[i] = (byte) (bCarryBits[i + 1] | bWithCarry[i + 1]);
		}
	}

	/**
	 * OR operation
	 * @param b1
	 * @param b2
	 * @param result
	 */
	public static void or(byte[] b1, byte[] b2, byte[] result) {
		if (b1.length == b2.length) {
			for (short i = 0; i < b1.length; i++) {
				result[i] = (byte) (b1[i] | b2[i]);
			}
		} else if (b1.length < b2.length) {
			short i, j;
			short len = (short) (b2.length - b1.length);
			for (i = 0; i < len; i++) {
				result[i] = b2[i];
			}
			for (i = len, j = 0; i < b2.length; i++, j++) {
				result[i] = (byte) (b1[j] | b2[i]);
			}
		} else {
			short i, j;
			short len = (short) (b1.length - b2.length);
			for (i = 0; i < len; i++) {
				result[i] = b1[i];
			}
			for (i = len, j = 0; i < b1.length; i++, j++) {
				result[i] = (byte) (b1[i] | b2[j]);
			}
		}
	}
	
	/**
	 * XOR operation
	 * @param b1
	 * @param b2
	 * @param result
	 */
	public static void xor(byte[] b1, byte[] b2, byte[] result) {
		if (b1.length == b2.length) {
			for (short i = 0; i < b1.length; i++) {
				result[i] = (byte) (b1[i] ^ b2[i]);
			}
		} else if (b1.length < b2.length) {
			short i, j;
			short len = (short) (b2.length - b1.length);
			for (i = 0; i < len; i++) {
				result[i] = b2[i];
			}
			for (i = len, j = 0; i < b2.length; i++, j++) {
				result[i] = (byte) (b1[j] ^ b2[i]);
			}
		} else {
			short i, j;
			short len = (short) (b1.length - b2.length);
			for (i = 0; i < len; i++) {
				result[i] = b1[i];
			}
			for (i = len, j = 0; i < b1.length; i++, j++) {
				result[i] = (byte) (b1[i] ^ b2[j]);
			}
		}
	}

	/**
	 * AND operation
	 * @param b1
	 * @param b2
	 * @param result
	 */
	public static void and(byte[] b1, byte[] b2, byte[] result) {
		fill(result, (byte) 0);
		if (b1.length == b2.length) {
			for (short i = 0; i < b1.length; i++) {
				result[i] = (byte) (b1[i] & b2[i]);
			}
		} else if (b1.length < b2.length) {
			short i, j;
			short len = (short) (b2.length - b1.length);
			for (i = len, j = 0; i < b2.length; i++, j++) {
				result[i] = (byte) (b1[j] & b2[i]);
			}
		} else {
			short i, j;
			short len = (short) (b1.length - b2.length);
			for (i = len, j = 0; i < b1.length; i++, j++) {
				result[i] = (byte) (b1[i] & b2[j]);
			}
		}
	}

	public static void arrayCopy(byte[] src, byte[] dest) {
		arrayCopy(src, dest, (short) src.length);
	}

	public static void arrayCopy(byte[] src, byte[] dest, short len) {
		arrayCopy(src, (short) 0, dest, (short) 0, len);
	}

	public static void arrayCopy(byte[] src, short srcPos, byte[] dest, short destPos, short len) {
		Util.arrayCopy(src, srcPos, dest, destPos, len);
	}

	public static void fill(byte[] src, byte value) {
		fill(src, value, (short) src.length);
	}

	public static void fill(byte[] src, byte value, short len) {
		Util.arrayFillNonAtomic(src, (short) 0, len, value);
	}

	/**
	 * compare two array
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean equals(byte[] b1, byte[] b2) {
		if (b1 == b2) {
			return true;
		}
		if ((b1 == null) || (b2 == null)) {
			return false;
		}
		short i = (short) b1.length;
		if (b2.length != i) {
			return false;
		}
		for (short j = 0; j < i; j++) {
			if (b1[j] != b2[j]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * power by exponent
	 * @param base
	 * @param exponent
	 * @return
	 */
	public static short powerN(short base, short exponent) {
		if (exponent == 0)
			return 1;
		else {
			divMod(exponent, (short) 2, results);
			if (results[1] == 0) {
				short x = powerN(base, (short) (results[0]));
				return (short) (multiply(x, x));
			} else
				return (short) (base * powerN(base, (short) (exponent - 1)));
			}
	}

	public static void getValue(byte[] src, byte[] result, short pointer) {
		getValue(src, result, pointer, (byte) 4);
	}
	
	/**
	 * Get value from array of bytes (represent array of int)
	 * @param src
	 * @param result
	 * @param pointer
	 * @param bytes
	 */
	private static void getValue(byte[] src, byte[] result, short pointer, byte bytes) {
		short pb = (short) (pointer*bytes);
		if (pb < src.length) {
			fill(result, (byte) 0);
			for (byte i = 0; i < bytes; i++) {
				short p = (short) (pb + i);
				result[i] = src[p];
			}
		}
	}

	public static void setValue(byte[] output, byte[] value, byte pointer) {
		setValue(output, value, pointer, (byte) 4);
	}
	
	/**
	 * Set value to array of bytes (represent array of int)
	 * @param output
	 * @param result
	 * @param pointer
	 * @param bytes
	 */
	private static void setValue(byte[] output, byte[] value, byte pointer, byte bytes) {
		short pb = (short) (pointer*bytes);
		if (pb < output.length) {
			for (byte i = 0; i < bytes; i++) {
				short p = (short) (pb + i);
				output[p] = value[i];
			}
		}
	}
	
	/**
	 * Division and modulo
	 * @since 20160311
	 * @param dividend
	 * @param divisor
	 * @param results
	 */
	public static void divMod(short dividend, short divisor, short[] results) {
		// The left-hand side of division, i.e. what is being divided
		short remain = dividend; 
		short part1 = divisor; // The right-hand side of division
		short result = 0;
		short mask = 1;

		if (part1 == 0) {
			// TODO: Do whatever should happen when dividing by zero.
			// Don't run the rest of this algorithm if part1 = 0.
			return;
		}

		while (part1 < remain)
		// Alternative: while(!(part1 & 0x8000)) // For 16-bit, test highest
		// order bit.
		// Alternative: while(not_signed(part1)) // Same as above: As long as
		// sign bit is not set in part1.
		{
			part1 = (short) (part1 << 1); // Logical bit-shift left.
			mask = (short) (mask << 1); // Logical bit-shift left.
		}
		do {
			if (remain >= part1) {
				remain = (short) (remain - part1);
				result = (short) (result + mask);
			}
			part1 = (short) (part1 >> 1); // Bit-wise logical shift right
			mask = (short) (mask >> 1);
		} while (mask != 0);
		results[0] = result; // result = division result (quotient)
		results[1] = remain; // remain = division remainder (modulo)
	}
	
	/**
	 * Multiply
	 * @since 20160311
	 * @param a
	 * @param b
	 * @return
	 */
	private static short multiply(short a, short b) {
		short result = 0;
		while(a != 0)
		{
		    if((a & 1) == 1) // If the lowest order bit is set in A?
		    {
		        result = (short) (result + b);
		    }
		    a = (short) (a >> 1); // Note: This must set highest order bit ZERO. It must not copy the sign bit.
		    b = (short) (b << 1); 
		}
		return result;
	}
	
	private static void reset(byte[] a, byte[] b, byte[] c) {
		reset(a);
		reset(b);
		reset(c);
	}

	/**
	 * Reset array
	 * @param a
	 */
	private static void reset(byte[] a) {
		if (a != null) {
			fill(a, (byte) 0);
		}
	}
}