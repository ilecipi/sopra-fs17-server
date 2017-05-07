package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.*;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.SiteBoardRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.NullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by erion on 21.03.17.
 */
@Service("siteBoardsService")
@Transactional
public class SiteBoardsService {

    @Autowired
    private SiteBoardRepository siteBoardRepository;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public SiteBoardsService(SiteBoardRepository siteBoardRepo) {
        this.siteBoardRepository = siteBoardRepo;
    }



    public String addTemple(Long gameId){
        Game game = gameRepository.findOne(gameId);
        StoneBoard temple = new Temple(game.getPlayers().size());
        game.getSiteBoards().add(temple);
        temple.setGame(game);
        temple=siteBoardRepository.save(temple);

        gameRepository.save(game);
        return "/game/"+gameId + "/" + temple.getId();
    }

    public String addPyramid(Long gameId){
        Game game = gameRepository.findOne(gameId);
        StoneBoard pyramid = new Pyramid();
        game.getSiteBoards().add(pyramid);
        pyramid.setGame(game);
        pyramid = siteBoardRepository.save(pyramid);
        game = gameRepository.save(game);
        return "/game/+gameId" + "/" + pyramid.getId();
    }

    public String addObelisk(Long gameId){
        Game game = gameRepository.findOne(gameId);
        StoneBoard obelisk = new Obelisk(game.getPlayers().size());
        game.getSiteBoards().add(obelisk);
        obelisk.setGame(game);
        obelisk = siteBoardRepository.save(obelisk);
        game = gameRepository.save(game);
        return "/game/+gameId" + "/" + obelisk.getId();
    }

    public String addBurialChamber(Long gameId){
        Game game = gameRepository.findOne(gameId);
        StoneBoard burialChamber = new BurialChamber();
        game.getSiteBoards().add(burialChamber);
        burialChamber.setGame(game);
        burialChamber = siteBoardRepository.save(burialChamber);
        game=gameRepository.save(game);
        return "/game/+gameId" + "/" + burialChamber.getId();
    }



    public Map<String,Integer> getPyramidPoints(long pyramidId){
        SiteBoard pyramid =siteBoardRepository.findById(pyramidId);
        if(pyramid instanceof Pyramid){
            return ((Pyramid) pyramid).countAfterMove();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getObeliskPoints(long obeliskId){
        SiteBoard obelisk =siteBoardRepository.findById(obeliskId);
        if(obelisk instanceof Obelisk){
            return ((Obelisk) obelisk).countEndOfGame();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getTemplePoints(long templeId){
        SiteBoard temple =siteBoardRepository.findById(templeId);
        if(temple instanceof Temple){
            return ((Temple) temple).countEndOfRound();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getBurialChamberPoints(long burialChamberId){
        SiteBoard burialChamber =siteBoardRepository.findById(burialChamberId);
        if(burialChamber instanceof BurialChamber){
            return ((BurialChamber) burialChamber).countEndOfGame();
        }else{
            throw new NullException();
        }
    }

    public String addMarket(Long gameId){
        Game game = gameRepository.findOne(gameId);
        SiteBoard market = new Market();
        game.getSiteBoards().add(market);
        market.setGame(game);
        market = siteBoardRepository.save(market);
        game=gameRepository.save(game);
        return "/game/+gameId" + "/" + market.getId();
    }

    public List<AMarketCard> getMarketCards(Long gameId){
        Game game = gameRepository.findOne(gameId);
        List<SiteBoard> siteBoards = game.getSiteBoards();
        Market market = new Market();
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    market= (Market) s;
                }
            }
        }
        return market.getMarketCards();
    }

    public Market getMarket(Game game){
        List<SiteBoard> siteBoards = game.getSiteBoards();
        Market market = null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    market= (Market) s;
                }
            }
        }
        return market;
    }

    public BurialChamber getBurialChamber(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if(game!=null){
            return game.getBurialChamber();
        }
        return null;
    }

    public Temple getTemple(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if(game!=null){
            return game.getTemple();
        }
        return null;
    }

    public Market getMarket(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if(game!=null){
            return game.getMarket();
        }
        return null;
    }

    public Pyramid getPyramid(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if(game!=null){
            return game.getPyramid();
        }
        return null;
    }

    public Obelisk getObelisk(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if(game!=null){
            return game.getObelisk();
        }
        return null;
    }








}
