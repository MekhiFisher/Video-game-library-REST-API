package videogame_library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import videogame_library.entity.Videogame;

public interface VideogameDao extends JpaRepository<Videogame, Long> {

}
