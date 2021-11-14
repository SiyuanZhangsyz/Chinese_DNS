package dnsMessage;

import resourceRecord.ResourceRecord;

public class DNSAnswer {
	private ResourceRecord rr;
	private short domainName;
	private short answertype;
	private short answerClass;
	private short answerTimeToLive;
	private short dataLength;
	private short ipAddress;
	
}
