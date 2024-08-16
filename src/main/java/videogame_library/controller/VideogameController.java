package videogame_library.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import videogame_library.controller.model.VideogameData;
import videogame_library.controller.model.VideogameData.GenreData;
import videogame_library.controller.model.VideogameData.PublisherData;
import videogame_library.entity.Genre;
import videogame_library.service.VideogameService;

@RestController
@RequestMapping("/videogame_library")
public class VideogameController {

	@Autowired
	private VideogameService videogameService;

	/*
	 * Video game 
	 * 1) Create video game 
	 * 2) Get video game/ get video game by ID 
	 * 3) Update video game 
	 * 4) Delete video game 
	 * 5) Get games by genre 
	 * 6) Get games by publisher
	 */
	@PostMapping("/publisher/{publisherId}/videogame")
	@ResponseStatus(code = HttpStatus.CREATED)
	public VideogameData createVideogame(@PathVariable Long publisherId, @RequestBody VideogameData videogameData) {
		return videogameService.saveVideogameWithPublisher(publisherId, videogameData);
	}

	@GetMapping("/videogame")
	public List<VideogameData> getAllVideogames() {
		return videogameService.getAllVideogames();
	}

	@GetMapping("/videogame/{videogameId}")
	public VideogameData getVideogameById(@PathVariable Long videogameId) {
		return videogameService.getVideogameById(videogameId);
	}

	@PutMapping("/videogame/{videogameId}")
	public VideogameData updateVideogameById(@PathVariable Long videogameId, @RequestBody VideogameData videogameData) {
		videogameData.setVideogameId(videogameId);
		return videogameService.saveVideogame(videogameData);
	}

	@DeleteMapping("/videogame/{videogameId}")
	public String deleteVideogameById(@PathVariable Long videogameId) {
		videogameService.deleteVideogameById(videogameId);
		String message = "Deleting videogame where Id=" + videogameId;
		return message;
	}

	@GetMapping("/videogame/genre/{genreId}")
	public List<VideogameData> getVideogameByGenres(@PathVariable Long genreId) {
		return videogameService.getVideogamesByGenre(genreId);
	}

	@GetMapping("/videogame/publisher/{publisherId}")
	public List<VideogameData> getVideogamesByPublisher(@PathVariable Long publisherId) {
		return videogameService.getVideogamesByPublisher(publisherId);
	}

	/*
	 * Genres 
	 * 1) Create new genre 
	 * 2) Add new genre to video game 
	 * 3) Get all genres
	 * 4) Get genre by Id 
	 * 5) Delete genre from a game by Id 
	 * 6) Delete all genres from a game
	 * 7) Delete genre by Id
	 */
	@PostMapping("/genre")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GenreData createGenre(@RequestBody GenreData genreData) {
		return videogameService.createGenre(genreData);
	}

	@PostMapping("/videogame/{videogameId}/{genreId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public VideogameData addGenreToVideogameById(@PathVariable Long videogameId, @PathVariable Long genreId) {
		return videogameService.saveVideogameGenre(genreId, videogameId);
	}

	@GetMapping("/genre")
	public List<GenreData> getAllGenres() {
		return videogameService.getAllGenres();
	}

	@GetMapping("/genre/{genreId}")
	public GenreData getGenreById(@PathVariable Long genreId) {
		return videogameService.getGenreById(genreId);
	}
	
	@PutMapping("/videogame/{videogameId}/genre/{genreId}")
	public VideogameData deleteGenreFromVideogameById(@PathVariable Long videogameId, @PathVariable Long genreId) {
		return videogameService.removeGenreFromVideogameById(videogameId, genreId);
	}

	@PutMapping("/videogame/{videogameId}/genre")
	public VideogameData removeGenresFromVideogame(@PathVariable Long videogameId) {
		return videogameService.removeGenres(videogameId);
	}
	
	@DeleteMapping("/genre/{genreId}")
	public String deleteGenreById(@PathVariable Long genreId) {
		videogameService.deletGenreById(genreId);
		String message = "Deleting genre where Id=" + genreId;
		return message;
	}
	
	/*
	 * Publishers 
	 * 1) Create publisher
	 * 2) Get all publishers
	 * 3) Get a publisher by Id 
	 * 4) Change a publisher on a specific game
	 * 5) Delete publisher 
	 */

	@PostMapping("/publisher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PublisherData createPublisher(@RequestBody PublisherData publisherData) {
		return videogameService.createPublisher(publisherData);
	}
	
	@GetMapping("/publisher")
	public List<PublisherData> getAllPublishers() {
		return videogameService.getAllPublishers();
	}
	
	@GetMapping("/publisher/{publisherId}")
	public PublisherData getPublisherById(@PathVariable Long publisherId) {
		return videogameService.getPublisherById(publisherId);
	}
	
	@PutMapping("/videogame/{videogameId}/publisher/{publisherId}")
	public VideogameData changePublisher(@PathVariable Long publisherId, @PathVariable Long videogameId) {
		return videogameService.changePublisher(publisherId, videogameId);
	}

	@DeleteMapping("/publisher/{publisherId}")
	public String deletePublisher(@PathVariable Long publisherId) {
		videogameService.deletePublisherById(publisherId);
		String message = "Deleting publisher where Id=" + publisherId;
		return message;
	}

}
