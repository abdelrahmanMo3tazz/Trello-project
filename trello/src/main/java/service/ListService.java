package service;

import model.List;

public interface ListService {
    List createList(List list);
    List getListById(Long id);
    List getAllLists();
    // Add other methods as needed
}
