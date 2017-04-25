package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.PyramidDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.TempleDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Pyramid;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
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
public class PyramidResource {
    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;

    @Autowired
    SiteBoardRepository siteBoardRepo;

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/pyramid")
    @ResponseStatus(HttpStatus.OK)
    public PyramidDTO getPyramid(@PathVariable Long gameId) {
        Pyramid pyramid = gameRepo.findOne(gameId).getPyramid();
        if (pyramid != null) {
            return new PyramidDTO(pyramid.getId(), pyramid.getAddedStones(), pyramid.isOccupied());
        }
        return null;
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/pyramid/points")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> getPyramidPoints(@PathVariable Long gameId) {
        Pyramid pyramid = gameRepo.findOne(gameId).getPyramid();
        return siteBoardsService.getPyramidPoints(pyramid.getId());
    }

}
