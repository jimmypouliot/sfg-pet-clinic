package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    private HashMap<Long, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(ID id) {
        return map.get(id);
    }

    public T save(T object) {
        if (object.getId() == null) {
            object.setId(getNextId());
        }

        map.put(object.getId(), object);

        return object;
    }

    public void delete(T object) {
        map.values().remove(object);
    }

    public void deleteById(ID id) {
        map.remove(id);
    }

    private Long getNextId() {
        return map.keySet().stream().max(Comparator.naturalOrder()).orElse(0L) + 1;
    }
}
