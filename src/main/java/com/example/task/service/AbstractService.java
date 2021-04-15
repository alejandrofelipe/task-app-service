package com.example.task.service;

import com.example.task.exception.ObjectNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<MODEL, ID, REPO extends JpaRepository<MODEL, ID>> {
    protected REPO repository;

    public AbstractService(REPO repository) {
        this.repository = repository;
    }

    public boolean exists(ID id) {
        return repository.existsById(id);
    }

    public boolean exists(Example<MODEL> example) {
        return repository.exists(example);
    }

    //Record cannot be found 404
    public MODEL get(ID id) {
        return optional(id)
                .orElseThrow(() -> new ObjectNotFoundException("Resource with id(" + id + ") cannot be found."));
    }

    //Record cannot be found 404
    public MODEL get(Example<MODEL> example) {
        return list(example).stream().findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Your search returned no results."));
    }

    //Record cannot be found 404
    public MODEL first(Example<MODEL> example) {
        return repository.findAll(example, PageRequest.of(0, 1)).stream().findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Your search returned no results."));
    }

    public Optional<MODEL> optional(ID id) {
        return repository.findById(id);
    }

    public List<MODEL> list() {
        return repository.findAll();
    }

    public List<MODEL> list(Example<MODEL> example) {
        return repository.findAll(example);
    }

    public MODEL save(MODEL m) {
        return repository.save(m);
    }

    public List<MODEL> save(List<MODEL> ms) {
        return repository.saveAll(ms);
    }

    public long count() {
        return repository.count();
    }

    public long count(Example<MODEL> example) {
        return repository.count(example);
    }

    public void delete(MODEL m) {
        repository.delete(m);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public void delete(Example<MODEL> example) {
        this.delete(repository.findAll(example));
    }

    public void delete(List<MODEL> ms) {
        repository.deleteAll(ms);
    }

    public void truncate() {
        repository.deleteAll();
    }
}
