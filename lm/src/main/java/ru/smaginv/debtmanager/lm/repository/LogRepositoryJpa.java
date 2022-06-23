package ru.smaginv.debtmanager.lm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smaginv.debtmanager.lm.entity.Log;

import java.util.Date;
import java.util.List;

public interface LogRepositoryJpa extends JpaRepository<Log, Long> {

    @Query("""
            SELECT l FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) = :date
            ORDER BY l.timestamp DESC
            """)
    List<Log> getOnDate(@Param("date") Date date);

    @Query("""
            SELECT l FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) >= :startDate
            AND function('date_trunc', 'day', l.timestamp) <= :endDate
            ORDER BY l.timestamp DESC
            """)
    List<Log> getBetweenDates(@Param("startDate") Date startDate,
                              @Param("endDate") Date endDate);

    @Query("""
            SELECT DISTINCT l FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) = :date
            OR l.username = :#{#log.username}
            OR l.requestURI = :#{#log.requestURI}
            OR l.method = :#{#log.method}
            ORDER BY l.timestamp DESC
            """)
    List<Log> find(@Param("log") Log log,
                   @Param("date") Date date);

    @Modifying
    @Query("""
            DELETE FROM Log l
            WHERE l.id = :logId
            """)
    int delete(@Param("logId") Long logId);

    @Modifying
    @Query("""
            DELETE FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) = :date
            """)
    int deleteOnDate(@Param("date") Date date);

    @Modifying
    @Query("""
            DELETE FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) >= :startDate
            AND function('date_trunc', 'day', l.timestamp) <= :endDate
            """)
    int deleteBetweenDates(@Param("startDate") Date startDate,
                           @Param("endDate") Date endDate);

    @Modifying
    @Query("""
            DELETE FROM Log l
            WHERE function('date_trunc', 'day', l.timestamp) = :date
            AND (
            :#{#log.username} IS NULL OR l.username = :#{#log.username})
            AND (:#{#log.requestURI} IS NULL OR l.requestURI = :#{#log.requestURI})
            AND (:#{#log.method} IS NULL OR l.method = :#{#log.method}
            )
            """)
    int deleteByConditionOnDate(@Param("log") Log log,
                                @Param("date") Date date);

    @Modifying
    @Query("""
            DELETE FROM Log l
            WHERE (:#{#log.username} IS NULL OR l.username = :#{#log.username})
            AND (:#{#log.requestURI} IS NULL OR l.requestURI = :#{#log.requestURI})
            AND (:#{#log.method} IS NULL OR l.method = :#{#log.method})
            """)
    int deleteAllByCondition(@Param("log") Log log);
}
