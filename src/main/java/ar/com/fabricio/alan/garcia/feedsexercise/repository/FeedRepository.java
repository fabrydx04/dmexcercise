package ar.com.fabricio.alan.garcia.feedsexercise.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Serializable> {
	boolean existsByDescriptionAndTitle(String description, String title);
	
	Optional<Feed> findByTitle(String title);

}
