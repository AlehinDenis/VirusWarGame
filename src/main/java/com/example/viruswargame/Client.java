package com.example.viruswargame;

import com.example.viruswargame.model.Player;
import com.example.viruswargame.rmi.IGame;
import com.example.viruswargame.service.BoardService;
import com.example.viruswargame.service.ClientService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static com.example.viruswargame.utils.Constants.PLAYER_O;
import static com.example.viruswargame.utils.Constants.PLAYER_X;

public class Client extends Application {
    private BoardService boardService;
    private Player player;
    private IGame stub;

    @Override
    public void start(Stage stage) throws Exception {
        stub = getStub();
        setUpBoard();
        ClientService clientService = new ClientService(boardService);
        stub.registerForCallback(clientService);
        setUpStage(stage);
    }

    private void setUpStage(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPrefSize(400, 400);
        root.setCenter(boardService);
        Scene scene = new Scene(root);
        switch(player) {
            case PLAYER_X -> stage.setTitle(PLAYER_X);
            case PLAYER_O -> stage.setTitle(PLAYER_O);
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private IGame getStub() throws NotBoundException, RemoteException{
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");
        return (IGame) registry.lookup("GameServer");
    }

    private void setUpBoard() throws Exception{
        player = stub.getPlayer();

        boardService = new BoardService(event -> {
            try {
                BoardService.Cell cell = (BoardService.Cell)event.getSource();
                stub.makeMove(cell.getYCoord() + 1, cell.getXCoord() + 1, player);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}