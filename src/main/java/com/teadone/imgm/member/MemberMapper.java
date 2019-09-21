package com.teadone.imgm.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public int duplicateCheckMem(MemberVO vo);
	public String logIn(MemberVO vo);
	public int join(MemberVO vo);
	public String findMemberId(MemberVO vo);
	public String findMemberPw(MemberVO vo);
}

