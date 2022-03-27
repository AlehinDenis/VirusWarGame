package com.example.viruswargame.rmi;

import com.example.viruswargame.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {
    void makeMove(int x, int y, Player player) throws RemoteException;
    Player getPlayer() throws Exception;
    void registerForCallback(IClientCallback client) throws RemoteException;
}
