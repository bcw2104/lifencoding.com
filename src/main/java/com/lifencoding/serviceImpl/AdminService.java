package com.lifencoding.serviceImpl;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lifencoding.entity.AdminVO;
import com.lifencoding.mapper.AdminMapper;
import com.lifencoding.util.SecureTool;
import com.lifencoding.util.FTPManager;
import com.lifencoding.util.FileTool;
import com.lifencoding.util.MailTool;

@Service
public class AdminService{
	@Autowired
	private SecureTool secureTool;
	@Autowired
	private MailTool mailTool;
	@Autowired
	private FTPManager manager;
	@Autowired
	private AdminMapper adminMapper;

	public boolean checkPassword(String id, String pw) throws Exception{
		AdminVO adminVO = adminMapper.select();

		if(adminVO.getAdminId().equals(id) && adminVO.getAdminPw().equals(secureTool.encrypt(pw,adminVO.getSalt())))
			return true;
		else
			return false;
	}

	public AdminVO getAdminInfo() {
		return adminMapper.info();
	}

	public AdminVO getAdminAllData() {
		return adminMapper.select();
	}

	public void changeAdminInfo(AdminVO adminVO) throws Exception {
		if(adminVO.getAdminPw() != null) {
			String salt = secureTool.createSalt();

			adminVO.setAdminPw(secureTool.encrypt(adminVO.getAdminPw(),salt));
			adminVO.setSalt(salt);
		}
		adminMapper.modify(adminVO);
	}

	public String changeAdminPwRandom() throws Exception {
		String randomStr = secureTool.makeRandomString(10);
		AdminVO adminVO = new AdminVO();
		adminVO.setAdminPw(randomStr);

		changeAdminInfo(adminVO);

		return randomStr;
	}

	public String sendMail(String email,String id,String pw) throws Exception {
		if(pw != null) {
			mailTool.sendAdminInfoMail(email, id, pw);
			return null;
		}
		else {
			String code = secureTool.makeRandomString(8);
			mailTool.sendConfirmMail(email,code);

			return code;
		}
	}

	public void changeProfileImg(MultipartFile mfile,String fileName) throws Exception {
		FTPClient client = manager.connect();
		FileTool fileTools = new FileTool(client);

		String oldFileName = adminMapper.info().getAdminImg();

		String dir = fileTools.getProfileDirPath();

		fileTools.remove(dir, oldFileName);

		AdminVO adminVO = new AdminVO();

		adminVO.setAdminImg(fileName);
		adminMapper.modify(adminVO);

		fileTools.createProfileImg(mfile,fileName);

		manager.disconnect(client);
	}

}
