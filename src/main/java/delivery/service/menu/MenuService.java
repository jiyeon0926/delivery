package delivery.service.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import delivery.repository.menu.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
}
