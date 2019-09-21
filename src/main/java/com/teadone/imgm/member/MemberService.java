package com.teadone.imgm.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements MemberMapper {

	@Autowired
	MemberMapper mapper;
	@Override
	public int duplicateCheckMem(MemberVO vo) {
		return mapper.duplicateCheckMem(vo);
	}
	@Override
	public String logIn(MemberVO vo) {
		return mapper.logIn(vo);
	}
	@Override
	public int join(MemberVO vo) {
		return mapper.join(vo);
	}
	@Override
	public String findMemberId(MemberVO vo) {
		return mapper.findMemberId(vo);
	}
	@Override
	public String findMemberPw(MemberVO vo) {
		return mapper.findMemberPw(vo);
	}
}
