package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.ScoreTrackingBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.MoveRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.ScoreTrackingBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonio99tv on 04/04/17.
 */

@Service("scoreTrackingBoardsService")
@Transactional
public class ScoreTrackingBoardService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ScoreTrackingBoardRepository scoreTrackingBoardRepo;

    @Autowired
    public ScoreTrackingBoardService(ScoreTrackingBoardRepository scoreTrackingBoardRepo) {
        this.scoreTrackingBoardRepo=scoreTrackingBoardRepo;
    }

    public Map<Long,Integer> getPoints(Long scoreTrackingBoardId){
        ScoreTrackingBoard scoreTrackingBoard = scoreTrackingBoardRepo.findById(scoreTrackingBoardId);
        Map<Long,Integer> tmp = scoreTrackingBoard.getScoreBoard();
        return tmp;
    }

    public Integer getUserPoints(Long scoreTrackingBoardId, Long userId){
        ScoreTrackingBoard scoreTrackingBoard = scoreTrackingBoardRepo.findById(scoreTrackingBoardId);
        Map<Long,Integer> tmp = scoreTrackingBoard.getScoreBoard();
        return tmp.get(userId);
    }
}
