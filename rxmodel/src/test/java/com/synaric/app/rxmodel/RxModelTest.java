package com.synaric.app.rxmodel;

import android.test.ActivityTestCase;

import java.util.Date;
import java.util.Random;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Synaric on 2016/8/29 0029.
 */
public class RxModelTest extends ActivityTestCase {

    public static final String TAG = "RxModelTest";

    private RxModel rxModel;

    private DbModel<TestEntity> model;

    private Random rand = new Random();

    public void testInit() throws Exception{
        rxModel = new RxModel.Builder(getActivity()).dbName("testdb.db").build();
        model = new DbModel<TestEntity>(rxModel) {
            @Override
            public String bindID(TestEntity s) {
                return s.getId();
            }
        };
    }

    public void testInsert() throws Exception{
        TestEntity a04e7c6b = generate("a04e7c6b");
        run(model.insert(a04e7c6b), "insert");
    }

    private TestEntity generate(String id) {
        Date date = new Date();
        return new TestEntity(id, rand.nextInt(), date);
    }

    private <T> void run(Observable<T> from, String tag) {
        from.observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new RxTestSubscriber<T>(tag));
    }

    /**
     * 测试用实体类。
     */
    class TestEntity {

        private String id;
        private int count;
        private Date date;

        public TestEntity(String id, int count, Date date) {
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
    }
}
