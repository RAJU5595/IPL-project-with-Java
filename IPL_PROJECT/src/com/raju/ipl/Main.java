package com.raju.ipl;

import java.io.File;
import java.util.*;

public class Main {

    public static final int ID = 0;

    public static final int SEASON = 1;

    public static final int CITY = 2;

    public static final int DATE = 3;

    public static final int MATCH_TEAM1 = 4;

    public static final int MATCH_TEAM2 = 5;

    public static final int TOSS_WINNER = 6;

    public static final int TOSS_DECISION = 7;

    public static final int RESULT = 8;

    public static final int DL_APPLIED = 9;

    public static final int WINNER = 10;

    public static final int WIN_BY_RUNS = 11;

    public static final int WIN_BY_WICKETS = 12;

    public static final int PLAYER_OF_THE_MATCH = 13;

    public static final int VENUE = 14;

    public static final int UMPIRE1 = 15;

    public static final int UMPIRE2 = 16;

    public static final int UMPIRE3 = 17;

    public static final int MATCH_ID = 0;

    public static final int INNING = 1;

    public static final int BATTING_TEAM = 2;

    public static final int BOWLING_TEAM = 3;

    public static final int OVER = 4;

    public static final int BALL = 5;

    public static final int BATSMAN = 6;

    public static final int NON_STRIKER = 7;

    public static final int BOWLER = 8;

    public static final int IS_SUPER_OVER = 9;

    public static final int WIDE_RUNS = 10;

    public static final int BYE_RUNS = 11;

    public static final int LEGBYE_RUNS = 12;

    public static final int NOBALL_RUNS = 13;

    public static final int PENALTY_RUNS = 14;

    public static final int BATSMAN_RUNS = 15;

    public static final int EXTRA_RUNS = 16;

    public static final int TOTAL_RUNS = 17;

    public static final int PLAYER_DISMISSED = 18;

    public static final int DISMISSAL_KIND = 19;

    public static final int FIELDER = 20;

    static HashMap<String, Integer> noOfMatchesPlayedPerYearOfAllTheYears = new HashMap<>();
    static HashMap<String, Integer> noOfMatchesWonOfAllTeamsOverAllTheYears = new HashMap<>();
    static HashMap<String, Integer> extraRunsConcededPerTeamIn2016 = new HashMap<>();

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYearOfAllTheYears(matches);
        findNumberOfMatchesWonPerTeamInAllSeasons(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
        findMostEconomicalBowlerIn2015(matches, deliveries);
        findHighestRunScorerInSeason2015(matches, deliveries);
        findHighestScorerAndHighestWicketTaker(deliveries);
    }

    private static void findHighestScorerAndHighestWicketTaker(List<Delivery> deliveries) {
        Map <String,Integer> batsManStatistics = new HashMap<>();
        Map<String,Integer> bowlerStatistics = new HashMap<>();

        for(Delivery delivery : deliveries){
            if(Objects.equals(delivery.getMatchId(), "1")){
                if(batsManStatistics.containsKey(delivery.getBatsman())){
                    batsManStatistics.put(delivery.getBatsman(),batsManStatistics.get(delivery.getBatsman())+Integer.parseInt(delivery.getBatsmanRuns()));
                }
                else {
                    batsManStatistics.put(delivery.getBatsman(), Integer.parseInt(delivery.getBatsmanRuns()));
                }
                if(delivery.getPlayerDismissed() != null){
                    if(bowlerStatistics.containsKey(delivery.getBowler())){
                        bowlerStatistics.put(delivery.getBowler(),bowlerStatistics.get(delivery.getBowler())+1);
                    }
                    else {
                        bowlerStatistics.put(delivery.getBowler(), 1);
                    }
                }
            }
        }

        List <Map.Entry<String ,Integer>> batsManStatisticsList = new ArrayList<>();
        batsManStatisticsList.addAll(batsManStatistics.entrySet());
        Collections.sort(batsManStatisticsList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> object1, Map.Entry<String, Integer> object2) {
                return object2.getValue().compareTo(object1.getValue());
            }
        });

        List <Map.Entry<String ,Integer>> bowlerStatisticsList = new ArrayList<>();
        bowlerStatisticsList.addAll(bowlerStatistics.entrySet());
        Collections.sort(bowlerStatisticsList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> object1, Map.Entry<String, Integer> object2) {
                return object2.getValue().compareTo(object1.getValue());
            }
        });

        System.out.println("Highest Run Scorer in Match with matchId = 1 :");
        System.out.println(batsManStatisticsList.get(0));
        System.out.println("Highest Wicket Taker in Match with matchId = 1 :");
        System.out.println(bowlerStatisticsList.get(0));
    }

    private static void findHighestRunScorerInSeason2015(List<Match> matches, List<Delivery> deliveries) {
        List<String> matchIdsIn2015 = new ArrayList<>();
        HashMap<String, Integer> individualBatsmanRunsIn2015 = new HashMap<>();

        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                matchIdsIn2015.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {
            if(matchIdsIn2015.contains(delivery.getMatchId())) {
                    if (individualBatsmanRunsIn2015.containsKey(delivery.getBatsman())) {
                        individualBatsmanRunsIn2015.put(delivery.getBatsman(), individualBatsmanRunsIn2015.get(delivery.getBatsman()) + Integer.parseInt(delivery.getBatsmanRuns()));
                    } else {
                        individualBatsmanRunsIn2015.put(delivery.getBatsman(), Integer.parseInt(delivery.getBatsmanRuns()));
                    }
            }
        }

        List<Map.Entry<String, Integer>> listOfIndividualBatsmanRunsIn2015 = new ArrayList<Map.Entry<String, Integer>>();
        listOfIndividualBatsmanRunsIn2015.addAll(individualBatsmanRunsIn2015.entrySet());

        Collections.sort(listOfIndividualBatsmanRunsIn2015, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println();
        System.out.println("Highest Run Scorer In The Year 2015 :");
        System.out.println(listOfIndividualBatsmanRunsIn2015.get(0));
    }

    private static void findMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        List<String> matchIdsIn2015 = new ArrayList<>();
        HashMap<String, Integer> noOfBallsByBowler = new HashMap<>();
        HashMap<String, Integer> totalRunsByBowler = new HashMap<>();

        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                matchIdsIn2015.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {
            if(matchIdsIn2015.contains(delivery.getMatchId())) {
                    if (noOfBallsByBowler.containsKey(delivery.getBowler())) {
                        noOfBallsByBowler.put(delivery.getBowler(), noOfBallsByBowler.get(delivery.getBowler()) + 1);
                    } else {
                        noOfBallsByBowler.put(delivery.getBowler(), 1);
                    }
            }
        }

        for (Delivery delivery : deliveries) {
            if(matchIdsIn2015.contains(delivery.getMatchId())) {
                    if (totalRunsByBowler.containsKey(delivery.getBowler())) {
                        totalRunsByBowler.put(delivery.getBowler(), totalRunsByBowler.get(delivery.getBowler()) + Integer.parseInt(delivery.getTotalRuns()));
                    } else {
                        totalRunsByBowler.put(delivery.getBowler(), Integer.parseInt(delivery.getTotalRuns()));
                    }
            }
        }

        HashMap<String, Integer> noOfOversByBowler = new HashMap<>();

        for (String bowler : noOfBallsByBowler.keySet()) {
            noOfOversByBowler.put(bowler, noOfBallsByBowler.get(bowler) / 6);
        }

        HashMap<String, Double> economyOfTheBowlers = new HashMap<>();

        for (String bowler : noOfOversByBowler.keySet()) {
            economyOfTheBowlers.put(bowler, ((double) totalRunsByBowler.get(bowler) / (double) noOfOversByBowler.get(bowler)));
        }

        List<Map.Entry<String, Double>> listOfEconomyOfTheBowlers = new ArrayList<Map.Entry<String, Double>>();
        listOfEconomyOfTheBowlers.addAll(economyOfTheBowlers.entrySet());
        Collections.sort(listOfEconomyOfTheBowlers, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> object1, Map.Entry<String, Double> object2) {
                return object1.getValue().compareTo(object2.getValue());
            }
        });

        System.out.println();
        System.out.println("For the year 2015 get the top economical bowlers :");
        System.out.println(listOfEconomyOfTheBowlers);
    }

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        List<String> matchIdsIn2016 = new ArrayList<>();

        for (Match match : matches) {
            if (match.getSeason().equals("2016")) {
                matchIdsIn2016.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {
            if(matchIdsIn2016.contains(delivery.getMatchId())) {
                    if (extraRunsConcededPerTeamIn2016.containsKey(delivery.getBattingTeam())) {
                        extraRunsConcededPerTeamIn2016.put(delivery.getBattingTeam(), extraRunsConcededPerTeamIn2016.get(delivery.getBattingTeam()) + Integer.parseInt(delivery.getExtraRuns()));
                    } else {
                        extraRunsConcededPerTeamIn2016.put(delivery.getBattingTeam(), Integer.parseInt(delivery.getExtraRuns()));
                    }
            }
        }

        System.out.println();
        System.out.println("For the year 2016 get the extra runs conceded per team :");
        System.out.println(extraRunsConcededPerTeamIn2016);
    }

    private static void findNumberOfMatchesWonPerTeamInAllSeasons(List<Match> matches) {
        for (Match match : matches) {
            if (noOfMatchesWonOfAllTeamsOverAllTheYears.containsKey(match.getWinner())) {
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(match.getWinner(), noOfMatchesWonOfAllTeamsOverAllTheYears.get(match.getWinner()) + 1);
            } else {
                if (match.getWinner().equals("")) {
                    continue;
                }
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(match.getWinner(), 1);
            }
        }

        System.out.println();
        System.out.println("Number of matches won of all teams over all the years of IPL :");
        System.out.println(noOfMatchesWonOfAllTeamsOverAllTheYears);
    }

    private static void findNumberOfMatchesPlayedPerYearOfAllTheYears(List<Match> matches) {
        for (Match match : matches) {
            if (noOfMatchesPlayedPerYearOfAllTheYears.containsKey(match.getSeason())) {
                noOfMatchesPlayedPerYearOfAllTheYears.put(match.getSeason(), noOfMatchesPlayedPerYearOfAllTheYears.get(match.getSeason()) + 1);
            } else {
                noOfMatchesPlayedPerYearOfAllTheYears.put(match.getSeason(), 1);
            }
        }

        System.out.println();
        System.out.println("Number of matches played per year of all the years in IPL :");
        System.out.println(noOfMatchesPlayedPerYearOfAllTheYears);
    }

    public static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();

        try {
            File matchFile = new File("./matches.csv");
            Scanner readMatchFile = new Scanner(matchFile);
            boolean skipFirstIteration = true;
            while (readMatchFile.hasNextLine()) {
                String line = readMatchFile.nextLine();
                if (skipFirstIteration) {
                    skipFirstIteration = false;
                    continue;
                }
                String[] data = line.split(",");
                Match match = new Match();
                match.setMatchId(data[ID]);
                match.setSeason(data[SEASON]);
                match.setWinner(data[WINNER]);
                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public static List<Delivery> getDeliveriesData() {
        int noOfColumns = 0;
        List<Delivery> deliveries = new ArrayList<Delivery>();
        try {
            File deliveryFile = new File("./deliveries.csv");
            Scanner readDeliveryFile = new Scanner(deliveryFile);
            boolean skipFirstIteration = true;
            while (readDeliveryFile.hasNextLine()) {
                String line = readDeliveryFile.nextLine();
                if (skipFirstIteration) {
                    String[] data = line.split(",");
                    noOfColumns = data.length;
                    skipFirstIteration = false;
                    continue;
                }
                String[] lineData = line.split(",");
                String [] data = new String[noOfColumns];
                for(int i=0;i<lineData.length;i++){
                    data[i]=lineData[i];
                }
                Delivery delivery = new Delivery();
                delivery.setMatchId(data[MATCH_ID]);
                delivery.setInning(data[INNING]);
                delivery.setBattingTeam(data[BATTING_TEAM]);
                delivery.setBowlingTeam(data[BOWLING_TEAM]);
                delivery.setBatsman(data[BATSMAN]);
                delivery.setBowler(data[BOWLER]);
                delivery.setBatsmanRuns(data[BATSMAN_RUNS]);
                delivery.setExtraRuns(data[EXTRA_RUNS]);
                delivery.setTotalRuns(data[TOTAL_RUNS]);
                delivery.setPlayerDismissed(data[PLAYER_DISMISSED]);
                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

}
