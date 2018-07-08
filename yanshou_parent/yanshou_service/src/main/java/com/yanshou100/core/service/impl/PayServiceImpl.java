package com.yanshou100.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.wxpay.sdk.WXPayUtil;
import com.yanshou100.core.dao.log.PayLogDao;
import com.yanshou100.core.pojo.log.PayLog;
import com.yanshou100.core.pojo.log.PayLogQuery;
import com.yanshou100.core.service.PayService;
import com.yanshou100.core.utils.HttpClient;

@Service
@Transactional
public class PayServiceImpl implements PayService {

	@Value("${appid}")
	private String appid;

	@Value("${mch_id}")
	private String mch_id;
	// API密钥
	@Value("${key}")
	private String key;
	// Appsecret
	// @Value("${secret}")
	// private String secret;

	@Value("${domainName}")
	private String domainName;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PayLogDao payLogDao;

	/**
	 * 生成二维码
	 */
	@Override
	public Map<String, String> payFee() {
		// 这儿先获取userId
		String userId = "1";
		PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(userId);

		// 统一下订单
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		Map<String, String> param = new HashMap<>();

		// 公众账号ID appid 是 String(32) wxd678efh567hg6787
		param.put("appid", appid);
		// 微信支付分配的公众账号ID（企业号corpid即为此appId）
		// 商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号
		param.put("mch_id", mch_id);
		// 设备号 device_info 否 String(32) 013467007045764
		// 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
		param.put("device_info", "WEB");
		// 随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
		// 随机字符串，长度要求在32位以内。推荐随机数生成算法
		param.put("nonce_str", WXPayUtil.generateNonceStr());

		// 商品描述 body 是 String(128) 腾讯充值中心-QQ会员充值
		// 商品简单描述，该字段请按照规范传递，具体请见参数规定
		param.put("body", "验收公示网");
		// 商品详情 detail 否 String(6000)
		// 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
		param.put("detail", "每次付款后都会有相应的积分，积分越多，免费上传次数越多");
		// 商户订单号 out_trade_no 是 String(32) 20150806125346
		// 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
		param.put("out_trade_no", payLog.getItemId().replaceAll("-", ""));

		// 标价币种 fee_type 否 String(16) CNY 符合ISO
		// 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
		// 标价金额 total_fee 是 Int 88 订单总金额，单位为分，详见支付金额
		// param.put("total_fee", payLog.getPayFee() * 100 + "");
		param.put("total_fee", "1");
		// 终端IP spbill_create_ip 是 String(16) 123.12.12.123
		// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		param.put("spbill_create_ip", "127.0.0.1");
		// 订单优惠标记 goods_tag 否 String(32) WXG
		// 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
		// 通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php
		// 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
		param.put("notify_url", domainName);
		// 交易类型 trade_type 是 String(16) JSAPI
		param.put("trade_type", "NATIVE");
		// 商品ID product_id 否 String(32) 12235413214070356458058
		// trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
		param.put("product_id", payLog.getItemId().replaceAll("-", ""));
		// 指定支付方式 limit_pay 否 String(32) no_credit
		// 上传此参数no_credit--可限制用户不能使用信用卡支付
		// 用户标识 openid 否 String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
		// trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换

		try {
			// 签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6
			// 通过签名算法计算得出的签名值，详见签名生成算法
			String signedXml = WXPayUtil.generateSignedXml(param, key);
			HttpClient httpClient = new HttpClient(url);
			httpClient.setXmlParam(signedXml);
			httpClient.setHttps(true);
			httpClient.post();
			String content = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(content);
			map.put("out_trade_no", payLog.getItemId().replaceAll("-", ""));
			map.put("total_fee", payLog.getPayFee() * 100 + "");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询支付结果
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> queryResult() {
		// 这儿先获取userId
		String userId = "1";
		PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(userId);

		// 查询订单
		String url = "https://api.mch.weixin.qq.com/pay/orderquery";

		Map<String, String> param = new HashMap<>();

		// 公众账号ID appid 是 String(32) wxd678efh567hg6787
		// 微信支付分配的公众账号ID（企业号corpid即为此appId）
		param.put("appid", appid);
		// 商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号
		param.put("mch_id", mch_id);
		// 微信订单号 transaction_id 二选一 String(32) 1009660380201506130728806387
		// 微信的订单号，建议优先使用
		// 商户订单号 out_trade_no String(32) 20150806125346
		// 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
		param.put("out_trade_no", payLog.getItemId().replaceAll("-", ""));
		// 随机字符串 nonce_str 是 String(32) C380BEC2BFD727A4B6845133519F3AD6
		// 随机字符串，不长于32位。推荐随机数生成算法
		param.put("nonce_str", WXPayUtil.generateNonceStr());
		// 签名 sign 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
		// 通过签名算法计算得出的签名值，详见签名生成算法

		try {
			// 签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6
			// 通过签名算法计算得出的签名值，详见签名生成算法
			String signedXml = WXPayUtil.generateSignedXml(param, key);
			HttpClient httpClient = new HttpClient(url);
			httpClient.setXmlParam(signedXml);
			httpClient.setHttps(true);
			httpClient.post();
			String content = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(content);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新支付记录表
	 * 
	 * @param payLog
	 */
	@Override
	public void updatePayLog(PayLog payLog) {
		PayLogQuery query = new PayLogQuery();
		query.createCriteria().andItemIdEqualTo(payLog.getItemId());
		List<PayLog> list = payLogDao.selectByExample(query);
		PayLog newPayLog = list.get(0);
		// 支付日期
		newPayLog.setPayDate(new Date());
		// 支付状态
		newPayLog.setPayStatus("0");
		// 支付类型
		newPayLog.setPayType("1");
		// 支付状态
		newPayLog.setPayStatus("0");
		// 微信支付订单号
		newPayLog.setTransactionid(payLog.getTransactionid());

		// 更新支付记录
		payLogDao.updateByPrimaryKey(newPayLog);
	}

	/**
	 * 关闭微信服务器上的订单，强制二维码失效，删除数据库中未完成交易的支付记录
	 */
	@Override
	public void closeOrderAndDeletePayLog() {
		// 这儿先获取userId
		String userId = "1";
		PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(userId);

		// 关闭订单
		String url = "https://api.mch.weixin.qq.com/pay/closeorder";

		Map<String, String> param = new HashMap<>();

		// 公众账号ID appid 是 String(32) wx8888888888888888
		// 微信分配的公众账号ID（企业号corpid即为此appId）
		param.put("appid", appid);
		// 商户号 mch_id 是 String(32) 1900000109 微信支付分配的商户号
		param.put("mch_id", mch_id);
		// 商户订单号 out_trade_no 是 String(32) 1217752501201407033233368018
		// 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
		param.put("out_trade_no", payLog.getItemId().replaceAll("-", ""));
		// 随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
		// 随机字符串，不长于32位。推荐随机数生成算法
		param.put("nonce_str", WXPayUtil.generateNonceStr());

		try {
			// 签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6
			// 通过签名算法计算得出的签名值，详见签名生成算法
			String signedXml = WXPayUtil.generateSignedXml(param, key);
			HttpClient httpClient = new HttpClient(url);
			httpClient.setXmlParam(signedXml);
			httpClient.setHttps(true);
			httpClient.post();
			String content = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(content);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//删除数据库中未进行付款的支付记录
		PayLogQuery query = new PayLogQuery();
		query.createCriteria().andItemIdEqualTo(payLog.getItemId());
		payLogDao.deleteByExample(query);
	}
}
