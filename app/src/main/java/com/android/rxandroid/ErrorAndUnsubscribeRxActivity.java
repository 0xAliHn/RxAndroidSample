package com.android.rxandroid;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.android.rxandroid.databinding.ActivitySampleRx4Binding;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ErrorAndUnsubscribeRxActivity extends AppCompatActivity {
    ActivitySampleRx4Binding rx4Binding;
    Observer<String> myObserver;
    int mCounter = 1;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 4");
        createObserver();

        rx4Binding = DataBindingUtil.setContentView(this, R.layout.activity_sample_rx4);

        rx4Binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rx4Binding.startButton.setEnabled(false);
                rx4Binding.stopButton.setEnabled(true);

                subscription = Observable.from(rx4Binding.editText.getText().toString().split("\n"))
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String morphedString) {
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (rx4Binding.errorToggle.isChecked())
                                    mCounter = 2 / 0;
                                return mCounter++ + ". " + morphedString;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(myObserver);
            }
        });

        rx4Binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stopping Subscriptions
                if (!subscription.isUnsubscribed())
                    subscription.unsubscribe();
                rx4Binding.startButton.setEnabled(true);
                rx4Binding.stopButton.setEnabled(false);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stopping Subscriptions
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    private void createObserver() {
        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                rx4Binding.startButton.setEnabled(true);
                rx4Binding.stopButton.setEnabled(false);
            }

            //Handling Errors
            //An error anywhere in between the source Observable and target Observer
            // gets propagated directly to the Observer’s onError() method.
            @Override
            public void onError(Throwable e) {
                Toast.makeText(ErrorAndUnsubscribeRxActivity.this,
                        "A \"" + e.getMessage() + "\" Error has been caught",
                        Toast.LENGTH_LONG).show();
                rx4Binding.startButton.setEnabled(true);
                rx4Binding.stopButton.setEnabled(false);
            }

            @Override
            public void onNext(String string) {
                rx4Binding.textView.setText(rx4Binding.textView.getText() + "\n" + string);
            }
        };

    }
}
