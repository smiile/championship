package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface GroupingDao {

    void saveOrUpdate(Grouping group);

    Grouping load(Long id);

    Collection<Grouping> listAll();
    
    void delete(Grouping group);
}
