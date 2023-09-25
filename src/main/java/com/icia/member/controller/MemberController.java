package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String save(){
        return "memberPage/memberSave";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/members")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPage/memberList";
    }

    @GetMapping("/member/login")
    public String login(){
        return "memberPage/memberLogin";
    }

    @PostMapping("/member/login")
    public String login(@RequestParam("memberEmail") String memberEmail,
                        @RequestParam("memberPassword") String memberPassword,
                        HttpSession session){
        MemberDTO memberDTO = memberService.login(memberEmail, memberPassword);
        if (memberDTO==null){
            return "redirect:/member/login";
        }else {
            session.setAttribute("loginId", memberDTO.getId());
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            session.setAttribute("loginName", memberDTO.getMemberName());
            return "redirect:/member/main/"+memberDTO.getId();
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginId");
        session.removeAttribute("loginEmail");
        session.removeAttribute("loginName");
        return "redirect:/";
    }

    @GetMapping("/member/main/{id}")
    public String main(@PathVariable("id") Long id,
                       HttpSession session){
        if(session.getAttribute("loginId") == null){
            return "redirect:/";
        }else{
            return "memberPage/memberMain";
        }
    }

    @GetMapping("/member/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "memberPage/memberDetail";
    }

    @GetMapping("/member/update/{id}")
    public String update(@PathVariable("id") Long id,
                         Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "memberPage/memberUpdate";
    }
}
