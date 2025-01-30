package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/users";
    }

    @GetMapping("/show")
    public String show(@RequestParam("id") long id,
                       Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(Model model,
                          @ModelAttribute("user") User user) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:admin";
    }

    @GetMapping("/edit")
    public String edit(Model model,
                       @RequestParam("id") long id) {
        model.addAttribute("user", userService.show(id));
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user")  User newUser,
                         @RequestParam("id") long id) {
        userService.update(newUser, id);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
