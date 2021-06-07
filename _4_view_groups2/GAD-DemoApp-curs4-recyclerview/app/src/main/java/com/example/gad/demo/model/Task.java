package com.example.gad.demo.model;

public class Task
{
    private String  mTitle;
    private String  mDescription;
    private boolean mStatus;

    public Task(String t, String d) {
        this(t, d, false);
    }

    public Task(String t, String d, boolean s) {
        mTitle          = t;
        mDescription    = d;
        mStatus         = s;
    }

    /** GETTERS **/
    public String  getTitle()        { return mTitle;        }
    public String  getDescription()  { return mDescription;  }
    public boolean getStatus()       { return mStatus;       }
    public boolean isDone()          { return mStatus;       }

    /** SETTERS **/
    public void setTitle(String t)          { mTitle = t;           }
    public void setDescription(String d)    { mDescription = d;     }
    public void setStatus(boolean s)        { mStatus = s;          }
}
