package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/members/signup")
    public String signUpPage() {
        return "members/new";
    }

    @PostMapping("/members/join")
    public String join(MemberForm form, Model model) {
        Member member = form.toEntity();
        log.info(member.toString());
//        System.out.println("member = " + member);
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        model.addAttribute("member", saved);
        return "members/show";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member findById = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", findById);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        Iterable<Member> MemberList = memberRepository.findAll();
        model.addAttribute("memberList", MemberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form){
        Member memberEntity = form.toEntity();

        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target != null){
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/"+memberEntity.getId();
    }
}
