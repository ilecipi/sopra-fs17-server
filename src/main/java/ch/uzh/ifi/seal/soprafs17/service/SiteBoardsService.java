package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.*;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.MoveRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.SiteBoardRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.NullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by erion on 21.03.17.
 */
@Service
@Transactional
public class SiteBoardsService {

    @Autowired
    private SiteBoardRepository siteBoardRepo;
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    public SiteBoardsService(SiteBoardRepository siteBoardRepo) {
        this.siteBoardRepo = siteBoardRepo;
    }



    public String addTemple(Long gameId){
        Game game = gameRepo.findOne(gameId);
        StoneBoard temple = new Temple(game.getPlayers().size());
        game.getSiteBoards().add(temple);
        temple.setGame(game);
        temple=siteBoardRepo.save(temple);

        gameRepo.save(game);
        return "/game/"+gameId + "/" + temple.getId();
    }

    public StoneBoard getTemple(Long templeId){
        StoneBoard temple = siteBoardRepo.findById(templeId);
        return temple;
    }

    public String addPyramid(Long gameId){
        Game game = gameRepo.findOne(gameId);
        StoneBoard pyramid = new Pyramid();
        game.getSiteBoards().add(pyramid);
        pyramid.setGame(game);
        pyramid = siteBoardRepo.save(pyramid);
        game = gameRepo.save(game);
        return "/game/+gameId" + "/" + pyramid.getId();
    }

    public String addObelisk(Long gameId){
        Game game = gameRepo.findOne(gameId);
        StoneBoard obelisk = new Obelisk(game.getPlayers().size());
        game.getSiteBoards().add(obelisk);
        obelisk.setGame(game);
        obelisk = siteBoardRepo.save(obelisk);
        game = gameRepo.save(game);
        return "/game/+gameId" + "/" + obelisk.getId();
    }

    public String addBurialChamber(Long gameId){
        Game game = gameRepo.findOne(gameId);
//        List<Stone> firstRow = new ArrayList<>();
//        List<Stone> secondRow = new ArrayList<>();
//        List<Stone> thirdRow = new ArrayList<>();
        StoneBoard burialChamber = new BurialChamber();
        game.getSiteBoards().add(burialChamber);
        burialChamber.setGame(game);
        burialChamber = siteBoardRepo.save(burialChamber);
        game=gameRepo.save(game);
        return "/game/+gameId" + "/" + burialChamber.getId();
    }

    public StoneBoard getObelisk(Long obeliskId){
        return siteBoardRepo.findById(obeliskId);
    }

    public StoneBoard getPyramid(Long pyramidId){
        return siteBoardRepo.findById(pyramidId);
    }

    public Map<String,Integer> getPyramidPoints(long pyramidId){
        SiteBoard pyramid =siteBoardRepo.findById(pyramidId);
        if(pyramid instanceof Pyramid){
            return ((Pyramid) pyramid).countAfterMove();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getObeliskPoints(long obeliskId){
        SiteBoard obelisk =siteBoardRepo.findById(obeliskId);
        if(obelisk instanceof Obelisk){
            return ((Obelisk) obelisk).countEndOfGame();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getTemplePoints(long templeId){
        SiteBoard temple =siteBoardRepo.findById(templeId);
        if(temple instanceof Temple){
            return ((Temple) temple).countEndOfRound();
        }else{
            throw new NullException();
        }
    }

    public Map<String,Integer> getBurialChamberPoints(long burialChamberId){
        SiteBoard burialChamber =siteBoardRepo.findById(burialChamberId);
        if(burialChamber instanceof BurialChamber){
            return ((BurialChamber) burialChamber).countEndOfGame();
        }else{
            throw new NullException();
        }
    }



}
