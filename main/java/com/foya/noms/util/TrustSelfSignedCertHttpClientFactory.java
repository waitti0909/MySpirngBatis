package com.foya.noms.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
public class TrustSelfSignedCertHttpClientFactory implements FactoryBean<HttpClient> {
	@Override
	  public boolean isSingleton() {
	    return true;
	  }

	  @Override
	  public Class<?> getObjectType() {
	    return HttpClient.class;
	  }

	  @Override
	  public HttpClient getObject() throws Exception {
//		SSLContext sslContext = SSLContext.getInstance("SSL");
//		sslContext.init(null, new TrustManager[] { new javax.net.ssl.X509TrustManager() {
//			
//			
//			
//            public X509Certificate[] getAcceptedIssuers() {
//                    System.out.println("getAcceptedIssuers =============");
//                    return null;
//            }
//
//            public void checkClientTrusted(X509Certificate[] certs,
//                            String authType) {
//                    System.out.println("checkClientTrusted =============");
//            }
//
//            public void checkServerTrusted(X509Certificate[] certs,
//                            String authType) {
//                    System.out.println("checkServerTrusted =============");
//            }
//} }, new SecureRandom());
//		
//		SSLSocketFactory sf = new SSLSocketFactory(sslContext);
		SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {
			

	        public boolean isTrusted(
	                final X509Certificate[] chain, String authType) throws CertificateException {
	            // Oh, I am easy...
	            return true;
	        }

	    },SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		Scheme httpsScheme = new Scheme("https", 443, sf);
		Scheme httpsScheme2 = new Scheme("https", 8443, sf);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(httpsScheme);
		schemeRegistry.register(httpsScheme2);
		// apache HttpClient version >4.2 should use BasicClientConnectionManager
		ClientConnectionManager cm = new BasicClientConnectionManager(schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm);
		return httpClient;
	    // provide SSLContext that allows self-signed certificate
//	    SSLContext sslContext =
//	      new SSLContextBuilder()
//	        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//	        .build();
//
//	    SSLConnectionSocketFactory sslConnectionSocketFactory
//	      = new SSLConnectionSocketFactory(sslContext);
//
//	    // based on HttpClients.createSystem()
//	    return HttpClientBuilder.create()
//	      .useSystemProperties()
//	      .setSSLSocketFactory(sslConnectionSocketFactory)  // add custom config
//	      .build();
	  }

}
