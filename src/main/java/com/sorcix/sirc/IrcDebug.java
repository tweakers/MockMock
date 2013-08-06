/*
 * IrcDebug.java
 * 
 * This file is part of the Sorcix Java IRC Library (sIRC).
 * 
 * Copyright (C) 2008-2010 Vic Demuzere http://sorcix.com
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.sorcix.sirc;

import java.io.PrintStream;

/**
 * Handles debug output on sIRC. The default output stream is {@code
 * System.out}, and debug is disabled until you enable it.
 * 
 * @author Sorcix
 */
public final class IrcDebug {
	
	/**
	 * Whether debug output is enabled.
	 */
	private static boolean enabled = false;
	/**
	 * The {@link PrintStream} to use for debug output.
	 */
	private static PrintStream out = System.out;
	
	/**
	 * Checks whether debug output is enabled.
	 * 
	 * @return True if debug output is enabled, false otherwise.
	 */
	public static boolean isEnabled() {
		return IrcDebug.enabled;
	}
	
	/**
	 * Sends a line to the log. All messages are prefixed with the
	 * current timestamp.
	 * 
	 * @param line The message to log.
	 */
	protected static void log(final String line) {
		if (IrcDebug.enabled) {
			IrcDebug.out.println("[" + System.currentTimeMillis() + "] " + line);
			IrcDebug.out.flush();
		}
	}
	
	/**
	 * Enables or disables debug output.
	 * 
	 * @param enable True to enable debug output, false to disable it.
	 */
	public static void setEnabled(final boolean enable) {
		IrcDebug.enabled = enable;
	}
	
	/**
	 * Changes the stream used for debug output.
	 * 
	 * @param out The new stream for debug output.
	 */
	public static void setLogStream(final PrintStream out) {
		if (out != null) {
			IrcDebug.out = out;
		}
	}
}
