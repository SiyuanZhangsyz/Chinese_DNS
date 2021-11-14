package main;

import java.io.IOException;

import dnsClient.DNSClient;
import dnsLocalServer.DNSLocalServer;

public class Main {

	public static void main(String[] args) throws IOException{
		
		DNSClient dnsClient = new DNSClient();		
		dnsClient.runTCPClient();
		
		//DNSLocalServer dnsLocalServer = new DNSLocalServer();
		//dnsLocalServer.runTCPServer();
		
		
	}

}
