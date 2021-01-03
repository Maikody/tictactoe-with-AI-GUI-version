package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Statistics {
    private static Statistics instance = new Statistics();
    private static String filename = "stats.txt";

    private static int xWinsTotalSum;
    private static int oWinsTotalSum;
    private static int drawsTotalSum;
    private static int gamesPlayed;

    private Board gameboard;

    private Statistics() {
    }

    public static Statistics getInstance() {
        return instance;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getXWinsTotalSum() {
        return xWinsTotalSum;
    }

    public int getOWinsTotalSum() {
        return oWinsTotalSum;
    }

    public  int getDrawsTotalSum() {
        return drawsTotalSum;
    }

    public void loadStatistics() throws IOException {
        gameboard = new Board();

        Path path = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input = br.readLine();
            String[] itemPieces = input.split("\\s+");
            String games = itemPieces[0];
            String xWinnings = itemPieces[1];
            String oWinnings = itemPieces[2];
            String draw = itemPieces[3];

            gamesPlayed = Integer.parseInt(games);
            xWinsTotalSum = Integer.parseInt(xWinnings);
            oWinsTotalSum = Integer.parseInt(oWinnings);
            drawsTotalSum = Integer.parseInt(draw);
        }
    }

    public void saveStatistics() throws IOException {
        Path path = Paths.get(filename);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(String.format("%s\t%s\t%s\t%s",
                    gamesPlayed + gameboard.getXWinningsCounter()
                            + gameboard.getOWinningsCounter()
                            + gameboard.getDrawsCounter(),
                    xWinsTotalSum + gameboard.getXWinningsCounter(),
                    oWinsTotalSum + gameboard.getOWinningsCounter(),
                    drawsTotalSum + gameboard.getDrawsCounter()));
        }
    }

}
