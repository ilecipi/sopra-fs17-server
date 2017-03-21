//package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;
//
//import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by erion on 20.03.17.
// */
//public class Pyramid extends StoneBoard {
////
////
////    final int[] points = {2,1,3,2,4,3,2,1,3,2,3,1,3,4};
////    List<Stone> addedStones=new ArrayList<>();
////    private Map<Long,Integer> score = new HashMap<Long,Integer>();
//////    private Map<Stone,Integer> firstColumn = new HashMap<Stone,Integer>(){{
//////        put(null,2);
//////        put(null,1);
//////        put(null,3);
//////        put(null,2);
//////        put(null,4);
//////        put(null,3);
//////        put(null,2);
//////        put(null,1);
//////        put(null,3);
//////        put(null,2);
//////        put(null,3);
//////        put(null,1);
//////        put(null,3);
//////        put(null,4);
//////    }};
////
//////    private Map<Stone,Integer> secondColumn = new HashMap<Stone,Integer>(){{
//////        put(null,2);
//////        put(null,4);
//////        put(null,3);
//////    }};
//////
//////    private Map<Stone,Integer> thirdColumn = new HashMap<Stone,Integer>(){{
//////        put(null,2);
//////        put(null,1);
//////        put(null,3);
//////    }};
//////
//////    private Map<Stone,Integer> secondLevelFirstColumn = new HashMap<Stone,Integer>(){{
//////        put(null,2);
//////        put(null,3);
//////    }};
//////
//////    private Map<Stone,Integer> secondLevelSecondColumn = new HashMap<Stone,Integer>(){{
//////        put(null,1);
//////        put(null,3);
//////    }};
//////
//////    private Map<Stone,Integer> thirdLevel = new HashMap<Stone,Integer>(){{
//////        put(null,4);
//////    }};
////
////
////    @Override
////    public void addStone(Stone stone) {
////        addedStones.add(stone);
////    }
////
////    @Override
////    public Map<Long, Integer> countAfterMove() {
//////        for(int i=0;i<addedStones.size();i++){
//////            currentScore = score.get(userId) + points[i];
//////            score.put(userId,currentScore);
//////        }
////        return null;
////    }
////
////    @Override
////    public Map<Long, Integer> countEndOfRound() {
////        return null;
////    }
////
////    @Override
////    public Map<Long, Integer> countEndOfGame() {
////        return null;
////    }
//}
