package com.yevhensuturin;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ReflectedHorse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random random = new Random();
    private CyclicBarrier barrier;

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public synchronized int getStrides(){
        return strides;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                synchronized (this){
                    strides += random.nextInt(3);
                }
                barrier.await();
            }
        }catch (InterruptedException e){
        }catch (BrokenBarrierException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Reflected Horse " + id + " ";
    }

    public String printWithParameters(int total){
        return toString() + " total: " + total;
    }


    @SpecialTrack(value = "|")
    public String tracks(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<this.getStrides(); i++)
            stringBuilder.append("*");
        return stringBuilder.toString();
    }

}
