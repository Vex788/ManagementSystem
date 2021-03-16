package com.zeus.inc.afinams.dt_repository;

import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.repository.ClientRepository;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ClientDTRepository extends DataTablesRepository<Client, Long>, ClientRepository {
}
