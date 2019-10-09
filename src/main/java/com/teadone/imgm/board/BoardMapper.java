package com.teadone.imgm.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	public List<BoardVO> getPosts();
	public BoardVO getPost(BoardVO vo);
	public int writePost(BoardVO vo);
}
