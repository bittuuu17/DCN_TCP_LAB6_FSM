package edu.nyu.dcn.fsm.assignment;

import Fsm.FSM;
import Fsm.Transition;

public class TCPFSMBuilder {

    private FSM tcpFsm;
    private TCPAction action = new TCPAction();
    private TCPState CLOSED = new TCPState("CLOSED");
    private TCPState LISTEN = new TCPState("LISTEN");
    private TCPState SYN_SENT = new TCPState("SYN_SENT");
    private TCPState SYN_RCVD = new TCPState("SYN_RCVD");
    private TCPState ESTABLISHED = new TCPState("ESTABLISHED");
    private TCPState FIN_WAIT_1 = new TCPState("FIN_WAIT_1");
    private TCPState FIN_WAIT_2 = new TCPState("FIN_WAIT_2");
    private TCPState CLOSING = new TCPState("CLOSING");
    private TCPState CLOSE_WAIT = new TCPState("CLOSE_WAIT");
    private TCPState LAST_ACK = new TCPState("LAST_ACK");
    private TCPState TIME_WAIT = new TCPState("TIME_WAIT");

    public TCPFSMBuilder() throws Exception {
        tcpFsm = new FSM("TCP_FSM", CLOSED);
        setupTransitions();
    }

    private void setupTransitions() throws Exception {
        // CLOSED
        tcpFsm.addTransition(new Transition(CLOSED, new TCPEvent(TCPEventType.PASSIVE), LISTEN, action));
        tcpFsm.addTransition(new Transition(CLOSED, new TCPEvent(TCPEventType.ACTIVE), SYN_SENT, action));

        // LISTEN
        tcpFsm.addTransition(new Transition(LISTEN, new TCPEvent(TCPEventType.SYN), SYN_RCVD, action));
        tcpFsm.addTransition(new Transition(LISTEN, new TCPEvent(TCPEventType.CLOSE), CLOSED, action));
        //tcpFsm.addTransition(new Transition(LISTEN, new TCPEvent(TCPEventType.SEND), SYN_SENT, action));

        // SYN_SENT
        tcpFsm.addTransition(new Transition(SYN_SENT, new TCPEvent(TCPEventType.SYNACK), ESTABLISHED, action));
        tcpFsm.addTransition(new Transition(SYN_SENT, new TCPEvent(TCPEventType.SYN), SYN_RCVD, action));
        tcpFsm.addTransition(new Transition(SYN_SENT, new TCPEvent(TCPEventType.CLOSE), CLOSED, action));

        // SYN_RCVD
        tcpFsm.addTransition(new Transition(SYN_RCVD, new TCPEvent(TCPEventType.ACK), ESTABLISHED, action));
        tcpFsm.addTransition(new Transition(SYN_RCVD, new TCPEvent(TCPEventType.CLOSE), FIN_WAIT_1, action));

        // ESTABLISHED
        tcpFsm.addTransition(new Transition(ESTABLISHED, new TCPEvent(TCPEventType.SDATA), ESTABLISHED, action));
        tcpFsm.addTransition(new Transition(ESTABLISHED, new TCPEvent(TCPEventType.RDATA), ESTABLISHED, action));
        tcpFsm.addTransition(new Transition(ESTABLISHED, new TCPEvent(TCPEventType.FIN), CLOSE_WAIT, action));
        tcpFsm.addTransition(new Transition(ESTABLISHED, new TCPEvent(TCPEventType.CLOSE), FIN_WAIT_1, action));

        // FIN_WAIT_1
        tcpFsm.addTransition(new Transition(FIN_WAIT_1, new TCPEvent(TCPEventType.FIN), CLOSING, action));
        tcpFsm.addTransition(new Transition(FIN_WAIT_1, new TCPEvent(TCPEventType.ACK), FIN_WAIT_2, action));

        // FIN_WAIT_2
        tcpFsm.addTransition(new Transition(FIN_WAIT_2, new TCPEvent(TCPEventType.FIN), TIME_WAIT, action));

        // CLOSING
        tcpFsm.addTransition(new Transition(CLOSING, new TCPEvent(TCPEventType.ACK), TIME_WAIT, action));

        // CLOSE_WAIT
        tcpFsm.addTransition(new Transition(CLOSE_WAIT, new TCPEvent(TCPEventType.CLOSE), LAST_ACK, action));

        // LAST_ACK
        tcpFsm.addTransition(new Transition(LAST_ACK, new TCPEvent(TCPEventType.ACK), CLOSED, action));

        // TIME_WAIT
        tcpFsm.addTransition(new Transition(TIME_WAIT, new TCPEvent(TCPEventType.TIMEOUT), CLOSED, action));
    }

    public FSM getFsm() {
        return tcpFsm;
    }
}
