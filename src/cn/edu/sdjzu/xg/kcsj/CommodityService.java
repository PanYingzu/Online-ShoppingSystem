package cn.edu.sdjzu.xg.kcsj;

import java.sql.SQLException;
import java.util.Collection;

public class CommodityService {
    public static CommodityDao commodityDao = CommodityDao.getInstance();
    private static CommodityService commodityService
            =new CommodityService();
    private CommodityService(){}

    public static CommodityService getInstance(){
        return commodityService;
    }

    public Collection<Commodity> findAll()throws SQLException {
        return commodityDao.findAll();
    }

    public Commodity find(Integer id) throws SQLException {
        return commodityDao.find(id);
    }

    public boolean update(Commodity commodity) throws SQLException {
        return commodityDao.update(commodity);
    }

    public boolean add(Commodity commodity) throws SQLException {
        return commodityDao.add(commodity);
    }

    public boolean delete(Integer id) throws SQLException {
        Commodity commodity = this.find(id);
        return commodityDao.delete(commodity);
    }

    public boolean delete(Commodity commodity) throws SQLException {
        return commodityDao.delete(commodity);
    }
}
