package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * Backing bean for new circulation page.
 * @author Alaa Qasem
 */
public class CirculationCreatingBacking {
    @Inject
    private CirculationDAO circulationDAO;
    @Inject
    private Circulation circulation;

    /**
     * Initialize the dtos used in bean.
     */
    @PostConstruct
    public void init() {

    }


}
