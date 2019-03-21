package com.kafkademo.source.data;

import org.springframework.data.repository.CrudRepository;

public interface RawUserRepository extends CrudRepository<RawUser, Long> {
}
