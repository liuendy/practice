package com.common.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;

/**
 * 验证码生成
 * 
 * @author Fan Beibei at 2014-5-26 <br/>
 *         last modefied by Fan Beibei at 2014-5-26
 */
@Controller
@RequestMapping("/verfyCode")
public class VerfyCodeController
{
	@Autowired
	private Producer captchaProducer;

	@RequestMapping("/verfyCode")
	public void testVerfyCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		renderCodejpg(request, response, "verfyCode");
	}

	/**
	 * 向前端返回验证码图片
	 * 
	 * @param request
	 * @param response
	 * @param sessionKey
	 *            放入session中的key值
	 * @throws IOException
	 * @author Fan Beibei at 2014-5-26 <br/>
	 *         last modefied by Fan Beibei at 2014-5-26
	 */
	protected void renderCodejpg(HttpServletRequest request,
			HttpServletResponse response, String sessionKey) throws IOException
	{
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(sessionKey, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try
		{
			out.flush();
		} finally
		{
			out.close();
		}
	}
}
