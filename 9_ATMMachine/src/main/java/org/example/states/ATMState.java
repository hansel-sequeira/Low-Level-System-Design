package org.example.states;

import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.entities.Card;
import org.example.enums.ATM_STATE;
import org.example.enums.OPERATION;

public class ATMState {

    protected ATMMachine atmMachine;

    public ATMState(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
    }

    public Account inputCard(Card card, String passcode) {
        throw new RuntimeException("Operation not supported");
    }
    public void ejectCard() {
        System.out.println("Card has been ejected");
        atmMachine.setCurrentState(ATM_STATE.START_STATE);
    }
    public void withdrawCash(Account account, int withdrawalAmount) {
        throw new RuntimeException("Operation not supported");
    }
    public void checkBalance(Account account) {
        throw new RuntimeException("Operation not supported");
    }
    public void chooseOperation(OPERATION operation) {
        throw new RuntimeException("Operation not supported");
    }
}
