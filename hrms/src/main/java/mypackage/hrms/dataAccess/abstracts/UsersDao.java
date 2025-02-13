package mypackage.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import mypackage.hrms.entities.concretes.Users;

public interface UsersDao extends JpaRepository<Users, Integer> {

}
