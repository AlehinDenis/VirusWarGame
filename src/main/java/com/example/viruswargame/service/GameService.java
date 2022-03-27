package com.example.viruswargame.service;

import com.example.viruswargame.model.CellState;
import com.example.viruswargame.model.Player;
import com.example.viruswargame.rmi.IClientCallback;
import com.example.viruswargame.rmi.IGame;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.example.viruswargame.utils.Constants.PLAYER_O;
import static com.example.viruswargame.utils.Constants.PLAYER_X;

public class GameService implements IGame {
    private final  CellState[][] board = new CellState[12][12];
    private final TurnService turnService = new TurnService();
    private final List<IClientCallback> clients = new ArrayList<>();
    private int connectedPlayers = 0;

    {
        for(int x = 0; x < 12; x++) {
            for(int y = 0; y < 12; y++) {
                board[x][y] = CellState.NOT_PRESSED;
            }
        }
    }

    @Override
    public void makeMove(int x, int y, Player player) throws RemoteException {
        if(isIgnoreMove(x, y, player)) {
            return;
        } else if(isFirstMoveX(x, y, player)) {
            board[10][1] = CellState.X_PRESSED;
            turnService.makeMove();
        } else if(isFirstMoveO(x, y, player)) {
            board[1][10] = CellState.O_PRESSED;
            turnService.makeMove();
        } else if (isMoveX(player)) {
            if (isPickedRightCellX(x, y)) {
                if (board[x][y] == CellState.O_PRESSED) {
                    board[x][y] = CellState.O_KILLED;
                } else {
                    board[x][y] = CellState.X_PRESSED;
                }
                turnService.makeMove();
            }
        } else if(isMoveO(player)) {
            if (isPickedRightCellO(x, y)) {
                if (board[x][y] == CellState.X_PRESSED) {
                    board[x][y] = CellState.X_KILLED;
                } else {
                    board[x][y] = CellState.O_PRESSED;
                }
                turnService.makeMove();
            }
        }
        notifyClients();
    }

    @Override
    public Player getPlayer() throws Exception{
        switch(connectedPlayers) {
            case 0 -> {

                connectedPlayers++;
                System.out.println(PLAYER_X + " connected");
                return Player.PLAYER_X;
            }
            case 1 -> {
                System.out.println(PLAYER_O + " connected");
                connectedPlayers++;
                return Player.PLAYER_O;
            }
            default -> throw new Exception("Error: 2 players is already connected");
        }
    }

    @Override
    public void registerForCallback(IClientCallback client) throws RemoteException{
        clients.add(client);
    }

    private void notifyClients() throws RemoteException{
        for(IClientCallback client: clients) {
            client.updateBoard(board);
        }
    }

    private boolean isIgnoreMove(int x, int y, Player player) {
        return board[x][y] == CellState.X_KILLED ||
                board[x][y] == CellState.O_KILLED ||
                (board[x][y] == CellState.X_PRESSED || board[x][y] == CellState.O_KILLED) && player == Player.PLAYER_X ||
                (board[x][y] == CellState.O_PRESSED || board[x][y] == CellState.X_KILLED) && player == Player.PLAYER_O;
    }

    private boolean isFirstMoveX(int x, int y, Player player) {
        return x == 10 && y == 1 &&
                player == Player.PLAYER_X &&
                turnService.getTurn() == Player.PLAYER_X;
    }

    private boolean isFirstMoveO(int x, int y, Player player) {
        return x == 1 && y == 10 &&
                player == Player.PLAYER_O &&
                turnService.getTurn() == Player.PLAYER_O;
    }

    private boolean isMoveX(Player player) {
        return turnService.getTurn() == Player.PLAYER_X &&
                player == Player.PLAYER_X;
    }

    private boolean isMoveO(Player player) {
        return turnService.getTurn() == Player.PLAYER_O &&
                player == Player.PLAYER_O;
    }

    private boolean isPickedRightCellX(int x, int y) {
        return board[x][y - 1] == CellState.X_PRESSED || board[x][y + 1] == CellState.X_PRESSED || board[x + 1][y] == CellState.X_PRESSED ||
                board[x - 1][y] == CellState.X_PRESSED || board[x + 1][y + 1] == CellState.X_PRESSED || board[x + 1][y - 1] == CellState.X_PRESSED ||
                board[x - 1][y + 1] == CellState.X_PRESSED || board[x - 1][y - 1] == CellState.X_PRESSED ||
                board[x][y - 1] == CellState.O_KILLED || board[x][y + 1] == CellState.O_KILLED || board[x + 1][y] == CellState.O_KILLED ||
                board[x - 1][y] == CellState.O_KILLED || board[x + 1][y + 1] == CellState.O_KILLED || board[x + 1][y - 1] == CellState.O_KILLED ||
                board[x - 1][y + 1] == CellState.O_KILLED || board[x - 1][y - 1] == CellState.O_KILLED;
    }

    private boolean isPickedRightCellO(int x, int y) {
        return board[x][y - 1] == CellState.O_PRESSED || board[x][y + 1] == CellState.O_PRESSED || board[x + 1][y] == CellState.O_PRESSED ||
                board[x - 1][y] == CellState.O_PRESSED || board[x + 1][y + 1] == CellState.O_PRESSED || board[x + 1][y - 1] == CellState.O_PRESSED ||
                board[x - 1][y + 1] == CellState.O_PRESSED || board[x - 1][y - 1] == CellState.O_PRESSED ||
                board[x][y - 1] == CellState.X_KILLED || board[x][y + 1] == CellState.X_KILLED || board[x + 1][y] == CellState.X_KILLED ||
                board[x - 1][y] == CellState.X_KILLED || board[x + 1][y + 1] == CellState.X_KILLED || board[x + 1][y - 1] == CellState.X_KILLED ||
                board[x - 1][y + 1] == CellState.X_KILLED || board[x - 1][y - 1] == CellState.X_KILLED;
    }
}
