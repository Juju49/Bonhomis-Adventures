/**
 * 
 */
package com.bonhomi.main;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * @author Julian
 *
 */
public final class DebugOutput {
	
	
	protected static PrintStream debugOutput;
	
	public DebugOutput(PipedInputStream i_stream) {
		try {
			debugOutput = new PrintStream(new PipedOutputStream(i_stream));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
