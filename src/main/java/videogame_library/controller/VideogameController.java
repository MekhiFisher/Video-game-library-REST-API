package videogame_library.controller;

import java.util.List;
import java.util.Map;

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
import videogame_library.service.VideogameService;

@RestController
@RequestMapping("/videogame_library")
public class VideogameController {

	@Autowired
	private VideogameService videogameService;

	/* Video game 
	 * 1) Create video game 
	 * 2) get video game/ get video game by ID 
	 * 3) update video game 
	 * 4) delete video game 
	 * 5) get games by genre 
	 * 6) get games by publisher
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
	public Map<String, String> deleteVideogameById(@PathVariable Long videogameId) {
		videogameService.deleteVideogameById(videogameId);
		return Map.of("Message", "Deletion of videogame where ID=" + videogameId + " was successful");
	}

	@GetMapping("/videogame/genre/{genreId}")
	public List<VideogameData> getVideogameByGenres(@PathVariable Long genreId) {
		return videogameService.getVideogamesByGenre(genreId);
	}
	
	@GetMapping("/videogame/publisher/{publisherId}")
	public List<VideogameData> getVideogamesByPublisher(@PathVariable Long publisherId) {
		return videogameService.getVideogamesByPublisher(publisherId);
	}
	
	//@formatter: off
	/* Genres 
	 * 1) Add new genre 
	 * 2) Delete all genres from a game
	 */
	//@formatter: on
	@PostMapping("/videogame/{videogameId}/{genreId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public VideogameData addGenreById(@PathVariable Long videogameId, @PathVariable Long genreId,
			@RequestBody VideogameData videogameData) {
		videogameData.setVideogameId(videogameId);
		return videogameService.saveVideogameGenre(genreId, videogameId);
	}

	@PutMapping("/videogame/{videogameId}/genre")
	public VideogameData removeGenresFromVideogame(@PathVariable Long videogameId) {
		return videogameService.removeGenres(videogameId);
	}

	@GetMapping("/genre")
	public List<GenreData> getAllGenres() {
		return videogameService.getAllGenres();
	}

	/*Publishers 
	 * 1) Delete publisher 
	 * 2) Change a publisher on a specific game
	 */
	@DeleteMapping("/publisher/{publisherId}")
	public Map<String, String> deletePublisher(@PathVariable Long publisherId) {
		videogameService.deletePublisherById(publisherId);
		return Map.of("Message", "Deletion of publisher where ID=" + publisherId + " was successful");
	}

	@PutMapping("/videogame/{videogameId}/publisher/{publisherId}")
	public VideogameData changePublisher(@PathVariable Long publisherId, @PathVariable Long videogameId) {
		return videogameService.changePublisher(publisherId, videogameId);
	}

}
