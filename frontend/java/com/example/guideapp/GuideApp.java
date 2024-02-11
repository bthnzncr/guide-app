package com.example.guideapp;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuideApp extends Application {
   ExecutorService srv = Executors.newCachedThreadPool();
}
