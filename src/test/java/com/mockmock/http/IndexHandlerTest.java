package com.mockmock.http;

import com.mockmock.htmlbuilder.FooterHtmlBuilder;
import com.mockmock.htmlbuilder.HeaderHtmlBuilder;
import com.mockmock.htmlbuilder.MailListHtmlBuilder;
import com.mockmock.mail.MailQueue;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IndexHandlerTest {

	IndexHandler handler;
	@Mock private MailQueue queue;
	@Mock private FooterHtmlBuilder footerHtmlBuilder;
	@Mock private HeaderHtmlBuilder headerHtmlBuilder;
	@Mock private MailListHtmlBuilder mailListHtmlBuilder;
	@Mock private Request request;
	@Mock private HttpServletRequest httpServletRequest;
	@Mock private HttpServletResponse httpServletResponse;

	@Before
	public void setUp() {
		handler = new IndexHandler();
		handler.setMailQueue(queue);
		handler.setHeaderHtmlBuilder(headerHtmlBuilder);
		handler.setFooterHtmlBuilder(footerHtmlBuilder);
		handler.setMailListHtmlBuilder(mailListHtmlBuilder);
	}

	@Test
	public void handle_notIndex() throws ServletException, IOException {
		handler.handle("/test", request, httpServletRequest, httpServletResponse);
		verify(headerHtmlBuilder, times(0)).build();
		verify(mailListHtmlBuilder, times(0)).build();
		verify(footerHtmlBuilder, times(0)).build();
		verify(httpServletResponse, times(0)).setStatus(200);
	}

	@Test
	public void handle_index() throws ServletException, IOException {
		when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
		handler.handle("/", request, httpServletRequest, httpServletResponse);
		verify(headerHtmlBuilder, times(1)).build();
		verify(mailListHtmlBuilder, times(1)).build();
		verify(footerHtmlBuilder, times(1)).build();
		verify(httpServletResponse, times(1)).setStatus(200);
	}

}
