package com.adg.challenge_1.model;

public class Contact
{
    private String  mName;
    private String  mPhoneNo;
    private Integer mIconType;

    public Contact(String mName, String mPhoneNo, Integer mIconType) {
        this.mName     = mName;
        this.mPhoneNo  = mPhoneNo;
        this.mIconType = mIconType;
    }

    //region Getters
    public String getName()        { return mName;     }
    public String getPhoneNumber()     { return mPhoneNo;  }
    public Integer getIconType()   { return mIconType; }
    //endregion

    //region Setters
    public void setName(String mName)          { this.mName = mName;           }
    public void setPhoneNo(String mPhoneNo)    { this.mPhoneNo = mPhoneNo;     }
    public void setIconType(Integer mIconType) { this.mIconType = mIconType;   }
    //endregion
}
