package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilecipi on 27.03.17.
 */
@RestController
@RequestMapping(ch.uzh.ifi.seal.soprafs17.web.rest.RoundResource.CONTEXT)
public class RoundResource {

        Logger logger = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.web.rest.RoundResource.class);

        static final String CONTEXT = "/round";

        @Autowired
        private RoundService RoundService;

        @RequestMapping(method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public Iterable<Round> listRounds() {
            logger.debug("listRounds");
            return RoundService.listRounds();
        }


}
