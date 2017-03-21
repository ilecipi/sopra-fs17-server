package ch.uzh.ifi.seal.soprafs17.service.SiteBoardsServices;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.TempleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Class for managing the Temple
 */
@Service
@Transactional
public class TempleService {

    @Autowired
    private TempleRepository templeRepo;

    @Autowired
    private GameRepository gameRepo;



    public String addTemple(Long gameId){
        Game game = gameRepo.findOne(gameId);
        Temple temple = new Temple(game.getPlayers().size());
        templeRepo.save(temple);
        game.setTemple(temple);
        temple.setGame(game);
        gameRepo.save(game);
        templeRepo.save(temple);
        return "/game/{gameId}/" + temple.getId();
    }


    public Temple getTemple(Long templeId){
        Temple temple = templeRepo.findOne(templeId);
        return temple;
    }


}
