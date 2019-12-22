package cn.edu.sdjzu.xg.kcsj;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/commodity.ctl")
public class CommodityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //读取参数id
        String id_str = req.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有商品对象，否则响应id指定的商品对象
            if (id_str == null) {
                responseCommodities(resp);
            } else {
                int id = Integer.parseInt(id_str);
                responseCommodity(id, resp);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            //响应message到前端
            resp.getWriter().println(message);
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            resp.getWriter().println(message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String commodity_json = JSONUtil.getJSON(req);
        //将JSON字串解析为Commodity对象
        Commodity commodityToAdd = JSON.parseObject(commodity_json,Commodity.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。
        commodityToAdd.setId(4 + (int)(Math.random()*100));
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Commodity对象
        try {
            CommodityDao.getInstance().add(commodityToAdd);
            message.put("message","增加成功");
        } catch (SQLException e) {
            message.put("message","数据库异常");
            e.printStackTrace();
        }catch (Exception e){
            message.put("message","其他异常");
            e.printStackTrace();
        }
        resp.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commodity_json = JSONUtil.getJSON(req);
        Commodity commodityToUpdate = JSON.parseObject(commodity_json,Commodity.class);
        JSONObject message = new JSONObject();
        try {
            CommodityDao.getInstance().update(commodityToUpdate);
            message.put("message","修改成功成功");
        } catch (SQLException e) {
            message.put("message","数据库异常");
            e.printStackTrace();
        }catch (Exception e){
            message.put("message","网络异常");
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //读取参数id
        String id_str = req.getParameter("id");
        int id = Integer.parseInt(id_str);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的商品
        try {
            CommodityService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        resp.getWriter().println(message);
    } //响应一个商品对象
    private void responseCommodity(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找商品
        Commodity commodity = CommodityService.getInstance().find(id);
        String commodity_json = JSON.toJSONString(commodity);

        //响应commodity_json到前端
        response.getWriter().println(commodity_json);
    }
    //响应所有商品对象
    private void responseCommodities(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有商品
        Collection<Commodity> commoditys = CommodityService.getInstance().findAll();
        String commodities_json = JSON.toJSONString(commoditys, SerializerFeature.DisableCircularReferenceDetect);

        //响应commoditys_json到前端
        response.getWriter().println(commodities_json);
    }
    
}
