package com.yevhensuturin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReflectedHorseRace {
    static final int FINISH_LINE = 50;
    private List<ReflectedHorse> horses;
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public ReflectedHorseRace(List<ReflectedHorse> inputHorses, final int pause){
        this.horses = inputHorses;

        barrier = new CyclicBarrier(horses.size(), () -> {
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<FINISH_LINE; i++)
                builder.append("=");

            System.out.println(builder.toString());

            horses.forEach(horse -> System.out.println(horse.tracks()));

            for(ReflectedHorse horse: horses){
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

        horses.forEach((horse) -> {
            horse.setBarrier(barrier);
            exec.execute(horse);
        });
    }

}
