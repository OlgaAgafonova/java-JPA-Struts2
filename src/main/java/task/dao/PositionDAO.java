package task.dao;

import task.entity.Position;

public interface PositionDAO {

    void save(Position position);

    Position getPositionById(Integer id);

    void deletePositionByID(Integer id);
}
