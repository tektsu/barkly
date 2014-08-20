package com.ticketmaster.jacs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.RuntimeException;

@Path("show/{quadrant: [1234]}/{barcode: [A-Za-z0-9]+}")
public class ShowBarcode {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String show(@PathParam("quadrant") int quadrant, @PathParam("barcode") String barcode) {
    	try {
    		TicketWindow.getInstance().displayBarcode(quadrant, barcode);
    	}
    	catch (RuntimeException e) {
    		return "error: " + quadrant + " " + e.getMessage();
    	}
        return "ok: " + quadrant + " " + barcode;
    }
}

