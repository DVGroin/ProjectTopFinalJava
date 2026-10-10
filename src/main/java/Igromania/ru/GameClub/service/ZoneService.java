package Igromania.ru.GameClub.service;

import Igromania.ru.GameClub.model.GameZone;
import Igromania.ru.GameClub.repository.GameZoneRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ZoneService {

    private final GameZoneRepository gameZoneRepository;

    public ZoneService(GameZoneRepository gameZoneRepository) {
        this.gameZoneRepository = gameZoneRepository;
    }

    public List<GameZone> getAllZones() {
        return gameZoneRepository.findAll();
    }

    public void saveZone(GameZone zone) {
        gameZoneRepository.save(zone);
    }

    public void deleteZone(Long id) {
        gameZoneRepository.deleteById(id);
    }
}
