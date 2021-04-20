package by.golik.dealerstat.controller;

import by.golik.dealerstat.repository.GameRepository;
import by.golik.dealerstat.service.impl.GameServiceImpl;

/**
 * @author Nikita Golik
 */
public class Main {
    public static void main(String[] args) {
        GameServiceImpl gameService = new GameServiceImpl();

        System.out.println(gameService.findAllGames());

    }
}
