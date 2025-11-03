package edu.nyu.dcn.fsm.assignment;

import Fsm.FSM;
import Fsm.Event;
import Fsm.FsmException;
import java.util.Scanner;
public class TCPFSMMain {

    public static void main(String[] args) throws Exception {
        TCPFSMBuilder builder = new TCPFSMBuilder();
        FSM tcpFsm = builder.getFsm();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String token = sc.next(); // read next input

            TCPEventType eventType;
            try {
                eventType = TCPEventType.valueOf(token); // will throw IllegalArgumentException if invalid
            } catch (IllegalArgumentException e) {
                System.err.println("Error: unexpected Event: " + token);
                continue; // ignore bad input
            }

            try {
                tcpFsm.doEvent(new TCPEvent(eventType));
            } catch (FsmException ex) {
                if(TCPEventType.valueOf(token)== TCPEventType.SEND){
                    System.err.println("ERROR: Event " + token + " not implemented as per LAB Note 2");
                }else{
                    System.err.println("ERROR: Event " + token + " not valid in state " + tcpFsm.currentState().getName());
                }

            }
        }

    }
}
