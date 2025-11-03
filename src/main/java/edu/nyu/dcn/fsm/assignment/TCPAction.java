package edu.nyu.dcn.fsm.assignment;

import Fsm.Action;
import Fsm.Event;
import Fsm.FSM;

public class TCPAction extends Action {

    private int dataSentCount = 0;
    private int dataReceivedCount = 0;

    @Override
    public void execute(FSM fsm, Event event) {
        String ev = event.getName();
        String currentState = fsm.currentState().getName(); // FSM already updated to next state

        switch (ev) {
            case "PASSIVE":
            case "ACTIVE":
            case "SYN":
            case "SYNACK":
            case "ACK":
            case "FIN":
            case "CLOSE":
            case "SEND":
            case "TIMEOUT":
                // Print event and current FSM state
                System.out.println("Event " + ev + " received, current State is " + currentState);
                break;

            case "RDATA":
                dataReceivedCount++;
                System.out.println("DATA received " + dataReceivedCount);
                break;

            case "SDATA":
                dataSentCount++;
                System.out.println("DATA sent " + dataSentCount);
                break;

            default:
                // Invalid event
                System.err.println("ERROR: Event " + ev + " not valid in state " + currentState);
        }
    }
}
