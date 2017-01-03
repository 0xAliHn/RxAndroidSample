package com.android.rxandroid;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.android.rxandroid.databinding.ActivitySampleRx1Binding;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class SimpleRxActivity extends AppCompatActivity {

    ActivitySampleRx1Binding rx1Binding;
    Observable<String> myObservable;
    Observer<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 1");
        createObservableAndObserver();

        rx1Binding = DataBindingUtil.setContentView(this, R.layout.activity_sample_rx1);

        //Linking Observable and Observer
        //This is done with the Observable’s subscribe() method,
        // which is called whenever the “Subscribe” button is clicked.
        rx1Binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myObservable.subscribe(myObserver);
            }
        });
    }

    //Creating an Observable
    private void createObservableAndObserver() {
        myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(rx1Binding.editText.getText().toString());
                        sub.onCompleted();
                    }
                }
        );

        //Creating an Observer
        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                System.out.println("SimpleRxActivity.onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("SimpleRxActivity.onError");

            }

            @Override
            public void onNext(String text) {
                System.out.println("SimpleRxActivity.onNext");
                rx1Binding.textView.setText(text);
            }
        };

    }
}
