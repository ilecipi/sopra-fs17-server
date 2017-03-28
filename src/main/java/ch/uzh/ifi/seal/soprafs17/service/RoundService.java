package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.*;
import ch.uzh.ifi.seal.soprafs17.model.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.ShipRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;

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

        private final RoundRepository roundRepository;

        @Autowired
        public RoundService(RoundRepository roundRepository) {
            this.roundRepository = roundRepository;
        }

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private SiteBoardsService siteBoardsService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private ShipRepository shipRepository;

    private final int MAX_ROUNDS_POSSIBLE=6;

    public List<Round> listRounds(){
        List<Round> result = new ArrayList<>();
        roundRepository.findAll().forEach(result::add);
        return result;
    }

    public void addRound(Long gameId){
        Game game = gameRepo.findOne(gameId);
        if(game.getRounds().size()<MAX_ROUNDS_POSSIBLE){
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
                    game=gameRepo.save(game);

                }
            }

            game.getRounds().add(round);
            gameRepo.save(game);
            round.setGame(game);
            roundRepository.save(round);

        }
    }


    }
