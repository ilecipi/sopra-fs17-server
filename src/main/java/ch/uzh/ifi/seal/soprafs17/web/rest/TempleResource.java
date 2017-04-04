package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.StoneBoardDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.TempleDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.TempleService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericResource;
import ch.uzh.ifi.seal.soprafs17.web.rest.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by erion on 20.03.17.
 */
@RestController
public class TempleResource extends GenericResource {

    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    TempleService templeService;

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/temple/{templeId}")
    @ResponseStatus(HttpStatus.OK)
    public TempleDTO getTemple(@PathVariable Long templeId) {
        logger.debug("getTemple: " + templeId);
        StoneBoard tmpTemple = siteBoardsService.getTemple(templeId);
        Stone[] stones = ((Temple)siteBoardsService.getTemple(templeId)).getStones();
        Long gameId= ((Temple) tmpTemple).getGame().getId();
        boolean isOccupied = ((Temple)siteBoardsService.getTemple(templeId)).isOccupied();
        TempleDTO templeDTO = new TempleDTO((tmpTemple).getId(),stones,((Temple) tmpTemple).getAddedStones(),gameId,isOccupied);
        return templeDTO;
    }

//    @RequestMapping(value = CONTEXT + "/{gameId}/temple/{templeId}/user/{userId}/ship/{shipId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void sailShip(@PathVariable Long templeId, Long gameId, Long userId, Long shipId) {
////        logger.debug("getTemple: " + templeId);
//        templeService.sailShip(gameId,userId,shipId,templeId);
//    }
}