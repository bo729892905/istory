package cn.bluemobi.controller.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.controller.admin.FileController;
import cn.bluemobi.entity.AboutUs;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.MobileCode;
import cn.bluemobi.entity.RegisterProtocol;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.AboutUsService;
import cn.bluemobi.service.AttentionService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MobileCodeService;
import cn.bluemobi.service.RegisterProtocolService;
import cn.bluemobi.service.SubscibeService;
import cn.bluemobi.util.encryption.MD5Tools;
import cn.bluemobi.util.text.TextHelper;

/**
 * App用户登录验证接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppLoginController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private RegisterProtocolService registerProtocolService;
	@Autowired
	private AboutUsService aboutUsService;
	@Autowired
	private AttentionService  attentionService;
	@Autowired
	private SubscibeService subscibeService;
	@Autowired
	private MobileCodeService mobileCodeService;
	
	/**
	 * app登录
	 */
	@RequestMapping(value = "app/login", method = RequestMethod.POST)
	public void checkMobileLogin() {
		AppData data = new AppData();
		try {
			// 获取用户名和密码，如果为空则返回参数错误
			String mobile = getParameter("mobile");
			String password = getParameter("password");
			if (TextHelper.isNullOrEmpty(mobile)
					|| TextHelper.isNullOrEmpty(password)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				Member member = memberService.getMemberByMobile(mobile);
				if (!TextHelper.isNullOrEmpty(member)) {
					if (MD5Tools.encode(password).equals(member.getPassword())) {
						if (member.getStatus().equals("1")) {
							if (TextHelper.isNullOrEmpty(member.getLastLoginTime())) {
								// 如果用户是第一次登录，则提示用户需要完善个人资料
								data.setStatus(FAIL);
								data.setMsg("need_complete_info");
								data.putInData("id", member.getId());
							} else {
								// 记录登录时间
								memberService.saveLastLoginTime(member.getId().toString());
								data.setStatus(SUCCESS);
								data.putInData("member", member);
							}
						} else {
							// 登录失败，禁止登陆
							data.setStatus(FAIL);
							data.setMsg("forbid_login");
						}
					} else {
						// 登录失败，密码错误
						data.setStatus(FAIL);
						data.setMsg("error_password");
					}
				} else {
					// 用户名不存在
					data.setStatus(FAIL);
					data.setMsg("error_mobile");
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		} finally {
			outJSON(data);
		}
	}

	/**
	 * 忘记密码
	 */
	@RequestMapping(value = "app/changePWD", method = RequestMethod.POST)
	public void changePWD(String oldPWD, String newPWD, String mobile) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(newPWD) || TextHelper.isNullOrEmpty(mobile)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				Member member = memberService.getMemberByMobile(mobile);
				if(!TextHelper.isNullOrEmpty(member)){
					if(TextHelper.isNullOrEmpty(oldPWD)){
						newPWD = MD5Tools.encode(newPWD);
						memberService.changePassword(newPWD, mobile);
						data.setStatus(SUCCESS);
					}else{
						if(MD5Tools.encode(oldPWD).equals(member.getPassword())){
							newPWD = MD5Tools.encode(newPWD);
							memberService.changePassword(newPWD, mobile);
							data.setStatus(SUCCESS);
						}else{
							data.setStatus(FAIL);
							data.setMsg("oldPWD_error");
						}
					}
				}else{
					data.setStatus(FAIL);
					data.setMsg("mobile_error");
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		} finally {
			outJSON(data);
		}
	}

	/**
	 * 用户资料修改
	 */
	@RequestMapping(value = "app/changeInfo", method = RequestMethod.POST)
	public void changeInfo(String memberId, MultipartRequest re,String name, String sex,String introduction) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			MultipartFile iconFile = re.getFile("icon");
			if (TextHelper.isNullOrEmpty(memberId)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				if(!TextHelper.isNullOrEmpty(name)){
					name = TextHelper.rhtml(name);
				}
				//2表示上传文件类型是图片
				Map<String,Object> img1Map = FileController.uploadFile(iconFile,"2");
				String icon = TextHelper.isNullOrEmpty(img1Map.get("data"))?"":img1Map.get("data").toString();
				if(TextHelper.isNullOrEmpty(icon)&&TextHelper.isNullOrEmpty(name)&&TextHelper.isNullOrEmpty(sex)&&TextHelper.isNullOrEmpty(introduction)){
					data.setStatus(FAIL);
					data.setMsg(PARAM_ERROR);
					return;
				}
				memberService.changeInfo(memberId, icon, name, sex,introduction);
				// 记录登录时间
				memberService.saveLastLoginTime(memberId);
				Member member = memberService.getMemberById(memberId);
				data.setStatus(SUCCESS);
				data.putInData("member", member);
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		} finally {
			outJSON(data);
		}
	}
	/**
	 * 注册协议
	 */
	@RequestMapping(value = "app/registerProtocol", method = RequestMethod.POST)
	public void registerProtocol() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			RegisterProtocol rp = registerProtocolService.getRegisterProtocol();
			data.putInData("registerProtocol",rp);
			data.setStatus(SUCCESS);
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
	
	
	
	/**
	 * 生成注册短信验证码
	 * @param args
	 */
	@RequestMapping(value = "app/getCode", method = RequestMethod.POST)
	public  void getCode() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			int code = (int)((Math.random()*9+1)*100000);
			String mobile = getParameter("mobile");
			//1是注册  0是找回密码
			String type = getParameter("type");
			if(TextHelper.isNullOrEmpty(mobile)||TextHelper.isNullOrEmpty(type)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				if(("1").equals(type)){//判断是手机号注册
					Member member = memberService.getMemberByMobile(mobile);
					//手机号未注册，则发送验证码
					if(TextHelper.isNullOrEmpty(member)){
							String resultStr = sendSms("【YOU故事】验证码是:"+code+"。您正在使用YOU故事注册会员，请尽快完成验证。",mobile);
							if(resultStr !="" && "success".equals(resultStr)){//发送成功
							 	mobileCodeService.createCode(code,mobile,type);
							   	data.putInData(map);
							   	data.setStatus(SUCCESS);
							 }else{
								data.setStatus(FAIL);
								data.setMsg("send_error");
							 }
					}else{//如果已经注册，则返回已经注册
						data.setStatus(FAIL);
						data.setMsg("already_register");
					}
				}else{//判断是修改密码操作
					Member member =  memberService.getMemberByMobile(mobile);
					//手机号存在
					if(!TextHelper.isNullOrEmpty(member)){
							String resultStr = sendSms("【YOU故事】验证码是:"+code+"。您正在使用YOU故事找回密码，请尽快完成验证。",mobile);
							if(resultStr !="" && "success".equals(resultStr)){
							 	mobileCodeService.createCode(code,mobile,type);
							   	data.putInData(map);
							   	data.setStatus(SUCCESS);
							 }else{
								data.setStatus(FAIL);
								data.setMsg("send_error");
							 }
					}else{//如果未注册，则返回未注册
						data.setStatus(FAIL);
						data.setMsg("mobile_not_register");
					}
					
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
	
	/**
	 * 验证码验证
	 */
	@RequestMapping(value = "app/checkCode", method = RequestMethod.POST)
	public void checkCode() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			String mobile = getParameter("mobile");
			String code = getParameter("code");
			String type = getParameter("type");
			if(TextHelper.isNullOrEmpty(mobile) || TextHelper.isNullOrEmpty(code) || TextHelper.isNullOrEmpty(type)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}
			else{
				MobileCode mc= mobileCodeService.getLastCode(mobile);
				if(!TextHelper.isNullOrEmpty(mc) && type.equals(mc.getType())){
					String createDate =  mc.getCreateDate();
					Date date = new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date date2 = sdf.parse(createDate);
			        long temp = date.getTime() - date2.getTime();    //相差毫秒数
			        long hours = temp / 1000 / 3600;                //相差小时数
			        long temp2 = temp % (1000 * 3600);
			        long mins = temp2 / 1000 / 60;                    //相差分钟数
			        if(hours>=1L||mins>=10L){    //时间超过10分钟，返回验证码失效
			        	data.setStatus(FAIL);
			        	data.setMsg("code_delay");
			        }else if(!mc.getCode().equals(code)||!mc.getMobile().equals(mobile)|| "1".equals(mc.getIsUsed())){         
			        	data.setStatus(FAIL);
			        	data.setMsg("code_error");
			        }else{
			        	//将验证码状态改为已使用
			        	mobileCodeService.updateUsed(mc.getId());
		        		data.setStatus(SUCCESS);
			        }
				}else{
					data.setStatus(FAIL);
					data.setMsg("code_error");
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
	
	/**
	 * 注册
	 */
	@RequestMapping(value = "app/doRegister", method = RequestMethod.POST)
	public void doRegister(Member me) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//判断参数是否为空
			if(TextHelper.isNullOrEmpty(me.getSource())||TextHelper.isNullOrEmpty(me.getPassword())||TextHelper.isNullOrEmpty(me.getMobile())){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}
			else{
				//手机注册，手机号是否存在，在发送验证码的时候就验证。
				//验证用户名是否存在
				Member member = memberService.getMemberByMobile(me.getMobile());
				//验证用户名是否存在,不为空，表示用户名已经存在
				if(!TextHelper.isNullOrEmpty(member)){
					data.setStatus(FAIL);
					data.setMsg("mobile_exist");
				}else{
					me.setSource("1");
					//将密码加密
					me.setPassword(MD5Tools.encode(me.getPassword()));
					Long id = memberService.create(me);
					data.putInData("id",id);
					data.setStatus(SUCCESS);
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
	
	/**
	 * 第三方登录，验证是否是用户。
	 */
	@RequestMapping(value = "app/forThirdLogin", method = RequestMethod.POST)
	public void forThirdLogin() {

		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			// 2 QQ 3微博 4微信
			String source = getParameter("source");
			String code = getParameter("code");
			if (TextHelper.isNullOrEmpty(source)
					&& TextHelper.isNullOrEmpty(code)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				// 1注册 2QQ 3微博 4微信
				// qq登录，如果已经存在则返回用户信息
				Member member = new Member();
				if (("2").equals(source)) {
					member = memberService.getMemberByQQ(code);
				} else if (("4").equals(source)) {
					member = memberService.getMemberByWechart(code);
				} else if (("3").equals(source)) {
					member = memberService.getMemberByWeibo(code);
				}
				if (!TextHelper.isNullOrEmpty(member)) {
					// 用户权限 1启用 0禁用
					if (member.getStatus().equals("1")) {
						if (TextHelper.isNullOrEmpty(member.getLastLoginTime())) {
							// 如果用户是第一次登录，则提示用户需要完善个人资料
							data.setStatus(FAIL);
							data.setMsg("need_complete_info");
							data.putInData("id", member.getId());
						} else {
							// 记录登录时间
							memberService.saveLastLoginTime(member.getId().toString());
							data.setStatus(SUCCESS);
							data.putInData("member", member);
						}
					} else {
						// 登录失败，禁止登陆
						data.setStatus(FAIL);
						data.setMsg("forbid_login");
					}
				} else {
					// 创建用户
					Long memberId = memberService.createMemberForThird(source,code);
					data.setStatus(FAIL);
					data.setMsg("need_complete_info");
					data.putInData("id", memberId);
				}
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		} finally {
			outJSON(data);
		}
	}
	/**
	 * 关于我们
	 */
	@RequestMapping(value = "app/aboutUs", method = RequestMethod.POST)
	public void aboutUs() {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			AboutUs au= aboutUsService.getAboutUs();
			data.putInData("AboutUs",au);
			data.setStatus(SUCCESS);
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
	
	/**
	 * 发送短信
	 * @param text
	 * @param mobile
	 * @return
	 * @throws IOException
	 */
	  public static String sendSms( String text, String mobile) throws IOException {
		  	//发送内容
			String content = text; 
			
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://m.5c.com.cn/api/send/index.php?");
			
			// APIKEY
			sb.append("apikey=319c7ab651d28685e860b59dbb39515f");

			//用户名
			sb.append("&username=bjyb");

			// 向StringBuffer追加密码
			sb.append("&password=asdf123");

			// 向StringBuffer追加手机号码
			sb.append("&mobile="+mobile);

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content="+URLEncoder.encode(content,"UTF-8"));

			// 创建url对象
			URL url = new URL(sb.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			String resultStr = "";
			if(inputline != null || inputline != "" ){
				String[] array =  inputline.split(":");
				resultStr = array[0].toString();
			}
			return resultStr;

	    }
	
	

}
