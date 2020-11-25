package springboot.jpatest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.jpatest.domain.Student;
import springboot.jpatest.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String inputStID) throws UsernameNotFoundException {
        Student student = studentRepository.findByStID(inputStID);

        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        if(("admin").equals(inputStID)) {
            auth.add(new SimpleGrantedAuthority("ADMIN"));
        }else {
            auth.add(new SimpleGrantedAuthority("USER"));
        }
        return new User(student.getStID(), student.getPassword(), auth);
    }
    /**
     * 회원가입
     */
    @Transactional
    public Student join(Student student){
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return student;
    }

    /**
     * 해당 아이디 찾기
     */

/*    public String findStIDById(String stID){  //findOne 은 엔티티 id로 검색하는거
        System.out.println("ser stId" + stID);
       return studentInterfaceRepository.findStIDById(stID);
    }*/
}
