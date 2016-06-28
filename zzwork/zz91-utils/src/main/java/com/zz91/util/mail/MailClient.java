package com.zz91.util.mail;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.zz91.util.http.HttpUtils;

import net.sf.json.JSONObject;

@Deprecated
public class MailClient {

    public boolean sendMail(JSONObject sto) throws IOException, HttpException {
        boolean flag = false;
        String str = sto.toString();
        NameValuePair[] data = { new BasicNameValuePair("email", str) };
        HttpUtils.getInstance().httpPost("http://192.168.2.210:8077/zzmail/emailsender/send.htm", data, "UTF-8");
        return flag;
    }
}
