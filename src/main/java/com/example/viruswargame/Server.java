package com.example.viruswargame;

import com.example.viruswargame.rmi.IGame;
import com.example.viruswargame.service.GameService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            IGame stub = (IGame) UnicastRemoteObject.exportObject(new GameService(), 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("GameServer", stub);
            System.out.println("Server started");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
