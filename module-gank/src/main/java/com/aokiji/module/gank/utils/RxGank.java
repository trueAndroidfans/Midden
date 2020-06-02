package com.aokiji.module.gank.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.os.Environment.DIRECTORY_PICTURES;

public class RxGank {

    public static Observable<String> saveImage(Context context, String imageUrl, String imageName) {
        return Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
            FutureTarget<Bitmap> target = Glide.with(context).asBitmap().load(imageUrl).submit();
            emitter.onNext(target.get());
            emitter.onComplete();
        }).flatMap((Function<Bitmap, ObservableSource<File>>) bitmap -> {
            // /storage/emulated/0/Android/data/../files/Pictures/xx.jpg
            File image = new File(context.getExternalFilesDir(DIRECTORY_PICTURES), String.format("%s.jpg", imageName));
            FileOutputStream outputStream = new FileOutputStream(image);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                outputStream.flush();
            }
            return Observable.just(image);
        }).map(file -> file.getPath()).subscribeOn(Schedulers.io());
    }

}
