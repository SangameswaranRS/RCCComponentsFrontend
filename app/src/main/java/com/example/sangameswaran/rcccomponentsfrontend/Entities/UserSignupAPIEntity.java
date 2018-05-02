package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class UserSignupAPIEntity {
    String uname,upassword,ucontact,uemailid,urole;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getUcontact() {
        return ucontact;
    }

    public void setUcontact(String ucontact) {
        this.ucontact = ucontact;
    }

    public String getUemailid() {
        return uemailid;
    }

    public void setUemailid(String uemailid) {
        this.uemailid = uemailid;
    }

    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }

    public interface RCCRCI{
        public void onSubmitDetails(ErrorEntity response, VolleyError error);
    }
}
