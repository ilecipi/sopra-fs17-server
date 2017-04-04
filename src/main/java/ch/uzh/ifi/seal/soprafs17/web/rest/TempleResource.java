package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.StoneBoardDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.TempleDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.TempleService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericResource;
import ch.uzh.ifi.seal.soprafs17.web.rest.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by erion on 20.03.17.
 */
@RestController
public class TempleResource extends GenericResource {

    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/temple")
    @ResponseStatus(HttpStatus.OK)
    public TempleDTO getTemple(@PathVariable Long gameId) {
        List<SiteBoard> siteBoards = gameRepo.findOne(gameId).getSiteBoards();
        Temple tmpTemple=null;
        for(SiteBoard s:siteBoards){
            if(s.getDiscriminatorValue().equals("temple")){
                tmpTemple = (Temple)s;
            }
        }
        if(tmpTemple == null) {
            return null;
        }
        Stone[] stones = tmpTemple.getStones();
        boolean isOccupied = tmpTemple.isOccupied();
        TempleDTO templeDTO = new TempleDTO(tmpTemple.getId(),stones,gameId,isOccupied);
        logger.debug("getTemple: " + tmpTemple.getId());
        return templeDTO;
    }
}