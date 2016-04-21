package com.astropay.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.astropay.service.context.AstropayServiceContext;

/**
 * Astropay Server
 *
 * @author echinelli
 */
public class AstropayServer {

	public static void main(String[] args) throws Exception {

		String context = args.length > 0 ? args[0] : "/astropay";
		Integer port = args.length > 1 ? Integer.parseInt(args[1]) : 9090;
		boolean prod = args.length > 2 ? Boolean.valueOf(args[2])
				: Boolean.FALSE;

		Server server = buildServer(context, port, prod);

		server.start();
	}

	private static Server buildServer(String context, Integer port, boolean prod) {
		Server server = new Server(port);

		ServletContextHandler handler = buildServletContextHandler(context);

		server.setHandler(handler);
		server.setStopAtShutdown(true);

		return server;
	}

	private static ServletContextHandler buildServletContextHandler(
			String contextPath) {

		ServletContextHandler servletHandler = new ServletContextHandler(
				ServletContextHandler.NO_SESSIONS);

		servletHandler.setContextPath(contextPath);

		WebApplicationContext webApplicationContext = buildServerContext(contextPath);
		servletHandler.addEventListener(new ContextLoaderListener(
				webApplicationContext));

		DispatcherServlet dispatcherServlet = new DispatcherServlet(
				webApplicationContext);
		ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
		servletHolder.setInitOrder(1);

		servletHandler.addServlet(servletHolder, "/*");

		return servletHandler;
	}

	private static WebApplicationContext buildServerContext(String contextPath) {

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(AstropayServiceContext.class);
		return applicationContext;
	}

}
