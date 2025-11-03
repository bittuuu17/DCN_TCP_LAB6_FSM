package edu.nyu.dcn.fsm.assignment;

import Fsm.Event;

public class TCPEvent extends Event {

    public TCPEvent(TCPEventType type) {
        super(type.name()); // store as string for FSM key
    }

    public TCPEventType getType() {
        return TCPEventType.valueOf(getName());
    }
}
