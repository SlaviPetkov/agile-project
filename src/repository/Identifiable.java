package repository;

public interface Identifiable<K>{
    K getId();
    void setId(K id);
}
