package com.goalddae.service;

public interface MatchStatusNotifier {
    void notifyMatchStatusChange(Long matchId, String status);
}
