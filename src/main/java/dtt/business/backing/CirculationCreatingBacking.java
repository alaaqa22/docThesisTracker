package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

/**
 * Backing bean for new circulation page.
 * @author Alaa Qasem
 */
public class CirculationCreatingBacking {
    @Inject
    private CirculationDAO circulationDAO;

    private Circulation circulation;

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {

    }


    /**
     * Creates a new circulation
     *
     * @param circ The circulation to create.
     */
    public void create(Circulation circ){

    }

    public CirculationDAO getCirculationDAO() {
        return circulationDAO;
    }

    public Circulation getCirculation() {
        return circulation;
    }
}
