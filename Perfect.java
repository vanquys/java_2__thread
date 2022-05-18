/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Tran Anh Huy
 * Dem so hoan hao tu 1 den 30000, dung thread
 */
public class Perfect extends Thread{
    //public List<Integer> primeList = new ArrayList<>();

    static ArrayBlockingQueue<Integer> perfectList;
    int start, end;

    //int max = num;
    public int count = 0;

    public Perfect(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int n = start; n <= end; n++) {
            int kt = kt_ht(n);
            if(kt == 1 && n != 1 && n != 0){ 
                count++;
                perfectList.add(n);
            }
        }
    }
    
    int kt_ht(int x){
        int i,dem=0;
        for(i=1;i<x;i++)
            if(x%i==0)
                dem += i;
        if(dem==x)
            return 1;
        return 0;
    }
    
    public static void main(String[] args) {

        int num = 30000;
        int [] perfects = new int [num];

        int nThreads = 2;
        final Perfect[] pThreads = new Perfect[nThreads];

        Perfect.perfectList = new ArrayBlockingQueue<>(perfects.length); // Guaranteed to be enough
        int step = perfects.length / nThreads + 1;
        for(int i = 0; i<nThreads; i++){
            pThreads[i] = new Perfect(i * step, Math.min(perfects.length, (i + 1) * step - 1));
            pThreads[i].start();
        }

        try {
            for (int i = 0; i < nThreads; i++)
                pThreads[i].join();
        } catch (InterruptedException e) {
        }
        
        System.out.println("----------------------------------------------------");
        System.out.println("So luong cac so nguyen to tu 1 den 30000");
        for ( int i = 0; i < nThreads; i++)
             System.out.println("Thread " + i + "  Prime count: " + pThreads[i].count);
        System.out.println("Total prime count: " + Perfect.perfectList.size()); 
        System.out.println(Perfect.perfectList);

    }
}