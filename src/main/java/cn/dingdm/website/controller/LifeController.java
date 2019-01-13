package cn.dingdm.website.controller;

import cn.dingdm.website.entities.Game;
import cn.dingdm.website.entities.Life;
import cn.dingdm.website.entities.Page;
import cn.dingdm.website.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/***
 * @author dinggc
 * Description //生活模块Controller
 * Date 下午5:30 18-12-31
 * Param
 * return
 **/
@Controller
public class LifeController {

    @Autowired
    LifeService lifeService;
    /***
     * @author dinggc
     * Description //跳转到生活页面
     * Date 下午5:30 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/life")
    public String life(){
        return "life";
    }
    /***
     * @author dinggc
     * Description //获取所有感悟文章信息
     * Date 下午5:31 18-12-31
     * Param [start, limit, nowPage, Number]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getlifeIformation")
    public Object getlifeIformation(Integer start, Integer limit,Integer nowPage,Integer number){
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        page.setTotal(lifeService.getLifeCount());
        List<Life> lifeList = lifeService.getLifeByCount(page);
        for(Life life:lifeList){
            String str = life.getJs();
            if(str.length()>10){
                str = str.substring(0,10);
                life.setJs(str+"......");
            }
        }
        page.setRoot(lifeList);
        return page;
    }
    /***
     * @author dinggc
     * Description //获取所有游戏的信息
     * Date 下午5:32 18-12-31
     * Param [start, limit, nowPage, Number]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getgameIformation")
    public Object getgameIformation(Integer start, Integer limit,Integer nowPage, Integer Number){
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        page.setTotal(lifeService.getGameCount());
        List<Game> gameList = lifeService.getGameByCount(page);
        for(Game game:gameList){
            String str = game.getGrgs();
            if(str.length()>10){
                str = str.substring(0,10);
                game.setGrgs(str+"......");
            }
        }
        page.setRoot(gameList);
        return page;
    }
    /***
     * @author dinggc
     * Description //根据生活文章的id跳转到对应的具体生活文章的详细页面
     * Date 下午5:32 18-12-31
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/lifeEntrance")
    public String lifeEntrance(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        int lifeid = Integer.parseInt(id);
        Life life = lifeService.getLifeById(lifeid);
        List<Life> lifeList = lifeService.getOtherLife(3);
        for(Life lif:lifeList){
            String str = lif.getJs();
            if(str.length()>25){
                str = str.substring(0,25);
                lif.setJs(str+"......");
            }
        }
        model.addAttribute("life",life);
        model.addAttribute("lives",lifeList);
        return "lifeview";
    }
    /***
     * @author dinggc
     * Description //根据游戏文章的id跳转到对应的具体游戏文章的详细页面
     * Date 下午5:33 18-12-31
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/gameEntrance")
    public String gameEntrance(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        int gameid = Integer.parseInt(id);
        Game game = lifeService.getGameById(gameid);
        List<Game> gameList = lifeService.getOtherGame(3);
        for(Game game1:gameList){
            String str = game1.getGrgs();
            if(str.length()>25){
                str = str.substring(0,25);
                game1.setGrgs(str+"......");
            }
        }
        model.addAttribute("game",game);
        model.addAttribute("games",gameList);
        return "gameview";
    }
}
