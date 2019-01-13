package cn.dingdm.website.service;

import cn.dingdm.website.entities.Game;
import cn.dingdm.website.entities.Life;
import cn.dingdm.website.entities.Page;

import java.util.List;

public interface LifeService {
    public int getLifeCount();
    public List<Life> getLifeByCount(Page page);
    public int getGameCount();
    public List<Game> getGameByCount(Page page);
    public Life getLifeById(int id);
    public List<Life> getOtherLife(int count);
    public Game getGameById(int id);
    public List<Game> getOtherGame(int count);
}
