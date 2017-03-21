package ch.uzh.ifi.seal.soprafs17.web.rest.SiteBoardsResource;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsServices.TempleService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by erion on 20.03.17.
 */
@RestController
public class TempleResource extends GenericResource {

    @Autowired
    TempleService templeService;

    private final String CONTEXT = "/games/";

    @RequestMapping(value = CONTEXT + "/game/{gameId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addTemple(@RequestParam("gameId") Long gameId) {
        return CONTEXT + templeService.addTemple(gameId);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/temple/{templeId}")
    @ResponseStatus(HttpStatus.OK)
    public Temple getTemple(@PathVariable Long templeId) {
//        logger.debug("getTemple: " + templeId);

        return templeService.getTemple(templeId);
    }
}
