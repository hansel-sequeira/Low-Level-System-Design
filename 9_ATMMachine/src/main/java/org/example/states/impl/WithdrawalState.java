package org.example.states.impl;

import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.enums.ATM_STATE;
import org.example.states.ATMState;

public class WithdrawalState extends ATMState {
    public WithdrawalState(ATMMachine atmMachine) {
        super(atmMachine);
    }

    @Override
    public void withdrawCash(Account account, int withdrawalAmount) {
        System.out.println("Withdrawing from the account");
        account.setBalance(account.getBalance() - withdrawalAmount);
        System.out.println("Moving back to the 'choose operation' state");
        this.atmMachine.setCurrentState(ATM_STATE.CHOOSE_OPERATION_STATE);
    }
}
