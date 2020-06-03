package com.aokiji.module.gank.utils;

import android.graphics.PointF;

import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.SupportRSBlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class RequestOptionsProvider {

    public static RequestOptions provide(int type) {
        RequestOptions options = new RequestOptions();
        switch (type) {
            case 0:
                options.transform(new GrayscaleTransformation());
                break;
            case 1:
                options.transform(new BlurTransformation(25));
                break;
            case 2:
                options.transform(new SupportRSBlurTransformation(25, 10));
                break;
            case 3:
                options.transform(new ToonFilterTransformation());
                break;
            case 4:
                options.transform(new SepiaFilterTransformation());
                break;
            case 5:
                options.transform(new ContrastFilterTransformation());
                break;
            case 6:
                options.transform(new InvertFilterTransformation());
                break;
            case 7:
                options.transform(new PixelationFilterTransformation());
                break;
            case 8:
                options.transform(new SketchFilterTransformation());
                break;
//            case 9:
//                options.transform(new SwirlFilterTransformation(0.5f, 1.0f, new PointF(0.5f, 0.5f)));
//                break;
            case 9:
                options.transform(new BrightnessFilterTransformation(0.5f));
                break;
            case 10:
                options.transform(new KuwaharaFilterTransformation());
                break;
            case 11:
                options.transform(new VignetteFilterTransformation(new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f));
                break;
            default:
                break;
        }

        return options;
    }

}
