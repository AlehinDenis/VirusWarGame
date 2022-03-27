package com.example.viruswargame.service;

import com.example.viruswargame.model.Player;

public class TurnService {
    private int turn = 0;
    private Player player = Player.PLAYER_X;

    public void makeMove() {
        turn++;
        if(turn == 3) {
            player = Player.PLAYER_O;
        } else if(turn == 6) {
            turn = 0;
            player = Player.PLAYER_X;
        }
    }

    public Player getTurn() {
        return player;
    };
}
