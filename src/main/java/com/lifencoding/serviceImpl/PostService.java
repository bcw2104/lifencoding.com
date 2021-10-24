package com.lifencoding.serviceImpl;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lifencoding.entity.PostVO;
import com.lifencoding.mapper.PostMapper;
import com.lifencoding.util.FTPManager;
import com.lifencoding.util.FileTool;

@Service
public class PostService{

	@Autowired
	private FTPManager manager;
	@Autowired
	private PostMapper postMapper;

	public boolean isFirstVisit(ArrayList<Integer> visitList,int postId) {
		for(int visit : visitList) {
			if(visit == postId) {
				return false;
			}
		}
		return true;
	}

	public String makePostThumbnail(String postContent,int len){
		String str = postContent.replaceAll("\\<.*?\\>", "").replaceAll("&nbsp;","");

		if(str.length() > len) {
			str = str.substring(0, len);
		}

		return str;
	}

	public void makeAllPostThumbnail(ArrayList<PostVO> list,int len){
		for(PostVO ele : list) {
			ele.setPostContent(makePostThumbnail(ele.getPostContent(),len));
		}
	}

	public int getNextPostId() {
		return postMapper.getSequenceNum();
	}

	public ArrayList<PostVO> getPostList(PostVO postVO) {
		return postMapper.select(postVO);
	}

	public PostVO getPost(PostVO postVO) {
		ArrayList<PostVO> list = postMapper.select(postVO);

		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	public ArrayList<PostVO> getNearPost(PostVO postVO) {
		return postMapper.selectNear(postVO);
	}

	public ArrayList<PostVO> getHotPostList(int cnt) {
		return postMapper.selectHot(cnt);
	}

	public void increaseHits(int postId) {
		postMapper.increaseHits(postId);
	}

	public void add(PostVO postVO) {
		postMapper.insert(postVO);
	}

	public void modify(PostVO postVO) {
		postMapper.modify(postVO);

	}

	public void delete(int postId) {
		postMapper.delete(postId);
	}

	public String uploadImg(int postId,String type,String fileName,MultipartFile mfile) throws Exception {
		FTPClient client = manager.connect();
		FileTool fileTool = new FileTool(client);

		String dir = fileTool.getPostDirPath();

		if(postId == 0) {
			dir+= File.separator + "temp" + File.separator + type;
		}
		else {
			dir+= File.separator + postId + File.separator + type;
		}

		String reply = fileTool.createPostTempFile(dir, fileName, mfile);

		manager.disconnect(client);

		return reply;
	}

	public void changeUploadDir(int postId) throws Exception {
		FTPClient client = manager.connect();
		FileTool fileTool = new FileTool(client);

		String path= fileTool.getPostDirPath();

		boolean reply = fileTool.rename(path+File.separator+"temp",path+File.separator+postId);

		if(!reply) {
			fileTool.createDirectorys(path+File.separator+postId);
		}
		manager.disconnect(client);
	}

	public void deleteThumbnail(int postId) throws Exception {
		FTPClient client = manager.connect();
		FileTool fileTool = new FileTool(client);

		String dir= fileTool.getPostDirPath()+File.separator+postId;
		String fileName = "thumbnail";

		fileTool.remove(dir,fileName);

		manager.disconnect(client);
	}
	public void deleteImgFile(int postId) throws Exception {
		FTPClient client = manager.connect();
		FileTool fileTool = new FileTool(client);

		String dir = fileTool.getPostDirPath();

		fileTool.remove(dir,String.valueOf(postId));

		manager.disconnect(client);
	}

}
