package org.example.controllers;

import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.entities.Card;
import org.example.enums.ATM_STATE;
import org.example.enums.OPERATION;
import org.example.states.ATMState;
import org.example.states.impl.*;

// could be singleton and synchronized
public class ATMController {
    private ATMMachine atmMachine;
    private StartState startState;
    private ChooseOperationState chooseOperationState;
    private WithdrawalState withdrawalState;
    private CheckBalanceState checkBalanceState;
    private Account currentAccount;

    public ATMController(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
        this.startState = new StartState(atmMachine);
        this.atmMachine.setCurrentState(ATM_STATE.START_STATE); // initial state is StartState.
        this.chooseOperationState = new ChooseOperationState(atmMachine);
        this.withdrawalState = new WithdrawalState(atmMachine);
        this.checkBalanceState = new CheckBalanceState(atmMachine);
    }

    private ATMState getStateInstance() {
        ATM_STATE currentStateEnum = atmMachine.getCurrentState();
        return switch(currentStateEnum) {
            case START_STATE -> startState;
            case WITHDRAWAL_STATE -> withdrawalState;
            case CHOOSE_OPERATION_STATE -> chooseOperationState;
            case CHECK_BALANCE_STATE -> checkBalanceState;
        };
    }

    public void inputCard(Card card, String passcode) {
        this.currentAccount = getStateInstance().inputCard(card, passcode);
    }
    public void ejectCard() {
        getStateInstance().ejectCard();
    }
    public void withdrawCash(int withdrawalAmount) {
        System.out.println("The current state: " + atmMachine.getCurrentState());
        getStateInstance().withdrawCash(currentAccount, withdrawalAmount);
    }
    public void checkBalance() {
        getStateInstance().checkBalance(currentAccount);
    }
    public void chooseOperation(OPERATION operation) {
        getStateInstance().chooseOperation(operation);
    }
}
