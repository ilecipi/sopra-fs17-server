package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.ShipFactory;
import ch.uzh.ifi.seal.soprafs17.model.repository.ShipRepository;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ilecipi on 22.03.17.
 */
@RestController
public class ShipResource {

        @Autowired
        ShipService shipService;

        @Autowired
        ShipRepository shipRepository;


        Logger logger = LoggerFactory.getLogger(ShipResource.class);

        static final String CONTEXT = "/games";

        @RequestMapping(value = CONTEXT + "/rounds/{roundId}")
        @ResponseStatus(HttpStatus.OK)
        public List<AShip> getShips(@PathVariable Long roundId) {
                logger.debug("getShips: " + roundId);

                return shipService.getShips(roundId);
        }

        @RequestMapping(value = CONTEXT + "/rounds/{roundId}/{shipId}")
        @ResponseStatus(HttpStatus.OK)
        public AShip getShip(@PathVariable Long roundId,@PathVariable Long shipId) {
                logger.debug("getShip: " + shipId);

                return shipService.getShip(roundId,shipId);
        }
}

