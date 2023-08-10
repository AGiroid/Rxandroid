package com.example.rxandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.rxandroid.databinding.ActivityMainBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  private val mainBinding: ActivityMainBinding by lazy {
      DataBindingUtil.setContentView(this,R.layout.activity_main)
  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      mainBinding.btnClick.setOnClickListener {
//          val intent=Intent(this,SecondActivity::class.java)
//          startActivity(intent)
          simpleObserver()
//          createObserver()
      }

      mainBinding.btnClick.clicks().throttleFirst(1500,TimeUnit.MILLISECONDS).subscribe{
          Log.d("Rxjava","Button Click")
      }

    }

    private fun createObserver() {
        val observable=Observable.create<String>{
            it.onNext("hello")
            it.onNext("world")
            it.onComplete()
        }
        observable.subscribe(object: Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("Rxjava","onSubscribe Method Called")
            }

            override fun onError(e: Throwable) {
                Log.d("Rxjava","onError Method Called - ${e.message}")

            }

            override fun onComplete() {
                Log.d("Rxjava","onComplete Method Called")

            }

            override fun onNext(t: String) {
                Log.d("Rxjava","onNext Method Called - $t")

            }

        })
    }

    private fun simpleObserver(){
        val list= listOf<String >("Abhishek","Meet","Iroid","Company")
        val observable=Observable.fromIterable(list)
        observable.subscribe(object: Observer<String>{
            override fun onSubscribe(d: Disposable) {
             Log.d("Rxjava","onSubscribe Method Called")
            }

            override fun onError(e: Throwable) {
                Log.d("Rxjava","onError Method Called - ${e.message}")

            }

            override fun onComplete() {
                Log.d("Rxjava","onComplete Method Called")

            }

            override fun onNext(t: String) {
                Log.d("Rxjava","onNext Method Called - $t")

            }

        })
    }
}