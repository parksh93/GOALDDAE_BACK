package com.goalddae.repository;

import com.goalddae.entity.ReportedReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReportedReplyJPARepository extends JpaRepository <ReportedReply, Long> {
    Optional<ReportedReply> findByReplyIdAndReporterUserId(long replyId, long reporterUserId);

    @Modifying
    @Transactional
    void deleteByReplyId(long replyId);
}
