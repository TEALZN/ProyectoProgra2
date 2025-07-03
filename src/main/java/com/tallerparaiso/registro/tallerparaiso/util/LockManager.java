/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.util;

/**
 *
 * @author Zailyn Tencio
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {

    private LockManager() {
        /* utilitario */ }
    private static final Map<String, Lock> locks = new ConcurrentHashMap<>();

    public static Lock getLock(String recurso) {
        return locks.computeIfAbsent(recurso, k -> new ReentrantLock());
    }

    public static <T> T executeWithLock(String recurso, java.util.concurrent.Callable<T> task) throws Exception {
        Lock lock = getLock(recurso);
        lock.lock();
        try {
            return task.call();
        } finally {
            lock.unlock();
        }
    }

    public static void runWithLock(String recurso, Runnable task) {
        Lock lock = getLock(recurso);
        lock.lock();
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }
}
