package tech.kerok.portfolio.rps.service;

import tech.kerok.portfolio.rps.model.Move;
import tech.kerok.portfolio.rps.service.exceptions.MoveNotFoundException;

import java.util.List;
import java.util.Optional;

public class MoveChain {

    List<Move> moveSet;
    private MoveLink head;

    public MoveChain(List<Move> moveSet) {
        this.moveSet = moveSet;

        buildLoopedChain(moveSet);
    }

    private void buildLoopedChain(List<Move> moveSet) {

        head = new MoveLink(moveSet.get(0));
        MoveLink current = head;
        for (int i = 0; i < moveSet.size() -1; i++) {
            current.setHigher(new MoveLink(moveSet.get(i+1)));
            current = current.getHigher();
        }
        current.setHigher(head);
    }

    public GameResult evaluate(Move hostMove, Move guestMove) {

        if (hostMove.equals(guestMove)) {
            return GameResult.TIE;
        }

        MoveLink hostMoveLink = getMoveLink(head, hostMove).orElseThrow(() -> new MoveNotFoundException(hostMove));
        if (guestMove.equals(hostMoveLink.getHigher().getMove())) {
            return GameResult.GUEST_WINNER;
        }
        return GameResult.HOST_WINNER;
    }


    public Optional<MoveLink> getMoveLink(MoveLink current, Move hostMove) {
        MoveLink completeLoopBreaker = current;
        do {
            if(current.getMove().equals(hostMove)) {
                return Optional.of(current);
            }
            current = current.getHigher();
        } while (!current.getHigher().equals(completeLoopBreaker.getHigher()));

        return Optional.empty();
    }

    private static class MoveLink {

        private final Move move;
        private MoveLink higher;

        public MoveLink(Move move) {

            this.move = move;
        }

        public void setHigher(MoveLink moveLink) {
            higher = moveLink;
        }

        public MoveLink getHigher() {
            return higher;
        }

        public Move getMove() {
            return move;
        }
    }
}


