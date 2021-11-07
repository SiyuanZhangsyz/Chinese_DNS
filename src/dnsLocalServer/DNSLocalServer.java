package dnsLocalServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DNSLocalServer {

	public DNSLocalServer() {

	}
	
	/*
	 * 
	 */
	@SuppressWarnings("resource")
	public void runTCPServer() throws IOException {
		ServerSocket ss = new ServerSocket(6666); //Monitor specified port
        System.out.println("TCP server in DNS LOCAL SERVER is running...");
        for (;;) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
	}
	
	public void runUDPClient() {
		
	}
	
	public void runUDPServer() {
		
	}

}
