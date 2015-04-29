package com.foya.noms.util;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.web.client.RestTemplate;

public class StatefullRestTemplate extends RestTemplate {
	 private  HttpClient httpClient;
	    private final CookieStore cookieStore;
	    private final HttpContext httpContext;
	    private final StatefullHttpComponentsClientHttpRequestFactory statefullHttpComponentsClientHttpRequestFactory;

	    public StatefullRestTemplate()
	    {
	    	this(false);
	    }

	    
	    public StatefullRestTemplate(boolean isSSL)
	    {
	        super();
	        
	        if(isSSL){
		        try {
					httpClient= new TrustSelfSignedCertHttpClientFactory().getObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        if(httpClient==null){
	        	httpClient= new DefaultHttpClient();
	        }
	        cookieStore = new BasicCookieStore();
	        httpContext = new BasicHttpContext();
	        httpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
	        statefullHttpComponentsClientHttpRequestFactory = new StatefullHttpComponentsClientHttpRequestFactory(httpClient, httpContext);
//	        super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
	        super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
	    }
	    
	    
	    public HttpClient getHttpClient()
	    {
	        return httpClient;
	    }

	    public CookieStore getCookieStore()
	    {
	        return cookieStore;
	    }

	    public HttpContext getHttpContext()
	    {
	        return httpContext;
	    }

	    public StatefullHttpComponentsClientHttpRequestFactory getStatefulHttpClientRequestFactory()
	    {
	        return statefullHttpComponentsClientHttpRequestFactory;
	    }
	    
	    
	    
//	    public void setRestSSLClient(RestTemplate restTemplate, Credentials credentials) {
//	      
//	        CommonsClientHttpRequestFactory factory = (CommonsClientHttpRequestFactory) restTemplate.getRequestFactory();
//	        this.client = factory.getHttpClient();
//	        client.getState().setCredentials(AuthScope.ANY, credentials);
//	        try {
//	            URL keystore_client = Thread.currentThread().getContextClassLoader().getResource("keystore_client").toURI().toURL();
//	            URL truststore_client = Thread.currentThread().getContextClassLoader().getResource("truststore_client").toURI().toURL();
//	            ProtocolSocketFactory protocolSocketFactory = new AuthSSLProtocolSocketFactory(keystore_client, "secret",
//	                    truststore_client, "secret");
//	            Protocol authhttps = new Protocol(HTTPS, protocolSocketFactory, HTTPS_PORT);
//	            Protocol.registerProtocol(HTTPS, authhttps);
//	            client.getHostConfiguration().setHost(HTTPS_HOST, HTTPS_PORT, authhttps);
//	        } catch (URISyntaxException e) {
//	            e.printStackTrace();
//	        } catch (MalformedURLException e) {
//	            e.printStackTrace();
//	        }
//	    }
}
