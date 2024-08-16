package videogame_library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import videogame_library.entity.Publisher;

public interface PublisherDao extends JpaRepository<Publisher, Long> {

}
