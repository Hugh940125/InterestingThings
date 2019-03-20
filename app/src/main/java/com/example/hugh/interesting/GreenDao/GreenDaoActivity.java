package com.example.hugh.interesting.GreenDao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hugh.interesting.Application.App;
import com.example.hugh.interesting.GreenDao.db.DaoSession;
import com.example.hugh.interesting.GreenDao.db.UserDao;
import com.example.hugh.interesting.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        App myApp = (App) getApplication();
        DaoSession daoSession =  myApp.getDaoSession();
        userDao = daoSession.getUserDao();

        User user = new User();
        user.setId(1L);
        user.setName("小明");
        user.setAge(16);

        userDao.insert(user);
    }

    public void delete(User user){
        userDao.delete(user);
    }

    public void deleteByUserId(long userid){
        userDao.deleteByKey(1L);
    }

    public void update(User user){
        userDao.update(user);
    }

    public List<User> query(){
        return userDao.loadAll();// 查询所有记录
    }

    public User query2(){
        return userDao.loadByRowId(1);//根据ID查询
    }

    public List<User> query3(){
        return userDao.queryRaw("where AGE>?","10");//查询年龄大于10的用户
    }

    //查询年龄大于10的用户
    public List<User> query4(){
        QueryBuilder<User> builder = userDao.queryBuilder();
        return  builder.where(UserDao.Properties.Age.gt(10)).build().list();
    }
}
