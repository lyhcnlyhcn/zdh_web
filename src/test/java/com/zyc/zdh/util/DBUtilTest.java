package com.zyc.zdh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DBUtilTest {

    @Test
    public void dbConnection(){

        String driver="oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@10.136.1.61:1521:orcl";
        String username = "fxjk";
        String password = "fxjk123";
        try {
            List<String> list=new DBUtil().R3(driver,url,username,password,"");

            for(String table:list){
                System.out.println("表名:"+table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dbConnectionMysql(){

        String driver="com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/mydb?serverTimezone=GMT%2B8&useSSL=false";
        String username = "zyc";
        String password = "123456";
        try {
            List<String> list=new DBUtil().R3(driver,url,username,password,"");

            for(String table:list){
                System.out.println("表名:"+table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void a(){
        String url="http://DEEP-2020KLZJDI:4040/api/v1/applications/local-1603508571432/jobs";

        try {
            List<NameValuePair> npl=new ArrayList<>();
            //npl.add(new BasicNameValuePair("status1","sab"));
            String result=HttpUtil.getRequest(url,npl);
            System.out.println(result);
            JSONArray jsonArray= JSON.parseArray(result);
            String jobGroup="jobGroup";
            List<String> killJobs=new ArrayList<>();
            for(Object jo:jsonArray){
                JSONObject j=(JSONObject) jo;
                if(j.getString(jobGroup).startsWith("769515191510503424")){
                    killJobs.add(j.getString(jobGroup));
                }
            }
            JSONObject js=new JSONObject();
            js.put("task_logs_id","111");//写日志使用
            js.put("jobGroups",killJobs);
            //发送杀死请求
            String kill_url="http://deep-2020klzjdi:60001/api/v1/kill";
            HttpUtil.postJSON(kill_url,js.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}