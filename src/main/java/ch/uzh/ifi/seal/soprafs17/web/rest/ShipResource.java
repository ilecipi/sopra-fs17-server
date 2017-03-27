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

        @RequestMapping(value = CONTEXT + "/{gameId}/ships/")
        @ResponseStatus(HttpStatus.OK)
        public IShip getShips(@PathVariable Long gameId) {
                logger.debug("getShips: " + gameId);

                return null;
        }

        @RequestMapping(value = CONTEXT + "/{gameId}/ships/", method = RequestMethod.POST)
        @ResponseStatus(HttpStatus.OK)
        public IShip addShip() {
         return this.shipRepository.save(new OneSeatedShip());
        }

        @RequestMapping(value = CONTEXT + "/{gameId}/ship/{shipId}")
        @ResponseStatus(HttpStatus.OK)
        public IShip getShip(@PathVariable Long gameId) {
                logger.debug("getShips: " + gameId);

                return null;
        }

        @RequestMapping(value = CONTEXT + "/{gameId}/ship/{shipId}", method = RequestMethod.PUT)
        @ResponseStatus(HttpStatus.OK)
        public void addStone(@PathVariable Long gameId,@PathVariable Long shipId,@RequestParam("playerToken") Long playerToken,@RequestParam("position") int position) {
//                shipService.addStone(gameId,shipId,playerToken,position);
        }
}

