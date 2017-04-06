package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.BurialChamberDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.BurialChamber;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by erion on 06.04.17.
 */
@RestController
public class BurialChamberResource {

    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;
    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/burialChamber")
    @ResponseStatus(HttpStatus.OK)
    public BurialChamberDTO getBurialChamber(@PathVariable Long gameId){
        List<SiteBoard> siteBoards = gameRepo.findOne(gameId).getSiteBoards();
        BurialChamber burialChamber=null;
        if(!siteBoards.isEmpty()){
            for(SiteBoard s: siteBoards){
                if(s.getDiscriminatorValue().equals("burialChamber")){
                    burialChamber=(BurialChamber)s;
                }
            }
        }
        BurialChamberDTO burialChamberDTO= new BurialChamberDTO(burialChamber.getId(),burialChamber.getFirstRow(),
                                burialChamber.getSecondRow(),burialChamber.getThirdRow(),burialChamber.isOccupied());
        return burialChamberDTO;
    }


}
