package com.example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by YJZX-LCH on 2016-11-28.
 */
public class Test {



    public static String PARTNER = "2088421979838964";

    public static String SELLER = "2088421979838964";

    public static String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN+Pr2R1MWZ85BY/z0Tua1icmoHoCfF/nftHOStqk9kzP6vyUQ601HMZ1ZDnfJpAdYlpBmKI8vKkVIWsBaybcvVpfRcZwgZcXH5NW47356TQ/8iUkbevnfpT8iYnQJZKH4sG2lRDT6wzgcaLGtTOCI3/5VGCMmjFSKnK6W4CYDRlAgMBAAECgYEAj0V2EdtJHXVMdzXNj098W/2Ax1/9f1dZ1399k+VpevjtBTT13Ybl4B3eUE8J+5DzwmwcO2moyPenc0MbH9M/h/S0uPq3MhFgowLNpyHzTAB2+wCJ5MPASrwC3H13nMwssKYPlejBjMhpqnCzUA/O4CfpyAJ+CEsdPap7A3zesAECQQDzfgFuq5+iOpJ2wCqt7o2VtrTqkkEUDvW/qGd9lYu2V8K+WF+MwLzfLBsn2VlMz6qD+pB2uQLezIpQeD7/FzDBAkEA6wuU8LfrmYsGcPuvSYTzzrkox+hqVYF4p7W/1L+nX1ovWWC5kBanfb4ylw38N5klE4GGv5BZEad3sLXBWRfIpQJBALqQ1rociED+ThDtJwaEHToUd0SwcWrUF5oVh85i0l0Rm2bLQdMQLud03Q3IbLL8/zMGvsAo/DkIUgZcTDCabUECQQCywYIqeJXzlin171PmQ/jwNR1wu69zDB5o3e7grElsWTg9bzRxAumq1eW/v+Ebn5r09MM4GV3D8Wszt2cujMoJAkA2zN2xVZPPVdgmuAc3N78F66VuHpYwRy76rVgQWAq7HvN/mcAIQpwZeWLNq925Tflb20zVg/SZXBqAzHm4PFw7";    // 鏀粯瀹濆叕閽?
    //notify_url
    public static String NOTIFY_URL = "http://localhost/yajol/web/www/starter/orange/paymentalipay_shansong.php";


    public static void main(String[] args) {

        Test test = new Test();
        String orderTitle = "1";
        String orderDescription = "";
        String totalPrice = "0.01";
        String outTradeNo = "G901642463498664";
        //
        String orderInfo = test.getOrderInfo(orderTitle, orderDescription, totalPrice,outTradeNo);


        System.out.println("订单信息 orderinfo :" + orderInfo);

        orderInfo = "_input_charset=utf-8&notify_url=http://localhost/yajol/web/www/starter/orange/paymentalipay_shansong.php&out_trade_no=G901642463498664&partner=2088421979838964&payment_type=1&return_url=http://localhost/yajol/web/www/starter/orange/paymentalipayReturn_shansong.php&seller_id=2088421979838964&service=create_direct_pay_by_user&subject=1&total_fee=0.01";
        String sign = test.sign(orderInfo);


        System.out.println("签名 sign：" + sign);
        try {

            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + test.getSignType();

        System.out.println("可用于 支付宝提交信息  payInfo：" + payInfo);
    }


    /**
     * get the sign type we use.
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * sign the order info.
     *
     * @param content
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * create the order info.
     */
    public String getOrderInfo(String subject, String body, String price, String outTradeNo) {
        price = "0.01";

        String orderInfo = "partner=" + "\"" + PARTNER + "\"";


        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";


        orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";


        orderInfo += "&subject=" + "\"" + subject + "\"";


        orderInfo += "&body=" + "\"" + body + "\"";


        orderInfo += "&total_fee=" + "\"" + price + "\"";


        orderInfo += "&notify_url=" + "\""
                + NOTIFY_URL
                + "\"";


        orderInfo += "&service=\"mobile.securitypay.pay\"";


        orderInfo += "&payment_type=\"1\"";


        orderInfo += "&_input_charset=\"utf-8\"";



        orderInfo += "&it_b_pay=\"30m\"";


        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";


        orderInfo += "&return_url=\"m.alipay.com\"";

        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
