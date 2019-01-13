package cn.dingdm.website.mapper;

import cn.dingdm.website.entities.Game;
import cn.dingdm.website.entities.Life;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LifeMapper {
    /***
     * @author dinggc
     * Description //获取生活文章的数量
     * Date 上午8:14 19-1-2
     * Param []
     * return int
     **/
    public int getLifeCount();
    /***
     * @author dinggc
     * Description //根据数量获取生活文章并进行分页
     * Date 上午8:15 19-1-2
     * Param [start, end]
     * return java.util.List<cn.dingdm.website.entities.Life>
     **/
    public List<Life> getLifeByCount(@Param("start") int start,@Param("end") int end);
    /***
     * @author dinggc
     * Description //获取游戏文章的数量
     * Date 上午8:15 19-1-2
     * Param []
     * return int
     **/
    public int getGameCount();
    /***
     * @author dinggc
     * Description //根据游戏数量进行分页
     * Date 上午8:16 19-1-2
     * Param [start, end]
     * return java.util.List<cn.dingdm.website.entities.Game>
     **/
    public List<Game> getGameByCount(@Param("start") int start, @Param("end") int end);
    /***
     * @author dinggc
     * Description //根据id获取某一篇文章的详细信息
     * Date 上午8:17 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Life
     **/
    public Life getLifeById(int id);
    /***
     * @author dinggc
     * Description //文章详细信息的其他生活文章
     * Date 上午8:17 19-1-2
     * Param [count]
     * return java.util.List<cn.dingdm.website.entities.Life>
     **/
    public List<Life> getOtherLife(int count);
    /***
     * @author dinggc
     * Description // 根据id获取某一篇游戏文章的详细信息
     * Date 上午8:18 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Game
     **/
    public Game getGameById(int id);
    /***
     * @author dinggc
     * Description //获取其他三篇游戏文章
     * Date 上午8:18 19-1-2
     * Param [count]
     * return java.util.List<cn.dingdm.website.entities.Game>
     **/
    public List<Game> getOtherGame(int count);
}
