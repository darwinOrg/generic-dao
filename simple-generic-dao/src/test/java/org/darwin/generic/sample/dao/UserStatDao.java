package org.darwin.generic.sample.dao;

import org.darwin.generic.sample.bo.UserStat;
import org.darwin.genericDao.dao.impl.GenericStatDao;
import org.darwin.genericDao.operate.Matches;
import org.darwin.genericDao.operate.Orders;

public class UserStatDao extends GenericStatDao<UserStat>{
    
    public static void main(String[] args) {
        
        UserStatDao dao = new UserStatDao();
        dao.find(Matches.one("acp", 1), Orders.asc("acp"));
    }

}
