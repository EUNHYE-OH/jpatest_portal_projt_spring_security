package springboot.jpatest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpatest.domain.Manager;
import springboot.jpatest.repository.ManagerRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Manager join(Manager manager){
        managerRepository.save(manager);
        return manager;
    }

    /**
     * 해당 아이디 찾기
     */
/*    public Manager findByMgID(String mgID){
       return managerRepository.findByMgID(mgID);
    }*/
}
