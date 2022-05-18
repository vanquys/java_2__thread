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
 * Dem so nguyen to tu 1 den 30000, dung thread
 */
public class Prime extends Thread{
    //public List<Integer> primeList = new ArrayList<>();

    static ArrayBlockingQueue<Integer> primeList;
    int start, end;

    //int max = num;
    public int count = 0;

    public Prime(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int n = start; n <= end; n++) {
            boolean prime = true;
            for (int j = 2; j < n; j++) {
                if (n%j == 0){
                    prime = false;
                    break;
                }
            }

            if(prime && n != 1 && n != 0){ 
                count++;
                primeList.add(n);
            }
        }
    }
    
    public static void main(String[] args) {

        int num = 30000;
        int [] primes = new int [num];

        int nThreads = 2;
        final Prime[] pThreads = new Prime[nThreads];

        Prime.primeList = new ArrayBlockingQueue<>(primes.length); // Guaranteed to be enough
        int step = primes.length / nThreads + 1;
        for(int i = 0; i<nThreads; i++){
            pThreads[i] = new Prime(i * step, Math.min(primes.length, (i + 1) * step - 1));
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
        System.out.println("Total prime count: " + Prime.primeList.size()); 
        System.out.println(Prime.primeList);

    }
}