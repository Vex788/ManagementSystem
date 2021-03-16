package com.zeus.inc.afinams.dt_repository;

import com.zeus.inc.afinams.model.Admin;
import com.zeus.inc.afinams.repository.AdminRepository;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface AdminDTRepository extends DataTablesRepository<Admin, Long>, AdminRepository {
}
