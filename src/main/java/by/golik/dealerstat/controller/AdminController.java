package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Nikita Golik
 */
@Controller
public class AdminController {

    private final CommentService commentService;

    @Autowired
    public AdminController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "main")
    public String listComments(Model model){
        model.addAttribute("comment", new Comment());
        model.addAttribute("listComments", this.commentService.listComments());

        return "main";
    }


//    @PostMapping(value = "/doctor/all-patient-receptions/add")
//    public String addComment(@ModelAttribute("comment") Comment comment){
//
//        if(comment.getId() == 0){
//            commentService.addComment(comment);
//        }else {
//            commentService.updateComment(comment);
//        }
//
//        return "redirect:/doctor/all-patient-receptions";
//    }
//
//
//    @RequestMapping("/doctor/all-patient-receptions/remove/{id}")
//    public String removeReception(@PathVariable("id") int id){
//        receptionService.removeReception(id);
//
//        return "redirect:/doctor/all-patient-receptions";
//    }
//
//
//    @RequestMapping("/doctor/all-patient-receptions/edit/{id}")
//    public String editReception(@PathVariable("id") int id, Model model){
//        model.addAttribute("reception", receptionService.getReceptionById(id));
//        model.addAttribute("listReceptions", receptionService.listReceptions());
//        model.addAttribute("listPatients", patientService.listPatients());
//        model.addAttribute("listUsersByRoleDoctor", userService.listUsersByRoleDoctor());
//
//        return "doctor/all-patient-receptions";
//    }
}
