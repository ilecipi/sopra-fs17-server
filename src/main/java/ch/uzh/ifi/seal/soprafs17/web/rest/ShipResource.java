package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.ShipFactory;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ilecipi on 22.03.17.
 */


@RestController
public class ShipResource {
        @Autowired
        ShipService shipService;


        Logger logger = LoggerFactory.getLogger(ShipResource.class);

        static final String CONTEXT = "/games";

        @RequestMapping(value = CONTEXT + "/{gameId}/ships/")
        @ResponseStatus(HttpStatus.OK)
        public ShipFactory getShips(@PathVariable Long gameId) {
        logger.debug("getShips: " + gameId);

            return shipService.getShips(gameId);
        }

//        @RequestMapping(value = CONTEXT + "/{gameId}/ship/{shipId}")
//        @ResponseStatus(HttpStatus.OK)
//        public ShipFactory getShip(@PathVariable Long gameId) {
//                logger.debug("getShips: " + gameId);
//
//                return shipService.getShips(gameId);
//        }
}

