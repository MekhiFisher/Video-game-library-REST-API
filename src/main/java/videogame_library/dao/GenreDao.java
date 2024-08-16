package videogame_library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import videogame_library.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {


		

}
