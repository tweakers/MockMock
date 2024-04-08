package com.mockmock.http;

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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteHandlerTest {

	private DeleteHandler handler;

	@Mock
	MailQueue queue;
	@Mock
	Request request;
	@Mock
	HttpServletRequest httpServletRequest;
	@Mock
	HttpServletResponse httpServletResponse;

	@Before
	public void setUp() {
		handler = new DeleteHandler();
		handler.setMailQueue(queue);
	}

	@Test
	public void handle_delete_all() throws ServletException, IOException {
		doNothing().when(queue).emptyQueue();
		handler.handle("/mail/delete/all", request, httpServletRequest, httpServletResponse);
		verify(queue, times(1)).emptyQueue();
	}

	@Test
	public void handle_delete_nothing() throws ServletException, IOException {
		handler.handle("/mail/delete/nothing", request, httpServletRequest, httpServletResponse);
		verify(queue, times(0)).emptyQueue();
	}

}
