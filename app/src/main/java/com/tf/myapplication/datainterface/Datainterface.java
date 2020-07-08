package com.tf.myapplication.datainterface;

/**
 * Created by 82108 on 2020-06-17.
 */
public interface Datainterface {
    // 데이터 삭제 후 목록 갱신을 위해 사용하는 콜백 메소드
    void dataRemove(int key, int pos);
    void dataDetail(int key);
}
