package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.*;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ilecipi on 27.03.17.
 */
@Service
@Transactional
public class RoundService {

    private final Logger log = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.service.RoundService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SiteBoardsService siteBoardsService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    private final int MAX_ROUNDS_POSSIBLE=6;

    public boolean isAllShipsSailed() {
        return allShipsSailed;
    }

    public void setAllShipsSailed(boolean allShipsSailed) {
        this.allShipsSailed = allShipsSailed;
    }

    private boolean allShipsSailed;

    public List<Round> listRounds(){
        List<Round> result = new ArrayList<>();
        roundRepository.findAll().forEach(result::add);
        return result;
    }

    public Round getSpecificRound(Long roundId){
        return roundRepository.findById(roundId);
    }

    public void addRound(Long gameId){
        Game game = gameRepository.findOne(gameId);

        Round round = new Round();
        round.setShips(new ArrayList<AShip>());
        roundRepository.save(round);
        boolean notChosen = true;
        Map<Integer, Integer[]> shipsCards = game.getShipsCards();
        Random rn = new Random();
        int selectShip;
        ShipFactory shipFactory = new ShipFactory();
        while(notChosen){
            selectShip = rn.nextInt()%6;
            if(shipsCards.containsKey(selectShip)){
                for(Integer i : shipsCards.get(selectShip)){
                    if(i==1){
                        round.getShips().add(shipRepository.save(new OneSeatedShip()));
                    }else if(i==2){
                        round.getShips().add(shipRepository.save(new TwoSeatedShip()));
                    }else if(i==3){
                        round.getShips().add(shipRepository.save(new ThreeSeatedShip()));
                    }else{
                        round.getShips().add(shipRepository.save(new FourSeatedShip()));
                    }

                }
                notChosen=false;
                shipsCards.remove(selectShip);
                round = roundRepository.save(round);
                game=gameRepository.save(game);

            }
        }

        game.getRounds().add(round);
        gameRepository.save(game);
        round.setGame(game);
        roundRepository.save(round);


    }

    public void addRounds(Long gameId){
        Game game = gameService.getGame(gameId);
        //check if it is not the first round
        if(game.getRounds().size()>0){
            Round round = game.getRounds().get(game.getRounds().size()-1);
            if(game.getRounds().size()<MAX_ROUNDS_POSSIBLE && round.getShips().isEmpty() ){
                addRound(gameId);
            }

        }
        //in case it is the first round
        else{
            addRound(gameId);
        }
    }



}
