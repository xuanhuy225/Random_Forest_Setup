/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.common.libs;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SafeList<T> {

    private final List<WeakReference<T>> _list = new ArrayList<>();
    private final List<WeakReference<T>> _readOnlyList = Collections.unmodifiableList(_list);
    private final ReentrantReadWriteLock _locker = new ReentrantReadWriteLock();

    public List<WeakReference<T>> getListForReading() {
        _locker.readLock().lock();
        return _readOnlyList;
    }

    public void unaccessList() {
        _locker.readLock().unlock();
    }

    public int size() {
        _locker.readLock().lock();
        try {
            return _list.size();
        } finally {
            _locker.readLock().unlock();
        }
    }

    public void add(T inst) {
        boolean isNew = true;
        T instI;
        _locker.writeLock().lock();
        try {
            for (int i = 0, size = _list.size(); i < size; i++) {
                instI = _list.get(i).get();
                if (instI == null) {
                    _list.remove(i);
                    size = _list.size();
                    --i;
                    continue;
                }

                if (instI == inst) {
                    isNew = false;
                }
            }

            if (isNew) {
                _list.add(new WeakReference<>(inst));
            }
        } finally {
            _locker.writeLock().unlock();
        }
    }

    public void remove(T inst) {
        T instI;
        _locker.writeLock().lock();
        try {
            for (int i = 0, size = _list.size(); i < size; i++) {
                instI = _list.get(i).get();
                if (instI == inst || instI == null) {
                    _list.remove(i);
                    size = _list.size();
                    --i;
                }
            }
        } finally {
            _locker.writeLock().unlock();
        }
    }

    public void cleanUp() {
        T instI;
        _locker.writeLock().lock();
        try {
            for (int i = 0, size = _list.size(); i < size; i++) {
                instI = _list.get(i).get();
                if (instI == null) {
                    _list.remove(i);
                    size = _list.size();
                    --i;
                }
            }
        } finally {
            _locker.writeLock().unlock();
        }
    }
}
