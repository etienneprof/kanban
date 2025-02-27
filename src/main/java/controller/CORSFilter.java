package controller;
import java.io.IOException;
import java.util.Arrays;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

// ATTENTION, à n'utiliser que pour les tests en local
@Provider
public class CORSFilter implements ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().put("Access-Control-Allow-Origin", Arrays.asList("*"));
		responseContext.getHeaders().put("Access-Control-Allow-Headers", Arrays.asList("CSRF-Token","X-Requested-By","Authorization","Content-Type"));
		responseContext.getHeaders().put("Access-Control-Allow-Credentials", Arrays.asList("true"));
		responseContext.getHeaders().put("Access-Control-Allow-Methods", Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","HEAD"));
		
	}
}