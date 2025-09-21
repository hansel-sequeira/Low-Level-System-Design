package org.example.states.impl;

import org.example.entities.ATMMachine;
import org.example.enums.ATM_STATE;
import org.example.enums.OPERATION;
import org.example.states.ATMState;

public class ChooseOperationState extends ATMState {
    public ChooseOperationState(ATMMachine atmMachine) {
        super(atmMachine);
    }

    public void chooseOperation(OPERATION operation) {
        System.out.println("At the 'choose operation' state");
        switch(operation) {
            case WITHDRAW : {
                this.atmMachine.setCurrentState(ATM_STATE.WITHDRAWAL_STATE);
                break;
            }
            case CHECK_BALANCE: {
                this.atmMachine.setCurrentState(ATM_STATE.CHECK_BALANCE_STATE);
                break;
            }
        }
    }

}
