package com.nj.baijiayun.neixun;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    boolean isBreak = false;

    @Test
    public void gson_isCorrect() {
        String json = "{\"data\":100000000}";
        System.out.println("json" + json);

        List<Object> objects = new ArrayList<>();
        Chapter chapter = new Chapter("1");
        Chapter chapter1 = new Chapter("2");
        Chapter chapter2 = new Chapter("3");
        objects.add(chapter);
        objects.add(chapter1);
        objects.add(chapter2);
        chapter.getChild().add(new Section("sec1"));
        chapter.getChild().add(new Section("sec6"));
        chapter.getChild().add(new Section("sec3"));
        chapter1.getChild().add(new Section("sec4"));
        chapter1.getChild().add(new Section("sec2"));


        Observable.just(objects.get(0))
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return o instanceof Chapter;
                    }
                }).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {
                return Observable.fromIterable(objects);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                System.out.println("filterxxxxx--->"+ ((Chapter) o).name);

                return ((Chapter) o).child != null;
            }
        }).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {

                System.out.println("flatMap--->");

                return Observable.fromIterable(((Chapter) o).child);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                System.out.println("filter--->" + ((Section) o).name);

                return ((Section) o).name.equals("sec2");
            }
        }).takeWhile(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                System.out.println("takeWhile--->" + ((Section) o).name);
                return false;
            }
        }).subscribe();

//        Map<String,String> jsonMap = new Gson().fromJson(str,new TypeToken<Map<String,String>>(){}.getType());
//        Map<String, String> jsonMap = GsonHelper.getGsonInstance().fromJson(json, new TypeToken<Map<String, String>>() {
//        }.getType());
//        System.out.println(GsonHelper.getGsonInstance().toJson(jsonMap));

        assertEquals(4, 2 + 2);


    }

    class Chapter {
        public Chapter(String name) {
            this.name = name;
        }

        private String name;
        private List<Section> child;

        public List<Section> getChild() {
            if (child == null) {
                child = new ArrayList<>();
            }
            return child;
        }

        public String getName() {
            return name;
        }
    }

    class Section {

        private String name;

        public Section(String name) {
            this.name = name;
        }
    }


}