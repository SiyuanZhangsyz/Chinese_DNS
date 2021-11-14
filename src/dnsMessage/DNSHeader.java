package dnsMessage;

public class DNSHeader {
	private short transactionId;
	private short flags;
	private short questions;
	private short answerRRs;
	private short authorityRRs;
	private short additionalRRs;
	
	public DNSHeader() {
		
	}
	
	
	public short getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(short transactionId) {
		this.transactionId = transactionId;
	}
	public short getFlags() {
		return flags;
	}
	public void setFlags(short flags) {
		this.flags = flags;
	}
	public short getQuestions() {
		return questions;
	}
	public void setQuestions(short questions) {
		this.questions = questions;
	}
	public short getAnswerRRs() {
		return answerRRs;
	}
	public void setAnswerRRs(short answerRRs) {
		this.answerRRs = answerRRs;
	}
	public short getAuthorityRRs() {
		return authorityRRs;
	}
	public void setAuthorityRRs(short authorityRRs) {
		this.authorityRRs = authorityRRs;
	}
	public short getAdditionalRRs() {
		return additionalRRs;
	}
	public void setAdditionalRRs(short additionalRRs) {
		this.additionalRRs = additionalRRs;
	}
	
	
	
}
