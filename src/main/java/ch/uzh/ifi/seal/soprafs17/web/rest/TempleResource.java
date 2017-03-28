package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
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

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/game/{gameId}/temple/{templeId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addStone(@PathVariable Long gameId,@PathVariable Long templeId,@RequestParam("playerToken") Long playerToken) {
        siteBoardsService.addStoneToTemple(templeId,playerToken,gameId);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/temple/{templeId}")
    @ResponseStatus(HttpStatus.OK)
    public Temple getTemple(@PathVariable Long templeId) {
        logger.debug("getTemple: " + templeId);

        return siteBoardsService.getTemple(templeId);
    }
}