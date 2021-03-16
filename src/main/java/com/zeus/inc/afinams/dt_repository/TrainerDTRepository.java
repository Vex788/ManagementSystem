package com.zeus.inc.afinams.dt_repository;

import com.zeus.inc.afinams.model.Trainer;
import com.zeus.inc.afinams.repository.TrainerRepository;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface TrainerDTRepository extends DataTablesRepository<Trainer, Long>, TrainerRepository {
}
