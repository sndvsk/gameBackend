package com.example.gameBackend.dto;

public class ResponseMessage {

    private boolean won;
    private double bet;
    private int clientNumber;
    private double winnings;
    private int serverNumber;
    //private int serverNumberCycles;

/*    public ResponseMessage(boolean won, double bet, int clientNumber, double winnings, int serverNumber, int serverNumberCycles) {
        this.won = won;
        this.bet = bet;
        this.clientNumber = clientNumber;
        this.winnings = winnings;
        this.serverNumber = serverNumber;
        this.serverNumberCycles = serverNumberCycles;
    }*/

    public ResponseMessage(boolean won, double bet, int clientNumber, double winnings, int serverNumber) {
        this.won = won;
        this.bet = bet;
        this.clientNumber = clientNumber;
        this.winnings = winnings;
        this.serverNumber = serverNumber;
    }

    public ResponseMessage(boolean won, double winnings) {
        this.won = won;
        this.winnings = winnings;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(int serverNumber) {
        this.serverNumber = serverNumber;
    }

    public double getWinnings() {
        return winnings;
    }

    public void setWinnings(double winnings) {
        this.winnings = winnings;
    }

/*    public int getServerNumberCycles() {
        return serverNumberCycles;
    }

    public void setServerNumberCycles(int serverNumberCycles) {
        this.serverNumberCycles = serverNumberCycles;
    }*/
}
