package videogame_library.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import videogame_library.controller.model.VideogameData;
import videogame_library.controller.model.VideogameData.GenreData;
import videogame_library.dao.GenreDao;
import videogame_library.dao.PublisherDao;
import videogame_library.dao.VideogameDao;
import videogame_library.entity.Genre;
import videogame_library.entity.Publisher;
import videogame_library.entity.Videogame;

@Service
public class VideogameService {

	@Autowired
	private VideogameDao videogameDao;

	@Autowired
	private GenreDao genreDao;

	@Autowired
	private PublisherDao publisherDao;

	// Video games
	public VideogameData saveVideogame(VideogameData videogameData) {
		Long videogameId = videogameData.getVideogameId();
		Videogame videogame = findVideogameById(videogameId);

		setVideogameFields(videogame, videogameData);
		Videogame savedVideogame = videogameDao.save(videogame);
		return new VideogameData(savedVideogame);
	}

	public VideogameData saveVideogameWithPublisher(Long publisherId, VideogameData videogameData) {
		Long videogameId = videogameData.getVideogameId();
		Publisher publisher = findPublisher(publisherId);
		Videogame videogame = findOrCreateVideogame(videogameId);

		setVideogameFields(videogame, videogameData);
		videogame.setPublisher(publisher);
		Videogame savedVideogame = videogameDao.save(videogame);
		return new VideogameData(savedVideogame);
	}

	public List<VideogameData> getAllVideogames() {
		List<VideogameData> results = new LinkedList<>();
		List<Videogame> videogames = videogameDao.findAll();

		for (Videogame videogame : videogames) {
			VideogameData buffer = new VideogameData(videogame);
			results.add(buffer);
		}
		return results;

	}

	public VideogameData getVideogameById(Long videogameId) {
		Videogame videogame = findVideogameById(videogameId);
		return new VideogameData(videogame);
	}

	public List<VideogameData> getVideogamesByGenre(Long genreId) {

		List<VideogameData> videogames = new LinkedList<>();
		Genre genreData = findGenreById(genreId);

		for (Videogame videogame : genreData.getVideogames()) {
			videogames.add(new VideogameData(videogame));
		}
		return videogames;
	}

	public void deleteVideogameById(Long videogameId) {
		Videogame videogame = findVideogameById(videogameId);
		videogameDao.delete(videogame);
	}

	public List<VideogameData> getVideogamesByPublisher(Long publisherId) {

		List<VideogameData> videogames = new LinkedList<>();
		Publisher publisher = findPublisherById(publisherId);

		for (Videogame videogame : publisher.getVideoGames()) {
			videogames.add(new VideogameData(videogame));
		}
		return videogames;
	}

	// Genre
	private Genre findGenreById(Long genreId) {
		return genreDao.findById(genreId)
				.orElseThrow(() -> new NoSuchElementException("Genre where ID=" + genreId + "does not exist"));
	}

	public VideogameData saveVideogameGenre(Long genreId, Long videogameId) {

		Genre genre = findGenreById(genreId);
		Videogame videogame = findVideogameById(videogameId);

		videogame.getGenres().add(genre);
		genre.getVideogames().add(videogame);

		return new VideogameData(videogame);
	}

	public List<GenreData> getAllGenres() {
		List<GenreData> results = new LinkedList<>();

		List<Genre> genres = genreDao.findAll();
		for (Genre genre : genres) {
			GenreData gd = new GenreData(genre);
			results.add(gd);
		}
		return results;
	}

	public VideogameData removeGenres(Long videogameId) {
		Videogame videogame = findVideogameById(videogameId);
		videogame.getGenres().clear();

		return new VideogameData(videogame);
	}

	// Publisher
	private Publisher findPublisherById(Long publisherId) {
		return publisherDao.findById(publisherId)
				.orElseThrow(() -> new NoSuchElementException("Publisher where ID=" + publisherId + "does not exist"));
	}

	public VideogameData changePublisher(Long publisherId, Long videogameId) {
		Publisher publisher = findPublisherById(publisherId);
		Videogame videogame = findVideogameById(videogameId);

		videogame.setPublisher(publisher);

		return new VideogameData(videogame);
	}

	public void deletePublisherById(Long publisherId) {
		Publisher publisher = findPublisherById(publisherId);
		publisherDao.delete(publisher);
	}

//----------------------------------------------------------------------------------------------------------------------------------------------//
	private void setVideogameFields(Videogame videogame, VideogameData videogameData) {

		videogame.setGameName(videogameData.getGameName());
		videogame.setHoursPlayed(videogameData.getHoursPlayed());
		videogame.setRating(videogameData.getRating());
		videogame.setNotes(videogameData.getNotes());

	}

	private Publisher findPublisher(Long publisherId) {
		return publisherDao.findById(publisherId)
				.orElseThrow(() -> new NoSuchElementException("Publisher where ID=" + publisherId + " does not exist"));
	}

	private Videogame findOrCreateVideogame(Long videogameId) {
		Videogame videogame;

		if (Objects.isNull(videogameId)) {
			videogame = new Videogame();
		} else {
			videogame = findVideogameById(videogameId);
		}

		return videogame;
	}

	private Videogame findVideogameById(Long videogameId) {
		return videogameDao.findById(videogameId)
				.orElseThrow(() -> new NoSuchElementException("Videogame where ID=" + videogameId + " does not exist"));
	}

}
