package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.model.ArtistType;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.model.Type;
import be.iccbxl.pid.reservationsSpringBoot.service.ArtistService;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@RestController
public class CSVController {

	@Autowired
	private ShowService showService;
	
	@Autowired
	private ArtistService artistService;
	

	@GetMapping("/exportCSV")
	public void exportCSV(HttpServletResponse response) {
	    try {
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=shows.csv");

	        List<Show> listShows = showService.getAll();

	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

	        String[] csvHeader = { "id", "title", "description", "price", "artists" };
	        CellProcessor[] processors = new CellProcessor[] {
	            new Optional(), // Processor pour id
	            new Optional(), // Processor pour title
	            new Optional(), // Processor pour description
	            new Optional(), // Processor pour price
	            new Optional()  // Processor pour artists
	        };

	        csvWriter.writeHeader(csvHeader);

	        for (Show show : listShows) {
	            List<String> artists = new ArrayList<>();

	            for (ArtistType artistType : show.getArtistTypes()) {
	                Artist artist = artistType.getArtist();
	                if (artist != null) {
	                    String fullName = artist.getFirstname() + " " + artist.getLastname();
	                    artists.add(fullName);
	                }
	            }

	            String artistNamesStr = String.join(", ", artists);

	            CSVShow csvShow = new CSVShow(
	                show.getId(),
	                show.getTitle(),
	                show.getDescription(),
	                show.getPrice(),
	                artistNamesStr
	            );

	            csvWriter.write(csvShow, csvHeader, processors);
	        }
	        csvWriter.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Data
	public class CSVShow {
	    private Long id;
	    private String title;
	    private String description;
	    private Double price;
	    private String artists;

	    public CSVShow(Long id, String title, String description, Double price, String artists) {
	        this.id = id;
	        this.title = title;
	        this.description = description;
	        this.price = price;
	        this.artists = artists;
	    }
	}

	
/*	public void importCSV(MultipartFile file) {
	    CsvMapReader mapReader = null;
	    try {
	        // Initialisation du CsvMapReader (votre code existant)
	        
	        Map<String, Object> rowMap;
	        while ((rowMap = mapReader.read(headers, processors)) != null) {
	            Show show = Show.createInstance();

	            show.setId((Long) rowMap.get("id"));
	            show.setTitle((String) rowMap.get("title"));
	            show.setDescription((String) rowMap.get("description"));
	            show.setPrice((Double) rowMap.get("price"));

	            List<ArtistType> artistTypes = new ArrayList<>();
	            String artistNames = (String) rowMap.get("artistNames");
	            for (String artistName : artistNames.split(", ")) {
	                List<Artist> artists = artistService.findByLastname(artistName);
	                if (!artists.isEmpty()) {
	                    // Suppose que vous récupérez le premier artiste trouvé avec ce nom de famille
	                    Artist artist = artists.get(0);

	                    String artistType = artist.getLastname();
	                    if (artistType != null) {
	                        artistTypes.add(artistType);
	                    }
	                }
	            }

	            show.setArtistTypes(artistTypes);
	            
	            // Enregistrez l'objet Show dans la base de données ou effectuez les opérations nécessaires
	            showService.add(show);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        // Fermeture du mapReader (votre code existant)
	    }
	}*/






	

	

}
