package com.cafe24.jblog2.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog2.dao.BlogDao;
import com.cafe24.jblog2.dao.CategoryDao;
import com.cafe24.jblog2.dao.PostDao;
import com.cafe24.jblog2.dao.UserDao;
import com.cafe24.jblog2.vo.BlogVo;
import com.cafe24.jblog2.vo.CategoryVo;
import com.cafe24.jblog2.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BlogDao blogDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private CategoryDao categoryDao;

	private static final String SAVE_PATH = "/mysite-uploads";
	private static final String URL = "/images";

	public BlogVo get(BlogVo blogVo) {
		return blogDao.get(blogVo);
	}

	public Map<Object, Object> get(ModelMap modelMap) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Optional<Integer> optional = (Optional<Integer>) modelMap.get("pathVariable");
		Optional<Integer> optional1 = (Optional<Integer>) modelMap.get("pathVariable1");
		Optional<Integer> optional2 = (Optional<Integer>) modelMap.get("pathVariable2");

		BlogVo blogVo = (BlogVo) modelMap.get("blogVo");
		modelMap.put("blogid", ((BlogVo) modelMap.get("blogVo")).getBlogid());

		if (optional.isPresent()) {
			modelMap.put("c_no", optional.get());
		} else if (optional1.isPresent()) {
			modelMap.put("c_no", optional1.get());
			modelMap.put("p_no", optional2.get());
		}

		map.put("blogVoGet", blogDao.get(blogVo));
		map.put("categoryVo", categoryDao.getList(blogVo, modelMap));
		map.put("postVoList", postDao.getList(modelMap));
		map.put("postVoOne", postDao.getOne(modelMap));

		return map;
	}

	public void writeblogAdminCategory(ModelMap modelMap) {
		categoryDao.writeblogAdminCategory(modelMap);
	}

	public List<CategoryVo> getCategoryList(ModelMap modelMap) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogid((String) modelMap.get("blogid"));
		return categoryDao.getList(blogVo, modelMap);
	}
//	public List<CategoryVo> getCategoryList2(ModelMap modelMap) {
//		BlogVo blogVo = new BlogVo();
//		blogVo.setBlogid((String) modelMap.get("blogid"));
//		return categoryDao.getList2(blogVo, modelMap);
//	}

	public void blogAdminWrite(ModelMap modelMap) {
		postDao.blogAdminWrite(modelMap);
	}

	public String restore(BlogVo blogVo) {
		String url = "";
		MultipartFile multipartFile = blogVo.getBlogimage();
		try {
			if (multipartFile.isEmpty()) {
				return url;
			}
			String originalFilename = multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();

			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			url = URL + "/" + saveFileName;
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		return url;
	}

	private String generateSaveFileName(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}

	public void updateBlog(BlogVo blogVo) {
		blogDao.updateBlog(blogVo);
	}

	public boolean deleteCategory(CategoryVo categoryVo) {
		int result2 = postDao.deletePost(categoryVo);
		boolean result = categoryDao.deleteCategory(categoryVo);
		return false;
	}

	public CategoryVo getCategoryNo(ModelMap modelMap) {
		return categoryDao.getCategoryNo(modelMap);
	}

	public void insertdefaultPost(CategoryVo getCategoryNo) {
		postDao.insertdefaultPost(getCategoryNo);
	}

	public UserVo checkUser(Optional<String> blogid) {
		return userDao.checkId(blogid.get());
	}

}
