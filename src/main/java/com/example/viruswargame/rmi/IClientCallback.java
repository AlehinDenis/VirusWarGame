package com.example.viruswargame.rmi;

import com.example.viruswargame.model.CellState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientCallback extends Remote {
    void updateBoard(CellState[][] board) throws RemoteException;
}
