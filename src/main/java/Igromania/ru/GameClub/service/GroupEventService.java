package Igromania.ru.GameClub.service;

import Igromania.ru.GameClub.model.GroupEvent;
import Igromania.ru.GameClub.repository.GroupEventRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupEventService {

    private final GroupEventRepository groupEventRepository;

    public GroupEventService(GroupEventRepository groupEventRepository) {
        this.groupEventRepository = groupEventRepository;
    }

    public void saveGroupEvent(GroupEvent groupEvent) {
        groupEventRepository.save(groupEvent);
    }
}
