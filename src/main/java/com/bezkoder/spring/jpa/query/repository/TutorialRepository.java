package com.bezkoder.spring.jpa.query.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.bezkoder.spring.jpa.query.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

  @Query(value = "SELECT * FROM tutorials", nativeQuery = true)
  List<Tutorial> findAll();

  @Query(value = "SELECT * FROM tutorials t WHERE t.published=?1", nativeQuery = true)
  List<Tutorial> findByPublished(boolean isPublished);

  @Query(value = "SELECT * FROM tutorials t WHERE t.title LIKE %?1%", nativeQuery = true)
  List<Tutorial> findByTitleLike(String title);

  @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
  List<Tutorial> findByTitleLikeCaseInsensitive(String title);

  @Query(value = "SELECT * FROM tutorials t WHERE t.level >= ?1", nativeQuery = true)
  List<Tutorial> findByLevelGreaterThanEqual(int level);

  @Query(value = "SELECT * FROM tutorials t WHERE t.created_at >= ?1", nativeQuery = true)
  List<Tutorial> findByDateGreaterThanEqual(Date date);

  @Query(value = "SELECT * FROM tutorials t WHERE t.level BETWEEN ?1 AND ?2", nativeQuery = true)
  List<Tutorial> findByLevelBetween(int start, int end);
  
  @Query(value = "SELECT * FROM tutorials t WHERE t.created_at BETWEEN ?1 AND ?2", nativeQuery = true)
  List<Tutorial> findByDateBetween(Date start, Date end);
  
  @Query(value = "SELECT * FROM tutorials t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end", nativeQuery = true)
  List<Tutorial> findByLevelBetween(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isPublished);

  @Transactional
  @Modifying
  @Query(value = "UPDATE tutorials SET published=true WHERE id=?1", nativeQuery = true)
  int publishTutorial(Long id);

  @Query(value = "SELECT * FROM tutorials t ORDER BY t.level DESC", nativeQuery = true)
  List<Tutorial> findAllOrderByLevelDesc();

  @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY t.level ASC", nativeQuery = true)
  List<Tutorial> findByTitleOrderByLevelAsc(String title);

  @Query(value = "SELECT * FROM tutorials t WHERE t.published=true ORDER BY t.created_at DESC", nativeQuery = true)
  List<Tutorial> findAllPublishedOrderByCreatedDesc();

  /* InvalidJpaQueryMethodException
  @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
  List<Tutorial> findByTitleAndSort(String title, Sort sort);
  */
  
  @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
  Page<Tutorial> findByTitleLike(String title, Pageable pageable);
  
  @Query(value = "SELECT * FROM tutorials t WHERE t.published=?1", nativeQuery = true)
  Page<Tutorial> findByPublished(boolean isPublished, Pageable pageable);

  @Query(value = "SELECT * FROM tutorials", nativeQuery = true)
  Page<Tutorial> findAllWithPagination(Pageable pageable);
}