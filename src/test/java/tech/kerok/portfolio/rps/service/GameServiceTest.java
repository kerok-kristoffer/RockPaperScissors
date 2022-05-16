package tech.kerok.portfolio.rps.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import tech.kerok.portfolio.rps.model.Move;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Test
    void rpsMoveRulesTest() {

        Logger logger = LoggerFactory.getLogger(GameServiceTest.class);

        ArrayList<Move> moveSet = new ArrayList<>(); // TODO kerok - create MoveSet enum with predefined MoveSets, allowing for extension?
        moveSet.add(Move.Rock);
        moveSet.add(Move.Paper);
        moveSet.add(Move.Scissors);
        MoveChain moveChain = new MoveChain(moveSet);

        logger.info("Testing evaluation of Rock moves");
        GameResult gameResult = moveChain.evaluate(Move.Rock, Move.Scissors);
        assertEquals(GameResult.HOST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Rock, Move.Paper);
        assertEquals(GameResult.GUEST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Rock, Move.Rock);
        assertEquals(GameResult.TIE, gameResult);

        logger.info("Testing evaluation of Scissors moves");
        gameResult = moveChain.evaluate(Move.Scissors, Move.Paper);
        assertEquals(GameResult.HOST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Scissors, Move.Rock);
        assertEquals(GameResult.GUEST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Scissors, Move.Scissors);
        assertEquals(GameResult.TIE, gameResult);

        logger.info("Testing evaluation of Paper moves");
        gameResult = moveChain.evaluate(Move.Paper, Move.Rock);
        assertEquals(GameResult.HOST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Paper, Move.Scissors);
        assertEquals(GameResult.GUEST_WINNER, gameResult);

        gameResult = moveChain.evaluate(Move.Paper, Move.Paper);
        assertEquals(GameResult.TIE, gameResult);

    }
}