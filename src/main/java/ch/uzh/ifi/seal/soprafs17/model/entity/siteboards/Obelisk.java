//package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;
//
//import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by erion on 20.03.17.
// */
//public class Obelisk extends StoneBoard {
//
//    private Map<Long,ArrayList<Stone>> obelisks = new HashMap<Long,ArrayList<Stone>>(){{
//        put(null,new ArrayList<Stone>());
//        put(null,new ArrayList<Stone>());
//        put(null,new ArrayList<Stone>());
//        put(null,new ArrayList<Stone>());
//    }};
//
//    public void addUser(Long userId){
//        if(!obelisks.containsKey(userId)){
//            obelisks.put(userId,new ArrayList<Stone>());
//        }
//    }
////    int counterBlack=0;
////    int counterWhite=0;
////    int counterBrown=0;
////    int counterGrey=0;
//
//    public void addStone(Stone stone,Long userId) {
//        if(obelisks.containsKey(userId)){
//            obelisks.get(userId).add(stone);
//        }
////        String stoneColor = stone.getColor();
////        if(stoneColor=="black"){
////            counterBlack++;
////        }else if(stoneColor=="white"){
////            counterWhite++;
////        }else if(stoneColor=="brown"){
////            counterBrown++;
////        }else if(stoneColor=="grey") {
////            counterGrey++;
////        }
//
//
//    }
//
//    @Override
//    public Map<Long, Integer> countAfterMove() {
//        return null;
//    }
//
//    @Override
//    public Map<Long, Integer> countEndOfRound() {
//        return null;
//    }
//
//    @Override
//    public Map<Long, Integer> countEndOfGame() {
//        return null;
//    }
//}
