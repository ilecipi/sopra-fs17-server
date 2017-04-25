package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ilecipi on 22.03.17.
 */

@Service("shipService")
@Transactional
public class ShipService {

        @Autowired
        private ShipRepository shipRepository;

        @Autowired
        private GameRepository gameRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoundRepository roundRepository;

        @Autowired
        public ShipService(ShipRepository shipRepository) {
                this.shipRepository = shipRepository;
        }

        public List<AShip> getShips(Long roundId){
               List<AShip> shipsInRound = roundRepository.findById(roundId).getShips();
               return shipsInRound;
        }

        public AShip getShip(Long roundId,Long shipId){
                if(roundRepository.findById(roundId).getShips().contains(shipRepository.findById(shipId))){
                        return shipRepository.findById(shipId);
                }
                        return null;
        }
}

