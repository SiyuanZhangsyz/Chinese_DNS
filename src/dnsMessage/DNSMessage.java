package dnsMessage;

import java.net.Socket;


public class DNSMessage {
	public static short HEADER_FLAGS_QR = (short) 0x8000;
	public static short HEADER_FLAGS_OPCODE = (short)0x7800;
	public static short HEADER_FLAGS_AA = (short)0x0400;
	public static short HEADER_FLAGS_TC = (short)0x0200;
	public static short HEADER_FLAGS_RD = (short)0x0100;
	public static short HEADER_FLAGS_RA = (short)0x0080;
	public static short HEADER_FLAGS_Z = (short)0x0070;
	public static short HEADER_FLAGS_RCODE = (short)0x000F;

	public static short HEADER_FLAG_RCODE_NoError = (short)0x00;
	public static short HEADER_FLAG_RCODE_FormatError = (short)0x01;
	public static short HEADER_FLAG_RCODE_ServerFailure = (short)0x02;
	public static short HEADER_FLAG_RCODE_NameError = (short)0x03;
	public static short HEADER_FLAG_RCODE_NotImplemented = (short)0x04;
	public static short HEADER_FLAG_RCODE_Refused = (short)0x05;
	
	
	public DNSMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 *	Create header by input value
	 */
	public DNSHeader createDNSHeader(short id, short flags, short questions, short answerRRs, short authorityRRs, short additionalRRs) { 
		DNSHeader header = new DNSHeader();
		
		header.setTransactionId(id);
		header.setFlags(flags);
		header.setQuestions(questions);
		header.setAnswerRRs(answerRRs);
		header.setAuthorityRRs(authorityRRs);
		header.setAdditionalRRs(additionalRRs);
		
		return header;
	}
	
	
	/*
	 *	Print all information for specified header
	 */
	void printMessageHeader(DNSHeader header) {  
	    System.out.printf("Transaction Id: %hu\n",header.getTransactionId());
	    System.out.printf("Flags: 0x%hx\n",header.getFlags());
	    System.out.printf("Questions: %hu\n",header.getQuestions());
	    System.out.printf("AnswerRRs: %hu\n",header.getAnswerRRs());
	    System.out.printf("AuthorityRRs: %hu\n",header.getAuthorityRRs());
	    System.out.printf("AdditionRRs: %hu\n",header.getAdditionalRRs());   
	}
	
	/*
	 *	Delete specifed header
	 */
	void deleteMessageHeader(DNSHeader header) {
		
	}
	
	
	/*
	 *	HEADER_FLAGS_QR = 0x8000, that is 1000 0000 0000 0000;
	 *	
	 *	& HEADER_FLAGS_QR operation will retrieve the first bit of flags;
	 *	
	 *	1 means this is a query request;
	 *	The result of & operation will not be 0;
	 *
	 *	0 means this is a answer;
	 *	The result of & operation will be 0;
	 */
	int getQRFromHeaderFlags(DNSHeader header){
		if((header.getFlags() & HEADER_FLAGS_QR) > 0)
			return 1;
		else
			return 0;
	}
	
	/*
	 * HEADER_FLAGS_QR = 0x8000, that is 1000 0000 0000 0000;
	 * 
	 */
	short SetQRForHeaderFlags(DNSHeader header,int qr ){
	    if( qr != 0 ){
	    	/*
	    	 * | HEADER_FLAGS_QR operation will set first bit of flags as 1
	    	 * 
	    	 */
	        header.setFlags((short)(header.getFlags() | HEADER_FLAGS_QR));
	        
	    }else{
	    	/*
	    	 * ~HEADER_FLAGS_QR operation will turn HEADER_FLAGS_QR into 0111 1111 1111 1111;
	    	 * 
	    	 * & operation will turn first bit of flags into 0 and other bits will stay unchanged;
	    	 */
	    	header.setFlags((short)(header.getFlags() & (~HEADER_FLAGS_QR)));
	    }
	    
	    return header.getFlags() ;
	}
	
		
	/*
	 * HEADER_FLAGS_OPCODE = 0x7800, that is 0111 1000 0000 0000
	 * 
	 * 0 means this is a standard query;
	 * 1 means this is a reverse query;
	 * 2 means this is a server status request
	 * 
	 */
	int msgHdrTagGetOPCODE(DNSHeader header){
		/*
		 * & operation will retrieve four bits of flags which is OPCODE in hex-decimal;
		 * 
		 * >>11 operation will get the exact value of opcode in the format of integer
		 * 
		 */
	    return ((header.getFlags() & HEADER_FLAGS_OPCODE) >> 11 );       
	}

	/*
	 * HEADER_FLAGS_OPCODE = 0x7800, that is 0111 1000 0000 0000
	 * 
	 */
	short msgHdrTagSetOPCODE(DNSHeader header, int opcode){
		/*
		 * ~HEADER_FLAGS_OPCODE operation will turn HEADER_FLAGS_OPCODE into 1000 0111 1111 1111;
		 * 
		 * & operation will turn four bits of OPCODE into 0;
		 * 
		 * 'opcode<<11' will transfer the opcode from integer format into hex-decimal format;
		 * 
		 * | operation will set four bits of OPCODE based on 'opcode<<11';
		 */
		header.setFlags((short)((header.getFlags() & ( ~HEADER_FLAGS_OPCODE))
				| (opcode<<11)));
		
	     return  header.getFlags() ;
	}
	
	
	/*
	 * 	HEADER_FLAGS_AA = 0x0400, that is 0000 0100 0000 0000;
	 * 
	 * 	When the value is 1, it means the name server is an authoritative server;
	 *	when it is 0, it means it is not an authoritative server;
	 *
	 *	& HEADER_FLAGS_AA will retrieve the bit of AA;
	 *
	 */
	int msgHdrTagGetAA(DNSHeader header){
		if((header.getFlags() & HEADER_FLAGS_AA) > 0)
			return 1;
		else
			return 0;
	}
	
	/*
	 * HEADER_FLAGS_AA = 0x0400, that is 0000 0100 0000 0000;
	 */
	short msgHdrTagSetAA( DNSHeader header ,int aa){
	    if( aa != 0 ){
	    	/*
	    	 * | HEADER_FLAGS_AA operation will set sixth bit of flags as 1
	    	 * 
	    	 */
	        header.setFlags((short)(header.getFlags() | HEADER_FLAGS_AA));
	        
	    }else{
	    	/*
	    	 * ~HEADER_FLAGS_AA operation will turn HEADER_FLAGS_AA into 1111 1011 1111 1111;
	    	 * 
	    	 * & operation will turn sixth bit of flags into 0 and other bits will stay unchanged;
	    	 */
	    	header.setFlags((short)(header.getFlags() & (~HEADER_FLAGS_AA)));
	    }
	    
	    return header.getFlags() ;
	}

	
	/*
	 * 	HEADER_FLAGS_TC = 0x0200, that is 0000 0010 0000 0000;
	 * 
	 * 	When the value is 1, it means that the response has exceeded 512 bytes and has been truncated,
	 * 	and only the first 512 bytes are returned
	 *
	 *	& HEADER_FLAGS_TC will retrieve the bit of TC;
	 *
	 */
	int msgHdrTagGetTC(DNSHeader header){
		if((header.getFlags() & HEADER_FLAGS_TC) > 0)
			return 1;
		else
			return 0;
	}
	
	/*
	 * HEADER_FLAGS_TC = 0x0200, that is 0000 0010 0000 0000;
	 */
	short msgHdrTagSetTC( DNSHeader header ,int tc){
	    if( tc != 0 ){
	    	/*
	    	 * | HEADER_FLAGS_TC operation will set seventh bit of flags as 1
	    	 * 
	    	 */
	        header.setFlags((short)(header.getFlags() | HEADER_FLAGS_TC));
	        
	    }else{
	    	/*
	    	 * ~HEADER_FLAGS_AA operation will turn HEADER_FLAGS_QR into 1111 1101 1111 1111;
	    	 * 
	    	 * & operation will turn sixth bit of flags into 0 and other bits will stay unchanged;
	    	 */
	    	header.setFlags((short)(header.getFlags() & (~HEADER_FLAGS_TC)));
	    }
	    
	    return header.getFlags() ;
	}
	
	
	/*
	 * 	HEADER_FLAGS_RD = 0x0200, that is 0000 0001 0000 0000;
	 * 
	 *	If this bit is 1, it tells the name server that this query must be processed.
	 *	This method is called a recursive query
	 * 
	 * 	If the bit is 0 and the requested name server does not have an authorized answer,
	 * 	it will return a list of other name servers that can answer the query.
	 *	This method is called a iterative query
	 *
	 *	& HEADER_FLAGS_TC will retrieve the bit of RD;
	 *
	 */
	int msgHdrTagGetRD(DNSHeader header){
		if((header.getFlags() & HEADER_FLAGS_RD) > 0)
			return 1;
		else
			return 0;
	}
	
	/*
	 * HEADER_FLAGS_TC = 0x0200, that is 0000 0010 0000 0000;
	 */
	short msgHdrTagSetRD(DNSHeader header ,int rd){
	    if(rd != 0){
	    	/*
	    	 * | HEADER_FLAGS_RD operation will set eighth bit of flags as 1
	    	 * 
	    	 */
	        header.setFlags((short)(header.getFlags() | HEADER_FLAGS_RD));
	        
	    }else{
	    	/*
	    	 * ~HEADER_FLAGS_RD operation will turn HEADER_FLAGS_RD into 1111 1110 1111 1111;
	    	 * 
	    	 * & operation will turn eight bit of flags into 0 and other bits will stay unchanged;
	    	 */
	    	header.setFlags((short)(header.getFlags() & (~HEADER_FLAGS_RD)));
	    }
	    
	    return header.getFlags() ;
	}
	
	/*
	 * 	HEADER_FLAGS_RA = 0x0080, that is 0000 0000 1000 0000;
	 * 
	 *	When the value is 1, it means that the server supports recursive query.
	 * 	
	 *	& HEADER_FLAGS_RA will retrieve the bit of RA;
	 *
	 */
	int msgHdrTagGetRA(DNSHeader header){
		if((header.getFlags() & HEADER_FLAGS_RA) > 0)
			return 1;
		else
			return 0;
	}
	
	/*
	 * HEADER_FLAGS_TC = 0x0080, that is 0000 0000 1000 0000;
	 * 
	 */
	short msgHdrTagSetRA(DNSHeader header ,int ra){
	    if(ra != 0){
	    	/*
	    	 * | HEADER_FLAGS_RA operation will set ninth bit of flags as 1
	    	 * 
	    	 */
	        header.setFlags((short)(header.getFlags() | HEADER_FLAGS_RA));
	        
	    }else{
	    	/*
	    	 * ~HEADER_FLAGS_RA operation will turn HEADER_FLAGS_RA into 1111 1111 0111 1111;
	    	 * 
	    	 * & operation will turn eight bit of flags into 0 and other bits will stay unchanged;
	    	 */
	    	header.setFlags((short)(header.getFlags() & (~HEADER_FLAGS_RA)));
	    }
	    
	    return header.getFlags() ;
	}
	
	/*
	 *	HEADER_FLAGS_RCODE = 0x000F, that is 0000 0000 0000 1111
	 *
	 *	0 means there is no error;
	 *
	 *	1 means the message format error (Format error), and the server cannot understand the requested message;
	 *
	 *	2 means that the domain name server failed (Server failure), because the server cannot handle the request;
	 *
	 *	3 means a name error (Name Error), which is only meaningful to the authorized domain name resolution server,
	 *	and indicates that the resolved domain name does not exist;
	 *
	 *	4 means that the query type is not supported (Not Implemented),
	 *	that is, the domain name server does not support the query type;
	 *
	 *	5 means Refused.
	 *	Generally, the server refuses to give a response due to the set policy.
	 *	For example, the server does not want to give a response to some requesters.
	 * 
	 */
	int msgHdrTagGetRCODE(DNSHeader header){
		/*
		 * & operation will retrieve four bits of flags which is RCODE in hex-decimal;
		 * 
		 */
	    return (header.getFlags() & HEADER_FLAGS_RCODE);       
	}

	/*
	 *	HEADER_FLAGS_RCODE = 0x000F, that is 0000 0000 0000 1111
	 * 
	 */
	short msgHdrTagSetRCODE(DNSHeader header, int rcode){
		/*
		 * ~HEADER_FLAGS_RCODE operation will turn HEADER_FLAGS_RCODE into 1111 1111 1111 0000;
		 * 
		 * & operation will turn four bits of RCODE into 0;
		 * 
		 * | operation will set four bits of RCODE based on 'rcode';
		 */
		header.setFlags((short)((header.getFlags() & ( ~HEADER_FLAGS_RCODE))
				| rcode));
		
	     return  header.getFlags() ;
	}
}
