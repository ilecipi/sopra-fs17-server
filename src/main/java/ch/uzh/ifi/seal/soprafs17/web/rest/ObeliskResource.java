package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.ObeliskDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Obelisk;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Pyramid;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.SiteBoardRepository;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ilecipi on 05.04.17.
 */
@RestController
public class ObeliskResource {
    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;

    @Autowired
    SiteBoardRepository siteBoardRepo;

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/obelisk")
    @ResponseStatus(HttpStatus.OK)
    public ObeliskDTO getObelisk(@PathVariable Long gameId) {
        Obelisk obelisk = gameRepo.findOne(gameId).getObelisk();
        return new ObeliskDTO(obelisk.getId(),obelisk.getObelisks(),obelisk.isOccupied());
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/obelisk/points")
    @ResponseStatus(HttpStatus.OK)
    public Map<String,Integer> getObeliskPoints(@PathVariable Long gameId) {
        Obelisk obelisk = gameRepo.findOne(gameId).getObelisk();
        return siteBoardsService.getObeliskPoints(obelisk.getId());
    }
}
