//package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;
//
//import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
//
//import java.util.ArrayList;
//import java.util.Map;
//
///**
// * Created by erion on 20.03.17.
// */
//public class BurialChamber extends StoneBoard{
//
//    private ArrayList<Stone> firstRow = new ArrayList<>();
//    private ArrayList<Stone> secondRow = new ArrayList<>();
//    private ArrayList<Stone> thirdRow = new ArrayList<>();
//
//    int columnCounter = 0;
//
//
//    @Override
//    public void addStone(Stone stone) {
//
//        if(this.firstRow.get(columnCounter)== null){
//            firstRow.add(stone);
//        }else if(this.secondRow.get(columnCounter)==null){
//            secondRow.add(stone);
//        }else if(this.thirdRow.get(columnCounter)==null){
//            thirdRow.add(stone);
//            columnCounter++;
//        }
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
//
//}
