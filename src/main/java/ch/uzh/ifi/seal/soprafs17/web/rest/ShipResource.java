package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.ShipDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.ShipFactory;
import ch.uzh.ifi.seal.soprafs17.model.repository.ShipRepository;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 22.03.17.
 */
@RestController
public class ShipResource {

        @Autowired
        ShipService shipService;

        @Autowired
        ShipRepository shipRepository;


        Logger logger = LoggerFactory.getLogger(ShipResource.class);

        static final String CONTEXT = "/games";

        @RequestMapping(value = CONTEXT + "/rounds/{roundId}/ships")
        @ResponseStatus(HttpStatus.OK)
        public List<ShipDTO> getShips(@PathVariable Long roundId) {
                logger.debug("getShips: " + roundId);

                List<AShip> ships =  shipService.getShips(roundId);
                List<ShipDTO> shipsDTO = new ArrayList<>();
                for(AShip s : ships){
                        if(s.getStones()!=null&& s.getSiteBoard()!=null) {
                                shipsDTO.add(new ShipDTO(s.getId(), s.getStones(), s.isReady(), s.getAddedStones(), s.isDocked(), s.getSiteBoard().getId()));
                        }else if(s.getStones()!=null&&s.getSiteBoard()==null) {
                                shipsDTO.add(new ShipDTO(s.getId(), s.getStones(), s.isReady(), s.getAddedStones(), s.isDocked(), null));
                        }
                        else{
                                shipsDTO.add(new ShipDTO(s.getId(),  null, s.isReady(), s.getAddedStones(), s.isDocked(), null));
                        }
                }
                return shipsDTO;
//                return  shipService.getShips(roundId);
        }

        @RequestMapping(value = CONTEXT + "/rounds/{roundId}/ships/{shipId}")
        @ResponseStatus(HttpStatus.OK)
        public ShipDTO getShip(@PathVariable Long roundId,@PathVariable Long shipId) {
                logger.debug("getShip: " + shipId);

//                return shipService.getShip(roundId,shipId);
                AShip s = shipService.getShip(roundId,shipId);
                if(s.getStones()!=null&&s.getSiteBoard()!=null) {
                        return new ShipDTO(s.getId(), s.getStones(), s.isReady(), s.getAddedStones(), s.isDocked(), s.getSiteBoard().getId());
                }else if(s.getStones()!=null&&s.getSiteBoard()==null){
                        return new ShipDTO(s.getId(), s.getStones(), s.isReady(), s.getAddedStones(), s.isDocked(), null);
                }else{
                        return new ShipDTO(s.getId(), null, s.isReady(), s.getAddedStones(), s.isDocked(), null);
                }
        }
}

