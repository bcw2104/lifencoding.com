package com.lifencoding.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lifencoding.entity.AdminVO;
import com.lifencoding.entity.CategoryVO;
import com.lifencoding.entity.PostVO;
import com.lifencoding.serviceImpl.AdminService;
import com.lifencoding.serviceImpl.CategoryService;
import com.lifencoding.serviceImpl.GuestService;
import com.lifencoding.serviceImpl.PostService;
import com.lifencoding.serviceImpl.SubCategoryService;
import com.lifencoding.util.GlobalValues;

@Controller
public class HomeController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private GuestService guestService;

	@GetMapping("/")
	public String home(Model model) {

		ArrayList<CategoryVO> categoryList = categoryService.getList();
		ArrayList<CategoryVO> subCategoryList = subCategoryService.getList();

		AdminVO adminInfo = adminService.getAdminInfo();

		int totalVisit = guestService.getTotalVisit();
		int todayVisit = guestService.getTodayVisit();

		model.addAttribute("totalVisit",totalVisit);
		model.addAttribute("todayVisit", todayVisit);
		model.addAttribute("content", GlobalValues.home);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("subCategoryList", subCategoryList);
		model.addAttribute("adminInfo",adminInfo);

		return "frame";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = {"/{categoryEn}","/{categoryEn}/{_postId}"})
	public String view(@PathVariable String categoryEn
							,@PathVariable(required=false) String _postId
							,Model model, HttpSession session) throws IOException {

		int postId;
		CategoryVO categoryVO = new CategoryVO();
		CategoryVO subCategoryVO = new CategoryVO();
		PostVO postVO = new PostVO();

		subCategoryVO.setCategoryEn(categoryEn);
		subCategoryVO = subCategoryService.get(subCategoryVO);

		if(subCategoryVO == null) throw new RuntimeException();

		postVO.setCategoryId(subCategoryVO.getCategoryId());
		categoryVO.setCategoryId(subCategoryVO.getParent());

		if(_postId == null)
			postId = postService.getRecentId(postVO);
		else
			postId = Integer.parseInt(_postId);

		postVO.setPostId(postId);
		postVO = postService.getPost(postVO);
		categoryVO = categoryService.get(categoryVO);

		if(session.getAttribute("postVisitList") == null) {
			session.setAttribute("postVisitList", new ArrayList<Integer>());
		}
		ArrayList<Integer> visitList = (ArrayList<Integer>) session.getAttribute("postVisitList");
		if(postService.isFirstVisit(visitList,postId)) {
			postService.increaseHits(postId);
			postVO.setHits(postVO.getHits()+1);
			visitList.add(postId);
		}

		if (postVO != null) {
			model.addAttribute("adminInfo", adminService.getAdminInfo());
			model.addAttribute("currentPost", postVO);
			model.addAttribute("postTumbnail", postService.makePostThumbnail( postVO.getPostContent(),160));
			model.addAttribute("nearPost", postService.getNearPost(postVO));
			model.addAttribute("content", GlobalValues.postView);
			model.addAttribute("currentCategory", categoryVO);
			model.addAttribute("currentSubCategory", subCategoryVO);
		} else {
			throw new RuntimeException();
		}

		return "frame";
	}
}

