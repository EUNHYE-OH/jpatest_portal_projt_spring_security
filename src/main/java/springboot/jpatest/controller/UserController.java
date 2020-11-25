package springboot.jpatest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springboot.jpatest.domain.Manager;
import springboot.jpatest.domain.Student;
import springboot.jpatest.domain.User;
import springboot.jpatest.repository.*;
import springboot.jpatest.service.ManagerService;
import springboot.jpatest.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j //로그남기기
@Controller
@RequiredArgsConstructor
public class UserController {

    private final StudentService studentService;
    private final ManagerService managerService;
    private final StudentRepository studentRepository;



    @GetMapping("/joinf")
    public String joinForm(){
        return "user/joinForm";
    }//joinForm

    @PostMapping("/joinPre")
    public ModelAndView joinPre(ModelAndView mv, Student student){
        mv.addObject("type", student.getType());
        mv.setViewName("user/joinForm");
        return mv;
    }

    @GetMapping("/join")
    public String join(Student student, Manager manager){
        if(student.getStID() != null)
            studentService.join(student);
        else if(manager.getMgID() != null)
            managerService.join(manager);
        return "redirect:/";
    }//join

    @GetMapping("findIdPop")
    public String findIdPop(){
        return "user/findIdPop";
    }

    @GetMapping("/loginf")
    public String loginForm(){
        return "home";
    }//loginf

    @PostMapping("/login")
    public String login(@RequestParam("stID") String inputStID, @RequestParam("password") String inputPassword, HttpServletRequest request){
        log.info("stID : {} , pw : {}", inputStID, inputPassword);
        Student student = this.studentRepository.findStudent(inputStID, inputPassword);
        if(student != null){
            HttpSession session = request.getSession(false);
            session.setAttribute("student",student);
            return "user/student/student";
        }
        return "user/joinPreForm";
    }//login

    @GetMapping("user/logOn")
    public String logOn(){
        return "user/student/student";
    }

/*    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }*/
}
