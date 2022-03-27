package com.example.viruswargame.service;

import com.example.viruswargame.model.CellState;
import com.example.viruswargame.rmi.IClientCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements IClientCallback{
    private final BoardService boardService;

    public ClientService(BoardService boardService) throws Exception{
        this.boardService = boardService;
    }

    @Override
    public void updateBoard(CellState[][] board) throws RemoteException {
        boardService.setBoard(board);
    }
}
