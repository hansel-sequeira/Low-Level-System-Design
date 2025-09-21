package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.ATM_STATE;
import org.example.states.ATMState;

@Getter
@Setter
public class ATMMachine {
    private ATM_STATE currentState;
}
