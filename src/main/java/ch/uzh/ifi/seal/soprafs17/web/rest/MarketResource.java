package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.MarketDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.SiteBoardRepository;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonio99tv on 08/04/17.
 */

@RestController
public class MarketResource {
    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    GameRepository gameRepo;

    @Autowired
    SiteBoardRepository siteBoardRepo;

    @Autowired
    RoundRepository roundRepository;

    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/market")
    @ResponseStatus(HttpStatus.OK)
    public MarketDTO getMarket(@PathVariable Long gameId) {
        Game game = gameRepo.findOne(gameId);
        int currentRound = game.getRounds().size()-1;
        Market market = siteBoardsService.getMarket(game);
        List<String> currentMarketCards = new ArrayList<>();
        for (AMarketCard mc : market.getMarketCards()){
            currentMarketCards.add(mc.getCardType());
        }
        return new MarketDTO(market.getId(),market.isOccupied(),currentMarketCards, market.getUserColor());
    }


}
