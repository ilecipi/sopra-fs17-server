package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ilecipi on 27.03.17.
 */
@RestController
public class RoundResource {

        Logger logger = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.web.rest.RoundResource.class);

        static final String CONTEXT = "/round";

        @Autowired
        private RoundService RoundService;

        @Autowired
        GameService gameService;

        @RequestMapping(method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public Iterable<Round> listRounds() {
            logger.debug("listRounds");
            return RoundService.listRounds();
        }

        @RequestMapping(value = CONTEXT + "/{gameId}",method = RequestMethod.PUT)
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void addRound(@PathVariable Long gameId){
            if(gameService.getGame(gameId).getRounds().size()>0){
                RoundService.addRound(gameId);
            }
        }
}
