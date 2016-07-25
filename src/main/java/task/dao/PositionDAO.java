package task.dao;

import task.entity.Position;

import java.util.List;

public interface PositionDAO {

    void save(Position position);

    Position getPositionById(Integer id);

    List<Position> getAll();

    void deletePositionByID(Integer id);
}
