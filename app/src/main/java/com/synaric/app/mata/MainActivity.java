package com.synaric.app.mata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.synaric.app.rxmodel.DbModel;
import com.synaric.app.rxmodel.RxModel;
import com.synaric.app.rxmodel.filter.Filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private RxModel rxModel;
    private DbModel<MyEntity> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testCreate();
        //testInsert();
        //testInsertAll();
        //testSave();
        //testSaveAll();
        //testQuery();
        testQueryAll();
        //testQueryFirst();
        //testDelete();
    }

    private void testCreate() {
        rxModel = new RxModel.Builder(this).dbName("testdb.db").build();
        model = new DbModel<MyEntity>(rxModel) {
            @Override
            public String bindID(MyEntity s) {
                return s.getId();
            }
        };
    }

    private void testInsert() {
        MyEntity a04ec7 = new MyEntity("a04ec7", 1, new Date());
        model
            .insert(a04ec7)
            .subscribe(aBoolean -> {
                Log.d(TAG, "insert: " + aBoolean);
            });
    }

    private void testInsertAll() {
        MyEntity a04ec8 = new MyEntity("a04ec8", 2, new Date());
        MyEntity a04ec9 = new MyEntity("a04ec9", 3, new Date());
        MyEntity a04ed0 = new MyEntity("a04ed0", 4, new Date());
        List<MyEntity> lst = new ArrayList<>();
        lst.add(a04ec8);
        lst.add(a04ec9);
        lst.add(a04ed0);

        model
            .insertAll(lst)
            .subscribe(aBoolean -> {
                Log.d(TAG, "insertAll: " + aBoolean);
            });
    }

    private void testSave() {
        //覆盖旧数据
        MyEntity a04ed0 = new MyEntity("a04ed0", 8, new Date());

        model
            .save(a04ed0)
            .subscribe(aBoolean -> {
                Log.d(TAG, "save: " + aBoolean);
            });
    }

    private void testSaveAll() {
        MyEntity a04ed0 = new MyEntity("a04ed0", 4, new Date());
        MyEntity a04ed1 = new MyEntity("a04ed1", 5, new Date());
        MyEntity a04ed2 = new MyEntity("a04ed2", 6, new Date());
        List<MyEntity> lst = new ArrayList<>();
        lst.add(a04ed0);
        lst.add(a04ed1);
        lst.add(a04ed2);

        model
            .saveAll(lst)
            .subscribe(aBoolean -> {
                Log.d(TAG, "saveAll: " + aBoolean);
            });
    }

    private void testQuery() {
        model
            .query(MyEntity.class, new Filter<MyEntity>() {
                @Override
                public boolean doIterativeFilter(MyEntity myEntity) {
                    //查询所有表MyEntity中count > 3的数据
                    return myEntity.getCount() > 3;
                }
            })
            .subscribe(testEntities -> {
                //打印查询结果
                Log.d(TAG, "query: ");
                for (MyEntity t : testEntities) {
                    Log.d(TAG, t.toString());
                }
            });
    }

    private void testQueryAll() {
        model
            .queryAll(MyEntity.class)
            .subscribe(testEntities -> {
                Log.d(TAG, "queryAll: ");
                for (MyEntity t : testEntities) {
                    Log.d(TAG, t.toString());
                }
            });
    }

    private void testQueryFirst() {
        model
            .queryFirst(MyEntity.class)
            .subscribe(myEntity -> {
                Log.d(TAG, "queryFirst: " + myEntity);
            });

        model
            .queryFirst(MyEntity.class, new Filter<MyEntity>() {
                @Override
                public boolean doIterativeFilter(MyEntity myEntity) {
                    return myEntity.getCount() > 4;
                }
            })
            .subscribe(myEntity -> {
                Log.d(TAG, "queryFirst: " + myEntity);
            });
    }

    private void testDelete() {
        model
            .delete(MyEntity.class, new Filter<MyEntity>() {
                @Override
                public boolean doIterativeFilter(MyEntity myEntity) {
                    return myEntity.getCount() > 5;
                }
            })
            .subscribe(integer -> {
                Log.d(TAG, "delete: rows = " + integer);
            });
    }

    class MyEntity {

        private String id;
        private int count;
        private Date date;

        public MyEntity(String id, int count, Date date) {
            this.id = id;
            this.count = count;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "MyEntity{" +
                    "id='" + id + '\'' +
                    ", count=" + count +
                    ", date=" + date +
                    '}';
        }
    }
}
