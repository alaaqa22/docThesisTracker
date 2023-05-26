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
    @Inject
    private Circulation circulation;

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {

    }


    /**
     * Method to create a new Circulation.
     *
     * @param id ID of circulation.
     * @param title title of circulation
     * @param description Description of circulation
     * @param doctoralCandidateName of circulation
     * @param doctoralSupervisor of circulation
     * @param startDeadline  of circulation
     * @param endDeadline of circulation
     *
     * @return New circulation with the given parameters.
     */
    public Circulation create(int id,String title,String description,String doctoralCandidateName,String doctoralSupervisor,
                       LocalDateTime startDeadline,LocalDateTime endDeadline){
        /*
                circulation.setId(id);

         */
        return circulation;


    }

    public CirculationDAO getCirculationDAO() {
        return circulationDAO;
    }

    public Circulation getCirculation() {
        return circulation;
    }
}
