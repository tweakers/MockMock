package com.mockmock.console;

import com.mockmock.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import static org.junit.Assert.*;

public class ParserTest {

	private static final String EMAIL_LIST_1VALUE = "example@example.com";
	private static final String EMAIL_LIST_2VALUES = EMAIL_LIST_1VALUE + ",nobody@example.com";
	private Parser service;
	private Settings settings;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUp() {
		service = new Parser();
		settings = new Settings();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		System.setSecurityManager(new NoExitSecurityManager());
	}

	@After
	public void tearDown() {
		System.setOut(originalOut);
		System.setErr(originalErr);
		System.setSecurityManager(null); // restore security manager
	}

	/**
	 * Writes the std or err output if a test is failed.
	 * This lets us see any error messages.
	 */
	@Rule(order = Integer.MIN_VALUE)
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			if(outContent.size() != 0) {
				originalOut.println("Standard Output:");
				originalOut.print(outContent);
				originalOut.println("----------");
			}
			if(errContent.size() != 0) {
				originalErr.println("Error Output:");
				originalErr.print(errContent);
				originalErr.println("----------");
			}
		}
	};

	/**
	 * An {@link Exception} to catch a System.exit() call
	 */
	protected static class ExitException extends SecurityException
	{
		public final int status;
		public ExitException(int status)
		{
			super("There is no escape!");
			this.status = status;
		}
	}

	/**
	 * A SecurityManager set up to catch a call to System.exit() and throw an {@link ExitException} instead.
	 */
	private static class NoExitSecurityManager extends SecurityManager
	{
		@Override
		public void checkPermission(Permission perm)
		{
			// allow anything.
		}
		@Override
		public void checkPermission(Permission perm, Object context)
		{
			// allow anything.
		}
		@Override
		public void checkExit(int status)
		{
			super.checkExit(status);
			throw new ExitException(status);
		}
	}

	@Test
	public void parseOption_help() {
		try {
			service.parseOptions(new String[]{"-?"}, settings);
		} catch (ExitException e)
		{
			assertEquals("Exit status", 0, e.status);
		}
		assertTrue(outContent.toString().contains("java -jar"));
		assertTrue(errContent.toString().isEmpty());
	}

	@Test
	public void parseOption_valid_P() {
		Settings actual = service.parseOptions(new String[]{"-p32"}, settings);
		assertEquals(settings, actual);
		assertEquals(32, actual.getSmtpPort());
		assertTrue(errContent.toString().isEmpty());
	}

	@Test
	public void parseOption_invalid_P() {
		int defaultSmtpPort = settings.getSmtpPort();
		Settings actual = service.parseOptions(new String[]{"-pa"}, settings);
		assertTrue(errContent.toString().contains("Invalid mail port given"));
		assertEquals(defaultSmtpPort, actual.getSmtpPort());
	}

	@Test
	public void parseOption_valid_H() {
		Settings actual = service.parseOptions(new String[]{"-h32"}, settings);
		assertEquals(32, actual.getHttpPort());
		assertTrue(errContent.toString().isEmpty());
	}

	@Test
	public void parseOption_invalid_H() {
		int defaultHttpPort = settings.getHttpPort();
		Settings actual = service.parseOptions(new String[]{"-ha"}, settings);
		assertTrue(errContent.toString().contains("Invalid http port given"));
		assertEquals(defaultHttpPort, actual.getHttpPort());
	}

	@Test
	public void parseOption_valid_M() {
		Settings actual = service.parseOptions(new String[]{"-m32"}, settings);
		assertEquals(32, actual.getMaxMailQueueSize());
		assertTrue(errContent.toString().isEmpty());
	}

	@Test
	public void parseOption_invalid_M() {
		int defaultMaxMailQueueSize = settings.getMaxMailQueueSize();
		Settings actual = service.parseOptions(new String[]{"-ma"}, settings);
		assertTrue(errContent.toString().contains("Invalid max mail queue size given"));
		assertEquals(defaultMaxMailQueueSize, actual.getMaxMailQueueSize());
	}

	@Test
	public void parseOption_valid_showEmail() {
		assertFalse(settings.getShowEmailInConsole());
		Settings actual = service.parseOptions(new String[]{"-ec"}, settings);
		assertTrue(actual.getShowEmailInConsole());
		assertTrue(errContent.toString().isEmpty());
	}

	@Test
	public void parseOption_valid_filterFrom_1long() {
		Settings actual = service.parseOptions(new String[]{"--filter-from=" + EMAIL_LIST_1VALUE}, settings);
		assertEquals(1, actual.getFilterFromEmailAddresses().size());
		assertTrue(actual.getFilterFromEmailAddresses().contains(EMAIL_LIST_1VALUE));
	}

	@Test
	public void parseOption_valid_filterFrom_2long() {
		Settings actual = service.parseOptions(new String[]{"--filter-from=" + EMAIL_LIST_2VALUES}, settings);
		assertEquals(2, actual.getFilterFromEmailAddresses().size());
		for(String email : EMAIL_LIST_2VALUES.split(",")) {
			assertTrue(actual.getFilterFromEmailAddresses().contains(email));
		}
	}

	@Test
	public void parseOption_valid_filterFrom_1short() {
		Settings actual = service.parseOptions(new String[]{"-f" + EMAIL_LIST_1VALUE}, settings);
		assertEquals(1, actual.getFilterFromEmailAddresses().size());
		assertTrue(actual.getFilterFromEmailAddresses().contains(EMAIL_LIST_1VALUE));
	}

	@Test
	public void parseOption_valid_filterFrom_2short() {
		Settings actual = service.parseOptions(new String[]{"-f" + EMAIL_LIST_2VALUES}, settings);
		assertEquals(2, actual.getFilterFromEmailAddresses().size());
	}

	@Test
	public void parseOption_valid_filterTo_1long() {
		Settings actual = service.parseOptions(new String[]{"--filter-to=" + EMAIL_LIST_1VALUE}, settings);
		assertEquals(1, actual.getFilterToEmailAddresses().size());
		assertTrue(actual.getFilterToEmailAddresses().contains(EMAIL_LIST_1VALUE));
	}

	@Test
	public void parseOption_valid_filterTo_2long() {
		Settings actual = service.parseOptions(new String[]{"--filter-to=" + EMAIL_LIST_2VALUES}, settings);
		assertEquals(2, actual.getFilterToEmailAddresses().size());
		for(String email : EMAIL_LIST_2VALUES.split(",")) {
			assertTrue(actual.getFilterToEmailAddresses().contains(email));
		}
	}

	@Test
	public void parseOption_valid_filterTo_1short() {
		Settings actual = service.parseOptions(new String[]{"-t" + EMAIL_LIST_1VALUE}, settings);
		assertEquals(1, actual.getFilterToEmailAddresses().size());
		assertTrue(actual.getFilterToEmailAddresses().contains(EMAIL_LIST_1VALUE));
	}

	@Test
	public void parseOption_valid_filterTo_2short() {
		Settings actual = service.parseOptions(new String[]{"-t" + EMAIL_LIST_2VALUES}, settings);
		assertEquals(2, actual.getFilterToEmailAddresses().size());
	}

}
