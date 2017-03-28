package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tonio99tv on 28/03/17.
 */
@Service
@Transactional
public class TempleService {

        private final Logger log = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.service.RoundService.class);

        private final TempleRepository templeRepository;

        @Autowired
        public TempleService(TempleRepository templeRepository) {
            this.templeRepository = templeRepository;
        }

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private GameRepository gameRepo;

        @Autowired
        private SiteBoardsService siteBoardsService;

        @Autowired
        private MoveRepository moveRepo;

        @Autowired
        private ShipService shipService;

        @Autowired
        private GameService gameService;

        @Autowired
        private ShipRepository shipRepository;

    //Player Sails Ship
    public void sailShip(Long gameId, Long userId, Long shipId, Long templeId){
        Game game  = gameRepo.findOne(gameId);
        User user = userRepo.findOne(userId);
        AShip ship = shipRepository.findOne(shipId);
        Temple temple = templeRepository.findOne(templeId);
        Round round = game.getCurrentRound();
        if(game.getCurrentPlayer()==user&&round.getShips().contains(ship)&&!temple.isOccupied()){
            Move move = new SailShipMove(user,ship,temple);
            move.makeMove(game);
            move.setUser(user);
            moveRepo.save(move);
            gameRepo.save(game);
            userRepo.save(user);
            shipRepository.save(ship);
            templeRepository.save(temple);
        }

    }
}
