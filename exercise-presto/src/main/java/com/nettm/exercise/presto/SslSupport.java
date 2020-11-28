package com.nettm.exercise.presto;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SslSupport {

    public static void main(String[] args) throws Exception {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);

        SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket();

        String[] protocols = socket.getSupportedProtocols();

        System.out.println("Supported Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(" " + protocols[i]);
        }

        protocols = socket.getEnabledProtocols();

        System.out.println("Enabled Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(" " + protocols[i]);
        }

    }
}
