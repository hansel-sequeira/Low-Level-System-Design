package org.example.states.impl;

import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.entities.Card;
import org.example.enums.ATM_STATE;
import org.example.states.ATMState;

public class StartState extends ATMState {

    public StartState(ATMMachine atmMachine) {
        super(atmMachine);
    }

    @Override
    public Account inputCard(Card card, String passcode) {
        if(card.getPasscode().equals(passcode)) {
            System.out.println("At the start state. Card validated and account fetched");
            this.atmMachine.setCurrentState(ATM_STATE.CHOOSE_OPERATION_STATE);
            Account account = new Account(123, 1000); // fetch the account based on the card details
            return account;
        } else {
            System.out.println("Invalid credentials. Ejecting the card");
            ejectCard();
            return null;
        }
    }
}
