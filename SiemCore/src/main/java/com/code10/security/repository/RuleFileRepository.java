package com.code10.security.repository;

public interface RuleFileRepository {

    void save(Long id, String rule);

    void deleteById(long id);

    void deleteAll();
}
