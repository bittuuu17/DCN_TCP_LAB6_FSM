package edu.nyu.dcn.fsm.assignment;

public enum TCPEventType {
    PASSIVE, ACTIVE, SYN, SYNACK, ACK,
    FIN, CLOSE, TIMEOUT, SDATA, RDATA, SEND;
}
