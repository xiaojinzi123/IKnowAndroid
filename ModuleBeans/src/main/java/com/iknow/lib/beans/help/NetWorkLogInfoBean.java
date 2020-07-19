package com.iknow.lib.beans.help;

import java.util.ArrayList;
import java.util.List;

public class NetWorkLogInfoBean {

    /**
     * 'req_method':'POST',
     * 'req_url':'www.baidu.com',
     * 'req_headers':['req_header1=1','req_header2=2','req_header3=3'],
     * 'req_body':'我是请求body,可以是一个json数据',
     * 'res_code':'200',
     * 'res_message':'ok',
     * 'res_headers':['res_header1=1','res_header2=2','res_header3=3'],
     * 'res_body':'我是响应body,可以是一个json数据'
     */

    private String req_method;
    private String req_url;
    private List<String> req_headers = new ArrayList<>();
    private String req_body;
    private int res_code;
    private String res_message;
    private List<String> res_headers = new ArrayList<>();
    private String res_body;

    public String getReq_method() {
        return req_method;
    }

    public void setReq_method(String req_method) {
        this.req_method = req_method;
    }

    public String getReq_url() {
        return req_url;
    }

    public void setReq_url(String req_url) {
        this.req_url = req_url;
    }

    public List<String> getReq_headers() {
        return req_headers;
    }

    public void setReq_headers(List<String> req_headers) {
        this.req_headers = req_headers;
    }

    public String getReq_body() {
        return req_body;
    }

    public void setReq_body(String req_body) {
        this.req_body = req_body;
    }

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getRes_message() {
        return res_message;
    }

    public void setRes_message(String res_message) {
        this.res_message = res_message;
    }

    public List<String> getRes_headers() {
        return res_headers;
    }

    public void setRes_headers(List<String> res_headers) {
        this.res_headers = res_headers;
    }

    public String getRes_body() {
        return res_body;
    }

    public void setRes_body(String res_body) {
        this.res_body = res_body;
    }
}
