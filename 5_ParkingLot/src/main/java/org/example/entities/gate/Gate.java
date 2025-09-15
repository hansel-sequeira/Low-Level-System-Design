package org.example.entities.gate;

import lombok.AllArgsConstructor;
import org.example.enums.GateType;

@AllArgsConstructor
public abstract class Gate {
    protected int gateNumber;
    protected GateType gateType;
}
