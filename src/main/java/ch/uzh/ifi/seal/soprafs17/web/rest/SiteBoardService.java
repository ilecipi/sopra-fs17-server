package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.*;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ilecipi on 11.05.17.
 */
@RestController
public class SiteBoardService {

    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;
    static final String CONTEXT = "/games";

    /**
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/burialChamber")
    @ResponseStatus(HttpStatus.OK)
    public BurialChamberDTO getBurialChamber(@PathVariable Long gameId){
        BurialChamber burialChamber=gameRepo.findOne(gameId).getBurialChamber();
        return new BurialChamberDTO(burialChamber.getId(),burialChamber.getFirstRow(),
                                burialChamber.getSecondRow(),burialChamber.getThirdRow(),burialChamber.isOccupied());
    }

    /**
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/temple")
    @ResponseStatus(HttpStatus.OK)
    public TempleDTO getTemple(@PathVariable Long gameId) {
        Game game = gameRepo.findOne(gameId);
        Temple temple = gameRepo.findOne(gameId).getTemple();
        if(temple!=null) {
            TempleDTO templeDTO = new TempleDTO(temple.getId(), temple.getStones(), gameId, temple.isOccupied(), temple.getInsertIndex(), temple.getCompletedRows());
            return templeDTO;
        }else{
            return null;
        }
    }

    /**
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/pyramid")
    @ResponseStatus(HttpStatus.OK)
    public PyramidDTO getPyramid(@PathVariable Long gameId) {
        Pyramid pyramid = gameRepo.findOne(gameId).getPyramid();
        if(pyramid!=null) {
            return new PyramidDTO(pyramid.getId(),pyramid.getAddedStones(), pyramid.isOccupied());
        }
        return null;
    }

    /**
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/obelisk")
    @ResponseStatus(HttpStatus.OK)
    public ObeliskDTO getObelisk(@PathVariable Long gameId) {
        Obelisk obelisk = gameRepo.findOne(gameId).getObelisk();
        return new ObeliskDTO(obelisk.getId(),obelisk.getObelisks(),obelisk.isOccupied());
    }

    /**
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/market")
    @ResponseStatus(HttpStatus.OK)
    public MarketDTO getMarket(@PathVariable Long gameId) {
        Game game = gameRepo.findOne(gameId);
        Market market = siteBoardsService.getMarket(game);
        List<String> currentMarketCards = new ArrayList<>();
        for (AMarketCard mc : market.getMarketCards()){
            if(mc.isTaken()){
                currentMarketCards.add("IS_TAKEN-"+mc.getId());
            }else {
                currentMarketCards.add(mc.getCardType()+"-"+mc.getId());
            }
        }
        return new MarketDTO(market.getId(),market.isOccupied(),currentMarketCards, market.getUserColor());
    }

}
