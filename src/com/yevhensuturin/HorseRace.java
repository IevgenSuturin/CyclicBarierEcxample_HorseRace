package com.yevhensuturin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HorseRace {
    static final int FINISH_LINE = 50;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause){
        barrier = new CyclicBarrier(nHorses, () -> {
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<FINISH_LINE; i++)
                builder.append("=");

            System.out.println(builder.toString());

            horses.forEach(horse -> System.out.println(horse.tracks()));

            for(Horse horse: horses){
                if(horse.getStrides()>FINISH_LINE){
                    System.out.println(horse + " won!");
                    exec.shutdownNow();
                    return;
                }
            }

            try{
                Thread.sleep(pause);
            }catch (InterruptedException e){
                System.out.println("Barier action sleep interrupted");
            }
        });

        for (int i=0; i<nHorses; i++){
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }
}
