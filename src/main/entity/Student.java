package entity;

import java.sql.Timestamp;

/**
 * Created by xhs on 2016/4/20.
 */

public class Student
{
    private int id;
    private String name;
    private int age;
    private Timestamp timestamp;
    private java.util.Date utilDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp birthday)
    {
        this.timestamp = birthday;
    }

    public java.util.Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }
}
