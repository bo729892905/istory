package cn.bluemobi.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
/**
 * JPush推送类
 * @author xiazf
 *
 */
public class PushExample {

    // demo App defined in resources/jpush-api.conf 
//	private static final String appKey ="7cb11c479d330870e628df39";
//	private static final String masterSecret = "7d0a14372d49500d12addf8e";
	
	
	
//	private static final String appKey ="48327a42a676f33b79851eba";
//	private static final String masterSecret = "e0bf80f4a3c2edf005025a9e";
	
	
	private static final String appKey ="d9ec4e8db50527e4605d78a9";
	private static final String masterSecret = "8fd7e2cedf40216693770996"; 
	
	public static final String TITLE = "Test from API example";
    public static final String ALERT = "1";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    
   /* //测试
	public static void main(String[] args) {
//	    testSendPush();
	}*/
	
	/**
	 * 发送消息
	 * @param id
	 * @param content
	 */
	public static void testSendPush(String id,String content) {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras(id,content);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(("Got result - " + result));

        } catch (APIConnectionException e) {
            System.err.println("Connection error. Should retry later. "+e);

        } catch (APIRequestException e) {
            System.err.println("Error response from JPush server. Should review and fix it. "+e);
            System.err.println("HTTP Status: " + e.getStatus());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getErrorMessage());
            System.err.println("Msg ID: " + e.getMsgId());
        }
	}
	
	
	
	/**
	 * App推送
	 * @return
	 */
	public static void testSendPushApp(String[] id,String content,String type) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios(id,content,type);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(("Got result - " + result));

        } catch (APIConnectionException e) {
            System.err.println("Connection error. Should retry later. "+e);

        } catch (APIRequestException e) {
            System.err.println("Error response from JPush server. Should review and fix it. "+e);
            System.err.println("HTTP Status: " + e.getStatus());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getErrorMessage());
            System.err.println("Msg ID: " + e.getMsgId());
        }
	}
	
	
	public static void main(String[] args) {
//		String[] id = new String[1];
//		id[0] = "ID_1";
//		for (int i = 0; i < id.length; i++) {
//			System.out.println(id[i]);
//		}
//        testSendPushApp(id,"推送消息测试","1","2","3");
//		testSendPushApp("hello");
		
	}
	
	/**
	 * 一次最多推送1000条
	 * @param list
	 * @param content
	 * @param type
	 */
	public static void testSendPushApp(List<String> list,String content,String type){
		String[] array = new String[1000]; 
		Boolean left = false;
		int count = 0;
	    for (int i = 0; i < list.size(); i++) {
			if(i%1000 == 0 && i != 0 ){
				String[] newArray = Arrays.copyOfRange(array,0,i-1);
			    testSendPushApp(newArray,content,type);
				array = new String[1000];
				left= true;
				count = 0;
			}
			count++;
			array[i%1000] = "ID_"+list.get(i);
		}
	    if(left || list.size()<=1000){
	    	String[] newArray = Arrays.copyOfRange(array,0,count);
	    	testSendPushApp(newArray,content,type);
	    }
		
		
	}
	public static void testSendPushApp(String message) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert(message);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(("Got result - " + result));

        } catch (APIConnectionException e) {
            System.err.println("Connection error. Should retry later. "+e);

        } catch (APIRequestException e) {
            System.err.println("Error response from JPush server. Should review and fix it. "+e);
            System.err.println("HTTP Status: " + e.getStatus());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getErrorMessage());
            System.err.println("Msg ID: " + e.getMsgId());
        }
	}
	
	
	
	
	public static PushPayload buildPushObject_all_all_alert(String message) {
	    return PushPayload.alertAll(message);
	}
	
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("id_115"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
    
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    
    /**
     * 往所有设备发送通知
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios(String[] id,String content,String type) {
    	
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(id))
                .setNotification(Notification.newBuilder()
                		.setAlert(content)
                		.addPlatformNotification(AndroidNotification.newBuilder().setTitle("YOU故事").addExtra("type", type).build())
                		.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra("type", type).build())
                		.build())
    		   .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                .build();
    }
    
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    /**
     * 单个设备消息推送
     * @param id
     * @param content
     * @return
     */
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String id,String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias(id))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
//                        .addExtra("from", "JPush");
                        .build())
                .build();
    }
    
    
    /**
     * 单个设备消息推送
     * @param id
     * @param content
     * @return
     */
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtrasForAll(String id,String content,String contentType,String ids) {
    	
    	
    	
    	
    	
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias(id))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content).setContentType(contentType)
                        .addExtra("ids", "id_115")
                        .build())
                .build();
    }
    
    
}

