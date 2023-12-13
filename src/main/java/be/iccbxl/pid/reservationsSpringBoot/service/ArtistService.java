package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists(){
        List<Artist> artists = new ArrayList<>();

        artistRepository.findAll().forEach(artists::add);

        return artists;
    }

    public Artist getArtist(String id) {
        int indice = Integer.parseInt(id);
        return artistRepository.findById(indice);
    }

    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void updateArtist(String id, Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(String id) {
        Long indice = (long) Integer.parseInt(id);
        artistRepository.deleteById(indice);

    }

	public List<Artist> findByLastname(String artistName) {
		return artistRepository.findByLastname(artistName);
	}
    
    

}
