/*
 * IrcQueue.java
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

import java.util.LinkedList;

/**
 * Outgoing message queue.
 * 
 * @author Sorcix
 */
final class IrcQueue {
	
	/** Message Queue. */
	private final LinkedList<String> queue;
	
	/**
	 * Creates a new outgoing message queue.
	 */
	protected IrcQueue() {
		this.queue = new LinkedList<String>();
	}
	
	/**
	 * Adds raw message to queue.
	 * 
	 * @param line The raw IRC line to add to the queue.
	 */
	protected void add(final String line) {
		synchronized (this.queue) {
			this.queue.addLast(line);
			this.queue.notify();
		}
	}
	
	/**
	 * Adds raw message to the front of the queue. This should only be
	 * used for urgent messages, as other will be delayed even more if
	 * this is used frequently.
	 * 
	 * @param line The raw IRC line to add to the queue.
	 */
	protected void addToFront(final String line) {
		synchronized (this.queue) {
			this.queue.addFirst(line);
			this.queue.notify();
		}
	}
	
	/**
	 * Takes a raw line from the queue.
	 * 
	 * @return A raw IRC line to be sent.
	 */
	protected String take() {
		String line;
		synchronized (this.queue) {
			if (this.queue.isEmpty()) {
				try {
					this.queue.wait();
				} catch (final InterruptedException e) {
					return null;
				}
			}
			line = this.queue.getFirst();
			this.queue.removeFirst();
			return line;
		}
	}
}
