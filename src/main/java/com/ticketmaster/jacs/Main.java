package com.ticketmaster.jacs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.lang.InterruptedException;
import java.net.URI;
import com.google.zxing.WriterException;

/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://127.0.0.1:8080/barkly/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.ticketmaster.jacs package
        final ResourceConfig rc = new ResourceConfig().packages("com.ticketmaster.jacs");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, WriterException {
		final HttpServer server = startServer();
		try {
			while (true) {
				Thread.sleep(10000);
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        server.shutdownNow();

		System.exit(0);
    }
}

