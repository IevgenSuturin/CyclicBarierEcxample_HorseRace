package com.yevhensuturin;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int horses = 7;
        int pause = 200;
//        new HorseRace(horses, pause);

        List<ReflectedHorse> reflectedHorses = new ArrayList<>();
        for(int i=0; i<horses; i++){
            reflectedHorses.add(new ReflectedHorse());
        }

        new ReflectedHorseRace(reflectedHorses, pause);
    }
}
