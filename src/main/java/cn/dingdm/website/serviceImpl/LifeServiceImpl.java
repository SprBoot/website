package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.entities.Game;
import cn.dingdm.website.entities.Life;
import cn.dingdm.website.entities.Page;
import cn.dingdm.website.mapper.LifeMapper;
import cn.dingdm.website.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LifeServiceImpl implements LifeService {

    @Autowired
    LifeMapper lifeMapper;
    @Override
    @Cacheable(cacheNames = "lifeCount")
    public int getLifeCount() {
        return lifeMapper.getLifeCount();
    }

    @Override
    @Cacheable(cacheNames = "lifeList")
    public List<Life> getLifeByCount(Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return lifeMapper.getLifeByCount(start,end-start);
    }

    @Override
    @CachePut(cacheNames = "gameCount")
    public int getGameCount() {
        return lifeMapper.getGameCount();
    }

    @Override
    @CachePut(cacheNames = "gameList")
    public List<Game> getGameByCount(Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return lifeMapper.getGameByCount(start,end-start);
    }

    @Override
    @CachePut(cacheNames = "life")
    public Life getLifeById(int id) {
        return lifeMapper.getLifeById(id);
    }

    @Override
    @CachePut(cacheNames = "otherLife")
    public List<Life> getOtherLife(int count) {
        return lifeMapper.getOtherLife(count);
    }

    @Override
    @CachePut(cacheNames = "game")
    public Game getGameById(int id) {
        return lifeMapper.getGameById(id);
    }

    @Override
    @CachePut(cacheNames = "otherGame")
    public List<Game> getOtherGame(int count) {
        return lifeMapper.getOtherGame(count);
    }
}
