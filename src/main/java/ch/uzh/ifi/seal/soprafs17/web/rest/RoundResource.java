package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.RoundDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ilecipi on 27.03.17.
 */
@RestController
public class RoundResource {

        Logger logger = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.web.rest.RoundResource.class);

        static final String CONTEXT = "/games";

        @Autowired
        private RoundService RoundService;

        @Autowired
        GameService gameService;

        @RequestMapping(value = CONTEXT + "/{gameId}/rounds",method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<RoundDTO> listRounds(@PathVariable Long gameId) {
            logger.debug("listRounds");
            List<Round> rounds = RoundService.listRounds();
            List<RoundDTO> roundsDTO = new ArrayList<>();
            for(Round r : rounds){
                List<Long> movesId = new ArrayList<>();
                List<Long> shipsId = new ArrayList<>();
                for(Move m : r.getMoves()){
                    movesId.add(m.getId());
                }
                for(AShip s : r.getShips()){
                    shipsId.add(s.getId());
                }
                roundsDTO.add(new RoundDTO(r.getId(),r.getGame().getId(), movesId, shipsId));
            }
            return roundsDTO;
        }

         @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}",method = RequestMethod.GET)
         @ResponseStatus(HttpStatus.OK)
         public RoundDTO getSpecificRound(@PathVariable Long gameId,@PathVariable Long roundId) {
             logger.debug("listSpecificRound");
             Round r = RoundService.getSpecificRound(roundId);
             List<Long> movesId = new ArrayList<>();
             List<Long> shipsId = new ArrayList<>();
             for(Move m : r.getMoves()){
                 movesId.add(m.getId());
             }
             for(AShip s : r.getShips()){
                 shipsId.add(s.getId());
             }
             return new RoundDTO(r.getId(),r.getGame().getId(), movesId, shipsId);

         }

        @RequestMapping(value = CONTEXT + "/{gameId}/rounds",method = RequestMethod.PUT)
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void addRound(@PathVariable Long gameId){
            if(gameService.getGame(gameId).getRounds().size()>0){
                RoundService.addRound(gameId);
            }
        }
}
