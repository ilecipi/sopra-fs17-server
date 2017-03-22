package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.TempleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erion on 21.03.17.
 */
@Service
@Transactional
public class SiteBoardsService {

    @Autowired
    private TempleRepository templeRepo;

    @Autowired
    private GameRepository gameRepo;



    public String addTemple(Long gameId){
        Game game = gameRepo.findOne(gameId);
        Temple temple = new Temple(game.getPlayers().size());
        System.out.println(game.getPlayers().size());
        game.getSiteBoards().add(temple);
        temple.setGame(game);
        temple=templeRepo.save(temple);

        gameRepo.save(game);
        return "/game/"+gameId + "/" + temple.getId();
    }

    public Temple getTemple(Long templeId){
        Temple temple = templeRepo.findOne(templeId);
        return temple;
    }

}
