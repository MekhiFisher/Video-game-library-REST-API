package videogame_library.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import videogame_library.entity.Genre;
import videogame_library.entity.Publisher;
import videogame_library.entity.Videogame;


@Data
@NoArgsConstructor
public class VideogameData {
	private Long videogameId;
	private String gameName;
	private double hoursPlayed;
	private int rating;
	private String notes;
	private Set<GenreData> genres = new HashSet<>();
	private PublisherData publisher;

	public VideogameData(Videogame videogame) {
		this.videogameId = videogame.getVideogameId();
		this.gameName = videogame.getGameName();
		this.hoursPlayed = videogame.getHoursPlayed();
		this.rating = videogame.getRating();
		this.notes = videogame.getNotes();
		//new
		this.publisher = new PublisherData(videogame.getPublisher());

		for (Genre genre : videogame.getGenres()) {
			this.genres.add(new GenreData(genre));
		}
	
		
	
	}
//Dont understand why i need it
//	public Videogame toVideogame() {
//		Videogame videogame = new Videogame();
//
//		videogame.setVideogameId(videogameId);
//		videogame.setGameName(gameName);
//		videogame.setHoursPlayed(hoursPlayed);
//		videogame.setRating(rating);
//		videogame.setNotes(notes);
//		videogame.setPublisher(publisher);
//
//		for (GenreData genreData : genres) {
//			videogame.getGenres().add(genreData.toGenre());
//		}
//		return videogame;
//	}

	@Data
	@NoArgsConstructor
	public static class GenreData {
		private Long genreId;
		private String genreName;
		private Set<VideogameData> videogames = new HashSet<>();

		public GenreData(Genre genre) {
			this.genreId = genre.getGenreId();
			this.genreName = genre.getGenreName();

//			for (Videogame videogame : genre.getVideogames()) {
//				this.videogames.add(new VideogameData(videogame));
//			}
		}

		// Dont understand
//		public Genre toGenre() {
//			Genre genre = new Genre();
//
//			genre.setGenreId(genreId);
//			genre.setGenreName(genreName);
//
//			for (Videogame videogame : genre.getVideogames()) {
//				this.videogames.add(new VideogameData(videogame));
//			}
//			return genre;
//		}

	}

	@Data
	@NoArgsConstructor
	public static class PublisherData {
		private Long publisherId;
		private String publisherName;
		private Set<VideogameData> videogames = new HashSet<>();

		public PublisherData(Publisher publisher) {
			this.publisherId = publisher.getPublisherId();
			this.publisherName = publisher.getPublisherName();

			//new commented out 98 99
//			for (Videogame videogame : publisher.getVideoGames()) {
//				this.videogames.add(new VideogameData(videogame));
//			}
		}

//		public Publisher toPublisher() {
//			Publisher publisher = new Publisher();
//
//			publisher.setPublisherId(publisherId);
//			publisher.setPublisherName(publisherName);
//
//			for (VideogameData videogameData : videogames) {
//				publisher.getVideoGames().add(videogameData.toVideogame());
//			}
//
//			return publisher;
//		}

	}
}
