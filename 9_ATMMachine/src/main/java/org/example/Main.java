package org.example;

import org.example.controllers.ATMController;
import org.example.entities.ATMMachine;
import org.example.entities.Account;
import org.example.entities.Card;
import org.example.enums.OPERATION;


public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ATMMachine atmMachine = new ATMMachine(); // just has atmState. Can plug in with cash bundles.
        ATMController atmController = new ATMController(atmMachine); // initial state is the startState.
        atmController.inputCard(new Card(999, 123, "password"), "password");
        atmController.chooseOperation(OPERATION.WITHDRAW);
        atmController.withdrawCash(100);
        atmController.ejectCard();
    }
}