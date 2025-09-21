package org.example.states.impl;

import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.enums.ATM_STATE;
import org.example.states.ATMState;

public class CheckBalanceState extends ATMState {
    public CheckBalanceState(ATMMachine atmMachine) {
        super(atmMachine);
    }

    @Override
    public void checkBalance(Account account) {
        System.out.println("The balance is: " + account.getBalance());
        System.out.println("Moving back to 'choose operation' state");
        this.atmMachine.setCurrentState(ATM_STATE.CHOOSE_OPERATION_STATE);
    }
}
