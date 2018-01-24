package com.fbee.modules.core.sms;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SSLClient {
	public CloseableHttpClient registerSSL(String protocol)
		    throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance(protocol);
		X509TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,String authType) 
					throws java.security.cert.CertificateException {
			   
			}
			public void checkServerTrusted(X509Certificate[] chain,String authType) 
	    		  throws java.security.cert.CertificateException {
				if ((chain == null) || (chain.length == 0)) {
					throw new IllegalArgumentException("null or zero-length certificate chain");
				}
		        if ((authType == null) || (authType.length() == 0)) {
		        	throw new IllegalArgumentException("null or zero-length authentication type");
		        }
		        boolean br = false;
		        Principal principal = null;
		        for (X509Certificate x509Certificate : chain) {
		        	principal = x509Certificate.getSubjectX500Principal();
		        	if (principal != null) {
		        		br = true;
		        		return;
		        	}
		        }
		        if (!(br)) {
		        	 throw new CertificateException("服务端证书验证失败！");
		        }
			}
	
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
	    };
	    sslContext.init(null, new TrustManager[] { tm }, new SecureRandom());
	    SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslcsf).build();
		
	    return httpClient;
	}
}
