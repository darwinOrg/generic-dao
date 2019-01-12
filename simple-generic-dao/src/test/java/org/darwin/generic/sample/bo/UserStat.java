package org.darwin.generic.sample.bo;

import org.darwin.genericDao.annotations.Column;
import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.annotations.UseQueryColumnFormat;
import org.darwin.genericDao.annotations.enums.QueryColumnFormat;

@Table(db = "stat", name = "user_stat")
@UseQueryColumnFormat(QueryColumnFormat.POJO_FIELD_NAME)
public class UserStat {
    
    private int date;

    private int userId;
    
    private int show;
    private int click;
    private int cost;
    
    @Column("sum(cost)/sum(click) as acp1")
    private int acp;
    
    @Column("sum(cost)/sum(show)*100 as cpm")
    private int cpm;
    
    @Column("sum(click)/sum(show) as ctr")
    private int ctr;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAcp() {
        return acp;
    }

    public void setAcp(int acp) {
        this.acp = acp;
    }

    public int getCpm() {
        return cpm;
    }

    public void setCpm(int cpm) {
        this.cpm = cpm;
    }

    public int getCtr() {
        return ctr;
    }

    public void setCtr(int ctr) {
        this.ctr = ctr;
    }
    
    
}
