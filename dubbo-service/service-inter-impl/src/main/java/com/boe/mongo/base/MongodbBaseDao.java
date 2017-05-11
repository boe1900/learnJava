package com.boe.mongo.base;

import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class MongodbBaseDao<T> {

  @Autowired
  private MongoTemplate mongoTemplate;
  
  /**
   * 创建collection
   * */
  public DBCollection createCollection(Class<T> cla){
      return mongoTemplate.createCollection(cla);
  }
  
  /**
   * 
   * 根据条件查询实体集合
   * 
   * */
  public List<T> findEntityList(Query query){
      return mongoTemplate.find(query, this.getEntityClass());
  }
  
  /**
   * 根据条件查询记录总数
   * @param query
   * @return
   */
  public Long getPageCount(Query query) {
	  return mongoTemplate.count(query, this.getEntityClass());
  }
  
  
  /**
   * 
   * 根据主键查询一个实体
   * 
   * */
  public T findById(Object id){
      return mongoTemplate.findById(id, this.getEntityClass());
  }
  
  /**
   * 
   * 保存一个实体
   * 
   * */
  public void save(T entity){
      mongoTemplate.save(entity);
  }
  
  /**
   * 
   * 批量保存实体
   * 
   * */
  public void batchSave(List<? extends Object> list){
    mongoTemplate.insertAll(list);
  }
  
  /**
   * 分页查询
   * 
   * */
  public List<T> findEntityListByPage(Query query, Sort sort, int page, int pageSize){
    query.with(sort).skip((page-1)*pageSize).limit(pageSize);
    return findEntityList(query);
  }
  
  /**
   * 按主键修改
   * 如果文档中没有相关key 会新增 使用$set修改器
   * @param query
   * @param update
   */
  public void updateFirst(Query query, Update update) {
	  
	  mongoTemplate.updateFirst(query, update, this.getEntityClass());
  }
  
  /**
   * 修改多条
   * @param query
   * @param update
   */
  public void updateMulti(Query query, Update update) {
	  
	  mongoTemplate.updateMulti(query, update, this.getEntityClass());
  } 
  
  /**
   * 删除
   * @param query
   */
  public void remove(Query query) {
	  
	  // mongoTemplate.remove(query, this.getEntityClass());
	  mongoTemplate.findAndRemove(query,  this.getEntityClass());
  }
  
  
  /**
   * 
   * 实体Class,需要子类重写该方法
   * 
   * */
  public abstract Class<T> getEntityClass();
  
  /**
   * 
   * get mongoTemplate
   * 
   * */
  public MongoTemplate getMongoTemplate() {
    return mongoTemplate;
  }

  
  /**
   * 
   * set mongoTemplate
   * 
   * */
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }


}

