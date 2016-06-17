package letsmeet;

import letsmeet.dataAccess.DataAccessObject;

import javax.ejb.EJB;

/**
 * Interface for objects that should be deleted (deletable).
 * @author Christian
 */
public abstract class Deletable {
    @EJB
   protected DataAccessObject dataAccessObject;
   public abstract void delete();
}
